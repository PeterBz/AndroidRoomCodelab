package com.example.android.gymtracker

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private val gymViewModel: GymViewModel by viewModels {
        GymViewModelFactory((application as GymApplication).repository)
    }

    private var currentWorkoutId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etExercise = findViewById<EditText>(R.id.etExercise)
        val etWeight = findViewById<EditText>(R.id.etWeight)
        val etReps = findViewById<EditText>(R.id.etReps)
        val btnNewWorkout = findViewById<Button>(R.id.btnNewWorkout)
        val btnAddSet = findViewById<Button>(R.id.btnAddSet)
        val tvOutput = findViewById<TextView>(R.id.tvDatabaseOutput)

        // --- HIER IST DIE ÄNDERUNG (Datum formatieren) ---
        gymViewModel.allWorkouts.observe(this) { workoutsWithSetsList ->
            val sb = StringBuilder()

            // Wir erstellen einen Formatierer (Tag.Monat.Jahr Stunde:Minute)
            val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.GERMANY)

            for (entry in workoutsWithSetsList) {
                val w = entry.workout
                val sets = entry.sets

                // Versuchen, den String in ein echtes Datum umzuwandeln
                val formattedDate = try {
                    val netDate = Date(w.date.toLong())
                    sdf.format(netDate)
                } catch (e: Exception) {
                    w.date // Falls es kein Timestamp war, zeig einfach den Originaltext
                }

                sb.append("=========================\n")
                sb.append("WORKOUT ID: ${w.workoutId}\n")
                // Hier nutzen wir jetzt das schöne Datum:
                sb.append("Name: ${w.name} ($formattedDate)\n")
                sb.append("-------------------------\n")

                if (sets.isEmpty()) {
                    sb.append("   (Noch keine Übungen)\n")
                } else {
                    for (s in sets) {
                        sb.append("   -> ${s.exerciseName}: ${s.weight}kg x ${s.reps}\n")
                    }
                }
                sb.append("\n")
            }
            tvOutput.text = sb.toString()
        }
        // -------------------------------------------------

        btnNewWorkout.setOnClickListener {
            val newWorkout = Workout(
                name = "Training",
                // Wir speichern weiterhin den Timestamp, damit man gut sortieren kann
                date = System.currentTimeMillis().toString()
            )

            gymViewModel.insertWorkout(newWorkout) { newId ->
                currentWorkoutId = newId
                runOnUiThread {
                    Toast.makeText(this, "Workout gestartet! ID: $newId", Toast.LENGTH_SHORT).show()
                    btnAddSet.isEnabled = true
                }
            }
        }

        btnAddSet.setOnClickListener {
            val uebung = etExercise.text.toString()
            val gewichtStr = etWeight.text.toString()
            val repsStr = etReps.text.toString()

            if (uebung.isNotEmpty() && gewichtStr.isNotEmpty()) {
                val gewicht = gewichtStr.toDouble()
                val reps = repsStr.toInt()

                val newSet = ExerciseSet(
                    exerciseName = uebung,
                    weight = gewicht,
                    reps = reps,
                    workoutOwnerId = currentWorkoutId
                )

                gymViewModel.insertSet(newSet)

                etExercise.text.clear()
                etWeight.text.clear()
                etReps.text.clear()
            }
        }
    }
}