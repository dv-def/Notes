package com.example.notes.ui.edit

import com.example.notes.domain.Note

class NoteValidator {
    fun validate(note: Note): ValidationResult {
        if (!validateTitle(note.title)) {
            return ValidationResult(
                success = false,
                message = "Заполните название",
                errorType = ValidationErrorType.TITLE
            )
        }

        if (!validateDescription(note.description)) {
            return ValidationResult(
                success = false,
                message = "Заполните содержание",
                errorType = ValidationErrorType.DESCRIPTION
            )
        }

        return ValidationResult(
            success = true
        )
    }

    private fun validateTitle(title: String): Boolean {
        return title.isNotBlank()
    }

    private fun validateDescription(description: String): Boolean {
        return description.isNotBlank()
    }
}