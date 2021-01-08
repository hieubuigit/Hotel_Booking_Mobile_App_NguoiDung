package com.chuyende.hotelbookingappofuser.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.chuyende.hotelbookingappofuser.R;
import com.chuyende.hotelbookingappofuser.activities.ManHinhChiTiet;
import com.chuyende.hotelbookingappofuser.activities.ManHinhQuenTaiKhoan;
import com.chuyende.hotelbookingappofuser.adapters.AdapterGridViewPhong;
import com.chuyende.hotelbookingappofuser.data_models.Phong;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.chuyende.hotelbookingappofuser.firebase_models.FBDataTaiKhoanNguoiDung;
import com.google.firebase.auth.PhoneAuthProvider;
import com.squareup.picasso.Picasso;

import java.util.Properties;

public class DialogManHinhQuenTaiKhoan extends DialogFragment {
    private EditText txtInputOTP, txtInputNewPass;
    private TextView tvEmail, tvTenTaiKhoan, tvXacNhan, tvHuy;
    FBDataTaiKhoanNguoiDung fbDataTaiKhoanNguoiDung = new FBDataTaiKhoanNguoiDung();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_quen_tai_khoan, container, false);

//        [START ánh xạ]
        tvEmail = view.findViewById(R.id.textView_Dialog_QTK_Email);
        tvTenTaiKhoan = view.findViewById(R.id.textView_Dialog_QTK_TenTK);
        tvXacNhan = view.findViewById(R.id.textView_Dialog_QTK_XacNhan);
        tvHuy = view.findViewById(R.id.textView_Dialog_QTK_Huy);
        txtInputOTP = view.findViewById(R.id.editText_Dialog_OTP);
        txtInputNewPass = view.findViewById(R.id.editText_Dialog_MK);
//        [END ánh xạ]

        String getKeyEmail = getArguments().getString("keyEmail");// lấy email từ màn hình quên tài khoản
        String getKeyTenTK = getArguments().getString("keyTenTaiKhoan");// lấy tên tài khoản từ màn hình quên tài khoản
        String getKeyOTP = getArguments().getString("keyOTP"); // lấy mã OTP từ màn hình quên tài khoản
        String getKeyDocumentID = getArguments().getString("keyDocumentID");

        String getabc = getArguments().getString("keyAccountUser");

        Log.d("=>>>>>>>>", "Key Account: " + getabc);

        tvEmail.setText("Email: " + getKeyEmail);// hiện email người dùng ở dialog
        tvTenTaiKhoan.setText("Tên tài khoản: " + getKeyTenTK); // hiện tên tài khoản người dùng ở dialog

//        [START đóng dialog]
        tvHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
//        [END đóng dialog]


//        [START confirm OTP, new password and update firebase]
        tvXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getTextOTP = txtInputOTP.getText().toString(); // gettext otp in DialogMahinhQuenTaiKhoan
                String getTextNewPass = txtInputNewPass.getText().toString(); // gettext new password in DialogMahinhQuenTaiKhoan
                if (!getTextOTP.equals(getKeyOTP)) {
                    Toast.makeText(getContext(), "Mã OTP chưa chính xác", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (getTextOTP.equals(getKeyOTP)) {
                    fbDataTaiKhoanNguoiDung.updateAccount(getTextNewPass, getKeyDocumentID);
                    Toast.makeText(getActivity(), "Changed Successfully", Toast.LENGTH_SHORT).show();
                    ((ManHinhQuenTaiKhoan) getActivity()).txt_Nhap_Email.getText().clear();// clear text in ManhinhQuenTaiKhoan
                }
                getDialog().dismiss();
            }
        });
//        [END confirm OTP, new password and update firebase]

        return view;
    }
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        // Build the dialog and set up the button click handlers
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setView(R.layout.dialog_quen_tai_khoan);
//        builder.setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//
//            }
//        });
//        builder.setNegativeButton(R.string.negative, new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                dialog.cancel();
//            }
//        });
//        return builder.create();
//    }
}

