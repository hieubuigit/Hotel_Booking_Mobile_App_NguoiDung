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
import com.chuyende.hotelbookingappofuser.data_models.Phong;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterGridViewPhong extends BaseAdapter {
    private ArrayList<Phong> arrListPhong;
    private LayoutInflater layoutInflater;
    private Context context;
    int Resource;

    public AdapterGridViewPhong(Context constructorContext, int resource, ArrayList<Phong> arrListPhong) {
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
        Picasso.with(context).load(url).into(clsViewHolder.imgHinh);// hiện hình ảnh từ source sử dụng Picasso(add thư viện picasso)
        clsViewHolder.tvTenPhong.setText(clsPhong.getTenPhong());
        clsViewHolder.rating.setRating((float) clsPhong.getRatingPhong());
        clsViewHolder.tvGiaPhong.setText(clsPhong.getGiaThue() + "VND/đêm");
        return view;
    }
}
