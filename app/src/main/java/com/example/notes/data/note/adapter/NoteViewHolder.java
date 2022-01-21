package com.example.notes.data.note.adapter;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notes.R;
import com.example.notes.data.note.Note;

public class NoteViewHolder extends RecyclerView.ViewHolder implements PopupMenu.OnMenuItemClickListener {
    private final TextView tvTitle;
    private final TextView tvDescription;
    private final TextView tvImportant;
    private final TextView tvDate;
    private final ImageView noteMenu;
    private Note note;

    private final PopupMenu popupMenu;
    private final NoteAdapter.OnClickNoteListener onClickNoteListener;

    public NoteViewHolder(@NonNull View itemView, NoteAdapter.OnClickNoteListener onClickNoteListener) {
        super(itemView);
        tvTitle = itemView.findViewById(R.id.note_title);
        tvDescription = itemView.findViewById(R.id.note_description);
        tvImportant = itemView.findViewById(R.id.note_important);
        tvDate = itemView.findViewById(R.id.note_date);
        noteMenu = itemView.findViewById(R.id.note_menu);
        this.onClickNoteListener = onClickNoteListener;

        popupMenu = new PopupMenu(itemView.getContext(), noteMenu);
        popupMenu.inflate(R.menu.note_context_menu);

        popupMenu.setOnMenuItemClickListener(this);

        noteMenu.setOnClickListener(v -> popupMenu.show());
    }

    public void bind(Note note) {
        this.note = note;

        tvTitle.setText(note.getTitle());
        tvDescription.setText(note.getDescription());

        String important = "Important: " + note.getImportant();
        tvImportant.setText(important);

        String date = "Date: " + note.getDate();
        tvDate.setText(date);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.note_menu_modify:
                onClickNoteListener.onModifyNote(this.note, getAdapterPosition());
                return true;

            case R.id.note_menu_delete:
                onClickNoteListener.onDeleteNote(this.note, getAdapterPosition());
                return true;

            default:
                return false;
        }
    }
}
