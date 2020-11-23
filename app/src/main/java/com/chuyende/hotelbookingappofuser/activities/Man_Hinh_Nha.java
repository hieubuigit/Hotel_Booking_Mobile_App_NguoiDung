package com.chuyende.hotelbookingappofuser.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.ScrollView;

import com.chuyende.hotelbookingappofuser.R;
import com.chuyende.hotelbookingappofuser.adapters.Adapter_GridView_Phong;
import com.chuyende.hotelbookingappofuser.data_models.ClsPhong;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Man_Hinh_Nha extends AppCompatActivity {
    GridView gridView;
    ScrollView scrollView;
    AutoCompleteTextView AutoCompleteTextView;
    ArrayAdapter arrayAdapter;
    Adapter_GridView_Phong adapter;
    BottomNavigationView bottomNavigationView; // khai bao layout fragment chính (activity_man layout)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man__hinh__nha);
        setControl();
        setEvent();
    }
    private void setEvent() {
        // gridview xem phong
        ArrayList<ClsPhong> arrListPhong = get_Data_Phong();
        adapter = new Adapter_GridView_Phong(this,R.layout.listview_item, arrListPhong);
        gridView.setAdapter(adapter);

        // multiautpcomplete search
        ArrayList arrayList = new ArrayList();
        String getenphong = "";
        for (int i = 0; i < arrListPhong.size(); i++)
        {
            getenphong = arrListPhong.get(i).getTenPhong();
            arrayList.add(getenphong);
        }
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1,arrayList);
        AutoCompleteTextView.setAdapter(arrayAdapter);
        AutoCompleteTextView.setThreshold(1);
//        // The text separated by commas
//        AutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());


        // bottom navigation
        bottomNavigationView.setSelectedItemId(R.id.item_Home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_Home:
                        return true;
                    case R.id.item_Favorite:
                        startActivity(new Intent(getApplicationContext(),Man_Hinh_Yeu_Thich.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.item_Account:
                        startActivity(new Intent(getApplicationContext(),Man_Hinh_Tai_Khoan.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
    
    public ArrayList<ClsPhong> get_Data_Phong(){
        ArrayList<ClsPhong> arrayList = new ArrayList<ClsPhong>();
        ClsPhong phong1 = new ClsPhong(R.drawable.phong,"room 1","240000VND/đêm", (float) 4.5);
        ClsPhong phong2 = new ClsPhong(R.drawable.phong,"room 2","340000VND/đêm", (float) 3.5);
        ClsPhong phong3 = new ClsPhong(R.drawable.phong,"room 3","140000VND/đêm", (float) 2.5);
        ClsPhong phong4 = new ClsPhong(R.drawable.phong,"room 4","190000VND/đêm", (float) 1.0);
        ClsPhong phong5 = new ClsPhong(R.drawable.phong,"room 5","190000VND/đêm", (float) 1.0);
        arrayList.add(phong1);
        arrayList.add(phong2);
        arrayList.add(phong3);
        arrayList.add(phong4);
        arrayList.add(phong5);
        return arrayList;
    }
    private void setControl() {
        gridView = findViewById(R.id.gridViewMain);
        AutoCompleteTextView = findViewById(R.id.multiauto_Search);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

    }

}