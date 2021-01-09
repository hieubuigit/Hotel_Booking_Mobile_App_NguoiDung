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
import com.chuyende.hotelbookingappofuser.data_models.DanhGia;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class BinhLuanAdapter extends RecyclerView.Adapter<BinhLuanAdapter.ViewHolder> {
    private static final int HEADER = 0;
    private static final int CONTENT = 1;
    Context context;
    ArrayList<DanhGia> listDanhGia;

    public BinhLuanAdapter(Context context, ArrayList<DanhGia> listDanhGia) {
        this.context = context;
        this.listDanhGia = listDanhGia;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // gán view
        View view = LayoutInflater.from(context).inflate(R.layout.item_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Gán dữ liêu
        DanhGia danhgia = listDanhGia.get(position);
        holder.txtLoiBinhLuan.setText(danhgia.getNoiDungComment());
        holder.rtbBinhLuan.setRating((int) danhgia.getRatingNguoiDung());
        Locale locale = new Locale("vn", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        holder.imgAvatar.setImageResource(R.drawable.ic_avatar_default);
    }

    @Override
    public int getItemCount() {
        return listDanhGia.size(); // trả item tại vị trí postion
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView txtLoiBinhLuan;
        RatingBar rtbBinhLuan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ view
            imgAvatar = itemView.findViewById(R.id.imgbinhluan);
            txtLoiBinhLuan = itemView.findViewById(R.id.edtBinhLuan);
            rtbBinhLuan = itemView.findViewById(R.id.rbratingDG);

        }
    }

}




