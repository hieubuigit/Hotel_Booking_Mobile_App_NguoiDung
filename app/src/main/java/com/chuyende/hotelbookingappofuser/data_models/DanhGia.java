package com.chuyende.hotelbookingappofuser.data_models;

public class DanhGia {
    private String maNguoiDung;
    private String maPhong;
    private String noiDungComment;
    private double ratingNguoiDung;

    public DanhGia(String maNguoiDung, String maPhong, String noiDungComment, double ratingNguoiDung) {
        this.maNguoiDung = maNguoiDung;
        this.maPhong = maPhong;
        this.noiDungComment = noiDungComment;
        this.ratingNguoiDung = ratingNguoiDung;
    }

    public DanhGia(String noiDungComment, Double ratingNguoiDung) {
    }

    public String getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(String maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getNoiDungComment() {
        return noiDungComment;
    }

    public void setNoiDungComment(String noiDungComment) {
        this.noiDungComment = noiDungComment;
    }

    public double getRatingNguoiDung() {
        return ratingNguoiDung;
    }

    public void setRatingNguoiDung(double ratingNguoiDung) {
        this.ratingNguoiDung = ratingNguoiDung;
    }

    @Override
    public String toString() {
        return "DanhGia{" +
                "maNguoiDung='" + maNguoiDung + '\'' +
                ", maPhong='" + maPhong + '\'' +
                ", noiDungComment='" + noiDungComment + '\'' +
                ", ratingNguoiDung=" + ratingNguoiDung +
                '}';
    }
}
