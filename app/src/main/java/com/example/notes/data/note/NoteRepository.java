package com.example.notes.data.note;

import java.util.ArrayList;
import java.util.List;

public class NoteRepository implements Repository {
    private final List<Note> notes = new ArrayList<>();
    private int counter;

    private static NoteRepository instance;

    public static NoteRepository getInstance() {
        if (instance == null) {
            instance = new NoteRepository();
        }

        return instance;
    }

    private NoteRepository(){
        this.initTestNotes();
    }

    @Override
    public int create(Note note) {
        int id = ++counter;
        note.setId(id);
        notes.add(note);
        return id;
    }

    @Override
    public Note read(int id) {
        for (Note note : notes) {
            if (note.getId() == id) {
                return note;
            }
        }

        return null;
    }

    @Override
    public void update(Note note) {
        for (Note n : notes) {
            if (n.getId().equals(note.getId())) {
                n.setTitle(note.getTitle());
                n.setDescription(note.getDescription());
                break;
            }
        }
    }

    @Override
    public void delete(int id) {
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getId() == id) {
                notes.remove(notes.get(i));
                break;
            }
        }
    }

    @Override
    public List<Note> getAll() {
        return notes;
    }

    private void initTestNotes() {
        this.create(new Note("Title 1", "Desc 1", "", "01-01-2022"));
        this.create(new Note("Title 2", "Desc 2", "", "01-01-2022"));
        this.create(new Note("Title 3", "Desc 3", "", "01-01-2022"));
        this.create(new Note("Title 4", "Desc 4", "", "01-01-2022"));
        this.create(new Note("Title 5", "Desc 5", "", "01-01-2022"));
        this.create(new Note("Title 6", "Desc 6", "", "01-01-2022"));
        this.create(new Note("Title 7", "Desc 7", "", "01-01-2022"));
        this.create(new Note("Title 8", "Desc 8", "", "01-01-2022"));
    }
}
