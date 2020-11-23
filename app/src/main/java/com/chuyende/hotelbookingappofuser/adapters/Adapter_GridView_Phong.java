package com.chuyende.hotelbookingappofuser.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.chuyende.hotelbookingappofuser.R;
import com.chuyende.hotelbookingappofuser.data_models.ClsPhong;

import java.util.ArrayList;

public class Adapter_GridView_Phong extends BaseAdapter {
    private ArrayList<ClsPhong> arrListPhong;
    private LayoutInflater layoutInflater;
    private Context context;
    int Resource;

    public Adapter_GridView_Phong(Context constructorContext, int resource, ArrayList<ClsPhong> arrListPhong) {
        this.context = constructorContext;
        this.Resource = resource;
        this.arrListPhong = arrListPhong;
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
        EditText txtTenPhong;
        RatingBar rating;
        TextView tvGiaPhong;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder clsViewHolder;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.listview_item, null);
            clsViewHolder = new ViewHolder();
            clsViewHolder.imgHinh = view.findViewById(R.id.imageView_listitem);
            clsViewHolder.txtTenPhong = view.findViewById(R.id.editText_listitem_tenphong);
            clsViewHolder.rating = view.findViewById(R.id.ratingbar_listitem);
            clsViewHolder.tvGiaPhong = view.findViewById(R.id.textView_listitem_giaphong);
            view.setTag(clsViewHolder);
        } else {
            clsViewHolder = (ViewHolder) view.getTag();
        }
        ClsPhong clsPhong = this.arrListPhong.get(i);
        clsViewHolder.imgHinh.setImageResource(clsPhong.getHinh());
        clsViewHolder.txtTenPhong.setText(clsPhong.getTenPhong());
        clsViewHolder.rating.setRating(clsPhong.getRating());
        clsViewHolder.tvGiaPhong.setText(clsPhong.getGiaPhong());
        return view;
    }
}
