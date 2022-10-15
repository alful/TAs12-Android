package com.example.tastegaaes;

public class DecodeAES {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPathfile() {
        return pathfile;
    }

    public void setPathfile(String pathfile) {
        this.pathfile = pathfile;
    }

    public String getNamafile() {
        return namafile;
    }

    public void setNamafile(String namafile) {
        this.namafile = namafile;
    }

    public Double getWaktu() {
        return waktu;
    }

    public void setWaktu(Double waktu) {
        this.waktu = waktu;
    }

    private int id;
    private String pathfile,namafile;
    private String plain;
    private String keys;

    public String getPlain() {
        return plain;
    }

    public void setPlain(String plain) {
        this.plain = plain;
    }

    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public String getChiper() {
        return chiper;
    }

    public void setChiper(String chiper) {
        this.chiper = chiper;
    }

    private String chiper;
    private Double waktu;
}
