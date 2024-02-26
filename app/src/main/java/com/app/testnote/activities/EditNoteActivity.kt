package com.app.testnote.activities

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.app.testnote.R
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import com.app.testnote.database.NoteApplication
import com.app.testnote.databinding.ActivityEditBinding
import com.app.testnote.models.Note
import com.app.testnote.viewmodels.NotesViewModel
import com.app.testnote.viewmodels.NotesViewModelFactory
import java.util.Locale

class EditNoteActivity : AppCompatActivity(){

    private lateinit var binding: ActivityEditBinding

    private val notesViewModel: NotesViewModel by viewModels {
        NotesViewModelFactory((this.application as NoteApplication).notesRepository)
    }

    companion object {
        var note : Note? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityEditBinding.inflate(layoutInflater)
        val rootView = binding.root
        setContentView(rootView)

        initView()
    }

    private fun initView() {

        binding.tIETEditTitle.setText(note!!.title)
        binding.tIETEditContent.setText(note!!.content)

        binding.ivBack.setOnClickListener {
            onBackPressed()
        }

        binding.ivSave.setOnClickListener {
            val note = prepareDataForViewModel()

            if(note.title.isEmpty() || note.content.isEmpty()) {
                Snackbar.make(binding.root, resources.getString(R.string.enter_title_note), Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            notesViewModel.editNote(note)
            binding.tIETEditTitle.clearFocus()
            binding.tIETEditContent.clearFocus()
            onBackPressed()
        }
    }

    private fun prepareDataForViewModel(): Note {
        val title = binding.tIETEditTitle.text?.trim().toString()
        val content = binding.tIETEditContent.text?.trim().toString()

        return Note(note!!.noteId, title, content)
    }

}