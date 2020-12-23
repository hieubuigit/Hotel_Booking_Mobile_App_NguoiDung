package com.chuyende.hotelbookingappofuser.activities;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.chuyende.hotelbookingappofuser.R;
import com.chuyende.hotelbookingappofuser.adapters.AdapterExpandableHeightGridView;
import com.chuyende.hotelbookingappofuser.adapters.AdapterGridViewPhong;
import com.chuyende.hotelbookingappofuser.data_models.LoaiPhong;
import com.chuyende.hotelbookingappofuser.data_models.Phong;
import com.chuyende.hotelbookingappofuser.data_models.TinhThanhPho;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.common.collect.Collections2;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import com.google.firebase.firestore.core.OrderBy;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ManHinhNha extends AppCompatActivity {
    AutoCompleteTextView autoCompleteTextView;

    AdapterExpandableHeightGridView adapterExpandableHeightGridView;

    ArrayAdapter arrAdtMultiAutoComplete;
    ArrayAdapter arrAdtSpinner_TinhThanhPho;
    ArrayAdapter arrAdtSpinner_LoaiPhong;

    AdapterGridViewPhong adapterGridViewPhong;

    BottomNavigationView bottomNavigationView; // khai bao layout fragment chính (activity_man layout)

    //    CollectionReference collectionReference = db.collection();
    ImageView imgClearSearch;

    ScrollView scrollView;

    Spinner sp_TinhThanh, sp_TieuChi, sp_LoaiPhong;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private ArrayList<Phong> arrlistPhong = new ArrayList<Phong>(); // danh sách phòng (xem phòng; gridview)
    private ArrayList<TinhThanhPho> arrlistTinhTP = new ArrayList<TinhThanhPho>(); // danh sách các tỉnh thành phố (spinner)
    private ArrayList<String> arrlistTenTinhTP = new ArrayList<String>(); // tên thành phố (dropdown spinner)
    private ArrayList<String> arrlistAutoComplete = new ArrayList<String>();// autocomplete; tìm kiếm, danh sách tên phòng
    private ArrayList<LoaiPhong> arrlistLoaiPhong = new ArrayList<LoaiPhong>();// danh sách loại phòng (spinner)
    private ArrayList<String> arrlistTenLoaiPhong = new ArrayList<String>();// danh sách tên loại phòng (dropdown spinner)
    private static final String TAT_CA_TINH_THANH = "<Tỉnh thành phố>";
    private static final String TAT_CA_LOAI_PHONG = "<Loại phòng>";

    @Override
    protected void onStart() {
        super.onStart();
        arrlistTenTinhTP.add(TAT_CA_TINH_THANH);
        arrlistTenLoaiPhong.add(TAT_CA_LOAI_PHONG);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man__hinh__nha);
        setControl();
        setEvent();
    }

    //    [START event]
    private void setEvent() {
//        [START hiện danh sách phòng]
//        lấy dữ liệu từ firebase thêm vào arraylist
        db.collection("Phong")
                .orderBy("ratingPhong", Query.Direction.DESCENDING)
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
                                arrAdtMultiAutoComplete.add(document.get("tenPhong").toString());
                            }
                        }
                    }
                });
//            [END hiện danh sách phòng]

//        [START control gridView]
//        adapterGridViewPhong = new AdapterGridViewPhong(ManHinhNha.this, R.layout.listview_item, arrlistPhong);
//        adapterExpandableHeightGridView.setAdapter(adapterGridViewPhong);
//        adapterExpandableHeightGridView.setExpanded(true);
//        [END control gridView]

//        [START sự kiện clear text autocompletextview]
        imgClearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // làm trống text
                autoCompleteTextView.getText().clear();
                // sau khi làm trống text thì quay lại dữ liệu ban đầu
                adapterGridViewPhong = new AdapterGridViewPhong(ManHinhNha.this, R.layout.listview_item, arrlistPhong);
                adapterExpandableHeightGridView.setAdapter(adapterGridViewPhong);
                adapterExpandableHeightGridView.setExpanded(true);
            }
        });
//        [END sự kiện clear text autocompletetextview]

