package com.chuyende.hotelbookingappofuser.data_models;

public class NguoiDung {
    private String maNguoiDung;
    private String tenNguoiDung;
    private String gioiTinhNguoiDung;
    private String ngaySinhNguoiDung;
    private String anhDaiDienNguoiDung;
    private String quocTichNguoiDung;
    private String maTaiKhoanNguoiDung;

    public NguoiDung() {
    }

    public NguoiDung(String maNguoiDung, String tenNguoiDung, String gioiTinhNguoiDung,
                     String ngaySinhNguoiDung, String anhDaiDienNguoiDung,
                     String quocTichNguoiDung, String maTaiKhoanNguoiDung) {
        this.maNguoiDung = maNguoiDung;
        this.tenNguoiDung = tenNguoiDung;
        this.gioiTinhNguoiDung = gioiTinhNguoiDung;
        this.ngaySinhNguoiDung = ngaySinhNguoiDung;
        this.anhDaiDienNguoiDung = anhDaiDienNguoiDung;
        this.quocTichNguoiDung = quocTichNguoiDung;
        this.maTaiKhoanNguoiDung = maTaiKhoanNguoiDung;
    }

    public String getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(String maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    public void setTenNguoiDung(String tenNguoiDung) {
        this.tenNguoiDung = tenNguoiDung;
    }

    public String getGioiTinhNguoiDung() {
        return gioiTinhNguoiDung;
    }

    public void setGioiTinhNguoiDung(String gioiTinhNguoiDung) {
        this.gioiTinhNguoiDung = gioiTinhNguoiDung;
    }

    public String getNgaySinhNguoiDung() {
        return ngaySinhNguoiDung;
    }

    public void setNgaySinhNguoiDung(String ngaySinhNguoiDung) {
        this.ngaySinhNguoiDung = ngaySinhNguoiDung;
    }

    public String getAnhDaiDienNguoiDung() {
        return anhDaiDienNguoiDung;
    }

    public void setAnhDaiDienNguoiDung(String anhDaiDienNguoiDung) {
        this.anhDaiDienNguoiDung = anhDaiDienNguoiDung;
    }


    public String getQuocTichNguoiDung() {
        return quocTichNguoiDung;
    }

    public void setQuocTichNguoiDung(String quocTichNguoiDung) {
        this.quocTichNguoiDung = quocTichNguoiDung;
    }

    public String getMaTaiKhoanNguoiDung() {
        return maTaiKhoanNguoiDung;
    }

    public void setMaTaiKhoanNguoiDung(String maTaiKhoanNguoiDung) {
        this.maTaiKhoanNguoiDung = maTaiKhoanNguoiDung;
    }
}
