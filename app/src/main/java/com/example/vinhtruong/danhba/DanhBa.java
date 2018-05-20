package com.example.vinhtruong.danhba;

/**
 * Created by vinhtruong on 4/15/2018.
 */

public class DanhBa {
    private int id;
    private String ten;
    private String sdt;

    public DanhBa(int id, String ten, String sdt) {
        this.id = id;
        this.ten = ten;
        this.sdt = sdt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}
