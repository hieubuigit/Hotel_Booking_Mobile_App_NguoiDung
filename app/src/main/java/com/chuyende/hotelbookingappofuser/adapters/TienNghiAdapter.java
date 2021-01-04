package com.chuyende.hotelbookingappofuser.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chuyende.hotelbookingappofuser.R;
import com.chuyende.hotelbookingappofuser.data_models.TienNghi;

import java.util.List;

public class TienNghiAdapter extends RecyclerView.Adapter<TienNghiAdapter.TienNghiHolder> {
    Context mContext;
    List<TienNghi> listTienNghis;

    public TienNghiAdapter() {
    }

    public TienNghiAdapter(Context mContext, List<TienNghi> listTienNghis) {
        this.mContext = mContext;
        this.listTienNghis = listTienNghis;
    }

    public static class TienNghiHolder extends RecyclerView.ViewHolder {
        ImageView imvTienNghi;
        TextView tvTienNghi;

        public TienNghiHolder(@NonNull View itemView) {
            super(itemView);

            // Find all view from UI
            imvTienNghi = itemView.findViewById(R.id.imvIconTienNghi);
            tvTienNghi = itemView.findViewById(R.id.tvTienNghi);
        }
    }

    @NonNull
    @Override
    public TienNghiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tien_nghi, parent, false);
        return new TienNghiHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TienNghiHolder holder, int position) {
        TienNghi aTienNghi = listTienNghis.get(position);

        if (!aTienNghi.getIconTienNghi().equals("") && !aTienNghi.getTienNghi().trim().equals("")) {
            Glide.with(mContext).load(aTienNghi.getIconTienNghi()).into(holder.imvTienNghi);
            holder.tvTienNghi.setText(aTienNghi.getTienNghi());
        }
    }

    @Override
    public int getItemCount() {
        return listTienNghis.size();
    }
}
