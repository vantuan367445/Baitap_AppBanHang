package com.example.tuanvatvo.baitap_appbanhang.model;

public class GioiHanh {
    private  int idSP;
    private String tenSP;
    private long giaSP;
    private String hinhSP;
    private int soluongSP;

    public GioiHanh(int idSP, String tenSP, long giaSP, String hinhSP, int soluongSP) {
        this.idSP = idSP;
        this.tenSP = tenSP;
        this.giaSP = giaSP;
        this.hinhSP = hinhSP;
        this.soluongSP = soluongSP;
    }

    public GioiHanh() {
    }

    public int getIdSP() {
        return idSP;
    }

    public void setIdSP(int idSP) {
        this.idSP = idSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public long getGiaSP() {
        return giaSP;
    }

    public void setGiaSP(long giaSP) {
        this.giaSP = giaSP;
    }

    public String getHinhSP() {
        return hinhSP;
    }

    public void setHinhSP(String hinhSP) {
        this.hinhSP = hinhSP;
    }

    public int getSoluongSP() {
        return soluongSP;
    }

    public void setSoluongSP(int soluongSP) {
        this.soluongSP = soluongSP;
    }
}
