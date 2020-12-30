package com.chuyende.hotelbookingappofuser.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.chuyende.hotelbookingappofuser.R;
import com.chuyende.hotelbookingappofuser.data_models.DichVu;

import java.util.BitSet;
import java.util.List;

public class DichVuAdapter extends PagerAdapter {

    private Context dvContext;
    private List<Bitmap> dvListDichVu;

    public DichVuAdapter(Context dvContext, List<Bitmap> dvListDichVu) {
        this.dvContext = dvContext;
        this.dvListDichVu = dvListDichVu;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_dichvu, container, false);
        ImageView imgLiveTV = view.findViewById(R.id.img_liveTV);
        //ImageView imghandbail = view.findViewById(R.id.img_handbail);
        //ImageView imgLocalCafe = view.findViewById(R.id.img_localCafe);
        //ImageView imgrestaurant = view.findViewById(R.id.img_restaurant);

        Bitmap dichvu = dvListDichVu.get(position);
        if (dichvu != null) {
            //Glide.with(dvContext).load(dichvu.getResourceId()).into(imgLiveTV);
            //Glide.with(dvContext).load(dichvu.getResourceId()).into(imghandbail);
            // Glide.with(dvContext).load(dichvu.getResourceId()).into(imgLocalCafe);
            // Glide.with(dvContext).load(dichvu.getResourceId()).into(imgrestaurant);
        }

        //chu ý Add to viewgroup
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        if (dvListDichVu != null) {
            return dvListDichVu.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
