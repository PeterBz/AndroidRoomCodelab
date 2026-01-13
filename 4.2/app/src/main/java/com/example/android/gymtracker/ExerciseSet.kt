package com.example.android.gymtracker

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "exercise_set_table",
    foreignKeys = [ForeignKey(
        entity = Workout::class,          // Gehört zu einem Workout
        parentColumns = ["workoutId"],    // Verknüpft mit der ID des Workouts
        childColumns = ["workoutOwnerId"],// Über unser Feld "workoutOwnerId"
        onDelete = ForeignKey.CASCADE     // Wenn das Workout gelöscht wird, sind auch die Sätze weg
    )]
)
data class ExerciseSet(
    @PrimaryKey(autoGenerate = true)
    val setId: Long = 0,

    val exerciseName: String, // z.B. "Squat" oder "Brustpresse"
    val weight: Double,       // z.B. 100.0 (kg)
    val reps: Int,            // z.B. 8 (Wiederholungen)

    // Der Fremdschlüssel: Zu welchem Workout gehört dieser Satz?
    val workoutOwnerId: Long
)