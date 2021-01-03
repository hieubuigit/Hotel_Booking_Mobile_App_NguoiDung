package com.chuyende.hotelbookingappofuser.firebase_models;

import android.util.Log;

import androidx.annotation.Nullable;

import com.chuyende.hotelbookingappofuser.data_models.Phong;
import com.chuyende.hotelbookingappofuser.interfaces.ListPhong;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FBDataPhong {
    FirebaseFirestore db;

    public static final String COLLECTION_PHONG = "Phong";
    public static final String TENPHONG = "tenPhong";
    public static String RATINGPHONG = "ratingPhong";
    public static final String GIATHUE = "giaThue";
    public static final String ANHDAIDIEN = "anhDaiDien";

    public FBDataPhong() {
        db = FirebaseFirestore.getInstance();
    }

    // lấy dữ liệu phòng sắp xếp theo rating giảm dần
    public void layDuLieuPhongRating(ListPhong listPhong) {
        db.collection(COLLECTION_PHONG)
                .orderBy(RATINGPHONG, Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.d("Error-DataPhong", " => " + error.getMessage());
                        }
                        if (value != null) {
                            ArrayList<Phong> arrlstPhong = new ArrayList<Phong>();
                            Phong phong;
                            for (QueryDocumentSnapshot document : value) {
                                phong = new Phong();
                                phong.setAnhDaiDien(document.getString(ANHDAIDIEN));
                                phong.setTenPhong(document.getString(TENPHONG));
                                phong.setRatingPhong(document.getDouble(RATINGPHONG));
                                phong.setGiaThue(document.getDouble(GIATHUE));
                                phong.setMaLoaiPhong(document.getString("maLoaiPhong"));
                                phong.setMaTinhThanhPho(document.getString("maTinhThanhPho"));
                                arrlstPhong.add(phong);
                            }
                            listPhong.interfaceListPhong(arrlstPhong);
                        }
                    }
                });
    }

    // lấy dữ liệu phòng sắp xếp theo giá thuê giảm dần
    public void layDuLieuPhongCostDES(ListPhong listPhong) {
        db.collection(COLLECTION_PHONG)
                .orderBy(GIATHUE, Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.d("Error-DataPhong", " => " + error.getMessage());
                        }
                        if (value != null) {
                            ArrayList<Phong> arrlstPhong = new ArrayList<Phong>();
                            Phong phong;
                            for (QueryDocumentSnapshot document : value) {
                                phong = new Phong();
                                phong.setAnhDaiDien(document.getString(ANHDAIDIEN));
                                phong.setTenPhong(document.getString(TENPHONG));
                                phong.setRatingPhong(document.getDouble(RATINGPHONG));
                                phong.setGiaThue(document.getDouble(GIATHUE));
                                phong.setMaLoaiPhong(document.getString("maLoaiPhong"));
                                phong.setMaTinhThanhPho(document.getString("maTinhThanhPho"));
                                arrlstPhong.add(phong);
                            }
                            listPhong.interfaceListPhong(arrlstPhong);
                        }
                    }
                });
    }

    // lấy dữ liệu phòng sắp xếp theo giá thuê tăng dần
    public void layDuLieuPhongCostASC(ListPhong listPhong) {
        db.collection(COLLECTION_PHONG)
                .orderBy(GIATHUE, Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.d("Error-DataPhong", " => " + error.getMessage());
                        }
                        if (value != null) {
                            ArrayList<Phong> arrlstPhong = new ArrayList<Phong>();
                            Phong phong;
                            for (QueryDocumentSnapshot document : value) {
                                phong = new Phong();
                                phong.setAnhDaiDien(document.getString(ANHDAIDIEN));
                                phong.setTenPhong(document.getString(TENPHONG));
                                phong.setRatingPhong(document.getDouble(RATINGPHONG));
                                phong.setGiaThue(document.getDouble(GIATHUE));
                                phong.setMaLoaiPhong(document.getString("maLoaiPhong"));
                                phong.setMaTinhThanhPho(document.getString("maTinhThanhPho"));
                                arrlstPhong.add(phong);
                            }
                            listPhong.interfaceListPhong(arrlstPhong);
                        }
                    }
                });
    }
}
