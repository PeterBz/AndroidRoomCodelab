package com.example.android.roomwordssample

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class WordViewModel(private val repository: WordRepository) : ViewModel() {

    // Wir wandeln den Flow aus dem Repository in LiveData um.
    // Die UI (Activity) beobachtet dieses LiveData-Objekt.
    // Sobald sich Daten ändern, wird die UI automatisch benachrichtigt.
    val allWords: LiveData<List<Word>> = repository.allWords.asLiveData()

    // Diese Funktion wird von der UI aufgerufen, um ein neues Wort zu speichern.
    // viewModelScope.launch sorgt dafür, dass das im Hintergrund passiert (nicht im UI-Thread).
    fun insert(word: Word) = viewModelScope.launch {
        repository.insert(word)
    }
}

// Eine Factory-Klasse wird benötigt, um das ViewModel mit Argumenten (hier: Repository) zu erstellen.
// Das ist Standard-Code in Android, wenn das ViewModel Abhängigkeiten hat.
class WordViewModelFactory(private val repository: WordRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WordViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}