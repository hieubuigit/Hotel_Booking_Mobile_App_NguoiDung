package com.chuyende.hotelbookingappofuser.activities;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.chuyende.hotelbookingappofuser.R;
import com.chuyende.hotelbookingappofuser.data_models.TaiKhoanNguoiDung;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Man_Hinh_Dang_Ky extends AppCompatActivity {
    ImageView img_Anh_Dai_Dien;
    EditText txt_Ten_Tai_Khoan_DK, txt_Email_DK, txt_SDT_DK, txt_MatKhau_DK, txt_XacNhan_DK;
    CheckBox chk_Xac_Nhan;
    Button btn_Dang_Ky;

    private static final String COLLECTION = "TaiKhoanNguoiDung";
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
//        btn_Dang_Ky.setEnabled(false); // chặn sự kiện click button
////        chk_Xac_Nhan.setEnabled(false); // chặn sự kiện click button
//        btn_Dang_Ky.setBackgroundResource(R.drawable.background_button_uncheck_mhdangky); // màu xám
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
                        taiKhoanNguoiDung.setTen_TKNguoiDung(document.get("tenTaiKhoan").toString());
                        taiKhoanNguoiDung.setMatKhau_TKNguoiDung(document.get("matKhau").toString());
                        taiKhoanNguoiDung.setEmail_TKNguoiDung(document.get("email").toString());
                        taiKhoanNguoiDung.setSdt_TKNguoiDung(document.get("soDienThoai").toString());
                        taiKhoanNguoiDung.setMa_TKNguoiDung(document.getId());
                        // thêm vào arraylist
                        arrlstTaiKhoanNguoiDung.add(taiKhoanNguoiDung);
                    }

                    Log.d("TAG-ArrayList2", "arrlist : " + arrlstTaiKhoanNguoiDung.toString());
                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });
//        [END lấy dữ liệu từ firebase thêm vào arraylist]

//         [START check ten tai khoan]
        // nếu ten tai khoan trùng sẽ báo lỗi
        // setOnFocusChangeListener: kiểm tra xem edittext ten tai khoan có dang focus hay khong
        // nếu tên tài khoản bị trùng với tài khoản đã đăng ký trong arraylist thì khi chuyển sang trường tiếp theo sẽ báo lỗi
//        txt_Ten_Tai_Khoan_DK.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                // boolean b == true => trường đó đang focus
//                // bolean b == false => trường đó không còn focus nữa (chuyển sang trường khác)
//                if (b == false) {
//                    for (int i = 0; i < arrlstTaiKhoanNguoiDung.size(); i++) {
//                        // lấy dữ liệu trường tên tài khoản so với arralist ten tai khoan
//                        if (txt_Ten_Tai_Khoan_DK.getText().toString().trim().equals(arrlstTaiKhoanNguoiDung.get(i).getTen_TKNguoiDung()) == true) {
//                            txt_Ten_Tai_Khoan_DK.setError("Tài khoản đã được đăng ký");
//                            if (chk_Xac_Nhan.isChecked() == true) {
//                                btn_Dang_Ky.setEnabled(false);
//                                btn_Dang_Ky.setBackgroundResource(R.drawable.background_button_uncheck_mhdangky); // màu xám
//                            }
//                        } else if (txt_Ten_Tai_Khoan_DK.getText().toString().trim().equals(arrlstTaiKhoanNguoiDung.get(i).getTen_TKNguoiDung()) == false) {
//                            if (chk_Xac_Nhan.isChecked() == true) {
//                                btn_Dang_Ky.setEnabled(true);
//                                btn_Dang_Ky.setBackgroundResource(R.drawable.press_button_change_color); // màu xanh
//                            } else {
//                                btn_Dang_Ky.setBackgroundResource(R.drawable.background_button_uncheck_mhdangky); // màu xám
//                                btn_Dang_Ky.setEnabled(false);
//                            }
//                        }
//                    }
//                    // set lỗi trường tên tài khoản
//                    if (txt_Ten_Tai_Khoan_DK.getText().toString().trim().length() == 0) {
//                        txt_Ten_Tai_Khoan_DK.setError("Hãy nhập tên tài khoản");
//                    }
//                }
//            }
//        });
//        txt_Email_DK.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                // boolean b == true => trường đó đang focus
//                // bolean b == false => trường đó không còn focus nữa (chuyển sang trường khác)
//                if (b == false) {
//                    for (int i = 0; i < arrlstTaiKhoanNguoiDung.size(); i++) {
//                        // lấy dữ liệu trường tên tài khoản so với arralist ten tai khoan
//                        if (txt_Email_DK.getText().toString().trim().equals(arrlstTaiKhoanNguoiDung.get(i).getEmail_TKNguoiDung()) == true) {
//                            txt_Email_DK.setError("Email đã được đăng ký");
//                            if (chk_Xac_Nhan.isChecked() == true) {
//                                btn_Dang_Ky.setEnabled(false);
//                                btn_Dang_Ky.setBackgroundResource(R.drawable.background_button_uncheck_mhdangky); // màu xám
//                            }
//                        } else if (txt_Email_DK.getText().toString().trim().equals(arrlstTaiKhoanNguoiDung.get(i).getEmail_TKNguoiDung()) == false) {
//                            if (chk_Xac_Nhan.isChecked() == true) {
//                                btn_Dang_Ky.setEnabled(true);
//                                btn_Dang_Ky.setBackgroundResource(R.drawable.press_button_change_color); // màu xanh
//                            } else {
//                                btn_Dang_Ky.setBackgroundResource(R.drawable.background_button_uncheck_mhdangky); // màu xám
//                                btn_Dang_Ky.setEnabled(false);
//                            }
//                        } else {
//
//                        }
//                    }
//                    // set lỗi trường email
//                    if (txt_Email_DK.getText().toString().trim().length() == 0) {
//                        txt_Email_DK.setError("Hãy nhập email");
//                    }
//                }
//            }
//        });
//        [END check ten tai khoan]

