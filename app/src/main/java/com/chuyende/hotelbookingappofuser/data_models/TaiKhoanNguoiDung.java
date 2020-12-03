package com.chuyende.hotelbookingappofuser.data_models;

import com.google.firestore.v1.TargetOrBuilder;

import java.util.ArrayList;

public class TaiKhoanNguoiDung {
    private String ma_TKNguoiDung;
    private String ten_TKNguoiDung;
    private String matKhau_TKNguoiDung;
    private String email_TKNguoiDung;
    private String sdt_TKNguoiDung;
    private String maOTP_TKNGuoiDung;
    private ArrayList<TaiKhoanNguoiDung> arrayList;
    public TaiKhoanNguoiDung() {
    }

    public TaiKhoanNguoiDung(String ma_TKNguoiDung, String ten_TKNguoiDung,
                             String matKhau_TKNguoiDung, String email_TKNguoiDung,
                             String sdt_TKNguoiDung, String maOTP_TKNGuoiDung, ArrayList<TaiKhoanNguoiDung> arrList) {
        this.ma_TKNguoiDung = ma_TKNguoiDung;
        this.ten_TKNguoiDung = ten_TKNguoiDung;
        this.matKhau_TKNguoiDung = matKhau_TKNguoiDung;
        this.email_TKNguoiDung = email_TKNguoiDung;
        this.sdt_TKNguoiDung = sdt_TKNguoiDung;
        this.maOTP_TKNGuoiDung = maOTP_TKNGuoiDung;
        this.arrayList = arrList;
    }

    public String getMa_TKNguoiDung() {
        return ma_TKNguoiDung;
    }

    public void setMa_TKNguoiDung(String ma_TKNguoiDung) {
        this.ma_TKNguoiDung = ma_TKNguoiDung;
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

    public String getMaOTP_TKNGuoiDung() {
        return maOTP_TKNGuoiDung;
    }

    public void setMaOTP_TKNGuoiDung(String maOTP_TKNGuoiDung) {
        this.maOTP_TKNGuoiDung = maOTP_TKNGuoiDung;
    }

    public ArrayList<TaiKhoanNguoiDung> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<TaiKhoanNguoiDung> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public String toString() {
        return "TaiKhoanNguoiDung{" +
                "ma_TKNguoiDung='" + ma_TKNguoiDung + '\'' +
                ", ten_TKNguoiDung='" + ten_TKNguoiDung + '\'' +
                ", matKhau_TKNguoiDung='" + matKhau_TKNguoiDung + '\'' +
                ", email_TKNguoiDung='" + email_TKNguoiDung + '\'' +
                ", sdt_TKNguoiDung='" + sdt_TKNguoiDung + '\'' +
                ", maOTP_TKNGuoiDung='" + maOTP_TKNGuoiDung + '\'' +
                ", arrayList=" + arrayList +
                '}';
    }
}
