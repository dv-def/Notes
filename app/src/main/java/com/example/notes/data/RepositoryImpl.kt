package com.example.notes.data

import com.example.notes.domain.Note
import com.example.notes.domain.Repository
import com.example.notes.utils.toEntity
import com.example.notes.utils.toNote
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class RepositoryImpl(
    private val dao: NoteDao
) : Repository {
    override fun getAll(): Single<List<Note>> {
        return Single.create { emitter ->
            dao.getAll()
                .map { it.map { entity -> entity.toNote() } }
                .subscribeBy(
                onSuccess = { notesList ->
                    emitter.onSuccess(notesList)
                },
                onError = {
                    emitter.onError(it)
                }
            )
        }.subscribeOn(Schedulers.io())
    }

    override fun insert(note: Note): Single<Unit> {
        return Single.create { emitter ->
            val result = dao.insert(note.toEntity())
            if (result > 0) {
                emitter.onSuccess(Unit)
            } else {
                emitter.onError(Exception("Не удалось сохранить заметку"))
            }
        }.subscribeOn(Schedulers.io())
    }

    override fun update(note: Note): Single<Unit> {
        return Single.create { emitter ->
            val result = dao.update(note.toEntity())
            if (result > 0) {
                emitter.onSuccess(Unit)
            } else {
                emitter.onError(Exception("Не удалось обновить заметку"))
            }
        }.subscribeOn(Schedulers.io())
    }

    override fun delete(note: Note): Single<Unit> {
        return Single.create { emitter ->
            val result = dao.delete(note.toEntity())
            if (result > 0) {
                emitter.onSuccess(Unit)
            } else {
                emitter.onError(Exception("Не удалось удалить заметку"))
            }
        }.subscribeOn(Schedulers.io())
    }
}