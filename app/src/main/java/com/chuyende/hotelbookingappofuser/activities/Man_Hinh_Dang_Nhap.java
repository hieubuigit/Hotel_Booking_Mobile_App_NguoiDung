package com.chuyende.hotelbookingappofuser.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chuyende.hotelbookingappofuser.R;

public class Man_Hinh_Dang_Nhap extends AppCompatActivity {
    TextView tv_NextTo_Dang_Ky, tv_NextTo_QTK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man__hinh__dang__nhap);
        setControl();
        setEvent();
    }
    private void setEvent() {
        tv_NextTo_Dang_Ky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Man_Hinh_Dang_Nhap.this, Man_Hinh_Dang_Ky.class);
                startActivity(intent);
            }
        });
        tv_NextTo_QTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Man_Hinh_Dang_Nhap.this, Man_Hinh_Quen_Tai_Khoan.class);
                startActivity(intent);
            }
        });
    }

    private void setControl() {
        tv_NextTo_Dang_Ky = findViewById(R.id.textView_MH_Dang_Nhap_Press_Dang_Ky);
        tv_NextTo_QTK = findViewById(R.id.textView_MH_Dang_Nhap_Press_QTK);
    }
}