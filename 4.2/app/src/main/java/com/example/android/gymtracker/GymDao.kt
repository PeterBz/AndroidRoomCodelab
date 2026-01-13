package com.example.android.gymtracker

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface GymDao {

    // 1. Ein neues Workout anlegen (gibt die neue ID zurück, das ist wichtig!)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workout: Workout): Long

    // 2. Einen Satz zu einem Workout hinzufügen
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSet(exerciseSet: ExerciseSet)

    // 3. Alles abrufen: Workouts INKLUSIVE ihrer Sätze
    // @Transaction ist WICHTIG, weil Room hier intern zwei Abfragen macht und diese atomar sein müssen
    @Transaction
    @Query("SELECT * FROM workout_table ORDER BY workoutId DESC")
    fun getWorkoutsWithSets(): Flow<List<WorkoutWithSets>>
}