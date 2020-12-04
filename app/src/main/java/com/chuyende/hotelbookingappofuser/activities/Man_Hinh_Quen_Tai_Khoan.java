package com.chuyende.hotelbookingappofuser.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chuyende.hotelbookingappofuser.R;
import com.chuyende.hotelbookingappofuser.dialogs.Dialog_Man_Hinh_Quen_Tai_Khoan;

public class Man_Hinh_Quen_Tai_Khoan extends AppCompatActivity{
    Button btn_Dat_Lai_Mat_Khau;
    EditText txt_Nhap_Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man__hinh__quen__tai__khoan);
        setControl();
        setEvent();
    }

    // sự kiện
    private void setEvent() {
//        Bundle bundle =getIntent().getExtras();
//        String getBundle = bundle.getString("key_id_ten_tai_khoan");
        btn_Dat_Lai_Mat_Khau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // show hộp thoại nhập mã otp và nhập mật khẩu mới
                Dialog_Man_Hinh_Quen_Tai_Khoan dialog = new Dialog_Man_Hinh_Quen_Tai_Khoan();
                dialog.show(getSupportFragmentManager(),"dialog");

            }
        });
    }

    // ánh xạ
    private void setControl() {
        //EditText
        txt_Nhap_Email = findViewById(R.id.editText_Email_QMK);
        //Button
        btn_Dat_Lai_Mat_Khau = findViewById(R.id.button_DatLaiMatKhau_QMK);
    }
}