package com.chuyende.hotelbookingappofuser.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chuyende.hotelbookingappofuser.R;
import com.chuyende.hotelbookingappofuser.activities.ManHinhChiTiet;
import com.chuyende.hotelbookingappofuser.data_models.Phong;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.chuyende.hotelbookingappofuser.activities.FragmentManHinhNha.ALL;

public class AdapterGridViewPhong extends BaseAdapter {
    private ArrayList<Phong> arrListPhong = new ArrayList<Phong>();
    private ArrayList<Phong> arrListPhongBackUp = new ArrayList<Phong>();
    private LayoutInflater layoutInflater;
    private Context context;
    int Resource;
    Intent intent;

    public static final String KEY_MA_PHONG = "maPhong";

    public AdapterGridViewPhong(Context constructorContext, int resource, ArrayList<Phong> arrListPhong) {
        this.context = constructorContext;
        this.Resource = resource;
        this.arrListPhong = arrListPhong;
        this.arrListPhongBackUp.addAll(arrListPhong);
        layoutInflater = LayoutInflater.from(constructorContext);
    }

    @Override
    public int getCount() {
        return arrListPhong.size();
    }

    @Override
    public Object getItem(int i) {
        return arrListPhong.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    static class ViewHolder {
        ImageView imgHinh;
        RatingBar rating;
        TextView tvGiaPhong, tvTenPhong;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder clsViewHolder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.listview_item, null);
            clsViewHolder = new ViewHolder();
            clsViewHolder.imgHinh = view.findViewById(R.id.imageView_listitem);
            clsViewHolder.tvTenPhong = view.findViewById(R.id.textView_listitem_tenphong);
            clsViewHolder.rating = view.findViewById(R.id.ratingbar_listitem);
            clsViewHolder.tvGiaPhong = view.findViewById(R.id.textView_listitem_giaphong);
            view.setTag(clsViewHolder);
        } else {
            clsViewHolder = (ViewHolder) view.getTag();
        }

        Phong clsPhong = this.arrListPhong.get(i);
        String url = clsPhong.getAnhDaiDien();
        Picasso.with(context).load(url).into(clsViewHolder.imgHinh);    // hiện hình ảnh từ source sử dụng Picasso(add thư viện picasso)
        clsViewHolder.tvTenPhong.setText(clsPhong.getTenPhong());
        clsViewHolder.rating.setRating((float) clsPhong.getRatingPhong());
        clsViewHolder.tvGiaPhong.setText(clsPhong.getGiaThue() + "VND/đêm");

        clsViewHolder.tvTenPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(view.getContext(), ManHinhChiTiet.class);
                intent.putExtra(KEY_MA_PHONG, clsPhong.getMaPhong());
                view.getContext().startActivity(intent);
            }
        });

        return view;
    }

    // Tìm và lọc
    public void timKiemVaLoc(String keySearch, String spItemKeyTinhThanhPho, String spItemKeyLoaiPhong, String spItemKeyTieuChi) {
        arrListPhong.clear();
        //         xem danh sach khi chưa lọc và chưa search
        if (keySearch.isEmpty() && spItemKeyTinhThanhPho.equals(ALL) && spItemKeyLoaiPhong.equals(ALL)) {
            arrListPhong.addAll(arrListPhongBackUp);
        } else {
            // xem danh sách và lọc khi đã nhập ký tự search
            if (!keySearch.isEmpty()) {
                // lọc tất cả theo ký tự search
                if (spItemKeyTinhThanhPho.equals(ALL) && spItemKeyLoaiPhong.equals(ALL)) {
                    keySearch = keySearch.toLowerCase();
                    for (Phong robot : arrListPhongBackUp) {
                        if (robot.getTenPhong().toLowerCase().contains(keySearch)) {
                            arrListPhong.add(robot);
                        }
                    }
                }
                // lọc tỉnh thành phố theo ký tự search
                else if (!spItemKeyTinhThanhPho.equals(ALL) && spItemKeyLoaiPhong.equals(ALL)) {
                    keySearch = keySearch.toLowerCase();
                    for (Phong robot : arrListPhongBackUp) {
                        if (robot.getTenPhong().toLowerCase().contains(keySearch) && robot.getMaTinhThanhPho().equals(spItemKeyTinhThanhPho.trim())) {
                            arrListPhong.add(robot);
                        }
                    }
                }
                // lọc loại phòng theo ký tự search
                else if (spItemKeyTinhThanhPho.equals(ALL) && !spItemKeyLoaiPhong.equals(ALL)) {
                    keySearch = keySearch.toLowerCase();
                    for (Phong robot : arrListPhongBackUp) {
                        if (robot.getTenPhong().toLowerCase().contains(keySearch) && robot.getMaLoaiPhong().equals(spItemKeyLoaiPhong.trim())) {
                            arrListPhong.add(robot);
                        }
                    }
                }
                // lọc vừa loại phòng vừa tỉnh thành phố theo ký tự search
                else {
                    keySearch = keySearch.toLowerCase();
                    for (Phong robot : arrListPhongBackUp) {
                        if (robot.getTenPhong().toLowerCase().contains(keySearch) && robot.getMaLoaiPhong().equals(spItemKeyLoaiPhong.trim())
                                && robot.getMaTinhThanhPho().equals(spItemKeyTinhThanhPho.trim())) {
                            arrListPhong.add(robot);
                        }
                    }
                }

            }
            // lọc mà không search
            else {
                // lọc theo tên tỉnh thành phố
                if (!spItemKeyTinhThanhPho.equals(ALL) && spItemKeyLoaiPhong.equals(ALL)) {
                    for (Phong robot : arrListPhongBackUp) {
                        if (robot.getMaTinhThanhPho().equals(spItemKeyTinhThanhPho.trim())) {
                            arrListPhong.add(robot);
                        }
                    }
                }
                // lọc theo loại phòng
                else if (spItemKeyTinhThanhPho.equals(ALL) && !spItemKeyLoaiPhong.equals(ALL)) {
                    for (Phong robot : arrListPhongBackUp) {
                        if (robot.getMaLoaiPhong().equals(spItemKeyLoaiPhong.trim())) {
                            arrListPhong.add(robot);
                        }
                    }
                }
                // lọc theo tên tỉnh thành phố và loại phòng
                else {
                    for (Phong robot : arrListPhongBackUp) {
                        if (robot.getMaLoaiPhong().equals(spItemKeyLoaiPhong.trim()) && robot.getMaTinhThanhPho().equals(spItemKeyTinhThanhPho.trim())) {
                            arrListPhong.add(robot);
                        }
                    }
                }
            }
        }

        notifyDataSetChanged();
    }
}
