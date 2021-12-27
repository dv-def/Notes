package com.example.notes.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.notes.R;
import com.example.notes.data.note.Note;
import com.example.notes.data.note.NoteRepository;
import com.example.notes.data.note.Repository;
import com.example.notes.data.note.adapter.NoteAdapter;

public class MainActivity extends AppCompatActivity implements NoteAdapter.OnClickNoteListener {
    public static final String NOTE_EXTRA = "NOTE_EXTRA";

    private final Repository repository = new NoteRepository();
    private RecyclerView rvNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTestNotes();

        NoteAdapter adapter = new NoteAdapter();
        adapter.setNotes(repository.getAll());
        adapter.setOnClickNoteListener(this);

        rvNotes = findViewById(R.id.rv_notes);
        rvNotes.setAdapter(adapter);
        rvNotes.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initTestNotes() {
        repository.create(new Note("Title 1", "Desc 1"));
        repository.create(new Note("Title 2", "Desc 2"));
        repository.create(new Note("Title 3", "Desc 3"));
        repository.create(new Note("Title 4", "Desc 4"));
        repository.create(new Note("Title 5", "Desc 5"));
        repository.create(new Note("Title 6", "Desc 6"));
        repository.create(new Note("Title 7", "Desc 7"));
        repository.create(new Note("Title 8", "Desc 8"));
    }

    @Override
    public void onClickNote(Note note) {
        Intent editIntent = new Intent(this, EditNoteActivity.class);
        editIntent.putExtra(NOTE_EXTRA, note);
        startActivity(editIntent);
    }
}