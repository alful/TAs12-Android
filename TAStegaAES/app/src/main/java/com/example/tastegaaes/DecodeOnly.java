package com.example.tastegaaes;

public class DecodeOnly {
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Double getPsnr() {
        return psnr;
    }

    public void setPsnr(Double psnr) {
        this.psnr = psnr;
    }

    public Double getWaktu() {
        return waktu;
    }

    public void setWaktu(Double waktu) {
        this.waktu = waktu;
    }

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String nama;
    private String path;

    public String getPlain() {
        return plain;
    }

    public void setPlain(String plain) {
        this.plain = plain;
    }

    private String plain;
    private Double psnr, waktu;
}
