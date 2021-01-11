package com.chuyende.hotelbookingappofuser.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.chuyende.hotelbookingappofuser.R;
import com.chuyende.hotelbookingappofuser.adapters.AdapterSendEmail;
import com.chuyende.hotelbookingappofuser.data_models.TaiKhoanNguoiDung;
import com.chuyende.hotelbookingappofuser.dialogs.DialogManHinhQuenTaiKhoan;
import com.chuyende.hotelbookingappofuser.firebase_models.FBDataTaiKhoanNguoiDung;
import com.chuyende.hotelbookingappofuser.interfaces.ListTaiKhoanNguoiDung;

import java.util.List;
import java.util.Random;

public class ManHinhQuenTaiKhoan extends AppCompatActivity {
    private Button btn_Dat_Lai_Mat_Khau;
    public EditText txt_Nhap_Email;
    FBDataTaiKhoanNguoiDung fbDataTaiKhoanNguoiDung = new FBDataTaiKhoanNguoiDung();
    Bundle bundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man__hinh__quen__tai__khoan);
        setControl();
        setEvent();
    }

    // sự kiện
    private void setEvent() {
        btn_Dat_Lai_Mat_Khau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fbDataTaiKhoanNguoiDung.layDuLieuTaiKhoanNguoiDung(new ListTaiKhoanNguoiDung() {
                    @Override
                    public void interfaceListTaiKhoanNguoiDung(List<TaiKhoanNguoiDung> listTaiKhoanNguoiDung) {
                        for (TaiKhoanNguoiDung robot : listTaiKhoanNguoiDung) {
                            if (txt_Nhap_Email.getText().toString().equals("")) {
                                txt_Nhap_Email.setError("Vui lòng nhập Email");
                                return;
                            }
                            if (txt_Nhap_Email.getText().toString().equals(robot.getEmail_TKNguoiDung())) {

                                sendEmail(); // sending email

//                                [START gửi key sang DialogManHinhQuenTaiKhoan]
                                bundle.putString("keyEmail", txt_Nhap_Email.getText().toString());
                                bundle.putString("keyTenTaiKhoan", robot.getTen_TKNguoiDung());
                                bundle.putString("keyDocumentID", robot.getId());
//                                [END gửi key sang DialogManHinhQuenTaiKhoan]

                                //show DialogManHinhQuenTaiKhoan
                                DialogManHinhQuenTaiKhoan dialog = new DialogManHinhQuenTaiKhoan();
                                dialog.setArguments(bundle);
                                dialog.show(getSupportFragmentManager(), "dialog");
                                return;
                            }
                        }
                    }
                });
            }
        });
    }

    private void sendEmail() {

        String reciptient = txt_Nhap_Email.getText().toString();// người nhận mail
        String subject = "Hotel booking app send OTP code";// tiêu đề mail
        Random random = new Random();// tạo số ngẫu nhiên
        int n = random.nextInt(10);// giá trị ngẫu nhiên từ 0 -> 9
        int n2 = random.nextInt(10);
        int n3 = random.nextInt(10);
        int n4 = random.nextInt(10);
        String numRandom = String.valueOf(n) + String.valueOf(n2) + String.valueOf(n3) + String.valueOf(n4); // chuyển số thành chuỗi sau đó cộng chuỗi
        String message = "Your OTP is: " + numRandom;// nội dung mail

        bundle.putString("keyOTP", numRandom); // gửi mã OTP numRandom sang màn hình DialogManHinhQuenTaiKhoan

        //Creating SendMail object
        AdapterSendEmail sm = new AdapterSendEmail(this, reciptient, subject, message);

        //Executing sendmail to send email
        sm.execute();

        Log.d("OTP in ManHinhQuenTaiKhoan", "=>: " + numRandom);
    }

    // ánh xạ
    private void setControl() {
        // EditText
        txt_Nhap_Email = findViewById(R.id.editText_Email_QMK);
        // Button
        btn_Dat_Lai_Mat_Khau = findViewById(R.id.button_DatLaiMatKhau_QMK);

    }
}