package com.chuyende.hotelbookingappofuser.data_models;

public class TaiKhoanNguoiDung {
    private String ten_TKNguoiDung;
    private String matKhau_TKNguoiDung;
    private String email_TKNguoiDung;
    private String sdt_TKNguoiDung;
    private String id;

    public TaiKhoanNguoiDung() {
    }

    public TaiKhoanNguoiDung(String ten_TKNguoiDung,
                             String matKhau_TKNguoiDung, String email_TKNguoiDung,
                             String sdt_TKNguoiDung, String id) {
        this.ten_TKNguoiDung = ten_TKNguoiDung;
        this.matKhau_TKNguoiDung = matKhau_TKNguoiDung;
        this.email_TKNguoiDung = email_TKNguoiDung;
        this.sdt_TKNguoiDung = sdt_TKNguoiDung;
        this.id = id;
    }

    public String getTen_TKNguoiDung() {
        return ten_TKNguoiDung;
    }

    public void setTen_TKNguoiDung(String ten_TKNguoiDung) {
        this.ten_TKNguoiDung = ten_TKNguoiDung;
    }

    public String getMatKhau_TKNguoiDung() {
        return matKhau_TKNguoiDung;
    }

    public void setMatKhau_TKNguoiDung(String matKhau_TKNguoiDung) {
        this.matKhau_TKNguoiDung = matKhau_TKNguoiDung;
    }

    public String getEmail_TKNguoiDung() {
        return email_TKNguoiDung;
    }

    public void setEmail_TKNguoiDung(String email_TKNguoiDung) {
        this.email_TKNguoiDung = email_TKNguoiDung;
    }

    public String getSdt_TKNguoiDung() {
        return sdt_TKNguoiDung;
    }

    public void setSdt_TKNguoiDung(String sdt_TKNguoiDung) {
        this.sdt_TKNguoiDung = sdt_TKNguoiDung;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "TaiKhoanNguoiDung{" +
                ", ten_TKNguoiDung='" + ten_TKNguoiDung + '\'' +
                ", matKhau_TKNguoiDung='" + matKhau_TKNguoiDung + '\'' +
                ", email_TKNguoiDung='" + email_TKNguoiDung + '\'' +
                ", sdt_TKNguoiDung='" + sdt_TKNguoiDung + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
