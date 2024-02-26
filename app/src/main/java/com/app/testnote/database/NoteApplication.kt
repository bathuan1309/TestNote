package com.app.testnote.database

import android.app.Application
import android.content.Context
import com.app.testnote.repositories.NotesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class NoteApplication: Application() {


    val notesDataBase by lazy { NotesDataBase.getDatabase(this) }
    val notesRepository by lazy { NotesRepository(notesDataBase.notesDAO()) }
}