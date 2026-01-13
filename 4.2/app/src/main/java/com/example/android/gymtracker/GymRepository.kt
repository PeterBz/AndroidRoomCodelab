package com.example.android.gymtracker

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class GymRepository(private val gymDao: GymDao) {

    // Hier bekommen wir die kombinierte Liste (Workout + Sätze) als Live-Stream.
    // Room kümmert sich automatisch um Updates, wenn neue Sätze dazukommen.
    val allWorkouts: Flow<List<WorkoutWithSets>> = gymDao.getWorkoutsWithSets()

    // 1. Workout speichern
    // Wir nutzen "suspend", damit die UI nicht einfriert.
    // Wir geben ein Long zurück (die neue ID), damit wir sie für die Sätze nutzen können.
    @WorkerThread
    suspend fun insertWorkout(workout: Workout): Long {
        return gymDao.insertWorkout(workout)
    }

    // 2. Satz speichern
    @WorkerThread
    suspend fun insertSet(exerciseSet: ExerciseSet) {
        gymDao.insertSet(exerciseSet)
    }
}