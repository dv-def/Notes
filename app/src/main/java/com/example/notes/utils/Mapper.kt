package com.example.notes.utils

import com.example.notes.data.NoteEntity
import com.example.notes.domain.Note

fun Note.toEntity(): NoteEntity {
    return NoteEntity(
        id = this.id,
        title = this.title,
        description = this.description
    )
}

fun NoteEntity.toNote(): Note {
    return Note(
        id = this.id,
        title = this.title,
        description = this.description
    )
}