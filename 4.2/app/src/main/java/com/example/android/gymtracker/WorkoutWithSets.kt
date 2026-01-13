package com.example.android.gymtracker

import androidx.room.Embedded
import androidx.room.Relation

data class WorkoutWithSets(
    // @Embedded bedeutet: Nimm alle Felder der Workout-Tabelle (Name, Datum) und pack sie hier rein
    @Embedded val workout: Workout,

    // @Relation sagt Room: "Suche in der ExerciseSet-Tabelle alle Einträge,
    // wo workoutOwnerId (Child) == workoutId (Parent) ist."
    @Relation(
        parentColumn = "workoutId",
        entityColumn = "workoutOwnerId"
    )
    val sets: List<ExerciseSet>
)
