package com.chuyende.hotelbookingappofuser.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.chuyende.hotelbookingappofuser.R;

import java.util.List;

public class PhotoAdapter extends PagerAdapter {

    private Context mContext;
    private List<Uri> mListUrisOfPhoto;

    public PhotoAdapter() {
    }

    public PhotoAdapter(Context mContext, List<Uri> mListUrisOfPhoto) {
        this.mContext = mContext;
        this.mListUrisOfPhoto = mListUrisOfPhoto;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_photo, container, false);
        ImageView imgphoto = view.findViewById(R.id.img_photo);

        Uri uriPhoto = mListUrisOfPhoto.get(position);

        if (!uriPhoto.toString().equals("")) {
            Glide.with(mContext).load(uriPhoto).into(imgphoto);
        }

        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view);

        return view;
    }

    @Override
    public int getCount() {
        if (mListUrisOfPhoto != null) {
            return mListUrisOfPhoto.size();
        } else {
            return 0;
        }
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }
}
