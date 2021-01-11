package com.chuyende.hotelbookingappofuser.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chuyende.hotelbookingappofuser.R;
import com.chuyende.hotelbookingappofuser.data_models.Phong;

import java.util.ArrayList;

public class PhongYeuThichAdapter extends RecyclerView.Adapter<PhongYeuThichAdapter.PhongYeuThichHolder> {
    ArrayList<Phong> listPhongs = new ArrayList<Phong>();
    Context context;

    // Constructor
    public PhongYeuThichAdapter(ArrayList<Phong> listPhongs, Context context) {
        this.listPhongs = listPhongs;
        this.context = context;
    }

    @NonNull
    @Override
    public PhongYeuThichHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PhongYeuThichHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_item_recyclerview_phong_yeu_thich, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PhongYeuThichHolder holder, int position) {
        Phong phong = listPhongs.get(position);
        // holder.imvAnhDaiDienPhong.setImageResource();
        holder.tvTenPhong.setText(phong.getTenPhong());
        holder.tvDiaChiPhong.setText(phong.getDiaChiPhong());
        holder.ratRating.setNumStars(1);
        holder.tvGiaThue.setText(String.valueOf(phong.getGiaThue()));
    }

    @Override
    public int getItemCount() {
        return listPhongs.size();
    }

    // Create holder
    public static class PhongYeuThichHolder extends RecyclerView.ViewHolder {
        ImageView imvAnhDaiDienPhong;
        TextView tvTenPhong;
        TextView tvDiaChiPhong;
        RatingBar ratRating;
        TextView tvGiaThue;

        // Constructor
        public PhongYeuThichHolder(@NonNull View itemView) {
            super(itemView);
            imvAnhDaiDienPhong = itemView.findViewById(R.id.imvAnhDaiDienPhong);
            tvTenPhong = itemView.findViewById(R.id.tvTenPhong);
            tvDiaChiPhong = itemView.findViewById(R.id.tvDiaChiPhong);
            ratRating = itemView.findViewById(R.id.ratRating);
            tvGiaThue = itemView.findViewById(R.id.tvGiaThue);
        }
    }
}
