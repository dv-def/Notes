package com.example.notes.ui.edit

import com.example.notes.domain.Note
import com.example.notes.domain.Repository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy

class EditNotePresenter(
    private val repository: Repository
) {
    private var view: EditNoteView? = null
    private val validator = NoteValidator()

    fun attach(view: EditNoteView) {
        this.view = view
    }

    fun detach() {
        this.view = null
    }

    fun saveNote(note: Note) {
        val validationResult = validator.validate(note)

        if (!validationResult.success) {
            validationResult.errorType?.let { type ->
                when (type) {
                    ValidationErrorType.TITLE -> {
                        view?.showTitleError(validationResult.message)
                    }
                    ValidationErrorType.DESCRIPTION -> {
                        view?.showDescriptionError(validationResult.message)

                    }
                }
            }

            return
        }

        repository.insert(note)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    view?.success()
                },
                onError = {
                    view?.showDataError(it.message.toString())
                }
            )
    }

    fun updateNote(note: Note) {
        repository.update(note)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onSuccess = {
                    view?.success()
                },
                onError = {
                    view?.showDataError(it.message.toString())
                }
            )
    }
}