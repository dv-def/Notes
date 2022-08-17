package com.example.notes.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.databinding.ItemNoteBinding
import com.example.notes.domain.Note

class NotesAdapter(
    private val onItemClick: (Note) -> Unit,
    private val onClickDelete: (Note, Int) -> Unit
) : RecyclerView.Adapter<NotesViewHolder>() {
    private val notesList = mutableListOf<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            binding = ItemNoteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onItemClick = onItemClick,
            onClickDelete = onClickDelete
        )
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(notesList[position])
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    fun updateData(newNotes: List<Note>) {
        notesList.clear()
        notesList.addAll(newNotes)
        notifyDataSetChanged()
    }

    fun delete(note: Note, pos: Int) {
        notesList.remove(note)
        notifyItemRemoved(pos)
    }
}