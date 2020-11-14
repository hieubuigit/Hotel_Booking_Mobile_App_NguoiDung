package com.chuyende.hotelbookingappofuser.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.chuyende.hotelbookingappofuser.Model.Dichvu;
import com.chuyende.hotelbookingappofuser.Model.Photo;
import com.chuyende.hotelbookingappofuser.R;


import java.util.List;

public class DichvuAdapter extends PagerAdapter {

    private Context dvContext;
    private List<Dichvu> dvListDichvu;

    public DichvuAdapter(Context dvContext, List<Dichvu> dvListDichvu){
        this.dvContext = dvContext;
        this.dvListDichvu = dvListDichvu;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_dichvu, container, false);
        ImageView imgLiveTV = view.findViewById(R.id.img_liveTV);
//        ImageView imghandbail = view.findViewById(R.id.img_handbail);
//        ImageView imgLocalCafe = view.findViewById(R.id.img_localCafe);
//        ImageView imgrestaurant = view.findViewById(R.id.img_restaurant);

        Dichvu dichvu = dvListDichvu.get(position);
        if(dichvu != null){
            Glide.with(dvContext).load(dichvu.getResourceId()).into(imgLiveTV);
//            Glide.with(dvContext).load(dichvu.getResourceId()).into(imghandbail);
//            Glide.with(dvContext).load(dichvu.getResourceId()).into(imgLocalCafe);
//            Glide.with(dvContext).load(dichvu.getResourceId()).into(imgrestaurant);
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
        if(dvListDichvu !=null){
            return dvListDichvu.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
}
