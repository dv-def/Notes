package com.example.notes.ui.list

import androidx.recyclerview.widget.RecyclerView
import com.example.notes.databinding.ItemNoteBinding
import com.example.notes.domain.Note

class NotesViewHolder(
    private val binding: ItemNoteBinding,
    private val onItemClick: (Note) -> Unit,
    private val onClickDelete: (Note, Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(note: Note) {
        binding.itemNoteTitle.text = note.title
        binding.itemNoteDescription.text = note.description
        binding.itemNoteBtnDelete.setOnClickListener {
            onClickDelete.invoke(note, adapterPosition)
        }

        itemView.setOnClickListener {
            onItemClick.invoke(note)
        }
    }
}