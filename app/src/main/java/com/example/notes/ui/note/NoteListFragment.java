package com.example.notes.ui.note;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.data.note.Note;
import com.example.notes.data.note.NoteRepository;
import com.example.notes.data.note.Repository;
import com.example.notes.data.note.SharedRepository;
import com.example.notes.data.note.adapter.NoteAdapter;
import com.example.notes.exceptions.FragmentControllerException;

public class NoteListFragment extends Fragment implements NoteAdapter.OnClickNoteListener {
    private RecyclerView rvNotes;
    private NoteAdapter adapter;

    private NoteListController controller;
    private Repository repository;

    public interface NoteListController {
        void modifyNote(Note note);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        if (context instanceof NoteListController) {
            super.onAttach(context);
            controller = (NoteListController) context;
            repository = SharedRepository.getInstance(context);
        } else {
            throw new FragmentControllerException("Context must implement NoteListController");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_note_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        adapter = new NoteAdapter();
        adapter.setOnClickNoteListener(this);

        rvNotes = view.findViewById(R.id.fragment_note_list_recycler_view);
        rvNotes.setAdapter(adapter);
        rvNotes.setLayoutManager(new LinearLayoutManager(requireContext()));
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.setNotes(repository.getAll());
        System.out.println(((SharedRepository) repository).watchPrefs());
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        requireActivity().getMenuInflater().inflate(R.menu.menu_note_list, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_add) {
            controller.modifyNote(null);
            return true;
        }

        return false;
    }

    @Override
    public void onModifyNote(Note note, int position) {
        controller.modifyNote(note);
    }

    @Override
    public void onDeleteNote(Note note, int position) {
        repository.delete(note.getId());
        adapter.deleteItem(position);
    }

    public void updateAdapter() {
        adapter.setNotes(repository.getAll());
    }
}
