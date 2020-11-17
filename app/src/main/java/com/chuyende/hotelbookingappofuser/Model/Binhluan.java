package com.chuyende.hotelbookingappofuser.Model;

public class Binhluan {
    private  String noidungBL;
    private int hinhanhBL;

    public Binhluan(String noidungBL, int hinhanhBL) {
        this.noidungBL = noidungBL;
        this.hinhanhBL = hinhanhBL;
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
}
