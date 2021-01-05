package com.chuyende.hotelbookingappofuser.data_models;

public class BinhLuan {
    private String noidungBL;
    private String Maphong;
    private String Manguoidung;
    private int DanhGia;

    public BinhLuan() {
    }

    public BinhLuan(String noidungBL, String maphong, String manguoidung, int danhGia) {
        this.noidungBL = noidungBL;
        Maphong = maphong;
        Manguoidung = manguoidung;
        DanhGia = danhGia;
    }



    public String getNoidungBL() {
        return noidungBL;
    }

    public void setNoidungBL(String noidungBL) {
        this.noidungBL = noidungBL;
    }

    public String getMaphong() {
        return Maphong;
    }

    public void setMaphong(String maphong) {
        Maphong = maphong;
    }

    public String getManguoidung() {
        return Manguoidung;
    }

    public void setManguoidung(String manguoidung) {
        Manguoidung = manguoidung;
    }

    public int getDanhGia() {
        return DanhGia;
    }

    public void setDanhGia(int danhGia) {
        DanhGia = danhGia;
    }

    @Override
    public String toString() {
        return "BinhLuan{" +
                "noidungBL='" + noidungBL + '\'' +
                ", Maphong='" + Maphong + '\'' +
                ", Manguoidung='" + Manguoidung + '\'' +
                ", DanhGia=" + DanhGia +
                '}';
    }
}
