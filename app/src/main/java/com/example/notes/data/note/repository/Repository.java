package com.example.notes.data.note.repository;

import com.example.notes.data.note.Note;

import java.util.ArrayList;
import java.util.List;

public interface Repository {
    int create(Note note);

    Note read(int id);

    void update(Note note);

    void delete(int id);

    List<Note> getAll();
}
