package com.app.testnote.recyclerview

import com.app.testnote.models.Note

interface RVNotesListOnItemClickListener {
    fun onItemClick(item: Note)
}