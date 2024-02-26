package com.app.testnote.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.app.testnote.recyclerview.RVNotesListOnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.app.testnote.databinding.ItemNotesBinding
import com.app.testnote.models.Note
import com.app.testnote.recyclerview.RVNotesDelete

class RvNotesAdapter(
    private var notesList: MutableList<Note> = mutableListOf(),
    private val onItemClickListener: RVNotesListOnItemClickListener,
    private val onItemDeleteListener: RVNotesDelete
) : RecyclerView.Adapter<RvNotesAdapter.RvNotesViewHolder>() {

    inner class RvNotesViewHolder(binding: ItemNotesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val tvTitle: MaterialTextView = binding.tvTitle
        private val tvContent: TextView = binding.tvContent
        val ivDelete: ImageView = binding.ivDelete


        fun bind(note: Note) {
            tvTitle.text = note.title
            tvContent.text = note.content

        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: MutableList<Note>) {
        notesList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): RvNotesAdapter.RvNotesViewHolder {
        val itemBinding =
            ItemNotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RvNotesViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RvNotesViewHolder, position: Int) {

        val item = notesList[position]

        holder.bind(item)

        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(item)
        }

        holder.ivDelete.setOnClickListener {
            onItemDeleteListener.onDeleteItem(item)
        }

    }

    override fun getItemId(position: Int): Long {
        return notesList[position].noteId
    }

    override fun getItemCount() = notesList.size

}