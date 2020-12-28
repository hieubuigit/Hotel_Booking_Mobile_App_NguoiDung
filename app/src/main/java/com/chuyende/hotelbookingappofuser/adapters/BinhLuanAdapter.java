package com.chuyende.hotelbookingappofuser.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chuyende.hotelbookingappofuser.R;
import com.chuyende.hotelbookingappofuser.data_models.BinhLuan;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class BinhLuanAdapter extends RecyclerView.Adapter<BinhLuanAdapter.ViewHolder> {
    private static final int HEADER = 0;
    private static final int CONTENT = 1;
    Context context;
    ArrayList<BinhLuan> listBinhLuan;

    public BinhLuanAdapter(Context context, ArrayList<BinhLuan> listBinhLuan) {
        this.context = context;
        this.listBinhLuan = listBinhLuan;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // gán view
        View view = LayoutInflater.from(context).inflate(R.layout.iten_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Gán dữ liêu
        BinhLuan binhluan = listBinhLuan.get(position);
        holder.txtloibinhluan.setText(binhluan.getNoidungBL());
        Locale locale = new Locale("vn", "VN");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        holder.imgAvatar.setImageResource(binhluan.getHinhanhBL());
    }

    @Override
    public int getItemCount() {
        return listBinhLuan.size(); // trả item tại vị trí postion
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAvatar;
        TextView txtloibinhluan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ view
            imgAvatar = itemView.findViewById(R.id.imgbinhluan);
            txtloibinhluan = itemView.findViewById(R.id.txtloibinhluan);
        }
    }

}




