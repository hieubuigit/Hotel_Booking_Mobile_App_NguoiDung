package com.chuyende.hotelbookingappofuser.data_models;

public class Phong {

    private String tenPhong;
    private Double giaThue;
    private int soKhach;
    private String maTienNghi;
    private String moTaPhong;
    private float ratingPhong;
    private String diaChiPhong;
    private Double kinhDo;
    private Double viDo;
    private int phanTramGiamGia;
    private String anhDaiDien;
    private String boSuuTapAnh;
    private String maKhachSan;

    public Phong() {
    }

    public Phong(String tenPhong, Double giaThue, int soKhach, String maTienNghi,
                 String moTaPhong, String diaChiPhong, Double kinhDo, Double viDo, int phanTramGiamGia, String anhDaiDien,
                 String boSuuTapAnh, String maKhachSan, float ratingPhong) {
        this.tenPhong = tenPhong;
        this.giaThue = giaThue;
        this.soKhach = soKhach;
        this.maTienNghi = maTienNghi;
        this.moTaPhong = moTaPhong;
        this.diaChiPhong = diaChiPhong;
        this.kinhDo = kinhDo;
        this.viDo = viDo;
        this.phanTramGiamGia = phanTramGiamGia;
        this.anhDaiDien = anhDaiDien;
        this.boSuuTapAnh = boSuuTapAnh;
        this.maKhachSan = maKhachSan;
        this.ratingPhong = ratingPhong;
    }

    public Phong(String tenPhong, Double giaThue, int soKhach, String maTienNghi,
                 String moTaPhong, float ratingPhong, String diaChiPhong, Double kinhDo, Double viDo, int phanTramGiamGia,
                 String anhDaiDien, String boSuuTapAnh, String maKhachSan) {
        this.tenPhong = tenPhong;
        this.giaThue = giaThue;
        this.soKhach = soKhach;
        this.maTienNghi = maTienNghi;
        this.moTaPhong = moTaPhong;
        this.ratingPhong = ratingPhong;
        this.diaChiPhong = diaChiPhong;
        this.kinhDo = kinhDo;
        this.viDo = viDo;
        this.phanTramGiamGia = phanTramGiamGia;
        this.anhDaiDien = anhDaiDien;
        this.boSuuTapAnh = boSuuTapAnh;
        this.maKhachSan = maKhachSan;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public Double getGiaThue() {
        return giaThue;
    }

    public void setGiaThue(Double giaThue) {
        this.giaThue = giaThue;
    }

    public int getSoKhach() {
        return soKhach;
    }

    public void setSoKhach(int soKhach) {
        this.soKhach = soKhach;
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

    public double getRatingPhong() {
        return ratingPhong;
    }

    public void setRatingPhong(float ratingPhong) {
        this.ratingPhong = ratingPhong;
    }

    public String getDiaChiPhong() {
        return diaChiPhong;
    }

    public void setDiaChiPhong(String diaChiPhong) {
        this.diaChiPhong = diaChiPhong;
    }

    public Double getKinhDo() {
        return kinhDo;
    }

    public void setKinhDo(Double kinhDo) {
        this.kinhDo = kinhDo;
    }

    public Double getViDo() {
        return viDo;
    }

    public void setViDo(Double viDo) {
        this.viDo = viDo;
    }

    public int getPhanTramGiamGia() {
        return phanTramGiamGia;
    }

    public void setPhanTramGiamGia(int phanTramGiamGia) {
        this.phanTramGiamGia = phanTramGiamGia;
    }

    public String getAnhDaiDien() {
        return anhDaiDien;
    }

    public void setAnhDaiDien(String anhDaiDien) {
        this.anhDaiDien = anhDaiDien;
    }

    public String getBoSuuTapAnh() {
        return boSuuTapAnh;
    }

    public void setBoSuuTapAnh(String boSuuTapAnh) {
        this.boSuuTapAnh = boSuuTapAnh;
    }

    public String getMaKhachSan() {
        return maKhachSan;
    }

    public void setMaKhachSan(String maKhachSan) {
        this.maKhachSan = maKhachSan;
    }

    @Override
    public String toString() {
        return "Phong{" +
                " -- tenPhong='" + tenPhong + '\'' +
                " -- giaThue=" + giaThue +
                " -- soKhach='" + soKhach + '\'' +
                " -- maTienNghi='" + maTienNghi.toString() + '\'' +
                " -- moTaPhong='" + moTaPhong + '\'' +
                " -- ratingPhong=" + ratingPhong +
                " -- diaChiPhong='" + diaChiPhong + '\'' +
                " -- kinhDo=" + kinhDo +
                " -- viDo=" + viDo +
                " -- phanTramGiamGia=" + phanTramGiamGia +
                " -- anhDaiDien='" + anhDaiDien + '\'' +
                " -- boSuuTapAnh='" + boSuuTapAnh + '\'' +
                " -- maKhachSan='" + maKhachSan + '\'' +
                '}';
    }
}
