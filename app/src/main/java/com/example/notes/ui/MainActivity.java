package com.example.notes.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.notes.R;
import com.example.notes.data.note.Note;
import com.example.notes.ui.note.NoteListFragment;

public class MainActivity extends AppCompatActivity implements NoteListFragment.NoteListController {
    private final int CONTAINER_ID = R.id.main_activity_fragment_host;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(CONTAINER_ID, new NoteListFragment())
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return true;
    }

    @Override
    public void onNoteClicked(Note note) {

    }
}