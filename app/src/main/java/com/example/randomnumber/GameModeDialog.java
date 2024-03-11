package com.example.randomnumber;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class GameModeDialog extends DialogFragment {
    public interface NoticeDialogListener {
        public void onDialogPositiveClick(int selected);
        public void onDialogNegativeClick();
    }

    NoticeDialogListener listener;
    private int selected_item = 0;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (NoticeDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("MainActivity"
                    + " must implement NoticeDialogListener");
        }
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.modal_title_str);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                listener.onDialogPositiveClick(selected_item);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                listener.onDialogNegativeClick();
            }
        });
        builder.setSingleChoiceItems(R.array.diaps_array,0, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                selected_item = which;
            }
        });

        return builder.create();
    }
}
