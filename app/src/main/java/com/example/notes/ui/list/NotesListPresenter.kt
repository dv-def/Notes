package com.example.notes.ui.list

import com.example.notes.domain.Note
import com.example.notes.domain.Repository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy

class NotesListPresenter(
    private val repository: Repository
) {
    private var view: NotesListView? = null

    fun attach(view: NotesListView) {
        this.view = view
    }

    fun detach() {
        view = null
    }

    fun onGetNotes() {
        view?.showLoading()
        repository.getAll()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = { notesList ->
                    view?.showData(notesList)
                    view?.hideLoading()
                },
                onError = {
                    view?.showError("Не удалось загрузить список заметок")
                    view?.hideLoading()
                }
            )
    }

    fun onClickDelete(note: Note, pos: Int) {
        repository.delete(note)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    view?.deleteNote(note, pos)
                },
                onError = {
                    view?.showError(it.message.toString())
                }
            )
    }
}