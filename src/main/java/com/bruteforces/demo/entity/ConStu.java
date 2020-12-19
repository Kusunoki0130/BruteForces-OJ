package com.bruteforces.demo.entity;

public class ConStu {

    private int conId;
    private int stuId;

    public ConStu() {

    }

    public ConStu(int conId, int stuId) {
        this.conId = conId;
        this.stuId = stuId;
    }

    public int getConId() {
        return conId;
    }

    public void setConId(int conId) {
        this.conId = conId;
    }

    public int getStuId() {
        return stuId;
    }

    public void setStuId(int stuId) {
        this.stuId = stuId;
    }
}
