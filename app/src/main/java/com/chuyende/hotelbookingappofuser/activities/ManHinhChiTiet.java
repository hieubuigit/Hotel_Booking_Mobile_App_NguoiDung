package com.chuyende.hotelbookingappofuser.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.chuyende.hotelbookingappofuser.R;
import com.chuyende.hotelbookingappofuser.adapters.BinhLuanAdapter;
import com.chuyende.hotelbookingappofuser.adapters.PhotoAdapter;
import com.chuyende.hotelbookingappofuser.adapters.TienNghiAdapter;
import com.chuyende.hotelbookingappofuser.data_models.DanhGia;
import com.chuyende.hotelbookingappofuser.data_models.Phong;
import com.chuyende.hotelbookingappofuser.data_models.TienNghi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import java.util.Arrays;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class ManHinhChiTiet extends AppCompatActivity implements OnMapReadyCallback {

    // Khai bao map
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    public static final int REQUEST_CODE = 101;

    // Viewpager
    ViewPager viewPagerBoSuuTap;
    PhotoAdapter photoAdapter;
    List<Uri> mListPhoto;
    List<Uri> dvListDichVu2;
    ImageButton ibHeart;
    CircleIndicator circleIndicator, circleIndicatordv;
    Timer mTimer;
    Boolean iconYeuThich = false;

    TextView txtTenPhong, txtDiaChi, txtGia, txtSoNguoi, txtMoTa;
    RatingBar rtPhong, rtDanhGia, rtDedanhgia;
    ImageView imgPhoTo, imgBinhLuan, imgDichvu;
    EditText edtLoiBinhLuan;
    Button btnBinhluan;

    TienNghiAdapter tienNghiAdapter;
    List<TienNghi> mListTienNghis;
    RecyclerView rcvTienNghi;
    RecyclerView.LayoutManager layoutManager;

    RecyclerView rcvBinhLuan;
    ArrayList<DanhGia> listDanhGia;
    BinhLuanAdapter binhLuanAdapter;
    Button btnDatNgay;
    GoogleMap googleMap;

    // Firebase
    FirebaseFirestore db;
    FirebaseStorage storage;
    StorageReference storageReference;
    CollectionReference DanhGiaRef;

    //Binh luan
    public static final String NOI_DUNG_COMMENT = "noiDungComment";
    public static final String RATING_NGUOI_DUNG = "ratingNguoiDung";
    public static final String MA_NGUOI_DUNG_DG = "maNguoiDung";
    public static final String MA_PHONG_DG = "maPhong";
    public static final String COLLECTION_DANH_GIA = "DanhGia";
    public static final String DANH_GIA = "DG02";


    // A room
    public static final String TAG = "ManHinhChiTiet";
    public static final String COLLECTION_PHONG = "Phong";
    public static final String MA_PHONG = "KS010WBuLfIBmX55ssYoGq3U";
    public static final String MA_NGUOI_DUNG = "ND01";
    public static String pathBoSuuTap = "";


    // Config TienNghi
    public static final String COLLECTION_TIEN_NGHI = "TienNghi";
    public static final String FIELD_MA_TIEN_NGHI = "maTienNghi";
    public static final String FIELD_TIEN_NGHI = "tienNghi";
    public static final String FIELD_ICON_TIEN_NGHI = "iconTienNghi";
    private ViewPager viewPagerDv;

    @Override
    protected void onStart() {
        super.onStart();

        // Read data a room from Firebase Firestore
        readDataOfARoom(COLLECTION_PHONG, MA_PHONG);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chi_tiet);
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        DanhGiaRef = db.collection("DanhGia");
        mListPhoto = new ArrayList<Uri>();
        dvListDichVu2 = new ArrayList<Uri>();
        mListTienNghis = new ArrayList<TienNghi>();

        // Find all view from layout
        setControl();

        // Read data a room from Firebase Firestore
        readDataOfARoom(COLLECTION_PHONG, MA_PHONG);
        readBoSuuTapAnh("/media/phong/KS010WBuLfIBmX55ssYoGq3U/boSuuTap");
        // Map
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();
        circleIndicator.setViewPager(viewPagerBoSuuTap);
        circleIndicatordv.setViewPager(viewPagerDv);

        //binh luan
        BinhLuan();
        readDanhGia();


        /*------------------------ Icon yêu thích --------------------- */

        ibHeart.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
        ibHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iconYeuThich) {
                    v.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
                    iconYeuThich = false;
                } else {
                    v.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
                    iconYeuThich = true;
                }
            }
        });



        // Chuyen sang man hinh dat phong
        btnDatNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManHinhChiTiet.this, ManHinhThanhToan.class);
                startActivity(intent);
            }
        });
    }

    // Function get all view from layout
    private void setControl() {
        viewPagerBoSuuTap = findViewById(R.id.viewpager);
        circleIndicator = findViewById(R.id.circle_indicator);
        ibHeart = findViewById(R.id.ibHeart);
        circleIndicatordv = findViewById(R.id.circle_indicator2);
        btnDatNgay = findViewById(R.id.btnDatngay);
        txtSoNguoi = findViewById(R.id.txtsonguoiTD);
        txtTenPhong = findViewById(R.id.txtTenphong);
        txtDiaChi = findViewById(R.id.txtDiachi);
        txtGia = findViewById(R.id.txtGiaphong);
        rtPhong = findViewById(R.id.rbrating);
        txtMoTa = findViewById(R.id.txtMota);
        imgPhoTo = findViewById(R.id.img_photo);
        imgBinhLuan = findViewById(R.id.imgbinhluan);
        edtLoiBinhLuan = findViewById(R.id.edtBinhLuan);
        rtDanhGia = findViewById(R.id.rbratingDG);
        rtDedanhgia = findViewById(R.id.rbDanhgia);
        rcvBinhLuan = findViewById(R.id.recyclerview);
        btnBinhluan = findViewById(R.id.btnBinhluan);
        rcvTienNghi = findViewById(R.id.rcvTienNghi);

    }

    // Get data of room with ID room
    public void readDataOfARoom(String collectionPhong, String maPhong) {
        try {
            db.collection(collectionPhong).document(maPhong).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Phong phong = documentSnapshot.toObject(Phong.class);
                    Log.d("test", "onComplete: " + phong.toString());

                    LatLng latLng = new LatLng(phong.getKinhDo(), phong.getViDo());
                    MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(phong.getTenPhong());
                    googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                    googleMap.addMarker(markerOptions);
                    // Set values to UI
                    txtTenPhong.setText(phong.getTenPhong());
                    txtDiaChi.setText(phong.getDiaChiPhong() + ", " + phong.getMaTinhThanhPho());
                    rtPhong.setRating((float) phong.getRatingPhong());
                    txtGia.setText(phong.getGiaThue() + " VND/đêm");
                    txtMoTa.setText(phong.getMoTaPhong().toString());
                    txtSoNguoi.setText(phong.getSoKhach() + " người");
                    // Bo Suu Tap anh cua phong
                    pathBoSuuTap = phong.getBoSuuTapAnh();
                    Log.d("PATH=>", pathBoSuuTap + " <!--");
                    if (!pathBoSuuTap.trim().equals("")) {
                        readBoSuuTapAnh(pathBoSuuTap);
                    }

                    // Tien nghi cua phong
                    String maTienNghi = phong.getMaTienNghi();
                    if (!maTienNghi.trim().equals("")) {
                        readDataTienNghiARoomWithIdTienNghi(maTienNghi);
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("ERR=>", "Listen boSuuTapAnh is failed! Error: " + e.getMessage());
                }
            });
        } catch (Exception e) {
            Log.d("ERR=>", "Listen data is failed! Error: " + e.getMessage());
        }
    }

    // Get all list images of room
    public void readBoSuuTapAnh(String linkBoSuuTapAnh) {
        Log.d("RUN=>", "Function boSuuTap running! PATH: " + linkBoSuuTapAnh);
        try {
            StorageReference listRef = storage.getReference().child(linkBoSuuTapAnh);
            listRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
                @Override
                public void onSuccess(ListResult listResult) {
                    Log.d("RESULT=>", listResult.getItems().size() + "");

                    // Size of files in boSuuTap bucket
                    int sizeItems = listResult.getItems().size();
                    List<Uri> listUriBoSuuTap = new ArrayList<Uri>();
                    for (StorageReference item : listResult.getItems()) {
                        Log.d("PATH=> =>", item.getPath());

                        item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Log.d("URI=>", "Link download: " + uri);
                                listUriBoSuuTap.add(uri);

                                if (listUriBoSuuTap.size() == sizeItems) {
                                    Log.i("SIZE=>", "Size list = " + listUriBoSuuTap.size() + "");
                                    mListPhoto.addAll(listUriBoSuuTap);

                                    photoAdapter = new PhotoAdapter(getApplicationContext(), mListPhoto);
                                    viewPagerBoSuuTap.setAdapter(photoAdapter);
                                    photoAdapter.notifyDataSetChanged();

                                    circleIndicator.setViewPager(viewPagerBoSuuTap);
                                    photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());

                                    // Auto switch to another Image
                                    autoSlideImages();
                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                Log.i("ERR=>", "Error read collection: " + e.getMessage());

                                Log.i("ERR=>", "Exception Error: " + e.getMessage());

                            }
                        });
                    }

                    Log.i("SIZE=>", listUriBoSuuTap.size() + "");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {


                    Log.d("ERR=>", e.getMessage() + "");
                    Log.d("ERR=>", "Listen bo suu tap: " + e.getMessage() + "");


                    Log.d("ERR=>", "Listen bo suu tap: " + e.getMessage() + "");

                }
            });
        } catch (Exception e) {
            Log.d("ERR=>", "Listen boSuuTap is failed! Error: " + e.getMessage());
        }
    }


    // Get all convenient of room from Firestore
    private void readDataTienNghiARoomWithIdTienNghi(String maTienNghi) {
        // Split maTienNghi
        List<String> listMaTienNghis = new ArrayList<String>();
        listMaTienNghis.addAll(Arrays.asList(maTienNghi.split(",")));

        int sizeListMaTienNghis = listMaTienNghis.size();
        Log.d("SIZE=>", "Size tien nghi: " + sizeListMaTienNghis);

        try {
            List<TienNghi> listTN = new ArrayList<TienNghi>();
            for (String item : listMaTienNghis) {
                db.collection(COLLECTION_TIEN_NGHI).document(item).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        String maTienNghi = (String) documentSnapshot.get(FIELD_MA_TIEN_NGHI);
                        String iconTienNghi = (String) documentSnapshot.get(FIELD_ICON_TIEN_NGHI);
                        String tienNghi = (String) documentSnapshot.get(FIELD_TIEN_NGHI);
                        TienNghi aTienNghi = new TienNghi(maTienNghi, iconTienNghi, tienNghi);

                        Log.d("TN=>", "Mot tien nghi: " + aTienNghi.toString());
                        listTN.add(aTienNghi);

                        Log.d("SIZE=>", "Size listTN: " + listTN.size());
                        if (listTN.size() == sizeListMaTienNghis) {
                            mListTienNghis.clear();
                            mListTienNghis.addAll(listTN);

                            tienNghiAdapter = new TienNghiAdapter(getApplicationContext(), mListTienNghis);
                            rcvTienNghi.setAdapter(tienNghiAdapter);
                            tienNghiAdapter.notifyDataSetChanged();

                            layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                            rcvTienNghi.setLayoutManager(layoutManager);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("ERR=>", "Error: " + e.getMessage());
                    }
                });
            }
        } catch (Exception e) {
            Log.i("ERR=>", "Listen TienNghi is failed! Error: " + e.getMessage());
        }
    }

    public void readDanhGia()
    {
        try {
            db.collection(COLLECTION_DANH_GIA).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if(error != null)
                    {
                        Log.w("DG", "error" + error);
                        return;
                    }

                    listDanhGia = new ArrayList<DanhGia>();
                    for (QueryDocumentSnapshot doc : value)
                    {
                        String maPhong = doc.getString(MA_PHONG_DG);
                        String maNguoiDung =  doc.getString(MA_NGUOI_DUNG_DG);
                        String noiDungComment = doc.getString(NOI_DUNG_COMMENT);
                        double ratingNguoiDung = Double.parseDouble(doc.get(RATING_NGUOI_DUNG).toString());
                        DanhGia danhGia = new DanhGia(maNguoiDung, maPhong, noiDungComment, ratingNguoiDung);

                        Log.d("DGNQ=>", "Mot tien nghi: " + danhGia.toString());
                        listDanhGia.add(danhGia);
                    }
                    binhLuanAdapter = new BinhLuanAdapter(ManHinhChiTiet.this, listDanhGia);
                    rcvBinhLuan.setAdapter(binhLuanAdapter);

                    layoutManager = new LinearLayoutManager(getApplicationContext());
                    rcvBinhLuan.setLayoutManager(layoutManager);


                }
            });
        }
        catch (Exception e)
        {
            Log.i("ERR=>", "Listen Danh Gia is failed! Error: " + e.getMessage());
        }




    }

    // Function auto switch Image in ViewPager
    private void autoSlideImages() {
        if (mListPhoto == null || mListPhoto.isEmpty() || viewPagerBoSuuTap == null) {
            return;
        }
        //Init timer
        if (mTimer == null) {
            mTimer = new Timer();
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = viewPagerBoSuuTap.getCurrentItem();
                        int totalItem = mListPhoto.size() - 1;
                        if (currentItem < totalItem) {
                            currentItem++;
                            viewPagerBoSuuTap.setCurrentItem(currentItem);
                        } else {
                            viewPagerBoSuuTap.setCurrentItem(0);
                        }
                    }
                });
            }
        }, 500, 5000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        double x = 10.983603;
        double y = 108.281124;
        this.googleMap = googleMap;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLastLocation();
                }
                break;
        }
    }

    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }

        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    Toast.makeText(getApplicationContext(), currentLocation.getLatitude() + " " + currentLocation.getLongitude(), Toast.LENGTH_LONG).show();

                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
                    supportMapFragment.getMapAsync(ManHinhChiTiet.this);
                }
            }
        });
    }


    public void BinhLuan() {

        btnBinhluan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noiDungComment = edtLoiBinhLuan.getText().toString();
                int ratingNguoiDung = (int) rtDedanhgia.getRating();
                String maPhong = MA_PHONG;
                String maNguoiDung = MA_NGUOI_DUNG;

                DanhGia danhGia = new DanhGia(maNguoiDung, maPhong, noiDungComment, ratingNguoiDung);

                Log.d("quy", danhGia.toString());


                try {
                    db.collection(COLLECTION_DANH_GIA).document(DANH_GIA).set(danhGia).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ManHinhChiTiet.this, "thanh cong", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ManHinhChiTiet.this, "Error", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, e.toString());
                        }
                    });
                }
                catch (Exception e)
                {
                    Toast.makeText(ManHinhChiTiet.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }
        });


    }







}