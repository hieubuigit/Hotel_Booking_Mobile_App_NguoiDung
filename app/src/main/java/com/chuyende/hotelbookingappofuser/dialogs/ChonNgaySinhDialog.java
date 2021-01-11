package com.chuyende.hotelbookingappofuser.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.chuyende.hotelbookingappofuser.R;

public class ChonNgaySinhDialog extends DialogFragment {
    TextView tvTieuDe;
    DatePicker dpChonNgaySinh;
    Button btnThoi, btnThem;

    public ChonNgaySinhDialog() {
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View viewDialog = inflater.inflate(R.layout.custom_dialog_chon_ngay_sinh, null);

        // Get all view from UI
        tvTieuDe.findViewById(R.id.tvTieuDe);
        dpChonNgaySinh.findViewById(R.id.dpChonNgaySinh);
        btnThoi.findViewById(R.id.btnThoi);
        btnThem.findViewById(R.id.btnThem);

        tvTieuDe.setText(R.string.title_dialog_chon_ngay_sinh);

        btnThoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("CNS=>", "Button thoi in dialog chon ngay sinh is tapped!");
                dismiss();
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("CNS=>", "Button them in dialog chon ngay sinh is tapped!");

                int day = dpChonNgaySinh.getDayOfMonth();
                int month = dpChonNgaySinh.getMonth();
                int year = dpChonNgaySinh.getYear();

                Log.d("CNS=>", day + "/" + month + "/" + year);
            }
        });

        builder.setView(viewDialog);

        return builder.create();
    }
}
