package com.example.notes.ui.edit

interface EditNoteView {
    fun showTitleError(message: String)
    fun showDescriptionError(message: String)
    fun showDataError(message: String)
    fun success()
}