//         [START multiautpcomplete search]
        arrAdtMultiAutoComplete = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrlistAutoComplete);
        autoCompleteTextView.setAdapter(arrAdtMultiAutoComplete);
//                db.collection("Phong")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        // nếu lấy thành công
//                        if (task.isSuccessful()) {
//                            // document duyệt dữ liệu trong task
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                arrAdtMultiAutoComplete.add(document.get("tenPhong").toString());
//                            }
//                        }
//                    }
//                });
        // khi click vào dữ liệu được show ra
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // bien item lay du lieu khi autocompletextview show ra ket qua
                String item = adapterView.getItemAtPosition(i).toString();
                // lấy dữ liệu có điều kiện, whereEqualTo("Tên field", giá trị trừu tượng: tùy vào kiểu dữ liệu của field (true, false, String, int...)
                db.collection("Phong")
                        .whereEqualTo("tenPhong", item)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    // mỗi lần click vào 1 item thì tạo danh sách mới, danh sách này chỉ hiện đúng thông tin của item
                                    // nếu không tạo danh sách mới thì mỗi lần click vào item khác sẽ hiện thông tin cũ
                                    ArrayList<Phong> arrlistKetQuaTimKiem = new ArrayList<Phong>();
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Phong clsphong = new Phong();
                                        // gán giá trị của field tenTaiKhoan trên firebase : document.get("my-field").toString(); vào các trường của class
                                        clsphong.setAnhDaiDien(document.get("anhDaiDien").toString());
                                        clsphong.setTenPhong(document.get("tenPhong").toString());
                                        clsphong.setRatingPhong(document.getDouble("ratingPhong"));
                                        clsphong.setGiaThue(document.getDouble("giaThue"));
                                        // thêm vào arraylist
                                        arrlistKetQuaTimKiem.add(clsphong);
                                        Log.d("Tag search", arrlistKetQuaTimKiem.toString());
                                    }
                                    adapterGridViewPhong = new AdapterGridViewPhong(ManHinhNha.this, R.layout.listview_item, arrlistKetQuaTimKiem);
                                    adapterExpandableHeightGridView.setAdapter(adapterGridViewPhong);
                                    adapterExpandableHeightGridView.setExpanded(true);
                                } else {
                                    Log.d("TAG11111111111111", "Error getting documents: ", task.getException());
                                }
                            }
                        });
//                Toast.makeText(ManHinhNha.this, "Selected Item is: \t" + item, Toast.LENGTH_LONG).show();
            }
        });
//        [END multiautpcomplete search]

//        [START du lieu spinner Tinh thanh pho]
//        lấy dữ liệu từ firebase thêm vào arraylist
        db.collection("TinhThanhPho").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                // nếu lấy thành công
                if (task.isSuccessful()) {
                    // document duyệt dữ liệu trong task
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        TinhThanhPho tinhThanhPho = new TinhThanhPho();
                        // gán giá trị của field tenTaiKhoan trên firebase : document.get("my-field").toString(); vào các trường của class
                        tinhThanhPho.setMaTinhThanhPho(document.get("maTinhThanhPho").toString());
                        tinhThanhPho.setTinhThanhPho(document.get("tinhThanhPho").toString());
                        // thêm vào arraylist
                        arrlistTinhTP.add(tinhThanhPho);
                        arrlistTenTinhTP.add(document.get("tinhThanhPho").toString());
                        arrAdtSpinner_TinhThanhPho.notifyDataSetChanged();
                    }
                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });

        arrAdtSpinner_TinhThanhPho = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrlistTenTinhTP);
        arrAdtSpinner_TinhThanhPho.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_TinhThanh.setAdapter(arrAdtSpinner_TinhThanhPho);
        // lọc phòng theo tỉnh thành
        sp_TinhThanh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                [ đã chọn tỉnh thành và loại phòng]
                if (sp_TinhThanh.getSelectedItem() != TAT_CA_TINH_THANH ){
                    db.collection("Phong")
                            .whereEqualTo("maTinhThanhPho", sp_TinhThanh.getSelectedItem().toString())
                            .whereEqualTo("maLoaiPhong", sp_LoaiPhong.getSelectedItem().toString())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    // nếu lấy thành công
                                    if (task.isSuccessful()) {
                                        ArrayList<Phong> arrlistLoc = new ArrayList<Phong>();
                                        // document duyệt dữ liệu trong task
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Phong clsphong = new Phong();
                                            // gán giá trị của field tenTaiKhoan trên firebase : document.get("my-field").toString(); vào các trường của class
                                            clsphong.setAnhDaiDien(document.get("anhDaiDien").toString());
                                            clsphong.setTenPhong(document.get("tenPhong").toString());
                                            clsphong.setRatingPhong(document.getDouble("ratingPhong"));
                                            clsphong.setGiaThue(document.getDouble("giaThue"));
                                            // thêm vào arraylist
                                            arrlistLoc.add(clsphong);
                                        }
                                        adapterGridViewPhong = new AdapterGridViewPhong(ManHinhNha.this,
                                                R.layout.listview_item, arrlistLoc);
                                        adapterExpandableHeightGridView.setAdapter(adapterGridViewPhong);
                                        adapterExpandableHeightGridView.setExpanded(true);

                                    } else {
                                        Log.d("TAG", "Error getting documents: ", task.getException());
                                    }
                                }
                            });
                }
