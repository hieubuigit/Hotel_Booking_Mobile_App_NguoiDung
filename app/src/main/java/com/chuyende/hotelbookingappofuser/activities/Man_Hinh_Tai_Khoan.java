package com.chuyende.hotelbookingappofuser.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.chuyende.hotelbookingappofuser.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Man_Hinh_Tai_Khoan extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man__hinh__tai__khoan);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.item_Account);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_Home:
                        startActivity(new Intent(getApplicationContext(),Man_Hinh_Nha.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.item_Favorite:
                        startActivity(new Intent(getApplicationContext(),Man_Hinh_Yeu_Thich.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.item_Account:
                        return true;
                }
                return false;
            }
        });
    }
}