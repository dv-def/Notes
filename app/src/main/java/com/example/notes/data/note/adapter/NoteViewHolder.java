package com.example.notes.data.note.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.data.note.Note;

public class NoteViewHolder extends RecyclerView.ViewHolder {
    private final TextView tvTitle;
    private final TextView tvDescription;

    public NoteViewHolder(@NonNull View itemView) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.note_title);
        tvDescription = itemView.findViewById(R.id.note_description);
    }

    public void bind(Note note) {
        tvTitle.setText(note.getTitle());
        tvDescription.setText(note.getDescription());
    }
}
