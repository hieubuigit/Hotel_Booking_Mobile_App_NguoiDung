package com.chuyende.hotelbookingappofuser.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.chuyende.hotelbookingappofuser.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FragmentMain extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(selectItem);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new FragmentManHinhNha()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener selectItem = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.item_Home:
                    fragment = new FragmentManHinhNha();
                    break;
                case R.id.item_Favorite:
                    fragment = new FragmentManHinhYeuThich();
                    break;
                case R.id.item_Account:
                    fragment = new FragmentManHinhTaiKhoan();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            return true;
        }
    };
}