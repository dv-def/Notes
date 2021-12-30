package com.example.notes.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.notes.R;
import com.example.notes.data.note.Note;
import com.example.notes.ui.note.EditNoteFragment;
import com.example.notes.ui.note.NoteListFragment;

public class MainActivity extends AppCompatActivity implements NoteListFragment.NoteListController, EditNoteFragment.EditNoteController {
    public static String NOTE_EXTRA = "NOTE_EXTRA";

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
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            findViewById(R.id.main_activity_edit_note_fragment_host).setVisibility(View.GONE);
            findViewById(R.id.main_activity_note_list_fragment_host).getLayoutParams().width = ConstraintLayout.LayoutParams.MATCH_PARENT;
        }
    }

    private void initListNoteFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_activity_note_list_fragment_host, new NoteListFragment())
                .commit();
    }

    private void initEditNoteFragment(@Nullable Note note) {
        EditNoteFragment fragment;
        if (note != null) {
            fragment = EditNoteFragment.getInstance(note);
        } else {
            fragment = new EditNoteFragment();
        }

        int containerId;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            FrameLayout noteEditLayout = findViewById(R.id.main_activity_edit_note_fragment_host);
            noteEditLayout.setVisibility(View.VISIBLE);

            FrameLayout noteListLayout = findViewById(R.id.main_activity_note_list_fragment_host);
            noteListLayout.getLayoutParams().width = 0;

            containerId = R.id.main_activity_edit_note_fragment_host;
        } else {
            containerId = R.id.main_activity_note_list_fragment_host;
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(containerId, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FragmentManager manager = getSupportFragmentManager();
        if (manager.findFragmentById(R.id.main_activity_edit_note_fragment_host) == null) {
            FrameLayout noteListLayout = findViewById(R.id.main_activity_note_list_fragment_host);
            noteListLayout.getLayoutParams().width = ConstraintLayout.LayoutParams.MATCH_PARENT;
        }
    }
}