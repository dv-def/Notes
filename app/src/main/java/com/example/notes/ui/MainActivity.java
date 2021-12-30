package com.example.notes.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.notes.R;
import com.example.notes.data.note.Note;
import com.example.notes.ui.note.EditNoteFragment;
import com.example.notes.ui.note.NoteListFragment;

public class MainActivity extends AppCompatActivity implements NoteListFragment.NoteListController, EditNoteFragment.EditNoteController {
    public static String NOTE_EXTRA = "NOTE_EXTRA";

    private final int CONTAINER_ID = R.id.main_activity_fragment_host;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initListNoteFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_add) {
            initEditNoteFragment(null);
            return true;
        }

        return false;
    }

    @Override
    public void onNoteClicked(Note note) {
        initEditNoteFragment(note);
    }

    @Override
    public void onNoteEdited() {
        initListNoteFragment();
    }

    private void initListNoteFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(CONTAINER_ID, new NoteListFragment())
                .commit();
    }

    private void initEditNoteFragment(@Nullable Note note) {
        EditNoteFragment fragment;
        if (note != null) {
            fragment = EditNoteFragment.getInstance(note);
        } else {
            fragment = new EditNoteFragment();
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(CONTAINER_ID, fragment)
                .addToBackStack(null)
                .commit();
    }
}