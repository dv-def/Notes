package com.example.notes.data.note;

import java.util.ArrayList;

public class NoteRepository implements Repository {
    private final ArrayList<Note> notes = new ArrayList<>();
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
    public ArrayList<Note> getAll() {
        return notes;
    }

    private void initTestNotes() {
        this.create(new Note("Title 1", "Desc 1", ""));
        this.create(new Note("Title 2", "Desc 2", ""));
        this.create(new Note("Title 3", "Desc 3", ""));
        this.create(new Note("Title 4", "Desc 4", ""));
        this.create(new Note("Title 5", "Desc 5", ""));
        this.create(new Note("Title 6", "Desc 6", ""));
        this.create(new Note("Title 7", "Desc 7", ""));
        this.create(new Note("Title 8", "Desc 8", ""));
    }
}
