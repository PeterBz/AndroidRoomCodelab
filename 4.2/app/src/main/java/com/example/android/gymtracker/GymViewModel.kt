package com.example.android.gymtracker

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class GymViewModel(private val repository: GymRepository) : ViewModel() {

    // 1. LiveData für die UI: Eine Liste von Workouts MIT ihren Sätzen
    // Sobald du einen neuen Satz speicherst, aktualisiert sich diese Liste automatisch.
    val allWorkouts: LiveData<List<WorkoutWithSets>> = repository.allWorkouts.asLiveData()

    // 2. Workout speichern (mit Rückgabe der ID)
    // Wir übergeben eine kleine Funktion "onResult", die aufgerufen wird, sobald die ID da ist.
    fun insertWorkout(workout: Workout, onResult: (Long) -> Unit) = viewModelScope.launch {
        val newId = repository.insertWorkout(workout)
        onResult(newId) // Hier geben wir die ID an die UI zurück
    }

    // 3. Satz speichern
    fun insertSet(exerciseSet: ExerciseSet) = viewModelScope.launch {
        repository.insertSet(exerciseSet)
    }
}

// Die Standard-Factory, genau wie in Aufgabe 1
class GymViewModelFactory(private val repository: GymRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GymViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GymViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}