package com.example.notes

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.example.notes.data.AppDataBase
import com.example.notes.data.RepositoryImpl
import com.example.notes.domain.Repository

class App : Application() {
    private val appDataBase by lazy {
        synchronized(AppDataBase::class) {
            Room.databaseBuilder(
                applicationContext,
                AppDataBase::class.java,
                AppDataBase.DB_NAME
            ).build()
        }
    }

    private val noteDao by lazy {
        appDataBase.noteDao()
    }

    val repository: Repository by lazy { RepositoryImpl(noteDao) }

}

fun Fragment.app(): App {
    return requireContext().applicationContext as App
}