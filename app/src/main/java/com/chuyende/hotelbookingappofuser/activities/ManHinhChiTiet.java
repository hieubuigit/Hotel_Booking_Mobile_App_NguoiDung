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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.chuyende.hotelbookingappofuser.R;
import com.chuyende.hotelbookingappofuser.adapters.BinhLuanAdapter;
import com.chuyende.hotelbookingappofuser.adapters.DichVuAdapter;
import com.chuyende.hotelbookingappofuser.adapters.PhotoAdapter;
import com.chuyende.hotelbookingappofuser.data_models.BinhLuan;
import com.chuyende.hotelbookingappofuser.data_models.Phong;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
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
    ViewPager viewPagerBoSuuTap, viewPagerDv;
    PhotoAdapter photoAdapter;
    List<Uri> mListPhoto;
    DichVuAdapter dichvuAdapter;
    List<Uri> dvListDichVu;

    ImageButton ibHeart;
    CircleIndicator circleIndicator, circleIndicatordv;
    Timer mTimer;
    Boolean iconYeuThich = false;

    TextView txtTenPhong, txtDiachi, txtGia, txtSoNguoi, txtMoTa;
    RatingBar rtPhong;
    ImageView imgPhoto;

    RecyclerView recyclerView;
    ArrayList<BinhLuan> listBinhLuan;
    BinhLuanAdapter binhLuanAdapter;
    Button btnDatNgay;
    GoogleMap googleMap;

    // Firebase
    FirebaseFirestore db;
    FirebaseStorage storage;

    // A room
    public static final String COLLECTION_PHONG = "Phong";
    public static final String MA_PHONG = "KS010WBuLfIBmX55ssYoGq3U";
    public static String pathBoSuuTap = "";

    @Override
    protected void onStart() {
        super.onStart();

        // Read data a room from Firebase Firestore
        readDataOfARoom(COLLECTION_PHONG, MA_PHONG);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinhchitiet);

        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        mListPhoto = new ArrayList<Uri>();

        // Find all view from layout
        setControl();

        // Map
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();


        /* ----------------- Set images dich vu ----------------- */
        //dvListDichVu = getDvListDichVu();
        //dichvuAdapter = new DichVuAdapter(this, dvListDichVu);
        //viewPagerDv.setAdapter(dichvuAdapter);
        circleIndicatordv.setViewPager(viewPagerDv);
        //dichvuAdapter.registerDataSetObserver(circleIndicatordv.getDataSetObserver());


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


        /*------------- Recycler View Binh Luan ---------------*/
        listBinhLuan = new ArrayList<>();
        listBinhLuan.add(new BinhLuan("Khach san rat tot", R.drawable.hinhgai1));
        listBinhLuan.add(new BinhLuan("Rat la ok", R.drawable.hinhgai2));
        binhLuanAdapter = new BinhLuanAdapter(getApplicationContext(), listBinhLuan);
        recyclerView.setAdapter(binhLuanAdapter);


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
        viewPagerDv = findViewById(R.id.viewpager2);
        circleIndicatordv = findViewById(R.id.circle_indicator2);
        btnDatNgay = findViewById(R.id.btnDatngay);
        txtSoNguoi = findViewById(R.id.txtsonguoiTD);
        txtTenPhong = findViewById(R.id.txtTenphong);
        txtDiachi = findViewById(R.id.txtDiachi);
        txtGia = findViewById(R.id.txtGiaphong);
        rtPhong = findViewById(R.id.rbrating);
        txtMoTa = findViewById(R.id.txtMota);
        imgPhoto = findViewById(R.id.img_photo);
        recyclerView = findViewById(R.id.recyclerview);
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
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                    googleMap.addMarker(markerOptions);

                    String[] tienIch = phong.getMaTienNghi().split(",");
                    for (String v : tienIch) {
                        Log.d("test", "onComplete: " + v);
                    }

                    txtTenPhong.setText(phong.getTenPhong());
                    txtDiachi.setText(phong.getDiaChiPhong() + ", "  +phong.getMaTinhThanhPho());
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

                    // Image cac tien nghi o day

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
                    Log.d("RESULT=>", listResult.getItems().size()+"");

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
                                    Log.i("SIZE=>", "Size list = " + listUriBoSuuTap.size()+"");
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
                                Log.i("ERR=>", "Exception Error: " + e.getMessage());
                            }
                        });
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("ERR=>", "Listen bo suu tap: " + e.getMessage()+"");
                }
            });
        } catch (Exception e) {
            Log.d("ERR=>", "Listen boSuuTap is failed! Error: " + e.getMessage());
        }
    }

    // Get all icons of convenient of room
    private List<Uri> readDataTienNghiARoom() {
        List<Uri> listUrisIconTienNghi = new ArrayList<Uri>();
        // Code here
        return listUrisIconTienNghi;
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

        // LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
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
    }

}