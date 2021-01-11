package com.chuyende.hotelbookingappofuser.activities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chuyende.hotelbookingappofuser.R;

public class YeuThichFragment extends Fragment {
    TextView tvTieuDe;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = null;
        v = inflater.inflate(R.layout.fragment_yeu_thich, container, false);

        // Get all view from layout
        tvTieuDe = v.findViewById(R.id.tvTieuDe);
        recyclerView = v.findViewById(R.id.rcvPhongYeuThich);

        tvTieuDe.setText(R.string.titleToolbar);
        return v;
    }
}