package com.chuyende.hotelbookingappofuser.data_models;

import android.media.Rating;

public class ClsPhong {
    private int hinh;
    private String tenPhong, giaPhong;
    private float rating;


    public ClsPhong() {
    }

    public ClsPhong(int hinh, String tenPhong, String giaPhong, float rating) {
        this.hinh = hinh;
        this.tenPhong = tenPhong;
        this.giaPhong = giaPhong;
        this.rating = rating;
    }

    public int getHinh() {
        return hinh;
    }

    public void setHinh(int hinh) {
        this.hinh = hinh;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public String getGiaPhong() {
        return giaPhong;
    }

    public void setGiaPhong(String giaPhong) {
        this.giaPhong = giaPhong;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "ClsPhong{" +
                "hinh=" + hinh +
                ", tenPhong='" + tenPhong + '\'' +
                ", giaPhong='" + giaPhong + '\'' +
                ", rating=" + rating +
                '}';
    }
}
