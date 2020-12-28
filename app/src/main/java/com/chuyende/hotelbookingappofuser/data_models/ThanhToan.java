package com.chuyende.hotelbookingappofuser.data_models;

public class ThanhToan {
    private String tenPhong;
    private Double giaThue;
    private int soKhach;
    private String diaChiPhong;
    private int phanTramGiamGia;
    private String anhDaiDien;

    public ThanhToan() {
    }

    public ThanhToan(String tenPhong, Double giaThue, int soKhach, String diaChiPhong, int phanTramGiamGia, String anhDaiDien) {
        this.tenPhong = tenPhong;
        this.giaThue = giaThue;
        this.soKhach = soKhach;
        this.diaChiPhong = diaChiPhong;
        this.phanTramGiamGia = phanTramGiamGia;
        this.anhDaiDien = anhDaiDien;
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

    public String getDiaChiPhong() {
        return diaChiPhong;
    }

    public void setDiaChiPhong(String diaChiPhong) {
        this.diaChiPhong = diaChiPhong;
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

    @Override
    public String toString() {
        return "ThanhToan{" +
                "tenPhong='" + tenPhong + '\'' +
                ", giaThue=" + giaThue +
                ", soKhach=" + soKhach +
                ", diaChiPhong='" + diaChiPhong + '\'' +
                ", phanTramGiamGia=" + phanTramGiamGia +
                ", anhDaiDien='" + anhDaiDien + '\'' +
                '}';
    }
}
