package com.chuyende.hotelbookingappofuser.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.chuyende.hotelbookingappofuser.Adapter.BinhluanAdapter;
import com.chuyende.hotelbookingappofuser.Adapter.DichvuAdapter;
import com.chuyende.hotelbookingappofuser.Adapter.PhotoAdapter;
import com.chuyende.hotelbookingappofuser.Model.Binhluan;
import com.chuyende.hotelbookingappofuser.Model.Dichvu;
import com.chuyende.hotelbookingappofuser.Model.Photo;
import com.chuyende.hotelbookingappofuser.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class Manhinhchitiet extends AppCompatActivity implements OnMapReadyCallback {

    //khai bao map
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;

    ViewPager viewPager, viewPagerdv;
    ImageButton ibHeart;
    CircleIndicator circleIndicator, circleIndicatordv;
    PhotoAdapter photoAdapter;
    DichvuAdapter dichvuAdapter;
    List<Dichvu> dvListDichvu;
    List<Photo> mListPhoto;
    Timer mTimer;
    Boolean iconyeuthich = false;

    RecyclerView recyclerView;
    ArrayList<Binhluan> listbinhluan;
    BinhluanAdapter binhluanAdapter;
    Button btndatngay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinhchitiet);


        setControl();


        //map

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLastLocation();


        mListPhoto = getListPhoto();
        photoAdapter = new PhotoAdapter(this, mListPhoto);
        viewPager.setAdapter(photoAdapter);

        circleIndicator.setViewPager(viewPager);
        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());

        //dich vu:
        dvListDichvu = getDvListDichvu();
        dichvuAdapter = new DichvuAdapter(this, dvListDichvu);
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
        listbinhluan.add(new Binhluan("khach san rat tot", R.drawable.hinhgai1));
        listbinhluan.add(new Binhluan("rat la ok", R.drawable.hinhgai2));
        binhluanAdapter = new BinhluanAdapter(getApplicationContext(), listbinhluan);
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
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);

            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    currentLocation = location;
                    Toast.makeText(getApplicationContext(), currentLocation.getLatitude() +" "+ currentLocation.getLongitude(), Toast.LENGTH_LONG).show();

                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);

                    supportMapFragment.getMapAsync(Manhinhchitiet.this);
                }
            }
        });
    }


    //hàm  click để chọn yêu thích
    View.OnClickListener Heart = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (iconyeuthich){
                view.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
            }
            else {
                view.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
            }

        }
    };

    private void setControl() {
        viewPager = findViewById(R.id.viewpager);
        circleIndicator = findViewById(R.id.circle_indicator);
        ibHeart = findViewById(R.id.ibHeart);
        viewPagerdv = findViewById(R.id.viewpager2);
        circleIndicatordv = findViewById(R.id.circle_indicator2);
        btndatngay = findViewById(R.id.btnDatngay);


    }


    private List<Photo> getListPhoto() {
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.hinh1));
        list.add(new Photo(R.drawable.hinh2));
        list.add(new Photo(R.drawable.hinh3));
        list.add(new Photo(R.drawable.hinh4));

        return list;
    }
    private List<Dichvu> getDvListDichvu(){
        List<Dichvu> list = new ArrayList<>();
        list.add(new Dichvu(R.drawable.ic_baseline_live_tv_24));
        list.add(new Dichvu(R.drawable.ic_baseline_local_cafe_24));
        list.add(new Dichvu(R.drawable.ic_baseline_sports_handball_24));
        list.add(new Dichvu(R.drawable.ic_baseline_restaurant_24));
        list.add(new Dichvu(R.drawable.ic_baseline_rowing_24));
        list.add(new Dichvu(R.drawable.ic_baseline_shopping_cart_24));

        return list;
    }

    private void autoSlideImages(){
        if(mListPhoto == null || mListPhoto.isEmpty() || viewPager == null){
            return;
        }
        //Init timer
        if(mTimer==null){
            mTimer = new Timer();
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem =  viewPager.getCurrentItem();
                        int totalItem = mListPhoto.size() - 1;
                        if(currentItem<totalItem){
                            currentItem++;
                            viewPager.setCurrentItem(currentItem);
                        } else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        },  500, 5000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mTimer !=null){
            mTimer.cancel();
            mTimer = null;
        }
    }
    public void open(){
        Intent intent = new Intent(this, Manhinhthanhtoan.class);
        startActivity(intent);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("I Am Here");

        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));
        googleMap.addMarker(markerOptions);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE:
                if(grantResults.length > 0 && grantResults[0]  == PackageManager.PERMISSION_GRANTED ){
                    fetchLastLocation();
                }
                break;
        }
    }
}