//        [START sự kiện click checkbox đổi màu button]
        chk_Xac_Nhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chk_Xac_Nhan.isChecked() == false) {
                    btn_Dang_Ky.setBackgroundResource(R.drawable.background_button_uncheck_mhdangky); // màu xám nếu uncheck
                    btn_Dang_Ky.setEnabled(false);
                }
                else {
                    btn_Dang_Ky.setBackgroundResource(R.drawable.press_button_change_color); // màu xanh khi check
                    btn_Dang_Ky.setEnabled(true);
                }
            }
        });
        for (int i = 0; i < arrlstTaiKhoanNguoiDung.size(); i++) {
            if (txt_Ten_Tai_Khoan_DK.getText().toString().trim().equals(arrlstTaiKhoanNguoiDung.get(i).getTen_TKNguoiDung()) == true) {
                txt_Ten_Tai_Khoan_DK.setError("Tài khoản đã được đăng ký");
            }
            else if (txt_Ten_Tai_Khoan_DK.getText().toString().trim().equals(arrlstTaiKhoanNguoiDung.get(i).getTen_TKNguoiDung()) == false
                    && txt_Email_DK.getText().toString().trim().equals(arrlstTaiKhoanNguoiDung.get(i).getEmail_TKNguoiDung()) == false){
                if (chk_Xac_Nhan.isChecked() == true){
                    btn_Dang_Ky.setBackgroundResource(R.drawable.press_button_change_color); // màu xanh khi check
                    btn_Dang_Ky.setEnabled(true);
                }
            }
            else if (txt_Email_DK.getText().toString().trim().equals(arrlstTaiKhoanNguoiDung.get(i).getEmail_TKNguoiDung()) == true) {
                txt_Email_DK.setError("Email đã được đăng ký");
            }
            else{

            }
        }
//        [END sự kiện click checkbox đổi màu button]

        if (txt_Ten_Tai_Khoan_DK.getText().toString().trim().length() == 0) {
            txt_Ten_Tai_Khoan_DK.setError("Hãy nhập tên tài khoản");
        }
        if (txt_Ten_Tai_Khoan_DK.getText().toString().trim().length() > 0) {
            chk_Xac_Nhan.setEnabled(true);
        }
        if (txt_Email_DK.getText().toString().trim().length() == 0) {
            txt_Email_DK.setError("Hãy nhập số điện thoại");
        }
        if (txt_Email_DK.getText().toString().trim().length() > 0) {
            chk_Xac_Nhan.setEnabled(true);
        }
        // set lỗi trường số điện thoại
        if (txt_SDT_DK.getText().toString().trim().length() == 0) {
            txt_SDT_DK.setError("Hãy nhập số điện thoại");
        }
        if (txt_SDT_DK.getText().toString().trim().length() > 0) {
            chk_Xac_Nhan.setEnabled(true);
        }
        // set lỗi trường mật khẩu
        if (txt_MatKhau_DK.getText().toString().trim().length() == 0) {
            txt_MatKhau_DK.setError("Hãy nhập mật khẩu");
        }
        if (txt_MatKhau_DK.getText().toString().trim().length() > 0) {
            chk_Xac_Nhan.setEnabled(true);
        }
        // kiếm tra mật khẩu có khớp với mật khẩu xác nhận hay không
        if (txt_MatKhau_DK.getText().toString().trim().equals(txt_XacNhan_DK.getText().toString().trim()) == false) {
            txt_XacNhan_DK.setError("Hãy xác nhận đúng mật khẩu");
        }
        if (txt_MatKhau_DK.getText().toString().trim().equals(txt_XacNhan_DK.getText().toString().trim()) == true) {
            chk_Xac_Nhan.setEnabled(true);
        }
//        [START sự kiện đăng ký]
        btn_Dang_Ky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
//        [END sự kiện đăng ký]
    }

    //    [START hàm thêm tài khoản]
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