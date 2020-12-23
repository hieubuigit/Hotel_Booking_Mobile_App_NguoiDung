package com.chuyende.hotelbookingappofuser.data_models;

import java.util.ArrayList;

public class Phong {
    private int soKhach, phanTramGiamGia, soLuotDat, soLuotHuy;
    private String tenPhong, maPhong, maTrangThaiPhong, maLoaiPhong, maTienNghi, moTaPhong, maTinhThanhPho, diaChiPhong, anhDaiDien;
    private double kinhDo, viDo, giaThue,ratingPhong;
    private ArrayList<String> boSuuTapAnh;

    public Phong() {

    }

    public Phong(String anhDaiDien, String tenPhong, double ratingPhong, double giaThue) {
        this.anhDaiDien = anhDaiDien;
        this.tenPhong = tenPhong;
        this.ratingPhong = ratingPhong;
        this.giaThue = giaThue;

    }

    public Phong(int soKhach, int phanTramGiamGia, int soLuotDat,
                 int soLuotHuy, String anhDaiDien, String tenPhong,
                 String maPhong, String maTrangThaiPhong,
                 String maLoaiPhong, String maTienNghi, String moTaPhong,
                 String maTinhThanhPho, String diaChiPhong, double ratingPhong,
                 double giaThue, double kinhDo, double viDo, ArrayList<String> boSuuTapAnh) {
        this.soKhach = soKhach;
        this.phanTramGiamGia = phanTramGiamGia;
        this.soLuotDat = soLuotDat;
        this.soLuotHuy = soLuotHuy;
        this.anhDaiDien = anhDaiDien;
        this.tenPhong = tenPhong;
        this.maPhong = maPhong;
        this.maTrangThaiPhong = maTrangThaiPhong;
        this.maLoaiPhong = maLoaiPhong;
        this.maTienNghi = maTienNghi;
        this.moTaPhong = moTaPhong;
        this.maTinhThanhPho = maTinhThanhPho;
        this.diaChiPhong = diaChiPhong;
        this.ratingPhong = ratingPhong;
        this.giaThue = giaThue;
        this.kinhDo = kinhDo;
        this.viDo = viDo;
        this.boSuuTapAnh = boSuuTapAnh;
    }

    public int getSoKhach() {
        return soKhach;
    }

    public void setSoKhach(int soKhach) {
        this.soKhach = soKhach;
    }

    public int getPhanTramGiamGia() {
        return phanTramGiamGia;
    }

    public void setPhanTramGiamGia(int phanTramGiamGia) {
        this.phanTramGiamGia = phanTramGiamGia;
    }

    public int getSoLuotDat() {
        return soLuotDat;
    }

    public void setSoLuotDat(int soLuotDat) {
        this.soLuotDat = soLuotDat;
    }

    public int getSoLuotHuy() {
        return soLuotHuy;
    }

    public void setSoLuotHuy(int soLuotHuy) {
        this.soLuotHuy = soLuotHuy;
    }

    public String getAnhDaiDien() {
        return anhDaiDien;
    }

    public void setAnhDaiDien(String anhDaiDien) {
        this.anhDaiDien = anhDaiDien;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getMaTrangThaiPhong() {
        return maTrangThaiPhong;
    }

    public void setMaTrangThaiPhong(String maTrangThaiPhong) {
        this.maTrangThaiPhong = maTrangThaiPhong;
    }

    public String getMaLoaiPhong() {
        return maLoaiPhong;
    }

    public void setMaLoaiPhong(String maLoaiPhong) {
        this.maLoaiPhong = maLoaiPhong;
    }

    public String getMaTienNghi() {
        return maTienNghi;
    }

    public void setMaTienNghi(String maTienNghi) {
        this.maTienNghi = maTienNghi;
    }

    public String getMoTaPhong() {
        return moTaPhong;
    }

    public void setMoTaPhong(String moTaPhong) {
        this.moTaPhong = moTaPhong;
    }

    public String getMaTinhThanhPho() {
        return maTinhThanhPho;
    }

    public void setMaTinhThanhPho(String maTinhThanhPho) {
        this.maTinhThanhPho = maTinhThanhPho;
    }

    public String getDiaChiPhong() {
        return diaChiPhong;
    }

    public void setDiaChiPhong(String diaChiPhong) {
        this.diaChiPhong = diaChiPhong;
    }

    public double getRatingPhong() {
        return ratingPhong;
    }

    public void setRatingPhong(double ratingPhong) {
        this.ratingPhong = ratingPhong;
    }

    public double getGiaThue() {
        return giaThue;
    }

    public void setGiaThue(double giaThue) {
        this.giaThue = giaThue;
    }

    public double getKinhDo() {
        return kinhDo;
    }

    public void setKinhDo(double kinhDo) {
        this.kinhDo = kinhDo;
    }

    public double getViDo() {
        return viDo;
    }

    public void setViDo(double viDo) {
        this.viDo = viDo;
    }

    public ArrayList<String> getBoSuuTapAnh() {
        return boSuuTapAnh;
    }

    public void setBoSuuTapAnh(ArrayList<String> boSuuTapAnh) {
        this.boSuuTapAnh = boSuuTapAnh;
    }

    @Override
    public String toString() {
        return "ClsPhong{" +
                "soKhach=" + soKhach +
                ", phanTramGiamGia=" + phanTramGiamGia +
                ", soLuotDat=" + soLuotDat +
                ", soLuotHuy=" + soLuotHuy +
                ", anhDaiDien=" + anhDaiDien +
                ", tenPhong='" + tenPhong + '\'' +
                ", maPhong='" + maPhong + '\'' +
                ", maTrangThaiPhong='" + maTrangThaiPhong + '\'' +
                ", maLoaiPhong='" + maLoaiPhong + '\'' +
                ", maTienNghi='" + maTienNghi + '\'' +
                ", moTaPhong='" + moTaPhong + '\'' +
                ", maTinhThanhPho='" + maTinhThanhPho + '\'' +
                ", diaChiPhong='" + diaChiPhong + '\'' +
                ", ratingPhong=" + ratingPhong +
                ", giaThue=" + giaThue +
                ", kinhDo=" + kinhDo +
                ", viDo=" + viDo +
                ", boSuuTapAnh=" + boSuuTapAnh +
                '}';
    }
}
