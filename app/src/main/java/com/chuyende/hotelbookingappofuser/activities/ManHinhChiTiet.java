package com.chuyende.hotelbookingappofuser.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.chuyende.hotelbookingappofuser.R;
import com.chuyende.hotelbookingappofuser.adapters.AdapterExpandableHeightGridView;
import com.chuyende.hotelbookingappofuser.adapters.AdapterGridViewPhong;
import com.chuyende.hotelbookingappofuser.data_models.Phong;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ManHinhChiTiet extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    AdapterGridViewPhong adapterGridViewPhong;
    AdapterExpandableHeightGridView adapterExpandableHeightGridView;
    private ArrayList<Phong> arrlistPhong = new ArrayList<Phong>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chi_tiet);

        adapterExpandableHeightGridView = (AdapterExpandableHeightGridView) findViewById(R.id.gridViewMain2);

        String ma = getIntent().getExtras().getString("key_tenPhong");
        Log.d("=>>>>>>>>.", ma);

        //        [START hiện danh sách phòng]
//        lấy dữ liệu từ firebase thêm vào arraylist
        db.collection("Phong")
                .whereEqualTo("tenPhong", ma)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        // nếu lấy thành công
                        if (task.isSuccessful()) {
                            // document duyệt dữ liệu trong task
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Phong clsphong = new Phong();
                                // gán giá trị của field tenTaiKhoan trên firebase : document.get("my-field").toString(); vào các trường của class
                                clsphong.setAnhDaiDien(document.get("anhDaiDien").toString());
                                clsphong.setTenPhong(document.get("tenPhong").toString());
                                clsphong.setRatingPhong(document.getDouble("ratingPhong"));
                                clsphong.setGiaThue(document.getDouble("giaThue"));
                                // thêm vào arraylist
                                arrlistPhong.add(clsphong);
                            }
                        }
                    }
                });
//            [END hiện danh sách phòng]
        //        [START control gridView]
        adapterGridViewPhong = new AdapterGridViewPhong(ManHinhChiTiet.this, R.layout.listview_item, arrlistPhong);
        adapterExpandableHeightGridView.setAdapter(adapterGridViewPhong);
        adapterExpandableHeightGridView.setExpanded(true);
//        [END control gridView]
    }
}