package com.chuyende.hotelbookingappofuser.data_models;

public class LoaiPhong {
    private String maLoaiPhong, loaiphong;

    public LoaiPhong() {
    }

    public LoaiPhong(String maLoaiPhong, String loaiphong) {
        this.maLoaiPhong = maLoaiPhong;
        this.loaiphong = loaiphong;
    }

    public String getMaLoaiPhong() {
        return maLoaiPhong;
    }

    public void setMaLoaiPhong(String maLoaiPhong) {
        this.maLoaiPhong = maLoaiPhong;
    }

    public String getLoaiphong() {
        return loaiphong;
    }

    public void setLoaiphong(String loaiphong) {
        this.loaiphong = loaiphong;
    }

    @Override
    public String toString() {
        return "LoaiPhong{" +
                "maLoaiPhong='" + maLoaiPhong + '\'' +
                ", loaiphong='" + loaiphong + '\'' +
                '}';
    }
}
