package com.chuyende.hotelbookingappofuser.data_models;

public class BinhLuan {
    private String noidungBL;
    private int hinhanhBL;
    private String Maphong;
    private String Manguoidung;
    private int DanhGia;

    public BinhLuan(String rat_la_ok, int hinhgai2) {
    }

    public BinhLuan(String noidungBL, int hinhanhBL, String maphong, String manguoidung, int danhGia) {
        this.noidungBL = noidungBL;
        this.hinhanhBL = hinhanhBL;
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

    public int getHinhanhBL() {
        return hinhanhBL;
    }

    public void setHinhanhBL(int hinhanhBL) {
        this.hinhanhBL = hinhanhBL;
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
                ", hinhanhBL='" + hinhanhBL + '\'' +
                ", Maphong='" + Maphong + '\'' +
                ", Manguoidung='" + Manguoidung + '\'' +
                ", DanhGia=" + DanhGia +
                '}';
    }
}
