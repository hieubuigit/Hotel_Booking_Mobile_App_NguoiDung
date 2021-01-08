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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chi_tiet);
    }
}