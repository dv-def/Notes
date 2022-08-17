package com.example.notes.domain

import io.reactivex.rxjava3.core.Single

interface Repository {
    fun getAll(): Single<List<Note>>
    fun insert(note: Note): Single<Unit>
    fun update(note: Note): Single<Unit>
    fun delete(note: Note): Single<Unit>
}