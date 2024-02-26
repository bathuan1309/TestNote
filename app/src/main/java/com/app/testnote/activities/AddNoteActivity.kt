package com.app.testnote.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.app.testnote.R
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.app.testnote.database.NoteApplication
import com.app.testnote.databinding.ActivityAddBinding
import com.app.testnote.models.Note
import com.app.testnote.viewmodels.NotesViewModel
import com.app.testnote.viewmodels.NotesViewModelFactory

class AddNoteActivity : AppCompatActivity(){

    private lateinit var binding: ActivityAddBinding

    private val notesViewModel: NotesViewModel by viewModels {
        NotesViewModelFactory((this.application as NoteApplication).notesRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityAddBinding.inflate(layoutInflater)
        val rootView = binding.root
        setContentView(rootView)

        initView()

    }

    private fun initView() {

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        binding.ivAdd.setOnClickListener {
            val note = prepareDataForViewModel()

            if(note.title.isEmpty() || note.content.isEmpty()) {
                Snackbar.make(binding.root, resources.getString(R.string.enter_title_note), Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            notesViewModel.addNewNote(note)
            binding.tIETAddTitle.clearFocus()
            binding.tIETAddContent.clearFocus()
            onBackPressed()
        }
    }


    private fun prepareDataForViewModel(): Note {
        val noteTitle = binding.tIETAddTitle.text?.trim().toString()
        val noteContent = binding.tIETAddContent.text?.trim().toString()

        return Note(0, noteTitle, noteContent)
    }


}