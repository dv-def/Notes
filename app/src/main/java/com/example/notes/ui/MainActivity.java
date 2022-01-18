package com.example.notes.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

    private FragmentManager fragmentManager;
    private NoteListFragment noteListFragment;
    private EditNoteFragment editNoteFragment;

    private final String NOTE_LIST_TAG = "NOTE_LIST";
    private final String EDIT_NOTE_TAG = "EDIT_NOTE_TAG";

    private boolean isLandscape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            fragmentManager
                    .beginTransaction()
                    .add(R.id.main_activity_note_list_fragment_host, new NoteListFragment())
                    .commit();
        }

        isLandscape = (findViewById(R.id.main_activity_edit_note_fragment_host) != null);

        noteListFragment = (NoteListFragment) fragmentManager.findFragmentByTag(NOTE_LIST_TAG);
        editNoteFragment = (EditNoteFragment) fragmentManager.findFragmentByTag(EDIT_NOTE_TAG);

        if (editNoteFragment != null) {
            fragmentManager.popBackStack(EDIT_NOTE_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            fragmentManager.executePendingTransactions();

            fragmentManager
                    .beginTransaction()
                    .replace(getContainerId(), editNoteFragment, EDIT_NOTE_TAG)
                    .addToBackStack(EDIT_NOTE_TAG)
                    .commit();
        }
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
        if (noteListFragment != null) {
            noteListFragment.updateAdapter();
        }

        fragmentManager
                .popBackStack(EDIT_NOTE_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    private void initEditNoteFragment(@Nullable Note note) {
        if (editNoteFragment == null) {
            editNoteFragment = EditNoteFragment.getInstance(note);
        }

        if (!editNoteFragment.isVisible()) {
            fragmentManager
                    .beginTransaction()
                    .replace(getContainerId(), editNoteFragment, EDIT_NOTE_TAG)
                    .addToBackStack(EDIT_NOTE_TAG)
                    .commit();
        }
    }

    private int getContainerId() {
        if (isLandscape) {
            return R.id.main_activity_edit_note_fragment_host;
        }

        return R.id.main_activity_note_list_fragment_host;
    }
}