//                [chọn tỉnh thành mà chưa chọn loại phòng]
                if (sp_TinhThanh.getSelectedItem() != TAT_CA_TINH_THANH && sp_LoaiPhong.getSelectedItem() == TAT_CA_LOAI_PHONG){
                    db.collection("Phong")
                            .whereEqualTo("maTinhThanhPho", sp_TinhThanh.getSelectedItem().toString())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    // nếu lấy thành công
                                    if (task.isSuccessful()) {
                                        ArrayList<Phong> arrListLoc = new ArrayList<Phong>();
                                        // document duyệt dữ liệu trong task
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Phong clsphong = new Phong();
                                            // gán giá trị của field tenTaiKhoan trên firebase : document.get("my-field").toString(); vào các trường của class
                                            clsphong.setAnhDaiDien(document.get("anhDaiDien").toString());
                                            clsphong.setTenPhong(document.get("tenPhong").toString());
                                            clsphong.setRatingPhong(document.getDouble("ratingPhong"));
                                            clsphong.setGiaThue(document.getDouble("giaThue"));
                                            // thêm vào arraylist
                                            arrListLoc.add(clsphong);
                                        }
                                        adapterGridViewPhong = new AdapterGridViewPhong(ManHinhNha.this,
                                                R.layout.listview_item, arrListLoc);
                                        adapterExpandableHeightGridView.setAdapter(adapterGridViewPhong);
                                        adapterExpandableHeightGridView.setExpanded(true);

                                    } else {
                                        Log.d("TAG", "Error getting documents: ", task.getException());
                                    }
                                }
                            });
                }
//                Toast.makeText(getApplicationContext(),sp_TinhThanh.getSelectedItem().toString(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//        [END du lieu spinner Tinh thanh pho]

