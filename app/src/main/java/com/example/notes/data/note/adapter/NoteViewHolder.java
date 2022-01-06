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
    private final TextView tvImportant;
    private Note note;

    public NoteViewHolder(@NonNull View itemView, NoteAdapter.OnClickNoteListener onClickNoteListener) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.note_title);
        tvDescription = itemView.findViewById(R.id.note_description);
        tvImportant = itemView.findViewById(R.id.note_important);

        itemView.setOnClickListener(view -> onClickNoteListener.onClickNote(this.note));
    }

    public void bind(Note note) {
        this.note = note;

        tvTitle.setText(note.getTitle());
        tvDescription.setText(note.getDescription());

        String important = "Important: " + note.getImportant();
        tvImportant.setText(important);
    }
}
