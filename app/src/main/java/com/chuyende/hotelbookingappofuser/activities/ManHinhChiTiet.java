package com.chuyende.hotelbookingappofuser.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.bumptech.glide.Glide;
import com.chuyende.hotelbookingappofuser.R;
import com.chuyende.hotelbookingappofuser.adapters.BinhLuanAdapter;
import com.chuyende.hotelbookingappofuser.adapters.DichVuAdapter;
import com.chuyende.hotelbookingappofuser.adapters.PhotoAdapter;
import com.chuyende.hotelbookingappofuser.data_models.BinhLuan;
import com.chuyende.hotelbookingappofuser.data_models.DichVu;
import com.chuyende.hotelbookingappofuser.data_models.Phong;
import com.chuyende.hotelbookingappofuser.data_models.Photo;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
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
    private static final int REQUEST_CODE = 101;

    ViewPager viewPager, viewPagerdv;
    ImageButton ibHeart;
    CircleIndicator circleIndicator, circleIndicatordv;
    PhotoAdapter photoAdapter;
    DichVuAdapter dichvuAdapter;
    List<DichVu> dvListDichVu;
    List<Photo> mListPhoto;
    Timer mTimer;
    Boolean iconyeuthich = false;

    TextView txtTenphong, txtDiachi, txtGia, txtSonguoi, txtMoTa;
    RatingBar rbDanhGia;
    ImageView imgPhoto;

    RecyclerView recyclerView;
    ArrayList<BinhLuan> listbinhluan;
    BinhLuanAdapter binhluanAdapter;
    Button btndatngay;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    GoogleMap googleMap;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinhchitiet);

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        //View page
//        getImage();

        //data base
        getData();
        setControl();


        //map
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();

        // Viewpage
        mListPhoto = getListPhoto();
        photoAdapter = new PhotoAdapter(this, mListPhoto);
        viewPager.setAdapter(photoAdapter);

        circleIndicator.setViewPager(viewPager);
        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());

        //dich vu:
        dvListDichVu = getDvListDichVu();
        dichvuAdapter = new DichVuAdapter(this, dvListDichVu);
        viewPagerdv.setAdapter(dichvuAdapter);

        circleIndicatordv.setViewPager(viewPagerdv);
        dichvuAdapter.registerDataSetObserver(circleIndicatordv.getDataSetObserver());

        //ham auto hinhanh
        autoSlideImages();

        //phần icon yêu thích
        ibHeart.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
        ibHeart.setOnClickListener(Heart);

        //custom recycler binh luan
        recyclerView = findViewById(R.id.recyclerview);
        listbinhluan = new ArrayList<>();
        listbinhluan.add(new BinhLuan("khach san rat tot", R.drawable.hinhgai1));
        listbinhluan.add(new BinhLuan("rat la ok", R.drawable.hinhgai2));
        binhluanAdapter = new BinhLuanAdapter(getApplicationContext(), listbinhluan);
        recyclerView.setAdapter(binhluanAdapter);

        // kết nối qua man hinh thanh toan
        btndatngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open();
            }
        });
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

    //hàm  click để chọn yêu thích
    View.OnClickListener Heart = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (iconyeuthich) {
                view.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
            } else {

                view.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
            }
        }
    };

    // ham bỏ yêu thich
//    View.OnClickListener Noheart = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            if (iconyeuthich)
//            {
//                view.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
//            }
//            else{
//                view.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
//            }
//        }
//    };

    private void setControl() {
        viewPager = findViewById(R.id.viewpager);
        circleIndicator = findViewById(R.id.circle_indicator);
        ibHeart = findViewById(R.id.ibHeart);
        viewPagerdv = findViewById(R.id.viewpager2);
        circleIndicatordv = findViewById(R.id.circle_indicator2);
        btndatngay = findViewById(R.id.btnDatngay);
        txtSonguoi = findViewById(R.id.txtsonguoiTD);
        txtTenphong = findViewById(R.id.txtTenphong);
        txtDiachi = findViewById(R.id.txtDiachi);
        txtGia = findViewById(R.id.txtGiaphong);
        rbDanhGia = findViewById(R.id.rbrating);
        txtMoTa = findViewById(R.id.txtMota);
        imgPhoto = findViewById(R.id.img_photo);
    }

    private List<Photo> getListPhoto() {

        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.hinh1));
        list.add(new Photo(R.drawable.hinh2));
        list.add(new Photo(R.drawable.hinh3));
        list.add(new Photo(R.drawable.hinh4));

        return list;
    }

    private List<DichVu> getDvListDichVu() {
        List<DichVu> list = new ArrayList<>();
        list.add(new DichVu(R.drawable.ic_baseline_live_tv_24));
        list.add(new DichVu(R.drawable.ic_baseline_local_cafe_24));
        list.add(new DichVu(R.drawable.ic_baseline_sports_handball_24));
        list.add(new DichVu(R.drawable.ic_baseline_restaurant_24));
        list.add(new DichVu(R.drawable.ic_baseline_rowing_24));
        list.add(new DichVu(R.drawable.ic_baseline_shopping_cart_24));

        return list;
    }

    private void autoSlideImages() {
        if (mListPhoto == null || mListPhoto.isEmpty() || viewPager == null) {
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
                        int currentItem = viewPager.getCurrentItem();
                        int totalItem = mListPhoto.size() - 1;
                        if (currentItem < totalItem) {
                            currentItem++;
                            viewPager.setCurrentItem(currentItem);
                        } else {
                            viewPager.setCurrentItem(0);
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

    public void open() {
        Intent intent = new Intent(this, ManHinhThanhToan.class);
        startActivity(intent);
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

    public void getData() {
        db.collection("Phong").document("KS010WBuLfIBmX55ssYoGq3U").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Phong phong = task.getResult().toObject(Phong.class);
                Log.d("test", "onComplete: " + phong.toString());
                LatLng latLng = new LatLng(phong.getKinhDo(), phong.getViDo());
                MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(phong.getTenPhong());
                googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));
                googleMap.addMarker(markerOptions);

                String[] tienIch = phong.getMaTienNghi().split(",");
                for (String v : tienIch) {
                    Log.d("test", "onComplete: " + v);

                }
                txtTenphong.setText(phong.getTenPhong());
                txtDiachi.setText(phong.getDiaChiPhong());
                txtGia.setText(phong.getGiaThue().toString());
                txtSonguoi.setText("" + phong.getSoKhach());
                rbDanhGia.setRating((float) phong.getRatingPhong());
                txtMoTa.setText(phong.getMoTaPhong().toString());

                // lấy hình vào viewpage


                task.getResult().getId();
                //intent
            }
        });
    }

    public void BinhLuan() {
    }

//    public void getImage()
//    {
//        storageReference.child("/media/phong/KS010WBuLfIBmX55ssYoGq3U/boSuuTap").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//            @Override
//            public void onSuccess(Uri uri) {
//                Glide.with(ManHinhChiTiet.this)
//                        .load(uri)
//                        .into((ImageView) getListPhoto());
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//                // Handle any errors
//            }
//        });
//
//    }

}