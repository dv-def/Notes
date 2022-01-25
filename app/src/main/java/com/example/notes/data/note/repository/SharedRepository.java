package com.example.notes.data.note.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.notes.data.note.Note;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedRepository implements Repository {
    private final SharedPreferences preferences;
    private final Gson gson;

    private final String PREFS_KEY = "NOTE";
    private static SharedRepository instance;

    private SharedRepository(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        gson = new Gson();
    }

    public static SharedRepository getInstance(Context context) {
        if (instance == null) {
            instance = new SharedRepository(context);
        }

        return instance;
    }

    @Override
    public int create(Note note) {
        List<Note> notes = deserialize();

        int id = notes.size() + 1;
        note.setId(id);
        notes.add(note);

        serialize(notes);

        return id;
    }

    @Override
    public Note read(int id) {
        List<Note> notes = deserialize();

        for (Note note : notes) {
            if (note.getId() == id) {
                return note;
            }
        }

        return null;
    }

    @Override
    public void update(Note note) {
        List<Note> notes = deserialize();

        for (Note n : notes) {
            if (n.equals(note)) {
                n.setTitle(note.getTitle());
                n.setDescription(note.getDescription());
                n.setDate(note.getDate());
                n.setImportant(note.getImportant());
                return;
            }
        }

        serialize(notes);
    }

    @Override
    public void delete(int id) {
        List<Note> notes = deserialize();

        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getId() == id) {
                notes.remove(notes.get(i));
                break;
            }
        }

        serialize(notes);
    }

    @Override
    public List<Note> getAll() {
        return deserialize();
    }

    private void serialize(List<Note> notes) {
        String json = gson.toJson(notes);
        writeInPrefs(json);
    }

    private List<Note> deserialize() {
        Type listType = new TypeToken<List<Note>>(){}.getType();
        List<Note> notes = gson.fromJson(preferences.getString(PREFS_KEY, ""), listType);

        if (notes == null) {
            notes = new ArrayList<>();
        }

        return notes;
    }

    private void writeInPrefs(String str) {
        preferences.edit().putString(PREFS_KEY, str).apply();
    }
}
