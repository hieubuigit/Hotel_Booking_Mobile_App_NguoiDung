package com.chuyende.hotelbookingappofuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.chuyende.hotelbookingappofuser.activities.frame_ManHinhNha;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation); // khai bao layout fragment chính (activity_man layout)
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.farme_container, new frame_ManHinhNha()).commit(); // chạy màn hình nhà dầu9 tiên
    }
    // click chon bottom navigation
    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    androidx.fragment.app.Fragment fragment =null;
                    switch (item.getItemId()){
                        case R.id.home:
                            fragment = new frame_ManHinhNha();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.farme_container, fragment).commit();
                    return true;
                }
            };
}