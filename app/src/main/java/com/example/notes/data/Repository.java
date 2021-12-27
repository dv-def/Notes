package com.example.notes.data;

import java.util.ArrayList;

public interface Repository {
    int create(Note note);

    Note read(int id);

    void update(Note note);

    void delete(int id);

    ArrayList<Note> getAll();
}
