package com.example.android.gymtracker

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_table")
data class Workout(
    @PrimaryKey(autoGenerate = true)
    val workoutId: Long = 0,

    val name: String,
    val date: String
)