//        [START du lieu spinner Loai phong]
//        lấy dữ liệu từ firebase thêm vào arraylist
        db.collection("LoaiPhong").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                // nếu lấy thành công
                if (task.isSuccessful()) {
                    // document duyệt dữ liệu trong task
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        LoaiPhong loaiPhong = new LoaiPhong();
                        // gán giá trị của field tenTaiKhoan trên firebase : document.get("my-field").toString(); vào các trường của class
                        loaiPhong.setMaLoaiPhong(document.get("maLoaiPhong").toString());
                        loaiPhong.setLoaiphong(document.get("loaiPhong").toString());
                        // thêm vào arraylist
                        arrlistLoaiPhong.add(loaiPhong);
                        arrlistTenLoaiPhong.add(document.get("loaiPhong").toString());
                        arrAdtSpinner_LoaiPhong.notifyDataSetChanged();
                    }
                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });

        arrAdtSpinner_LoaiPhong = new ArrayAdapter(this, android.R.layout.simple_spinner_item, arrlistTenLoaiPhong);
        arrAdtSpinner_LoaiPhong.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_LoaiPhong.setAdapter(arrAdtSpinner_LoaiPhong);

        // loc phòng theo loai phong
        sp_LoaiPhong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                [đã chọn loại phòng và tỉnh thành]
                if (sp_LoaiPhong.getSelectedItem() != TAT_CA_LOAI_PHONG) {
                    db.collection("Phong")
                            .whereEqualTo("maTinhThanhPho", sp_TinhThanh.getSelectedItem().toString())
                            .whereEqualTo("maLoaiPhong", sp_LoaiPhong.getSelectedItem().toString())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    // nếu lấy thành công
                                    if (task.isSuccessful()) {
                                        ArrayList<Phong> arrListLoc = new ArrayList<Phong>();
                                        // document duyệt dữ liệu trong task
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Phong clsphong = new Phong();
                                            // gán giá trị của field tenTaiKhoan trên firebase : document.get("my-field").toString(); vào các trường của class
                                            clsphong.setAnhDaiDien(document.get("anhDaiDien").toString());
                                            clsphong.setTenPhong(document.get("tenPhong").toString());
                                            clsphong.setRatingPhong(document.getDouble("ratingPhong"));
                                            clsphong.setGiaThue(document.getDouble("giaThue"));
                                            // thêm vào arraylist
                                            arrListLoc.add(clsphong);
                                        }
                                        adapterGridViewPhong = new AdapterGridViewPhong(ManHinhNha.this,
                                                R.layout.listview_item, arrListLoc);
                                        adapterExpandableHeightGridView.setAdapter(adapterGridViewPhong);
                                        adapterExpandableHeightGridView.setExpanded(true);
                                    }
                                }
                            });
                }
                // [chọn loại phòng mà chưa chọn tỉnh thành]
                if (sp_LoaiPhong.getSelectedItem() != TAT_CA_LOAI_PHONG && sp_TinhThanh.getSelectedItem() == TAT_CA_TINH_THANH) {
                    db.collection("Phong")
                            .whereEqualTo("maLoaiPhong", sp_LoaiPhong.getSelectedItem().toString())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    // nếu lấy thành công
                                    if (task.isSuccessful()) {
                                        ArrayList<Phong> arrListLoc = new ArrayList<Phong>();
                                        // document duyệt dữ liệu trong task
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Phong clsphong = new Phong();
                                            // gán giá trị của field tenTaiKhoan trên firebase : document.get("my-field").toString(); vào các trường của class
                                            clsphong.setAnhDaiDien(document.get("anhDaiDien").toString());
                                            clsphong.setTenPhong(document.get("tenPhong").toString());
                                            clsphong.setRatingPhong(document.getDouble("ratingPhong"));
                                            clsphong.setGiaThue(document.getDouble("giaThue"));
                                            // thêm vào arraylist
                                            arrListLoc.add(clsphong);
                                        }
                                        adapterGridViewPhong = new AdapterGridViewPhong(ManHinhNha.this,
                                                R.layout.listview_item, arrListLoc);
                                        adapterExpandableHeightGridView.setAdapter(adapterGridViewPhong);
                                        adapterExpandableHeightGridView.setExpanded(true);
                                    }
                                }
                            });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//        [END du lieu spinner LoaiPhong]

