package com.example.android.roomwordssample

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class WordsApplication : Application() {

    // Wir erstellen einen Scope, der so lange lebt wie die App selbst.
    // Das ist nützlich, wenn wir später Datenbankoperationen haben, die nicht abgebrochen werden sollen.
    val applicationScope = CoroutineScope(SupervisorJob())

    // "lazy" bedeutet: Die Datenbank wird erst erstellt, wenn wir sie zum ersten Mal wirklich brauchen.
    // Das spart Zeit beim App-Start.
    val database by lazy { WordRoomDatabase.getDatabase(this) }

    // Auch das Repository wird nur einmal erstellt.
    val repository by lazy { WordRepository(database.wordDao()) }
}