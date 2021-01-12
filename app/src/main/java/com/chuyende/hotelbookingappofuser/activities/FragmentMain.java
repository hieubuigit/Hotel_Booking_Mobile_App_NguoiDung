package com.chuyende.hotelbookingappofuser.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.chuyende.hotelbookingappofuser.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import static com.chuyende.hotelbookingappofuser.activities.ManHinhDangNhap.TENTK;

public class FragmentMain extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    public static String TENTKND = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fragment);

        // Get data on Intent from ManHinhDangNhap
        Bundle bundle = getIntent().getExtras();
        TENTKND = bundle.getString(TENTK);

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
                    fragment = new YeuThichFragment();
                    break;
                case R.id.item_Account:
                    fragment = new TaiKhoanNguoiDungFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            return true;
        }
    };
}