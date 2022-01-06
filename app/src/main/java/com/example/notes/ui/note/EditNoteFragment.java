package com.example.notes.ui.note;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.notes.R;
import com.example.notes.data.note.Note;
import com.example.notes.data.note.NoteRepository;
import com.example.notes.data.note.Repository;
import com.example.notes.exceptions.FragmentControllerException;
import com.example.notes.ui.MainActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class EditNoteFragment extends Fragment {
    private TextInputLayout edTitle;
    private TextInputLayout edDescription;
    private Spinner spinnerImportant;
    private MaterialButton btnSave;

    private EditNoteController controller;

    private Repository repository = NoteRepository.getInstance();
    private Note note;

    public static EditNoteFragment getInstance(Note note) {
        Bundle args = new Bundle();
        args.putParcelable(MainActivity.NOTE_EXTRA, note);

        EditNoteFragment fragment = new EditNoteFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        if (context instanceof EditNoteController) {
            super.onAttach(context);
            controller = (EditNoteController) context;
        } else {
            throw new FragmentControllerException("Context must implement EditNoteController");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null && args.containsKey(MainActivity.NOTE_EXTRA)) {
            note = (Note) args.getParcelable(MainActivity.NOTE_EXTRA);
        } else {
            note = new Note("", "", "");
        }
        return inflater.inflate(R.layout.fragment_edit_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edTitle = view.findViewById(R.id.fragment_edit_note_ed_title);
        edDescription = view.findViewById(R.id.fragment_edit_note_ed_description);
        spinnerImportant = view.findViewById(R.id.fragment_edit_note_spinner_important);
        btnSave = view.findViewById(R.id.fragment_edit_note_btn_save);

        if (note.getId() != null) {
            edTitle.getEditText().setText(note.getTitle());
            edDescription.getEditText().setText(note.getDescription());
            btnSave.setText(R.string.update);
        }

        spinnerImportant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String[] importantValues = requireContext().getResources().getStringArray(R.array.important);
                note.setImportant(importantValues[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnSave.setOnClickListener(v -> {
            note.setTitle(edTitle.getEditText().getText().toString());
            note.setDescription(edDescription.getEditText().getText().toString());

            if (note.getId() == null) {
                repository.create(note);
            } else {
                repository.update(note);
            }

            controller.onNoteEdited();
        });
    }

    public interface EditNoteController {
        void onNoteEdited();
    }
}
