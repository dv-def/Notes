package com.example.notes.data

import androidx.room.*
import io.reactivex.rxjava3.core.Single

@Dao
interface NoteDao {
    @Query("SELECT * FROM ${NoteEntity.TABLE_NAME}")
    fun getAll(): Single<List<NoteEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: NoteEntity): Long

    @Update
    fun update(note: NoteEntity): Int

    @Delete
    fun delete(note: NoteEntity): Int
}