package com.example.android.roomwordssample

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

// Wir übergeben das DAO im Konstruktor, nicht die ganze Datenbank
class WordRepository(private val wordDao: WordDao) {

    // Room führt alle Queries mit Flow automatisch im Hintergrund aus.
    // Wir erhalten hier "live" Updates, wenn sich Daten ändern.
    val allWords: Flow<List<Word>> = wordDao.getAlphabetizedWords()

    // Standardmäßig muss Room Datenbankoperationen außerhalb des Main-Threads ausführen.
    // "suspend" sagt Kotlin, dass diese Funktion angehalten und später fortgesetzt werden kann (Coroutines).
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }
}