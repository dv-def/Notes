package com.example.notes.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.notes.R;
import com.example.notes.data.note.Note;
import com.example.notes.data.note.NoteRepository;
import com.example.notes.data.note.Repository;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class EditNoteActivity extends AppCompatActivity {
    private TextInputLayout edTitle;
    private TextInputLayout edDescription;

    private final Repository repository = NoteRepository.getInstance();

    private Note note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        edTitle = findViewById(R.id.ed_title);
        edDescription = findViewById(R.id.ed_description);
        MaterialButton btnSave = findViewById(R.id.btn_save);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(MainActivity.NOTE_EXTRA)) {
            note = (Note) intent.getParcelableExtra(MainActivity.NOTE_EXTRA);
            btnSave.setText(R.string.update);
        } else {
            note = new Note("", "");
        }

        edTitle.getEditText().setText(note.getTitle());
        edDescription.getEditText().setText(note.getDescription());

        btnSave.setOnClickListener(view -> {
            String title = edTitle.getEditText().getText().toString();
            note.setTitle(title);

            String description = edDescription.getEditText().getText().toString();
            note.setDescription(description);

            if (note.getId() == null) {
                repository.create(note);
            } else {
                repository.update(note);
            }
            finish();
        });
    }
}