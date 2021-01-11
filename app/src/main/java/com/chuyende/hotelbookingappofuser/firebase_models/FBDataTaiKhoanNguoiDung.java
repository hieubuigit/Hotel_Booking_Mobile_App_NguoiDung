package com.chuyende.hotelbookingappofuser.firebase_models;

import android.util.Log;

import androidx.annotation.Nullable;

import com.chuyende.hotelbookingappofuser.data_models.TaiKhoanNguoiDung;
import com.chuyende.hotelbookingappofuser.interfaces.ListTaiKhoanNguoiDung;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FBDataTaiKhoanNguoiDung {
    FirebaseFirestore db;

    public static final String COLLECTION_TAIKHOANNGUOIDUNG = "TaiKhoanNguoiDung";
    public static final String TENTAIKHOANNGUOIDUNG = "tenTaiKhoan";
    public static final String MATKHAUTAIKHOANNGUOIDUNG = "matKhau";
    public static final String SODIENTHOAI = "soDienThoai";
    public static final String EMAILTAIKHOANNGUOIDUNG = "email";

    public FBDataTaiKhoanNguoiDung() {
        db = FirebaseFirestore.getInstance();
    }

    // lấy dữ liệu tài khoản người dùng
    public void layDuLieuTaiKhoanNguoiDung(ListTaiKhoanNguoiDung listTaiKhoanNguoiDung) {
        db.collection(COLLECTION_TAIKHOANNGUOIDUNG)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.d("Error-DataTaiKhoanNguoiDung", " => " + error.getMessage());
                        }
                        if (value != null) {
                            ArrayList<TaiKhoanNguoiDung> arrlstTaiKhoanNguoiDung = new ArrayList<TaiKhoanNguoiDung>();
                            TaiKhoanNguoiDung taiKhoanNguoiDung;
                            for (QueryDocumentSnapshot document : value) {
                                taiKhoanNguoiDung = new TaiKhoanNguoiDung();
                                taiKhoanNguoiDung.setSdt_TKNguoiDung(document.getString(SODIENTHOAI));
                                taiKhoanNguoiDung.setEmail_TKNguoiDung(document.getString(EMAILTAIKHOANNGUOIDUNG));
                                taiKhoanNguoiDung.setMatKhau_TKNguoiDung(document.getString(MATKHAUTAIKHOANNGUOIDUNG));
                                taiKhoanNguoiDung.setTen_TKNguoiDung(document.getString(TENTAIKHOANNGUOIDUNG));
                                taiKhoanNguoiDung.setId(document.getId());
                                arrlstTaiKhoanNguoiDung.add(taiKhoanNguoiDung);
                            }
                            listTaiKhoanNguoiDung.interfaceListTaiKhoanNguoiDung(arrlstTaiKhoanNguoiDung);
                        }
                    }
                });
    }

    // cập nhật mật khẩu
    public void updateAccount(String passNeedToChange, String getDocumentById) {
        Map<String, Object> data = new HashMap<>();
        data.put(MATKHAUTAIKHOANNGUOIDUNG, passNeedToChange);
        db.collection(COLLECTION_TAIKHOANNGUOIDUNG).document(getDocumentById).update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("Update Password", "====thành công====");
            }
        });
    }
}
