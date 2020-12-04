package com.chuyende.hotelbookingappofuser.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ScrollView;

import com.chuyende.hotelbookingappofuser.R;
import com.chuyende.hotelbookingappofuser.adapters.Adapter_ExpandableHeightGridView;
import com.chuyende.hotelbookingappofuser.adapters.Adapter_GridView_Phong;
import com.chuyende.hotelbookingappofuser.data_models.ClsPhong;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Man_Hinh_Ket_Qua_Tim_Kiem extends AppCompatActivity {
    ScrollView scrollView;
    Adapter_ExpandableHeightGridView gridView;
    BottomNavigationView bottomNavigationView;
    Adapter_GridView_Phong adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man__hinh__ket__qua__tim__kiem);
        setControl();
        setEvent();
    }
    private void setEvent() {
        // gridview xem phong
        ArrayList<ClsPhong> arrListPhong = get_Data_Phong();
        adapter = new Adapter_GridView_Phong(this, R.layout.listview_item, arrListPhong);
        gridView.setAdapter(adapter);
        gridView.setExpanded(true);
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
    // Ánh xạ
    private void setControl() {
        // grid view
        gridView = findViewById(R.id.gridViewMain);
        // bottomnavigation
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        // scrollview
        scrollView = findViewById(R.id.scrollView);
        // gridview full height
    }
}