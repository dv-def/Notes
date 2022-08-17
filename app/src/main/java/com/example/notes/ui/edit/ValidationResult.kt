package com.example.notes.ui.edit

data class ValidationResult(
    val success: Boolean,
    val message: String = "",
    val errorType: ValidationErrorType? = null
)

enum class ValidationErrorType {
    TITLE, DESCRIPTION
}