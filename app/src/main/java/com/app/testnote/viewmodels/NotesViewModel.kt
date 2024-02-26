package com.app.testnote.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.app.testnote.models.Note
import com.app.testnote.repositories.NotesRepository
import kotlinx.coroutines.launch

class NotesViewModel(private val notesRepository: NotesRepository) : ViewModel() {

    val getAllNotes: LiveData<MutableList<Note>> = notesRepository.getAllNotes.asLiveData()


    fun addNewNote(note: Note) = viewModelScope.launch {
        notesRepository.addNewNote(note)
    }

    fun editNote(note: Note) = viewModelScope.launch {
        notesRepository.editNote(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        notesRepository.deleteNote(note)
    }

}

class NotesViewModelFactory(private val notesRepository: NotesRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NotesViewModel(notesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}