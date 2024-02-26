package com.app.testnote.repositories

import androidx.annotation.WorkerThread
import com.app.testnote.models.Note
import com.app.testnote.database.NotesDao
import kotlinx.coroutines.flow.Flow

class NotesRepository(private val notesDao: NotesDao) {

    val getAllNotes: Flow<MutableList<Note>> = notesDao.getAllNotes()

    @WorkerThread
    suspend fun addNewNote(note: Note) {
        notesDao.addNewNote(note)
    }

    @WorkerThread
    suspend fun editNote(note: Note) {
        notesDao.editNote(note)
    }

    @WorkerThread
    suspend fun deleteNote(note: Note) {
        notesDao.deleteNote(note)
    }

}