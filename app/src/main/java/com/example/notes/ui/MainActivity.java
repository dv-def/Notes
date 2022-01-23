package com.example.notes.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.notes.R;
import com.example.notes.data.note.Note;
import com.example.notes.ui.app.AboutFragment;
import com.example.notes.ui.app.ExitDialogFragment;
import com.example.notes.ui.note.EditNoteFragment;
import com.example.notes.ui.note.NoteListFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NoteListFragment.NoteListController, EditNoteFragment.EditNoteController {
    public static String NOTE_EXTRA = "NOTE_EXTRA";

    private FragmentManager fragmentManager;
    private NoteListFragment noteListFragment;
    private EditNoteFragment editNoteFragment;

    private final String NOTE_LIST_TAG = "NOTE_LIST";
    private final String EDIT_NOTE_TAG = "EDIT_NOTE_TAG";

    private final String EXIT_APP_TAG = "EXIT_APP_TAG";

    private boolean isLandscape;

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            fragmentManager
                    .beginTransaction()
                    .add(R.id.main_activity_note_list_fragment_host, new NoteListFragment(), NOTE_LIST_TAG)
                    .commit();
        }

        isLandscape = (findViewById(R.id.main_activity_edit_note_fragment_host) != null);

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

        Toolbar toolbar = findViewById(R.id.main_activity_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.main_activity_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.open_drawer, R.string.close_drawer
        );

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.main_activity_navigation_view);
        navigationView.setNavigationItemSelectedListener(menuItem -> {

            if (menuItem.getItemId() == R.id.menu_drawer_about_item) {
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.main_activity_note_list_fragment_host, new AboutFragment())
                        .addToBackStack(NOTE_LIST_TAG)
                        .commit();

                drawerLayout.close();

                return true;
            }

            return false;
        });
    }

    @Override
    public void modifyNote(Note note) {
        initEditNoteFragment(note);
    }

    @Override
    public void onNoteEdited() {
        if (isLandscape) {
            noteListFragment = (NoteListFragment) fragmentManager.findFragmentByTag(NOTE_LIST_TAG);
            noteListFragment.updateAdapter();
        }

        fragmentManager
                .popBackStack(EDIT_NOTE_TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isOpen()) {
            drawerLayout.close();
        } else {
            if (fragmentManager.getBackStackEntryCount() == 0) {
                ExitDialogFragment exitDialogFragment = new ExitDialogFragment();
                exitDialogFragment.show(getSupportFragmentManager(), EXIT_APP_TAG);
            } else {
                super.onBackPressed();
            }
        }
    }

    private void initEditNoteFragment(@Nullable Note note) {
        if (editNoteFragment == null) {
            editNoteFragment = new EditNoteFragment();
        }

        Bundle args = new Bundle();
        args.putParcelable(NOTE_EXTRA, note);

        editNoteFragment.setArguments(args);

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