package com.example.notes.data;

import java.util.ArrayList;

public class NoteRepository implements Repository {
    private final ArrayList<Note> notes = new ArrayList<>();

    @Override
    public int create(Note note) {
        int lastId;
        if (notes.size() == 0) {
            lastId = 0;
        } else {
            lastId = notes.get(notes.size() - 1).getId();
        }

        note.setId(lastId + 1);
        notes.add(note);
        return note.getId();
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
}
