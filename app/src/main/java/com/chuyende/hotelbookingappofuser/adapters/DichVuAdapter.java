package com.chuyende.hotelbookingappofuser.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.chuyende.hotelbookingappofuser.R;
import com.chuyende.hotelbookingappofuser.activities.ManHinhChiTiet;
import com.chuyende.hotelbookingappofuser.data_models.DichVu;

import java.util.BitSet;
import java.util.List;

public class DichVuAdapter extends PagerAdapter {


    private Context dvContext;
    private List<Uri> dvListDichVu;

    public DichVuAdapter(ManHinhChiTiet mContext, List<String> dvListDichVu2) {
    }

    public DichVuAdapter(Context dvContext, List<Uri> dvListDichVu) {
        this.dvContext = dvContext;
        this.dvListDichVu = dvListDichVu;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_dichvu, container, false);

        ImageView imgDichvu = view.findViewById(R.id.img_Dichvu);
        TextView  txtTenDV = view.findViewById(R.id.txtTenDV);

        Uri uriDichvu = dvListDichVu.get(position);

        if(!uriDichvu.toString().equals(" ")){
            Glide.with(container).load(uriDichvu).into(imgDichvu);


        }


        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
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
