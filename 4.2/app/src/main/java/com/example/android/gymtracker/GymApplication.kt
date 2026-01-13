package com.example.android.gymtracker

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class GymApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    // Datenbank und Repository werden erst erstellt, wenn sie gebraucht werden (lazy)
    val database by lazy { GymDatabase.getDatabase(this) }
    val repository by lazy { GymRepository(database.gymDao()) }
}