//        // lọc phòng theo tiêu chí
        sp_TieuChi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                if (item.equals("Rating") == true){
                    db.collection("Phong")
                            .orderBy("ratingPhong", Query.Direction.DESCENDING)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    // nếu lấy thành công
                                    if (task.isSuccessful()) {
                                        ArrayList<Phong> arrlistSortRating = new ArrayList<Phong>();
                                        // document duyệt dữ liệu trong task
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Phong clsphong = new Phong();
                                            // gán giá trị của field tenTaiKhoan trên firebase : document.get("my-field").toString(); vào các trường của class
                                            clsphong.setAnhDaiDien(document.get("anhDaiDien").toString());
                                            clsphong.setTenPhong(document.get("tenPhong").toString());
                                            clsphong.setRatingPhong(document.getDouble("ratingPhong"));
                                            clsphong.setGiaThue(document.getDouble("giaThue"));
                                            // thêm vào arraylist
                                            arrlistSortRating.add(clsphong);
                                        }
                                        adapterGridViewPhong = new AdapterGridViewPhong(ManHinhNha.this, R.layout.listview_item, arrlistSortRating);
                                        adapterExpandableHeightGridView.setAdapter(adapterGridViewPhong);
                                        adapterExpandableHeightGridView.setExpanded(true);
                                    }
                                }
                            });
                }
                if (item.equals("Giá tăng dần") == true){
                    db.collection("Phong")
                            .orderBy("giaThue", Query.Direction.ASCENDING)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    // nếu lấy thành công
                                    if (task.isSuccessful()) {
                                        ArrayList<Phong> arrlistSortAscending = new ArrayList<Phong>();
                                        // document duyệt dữ liệu trong task
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Phong clsphong = new Phong();
                                            // gán giá trị của field tenTaiKhoan trên firebase : document.get("my-field").toString(); vào các trường của class
                                            clsphong.setAnhDaiDien(document.get("anhDaiDien").toString());
                                            clsphong.setTenPhong(document.get("tenPhong").toString());
                                            clsphong.setRatingPhong(document.getDouble("ratingPhong"));
                                            clsphong.setGiaThue(document.getDouble("giaThue"));
                                            // thêm vào arraylist
                                            arrlistSortAscending.add(clsphong);
                                        }
                                        adapterGridViewPhong = new AdapterGridViewPhong(ManHinhNha.this, R.layout.listview_item, arrlistSortAscending);
                                        adapterExpandableHeightGridView.setAdapter(adapterGridViewPhong);
                                        adapterExpandableHeightGridView.setExpanded(true);
                                    }
                                }
                            });

                }
                if (item.equals("Giá giảm dần") == true){
                    db.collection("Phong")
                            .orderBy("giaThue", Query.Direction.DESCENDING)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    // nếu lấy thành công
                                    if (task.isSuccessful()) {
                                        ArrayList<Phong> arrlistSortDescending = new ArrayList<Phong>();
                                        // document duyệt dữ liệu trong task
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Phong clsphong = new Phong();
                                            // gán giá trị của field tenTaiKhoan trên firebase : document.get("my-field").toString(); vào các trường của class
                                            clsphong.setAnhDaiDien(document.get("anhDaiDien").toString());
                                            clsphong.setTenPhong(document.get("tenPhong").toString());
                                            clsphong.setRatingPhong(document.getDouble("ratingPhong"));
                                            clsphong.setGiaThue(document.getDouble("giaThue"));
                                            // thêm vào arraylist
                                            arrlistSortDescending.add(clsphong);
                                        }
                                        adapterGridViewPhong = new AdapterGridViewPhong(ManHinhNha.this, R.layout.listview_item, arrlistSortDescending);
                                        adapterExpandableHeightGridView.setAdapter(adapterGridViewPhong);
                                        adapterExpandableHeightGridView.setExpanded(true);
                                    }
                                }
                            });
                }
                Toast.makeText(getApplicationContext(),adapterView.getItemAtPosition(i).toString(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//        [START bottom navigation]
        bottomNavigationView.setSelectedItemId(R.id.item_Home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_Home:
                        return true;
                    case R.id.item_Favorite:
                        startActivity(new Intent(getApplicationContext(), ManHinhYeuThich.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.item_Account:
                        startActivity(new Intent(getApplicationContext(), ManHinhTaiKhoan.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });
//        [END bottom navigation]
    }

    //    [END event]
    // Ánh xạ
    private void setControl() {
        // gridview full height
        adapterExpandableHeightGridView = findViewById(R.id.gridViewMain);
        // autocomplete
        autoCompleteTextView = findViewById(R.id.multiauto_Search);
        // bottomnavigation
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        // scrollview
        scrollView = findViewById(R.id.scrollView);
        // spinner
        sp_LoaiPhong = findViewById(R.id.spinner_LoaiPhong_Man_Hinh_Nha);
        sp_TieuChi = findViewById(R.id.spinner_TieuChi_Man_Hinh_Nha);
        sp_TinhThanh = findViewById(R.id.spinner_TinhThanhPho_Man_Hinh_Nha);
        // imageview
        imgClearSearch = findViewById(R.id.imageview_ClearText);
    }

}