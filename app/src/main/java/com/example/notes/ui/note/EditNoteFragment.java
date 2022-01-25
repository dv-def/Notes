package com.example.notes.ui.note;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.notes.R;
import com.example.notes.data.note.Note;
import com.example.notes.data.note.NoteRepository;
import com.example.notes.data.note.Repository;
import com.example.notes.data.note.SharedRepository;
import com.example.notes.exceptions.FragmentControllerException;
import com.example.notes.ui.MainActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class EditNoteFragment extends Fragment {
    private TextInputLayout edTitle;
    private TextInputLayout edDescription;
    private Spinner spinnerImportant;
    private TextView tvDate;
    private MaterialButton btnSave;

    private EditNoteController controller;

    private Repository repository;
    private Note note;

    public interface EditNoteController {
        void onNoteEdited();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        if (context instanceof EditNoteController) {
            super.onAttach(context);
            controller = (EditNoteController) context;
            repository = SharedRepository.getInstance(context);
        } else {
            throw new FragmentControllerException("Context must implement EditNoteController");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null
                && args.containsKey(MainActivity.NOTE_EXTRA)
                && args.getParcelable(MainActivity.NOTE_EXTRA) != null) {
            note = (Note) args.getParcelable(MainActivity.NOTE_EXTRA);
        } else {
            note = new Note("", "", "", "");
        }
        return inflater.inflate(R.layout.fragment_edit_note, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        edTitle = view.findViewById(R.id.fragment_edit_note_ed_title);
        edDescription = view.findViewById(R.id.fragment_edit_note_ed_description);
        spinnerImportant = view.findViewById(R.id.fragment_edit_note_spinner_important);
        tvDate = view.findViewById(R.id.fragment_edit_note_tv_date);
        btnSave = view.findViewById(R.id.fragment_edit_note_btn_save);

        if (note.getId() != null) {
            edTitle.getEditText().setText(note.getTitle());
            edDescription.getEditText().setText(note.getDescription());
            btnSave.setText(R.string.update);
        }

        if (note.getDate().isEmpty()) {
            tvDate.setText(R.string.select_date);
        } else {
            tvDate.setText(note.getDate());
        }

        tvDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();

            DatePickerDialog dialog = new DatePickerDialog(
                    requireContext(),
                    R.style.DatePicker,
                    onDateSetListener(),
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH));

            dialog.show();
        });

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

    private DatePickerDialog.OnDateSetListener onDateSetListener() {
        return (datePicker, year, month, day) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, month, day);

            String date = String.valueOf(DateFormat.format("dd-MM-yyyy", newDate.getTime()));
            tvDate.setText(date);
            note.setDate(date);
        };
    }
}
