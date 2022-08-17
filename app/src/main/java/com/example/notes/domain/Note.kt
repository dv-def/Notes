package com.example.notes.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Note(
    val id: Int? = null,
    val title: String,
    val description: String,
): Parcelable
