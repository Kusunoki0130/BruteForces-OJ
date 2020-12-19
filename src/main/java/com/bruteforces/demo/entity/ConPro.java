package com.bruteforces.demo.entity;

public class ConPro {

    private int conId;
    private int proId;

    public ConPro() {

    }

    public ConPro(int conId, int proId) {
        this.conId = conId;
        this.proId = proId;
    }

    public int getConId() {
        return conId;
    }

    public void setConId(int conId) {
        this.conId = conId;
    }

    public int getProId() {
        return proId;
    }

    public void setProId(int proId) {
        this.proId = proId;
    }
}
