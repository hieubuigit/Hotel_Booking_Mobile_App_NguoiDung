package com.chuyende.hotelbookingappofuser.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.chuyende.hotelbookingappofuser.R;

public class DialogManHinhQuenTaiKhoan extends DialogFragment {

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout to use as dialog or embedded fragment
//        return inflater.inflate(R.layout.dialog_quen_tai_khoan, container, false);
//    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Build the dialog and set up the button click handlers
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(R.layout.dialog_quen_tai_khoan);
        builder
                .setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .setNegativeButton(R.string.negative, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                    }
                });
        return builder.create();
    }

}

