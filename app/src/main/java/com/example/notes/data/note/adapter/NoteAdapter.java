package com.example.notes.data.note.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.data.note.Note;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {
    private final ArrayList<Note> notes = new ArrayList<>();
    private OnClickNoteListener onClickNoteListener;

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false);
        return new NoteViewHolder(view, onClickNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bind(notes.get(position));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void deleteItem(int position) {
        notifyItemRemoved(position);
    }

    public void setNotes(ArrayList<Note> newNotes) {
        this.notes.clear();
        this.notes.addAll(newNotes);
        notifyDataSetChanged();
    }

    public void setOnClickNoteListener(OnClickNoteListener onClickNoteListener) {
        this.onClickNoteListener = onClickNoteListener;
    }

    public interface OnClickNoteListener {
        void onEditNote(Note note, int position);
        void onDeleteNote(Note note, int position);
    }
}
