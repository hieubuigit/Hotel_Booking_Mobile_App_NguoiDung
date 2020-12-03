package com.chuyende.hotelbookingappofuser.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.chuyende.hotelbookingappofuser.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Man_Hinh_Dang_Ky extends AppCompatActivity {
    ImageView img_Anh_Dai_Dien;
    EditText txt_Ten_Tai_Khoan_DK, txt_Email_DK, txt_SDT_DK, txt_MatKhau_DK, txt_XacNhan_DK;
    CheckBox chk_Xac_Nhan;
    Button btn_Dang_Ky;
    private static final String COLLECTION = "TaiKhoanNguoiDung";
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man__hinh__dang__ky);
        setControl();
        setEvent();
    }

    private void setEvent() {
        // sự kiện click checkbox đổi màu button
        chk_Xac_Nhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chk_Xac_Nhan.isChecked() == false) {
                    btn_Dang_Ky.setBackgroundResource(R.drawable.background_button_uncheck_mhdangky); // màu xám nếu uncheck
                } else {
                    btn_Dang_Ky.setBackgroundResource(R.drawable.press_button_change_color); // màu xanh khi check
                }
            }
        });

        // sự kiện đăng ký
        btn_Dang_Ky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set lỗi trường tên tài khoản
                if (txt_Ten_Tai_Khoan_DK.getText().toString().trim().length() == 0) {
                    txt_Ten_Tai_Khoan_DK.setError("Hãy nhập tên tài khoản");
                }
                // set lỗi trường email
                if (txt_Email_DK.getText().toString().trim().length() == 0) {
                    txt_Email_DK.setError("Hãy nhập email");
                }
                // set lỗi trường số điện thoại
                if (txt_SDT_DK.getText().toString().trim().length() == 0) {
                    txt_SDT_DK.setError("Hãy nhập số điện thoại");
                }
                // set lỗi trường mật khẩu
                if (txt_MatKhau_DK.getText().toString().trim().length() == 0) {
                    txt_MatKhau_DK.setError("Hãy nhập mật khẩu");
                }
                // kiếm tra mật khẩu có khớp với mật khẩu xác nhận hay không
                if (txt_MatKhau_DK.getText().toString().trim().equals(txt_XacNhan_DK.getText().toString().trim()) == false) {
                    txt_XacNhan_DK.setError("Hãy xác nhận đúng mật khẩu");
                }
                // các điều kiện để có thể đăng ký
                if (chk_Xac_Nhan.isChecked() == true
                        && txt_Ten_Tai_Khoan_DK.getText().toString().trim().length() > 0
                        && txt_Email_DK.getText().toString().trim().length() > 0
                        && txt_SDT_DK.getText().toString().trim().length() > 0
                        && txt_MatKhau_DK.getText().toString().trim().length() > 0
                        && txt_MatKhau_DK.getText().toString().trim().equals(txt_XacNhan_DK.getText().toString().trim()) == true) {
                    themTaiKhoan();
                }
            }
        });
    }

    // hàm thêm tài khoản
    public void themTaiKhoan() {
        // them mot document ngau nhien
        Map<String, Object> data = new HashMap<String, Object>(); // biến trung gian kết nối code android và firebase
        // put: thêm giá trị vào k: ... , giá trị đó là phần bên phải;
        data.put("email", txt_Email_DK.getText().toString());
        data.put("soDienThoai", txt_SDT_DK.getText().toString());
        data.put("tenTaiKhoan", txt_Ten_Tai_Khoan_DK.getText().toString());
        data.put("matKhau", txt_MatKhau_DK.getText().toString());
        db.collection(COLLECTION).add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                // nếu thêm dữ liệu thành công hiện dialog thông báo đăng ký thành công
                AlertDialog.Builder builder = new AlertDialog.Builder(Man_Hinh_Dang_Ky.this);
                builder.setMessage("Đăng ký thành công");
                // tạo nút chuyển sang màn hình đăng nhập
                builder.setPositiveButton("Đăng nhập", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // chuyển màn hình
                        Intent intent = new Intent(Man_Hinh_Dang_Ky.this, Man_Hinh_Dang_Nhap.class);
                        startActivity(intent);
                    }
                });
                // khởi tạo dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    // Ánh xạ
    private void setControl() {
        img_Anh_Dai_Dien = findViewById(R.id.imageView_Anh_Dai_Dien_DangKy);
        txt_Ten_Tai_Khoan_DK = findViewById(R.id.editTextTenTK_DangKy);
        txt_Email_DK = findViewById(R.id.editText_Email_DangKy);
        txt_SDT_DK = findViewById(R.id.editText_SDT_DangKy);
        txt_MatKhau_DK = findViewById(R.id.editText_MatKhau_DangKy);
        txt_XacNhan_DK = findViewById(R.id.editText_XacNhan_DangKy);
        chk_Xac_Nhan = findViewById(R.id.checkBox_DangKy);
        btn_Dang_Ky = findViewById(R.id.button_DangKy);
    }
}