package com.example.notes.ui.list

import com.example.notes.domain.Note

interface NotesListView {
    fun showData(notesList: List<Note>)
    fun showError(message: String)
    fun showLoading()
    fun hideLoading()
    fun deleteNote(note: Note, pos: Int)
}