package com.example.tuanvatvo.baitap_appbanhang.model;

import android.content.Intent;

import java.io.Serializable;

public class SanPham implements Serializable {
    public  int Id;
    public String Tensanpham;
    public int Giasanpham;
    public String Hinhanhsanpham;
    public String Motasanpham;
    public int IdSanpham;

    public SanPham(int id, String tensanpham, int giasanpham, String hinhanhsanpham, String motasanpham, int idSanpham) {
        Id = id;
        Tensanpham = tensanpham;
        Giasanpham = giasanpham;
        Hinhanhsanpham = hinhanhsanpham;
        Motasanpham = motasanpham;
        IdSanpham = idSanpham;
    }

    public SanPham() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTensanpham() {
        return Tensanpham;
    }

    public void setTensanpham(String tensanpham) {
        Tensanpham = tensanpham;
    }

    public int getGiasanpham() {
        return Giasanpham;
    }

    public void setGiasanpham(int giasanpham) {
        Giasanpham = giasanpham;
    }

    public String getHinhanhsanpham() {
        return Hinhanhsanpham;
    }

    public void setHinhanhsanpham(String hinhanhsanpham) {
        Hinhanhsanpham = hinhanhsanpham;
    }

    public String getMotasanpham() {
        return Motasanpham;
    }

    public void setMotasanpham(String motasanpham) {
        Motasanpham = motasanpham;
    }

    public int getIdSanpham() {
        return IdSanpham;
    }

    public void setIdSanpham(int idSanpham) {
        IdSanpham = idSanpham;
    }

    @Override
    public String toString() {
        return this.Id + " " + this.Tensanpham + " " + this.Giasanpham;
    }
}
