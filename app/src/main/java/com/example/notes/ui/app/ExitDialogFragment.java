package com.example.notes.ui.app;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.notes.R;

public class ExitDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle(R.string.warning);
        builder.setMessage(R.string.sure_exit);

        builder.setNegativeButton(R.string.no, (dialog, i) -> dialog.cancel());

        builder.setPositiveButton(R.string.yes, (dialog, i) -> {
            dialog.dismiss();
            Toast.makeText(requireContext(), R.string.bye, Toast.LENGTH_SHORT).show();
            requireActivity().finish();
        });

        return builder.create();
    }
}
