package com.example.myapplication.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.R;
import com.google.android.material.button.MaterialButton;

public class DeleteDialogFragment extends DialogFragment {
    private OnDeleteDialogListener onDeleteDialogListener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = requireActivity().getLayoutInflater()
                .inflate(R.layout.delete_dialog_fragment,null);
        MaterialButton deleteBtn = view.findViewById(R.id.delete_dialog);
        deleteBtn.setOnClickListener(v -> {
            if(onDeleteDialogListener != null){
                onDeleteDialogListener.onDelete();
                dismiss();
            }
        });
        MaterialButton cancelDelete=view.findViewById(R.id.cancel_delete_dialog);
        cancelDelete.setOnClickListener(v -> {
            if(onDeleteDialogListener != null){
                onDeleteDialogListener.cancelDelete();
                dismiss();
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity())
                .setView(view);
        return builder.create();
    }

    public void setOnDeleteDialogListener(OnDeleteDialogListener onDeleteDialogListener) {
        this.onDeleteDialogListener = onDeleteDialogListener;
    }
}
