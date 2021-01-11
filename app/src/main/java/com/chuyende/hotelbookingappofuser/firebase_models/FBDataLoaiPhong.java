package com.chuyende.hotelbookingappofuser.firebase_models;

import android.util.Log;

import androidx.annotation.Nullable;

import com.chuyende.hotelbookingappofuser.data_models.LoaiPhong;
import com.chuyende.hotelbookingappofuser.interfaces.ListLoaiPhong;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FBDataLoaiPhong {
    FirebaseFirestore db;

    public static final String COLLECTION_LOAIPHONG = "LoaiPhong";
    public static final String MALOAIPHONG = "maLoaiPhong";
    public static final String LOAIPHONG = "loaiPhong";

    public FBDataLoaiPhong() {
        db = FirebaseFirestore.getInstance();
    }

    public void layDuLieuLoaiPhong(ListLoaiPhong listLoaiPhong) {
        db.collection(COLLECTION_LOAIPHONG).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.d("Error-DataLoaiPhong", " => " + error.getMessage());
                }
                if (value != null) {
                    ArrayList<LoaiPhong> arrlstLoaiPhong = new ArrayList<LoaiPhong>();
                    LoaiPhong loaiPhong;
                    for (QueryDocumentSnapshot document : value) {
                        loaiPhong = new LoaiPhong(document.getString(MALOAIPHONG), document.getString(LOAIPHONG));
                        arrlstLoaiPhong.add(loaiPhong);
                    }
                    listLoaiPhong.interfaceListLoaiPhong(arrlstLoaiPhong);
                }
            }
        });
    }
}
