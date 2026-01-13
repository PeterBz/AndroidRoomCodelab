package com.example.android.gymtracker

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Hier registrieren wir BEIDE Tabellen: Workout und ExerciseSet
@Database(entities = [Workout::class, ExerciseSet::class], version = 1, exportSchema = false)
abstract class GymDatabase : RoomDatabase() {

    abstract fun gymDao(): GymDao

    companion object {
        @Volatile
        private var INSTANCE: GymDatabase? = null

        fun getDatabase(context: Context): GymDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GymDatabase::class.java,
                    "gym_database" // Der Name der Datei auf dem Handy
                )
                    // Diese Option löscht die Datenbank, wenn du die Tabellenstruktur änderst (nur für Entwicklung gut!)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}