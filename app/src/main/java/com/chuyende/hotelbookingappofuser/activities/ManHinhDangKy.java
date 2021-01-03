package com.chuyende.hotelbookingappofuser.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.chuyende.hotelbookingappofuser.R;
import com.chuyende.hotelbookingappofuser.data_models.TaiKhoanNguoiDung;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ManHinhDangKy extends AppCompatActivity {
    ImageView img_Anh_Dai_Dien;
    EditText txt_Ten_Tai_Khoan_DK, txt_Email_DK, txt_SDT_DK, txt_MatKhau_DK, txt_XacNhan_DK;
    CheckBox chk_Xac_Nhan;
    Button btn_Dang_Ky;
    private FirebaseAuth fbAuth;
    public static final String COLLECTION = "TaiKhoanNguoiDung";
    public static final String TENTAIKHOAN = "tenTaiKhoan";
    public static final String MATKHAU = "matKhau";
    public static final String EMAIL = "email";
    public static final String SODIENTHOAI = "soDienThoai";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public final ArrayList<TaiKhoanNguoiDung> arrlstTaiKhoanNguoiDung = new ArrayList<TaiKhoanNguoiDung>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man__hinh__dang__ky);
        setControl();
        setEvent();
    }

    private void setEvent() {
//        [START lấy dữ liệu từ firebase thêm vào arraylist]
        db.collection("TaiKhoanNguoiDung").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                // nếu lấy thành công
                if (task.isSuccessful()) {
                    // document duyệt dữ liệu trong task
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        TaiKhoanNguoiDung taiKhoanNguoiDung = new TaiKhoanNguoiDung();
                        // gán giá trị của field tenTaiKhoan trên firebase : document.get("my-field").toString(); vào các trường của class
                        taiKhoanNguoiDung.setTen_TKNguoiDung(document.get(TENTAIKHOAN).toString());
                        taiKhoanNguoiDung.setMatKhau_TKNguoiDung(document.get(MATKHAU).toString());
                        taiKhoanNguoiDung.setEmail_TKNguoiDung(document.get(EMAIL).toString());
                        taiKhoanNguoiDung.setSdt_TKNguoiDung(document.get(SODIENTHOAI).toString());
//                        taiKhoanNguoiDung.setMa_TKNguoiDung(document.getId());
                        // thêm vào arraylist
                        arrlstTaiKhoanNguoiDung.add(taiKhoanNguoiDung);
                    }

                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });
//        [END lấy dữ liệu từ firebase thêm vào arraylist]

//        [START sự kiện click checkbox đổi màu button]
        chk_Xac_Nhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chk_Xac_Nhan.isChecked() == false) {
                    btn_Dang_Ky.setBackgroundResource(R.drawable.background_button_uncheck_mhdangky); // màu xám nếu uncheck
                    btn_Dang_Ky.setEnabled(false);
                } else {
                    btn_Dang_Ky.setBackgroundResource(R.drawable.press_button_change_color); // màu xanh khi check
                    btn_Dang_Ky.setEnabled(true);
                }
            }
        });
//        [END sự kiện click checkbox đổi màu button]

//        [START sự kiện đăng ký]
        btn_Dang_Ky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set lỗi trường email
                if (txt_Email_DK.getText().toString().trim().length() == 0) {
                    txt_Email_DK.setError("Hãy nhập email");
                }
                if (txt_Ten_Tai_Khoan_DK.getText().toString().trim().length() == 0) {
                    txt_Ten_Tai_Khoan_DK.setError("Hãy nhập tài khoản");
                }
                // set lỗi trường số điện thoại
                if (txt_SDT_DK.getText().toString().trim().length() == 0) {
                    txt_SDT_DK.setError("Hãy nhập số điện thoại");
                }
                // set lỗi trường mật khẩu
                if (txt_MatKhau_DK.getText().toString().trim().length() == 0) {
                    txt_MatKhau_DK.setError("Hãy nhập mật khẩu");
                }
                if (txt_MatKhau_DK.getText().toString().trim().length() <= 7) {
                    txt_MatKhau_DK.setError("Mật khẩu phải từ 8 ký tự trở lên");
                }
                // kiếm tra mật khẩu có khớp với mật khẩu xác nhận hay không
                if (txt_MatKhau_DK.getText().toString().trim().equals(txt_XacNhan_DK.getText().toString().trim()) == false) {
                    txt_XacNhan_DK.setError("Hãy xác nhận đúng mật khẩu");
                }
                for (int i = 0; i < arrlstTaiKhoanNguoiDung.size(); i++) {
                    // kiểm tra trùng tên tài khoản
                    // nếu trùng thì câu lệnh return sẽ thoát phương thức này. Khi nhấp vào button sẽ chạy lại phương thức
                    if (txt_Ten_Tai_Khoan_DK.getText().toString().trim().equals(arrlstTaiKhoanNguoiDung.get(i).getTen_TKNguoiDung()) == true) {
                        txt_Ten_Tai_Khoan_DK.setError("Tài khoản đã được đăng ký");
                        return;
                    }
                    // kiểm tra trùng email
                    if (txt_Email_DK.getText().toString().trim().equals(arrlstTaiKhoanNguoiDung.get(i).getEmail_TKNguoiDung()) == true) {
                        txt_Email_DK.setError("Email đã được đăng ký");
                        return;
                    }

                }
                // các điều kiện để có thể đăng ký
                if (chk_Xac_Nhan.isChecked() == true
                        && txt_SDT_DK.getText().toString().trim().length() > 0
                        && txt_MatKhau_DK.getText().toString().trim().length() > 7
                        && txt_MatKhau_DK.getText().toString().trim().equals(txt_XacNhan_DK.getText().toString().trim()) == true) {
                    themTaiKhoan();
                } else {
                    Toast.makeText(getApplicationContext(), "Dk thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
//        [END sự kiện đăng ký]
        fbAuth = FirebaseAuth.getInstance();

    }

    //    [START hàm thêm tài khoản]
    public void themTaiKhoan() {
        // them mot document ngau nhien
        Map<String, Object> data = new HashMap<String, Object>(); // biến trung gian kết nối code android và firebase
        // put: thêm giá trị vào k: ... , giá trị đó là phần bên phải;
        data.put(EMAIL, txt_Email_DK.getText().toString());
        data.put(SODIENTHOAI, txt_SDT_DK.getText().toString());
        data.put(TENTAIKHOAN, txt_Ten_Tai_Khoan_DK.getText().toString());
        data.put(MATKHAU, txt_MatKhau_DK.getText().toString());
        db.collection(COLLECTION).add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                // nếu thêm dữ liệu thành công hiện dialog thông báo đăng ký thành công
                AlertDialog.Builder builder = new AlertDialog.Builder(ManHinhDangKy.this);
                builder.setMessage("Đăng ký thành công");
                // tạo nút chuyển sang màn hình đăng nhập
                builder.setPositiveButton("Đăng nhập", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // chuyển màn hình
                        Intent intent = new Intent(ManHinhDangKy.this, ManHinhDangNhap.class);
                        startActivity(intent);
                    }
                });
                // khởi tạo dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        // đăng ký tài khoản google
        fbAuth.createUserWithEmailAndPassword(txt_Email_DK.getText().toString().trim(),txt_MatKhau_DK.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Email registered", Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.d("Error register email", " => " + task.getException());
                }
            }
        });
    }
//    [END hàm thêm tài khoản]

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