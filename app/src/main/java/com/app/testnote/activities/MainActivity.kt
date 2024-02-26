package com.app.testnote.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ActionMode
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.app.testnote.R
import com.app.testnote.adapters.RvNotesAdapter
import com.app.testnote.database.NoteApplication
import com.app.testnote.databinding.ActivityMainBinding
import com.app.testnote.models.Note
import com.app.testnote.recyclerview.RVNotesDelete
import com.app.testnote.recyclerview.RVNotesListOnItemClickListener
import com.app.testnote.viewmodels.NotesViewModel
import com.app.testnote.viewmodels.NotesViewModelFactory

class MainActivity : AppCompatActivity(), RVNotesListOnItemClickListener, RVNotesDelete {

    private lateinit var binding: ActivityMainBinding

    private lateinit var notesListAdapter: RvNotesAdapter
    private var notesListForRV = mutableListOf<Note>()
    private var notesListFromViewModel = mutableListOf<Note>()

    private val notesViewModel: NotesViewModel by viewModels {
        NotesViewModelFactory((this.application as NoteApplication).notesRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val rootView = binding.root
        setContentView(rootView)

        setUpRecyclerView()
        getNotesFromViewModel()

        clickAdd()
    }

   private fun clickAdd() {
       binding.ivAdd.setOnClickListener {
           val intent = Intent(this@MainActivity, AddNoteActivity::class.java)
           startActivity(intent)
       }
   }

    private fun setUpRecyclerView() {

        notesListAdapter = RvNotesAdapter(notesListForRV, this, this)
        binding.rvNotes.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvNotes.setHasFixedSize(true)
        binding.rvNotes.adapter = notesListAdapter

    }

    private fun getNotesFromViewModel() {
        notesViewModel.getAllNotes.observe(this, Observer { noteList ->
            noteList?.let {
                notesListForRV.clear()
                notesListFromViewModel.clear()
                it.forEach { note ->
                    notesListFromViewModel.add(
                        Note(
                            noteId = note.noteId,
                            title = note.title,
                            content = note.content,
                        )
                    )
                }
                addNotesToRV(notesListFromViewModel)
                showList()
            }

        })
    }

    private fun showList() {
        if(notesListFromViewModel.isEmpty()) {
            binding.rvNotes.visibility = View.GONE
            binding.tvNoNote.visibility = View.VISIBLE
        } else{
            binding.rvNotes.visibility = View.VISIBLE
            binding.tvNoNote.visibility = View.GONE
        }
    }

    private fun addNotesToRV(noteList: MutableList<Note>) {
        noteList.forEach { note ->
            notesListForRV.add(
                Note(
                    noteId = note.noteId,
                    title = note.title,
                    content = note.content
                )
            )
            notesListAdapter.updateList(notesListForRV)
        }
    }

    override fun onItemClick(item: Note) {
        val intent = Intent(this@MainActivity, EditNoteActivity::class.java)
        EditNoteActivity.note = item
        startActivity(intent)
    }


    override fun onDeleteItem(item: Note) {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.delete_selected_items))
            .setMessage(getString(R.string.delete_item_message))
            .setPositiveButton(resources.getString(R.string.yes)) { dialog, _ ->
                notesViewModel.deleteNote(item)
                Snackbar.make(binding.root, resources.getString(R.string.note_deleted), Snackbar.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            .setNegativeButton(resources.getString(R.string.no)) { dialog, _ ->
                dialog.dismiss()
            }.show()
    }
}