package com.bruteforces.demo.entity;

import java.sql.Timestamp;

public class Submission {
    private int subId;
    private int conId;
    private int proId;
    private int stuId;
    private String subSrc;
    private Timestamp subTime;
    private String result;
    private String language;
    private int subTimeLimit;
    private int subMemoryLimit;
    private int subLength;

    public Submission() {

    }

    public Submission(int subId, int conId, int proId, int stuId, String subSrc, Timestamp subTime, String result, String language, int subTimeLimit, int subMemoryLimit, int subLength) {
        this.subId = subId;
        this.conId = conId;
        this.proId = proId;
        this.stuId = stuId;
        this.subSrc = subSrc;
        this.subTime = subTime;
        this.result = result;
        this.language = language;
        this.subTimeLimit = subTimeLimit;
        this.subMemoryLimit = subMemoryLimit;
        this.subLength = subLength;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getSubId() {
        return subId;
    }

    public void setSubId(int subId) {
        this.subId = subId;
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

    public int getStuId() {
        return stuId;
    }

    public void setStuId(int stuId) {
        this.stuId = stuId;
    }

    public String getSubSrc() {
        return subSrc;
    }

    public void setSubSrc(String subSrc) {
        this.subSrc = subSrc;
    }

    public Timestamp getSubTime() {
        return subTime;
    }

    public void setSubTime(Timestamp subTime) {
        this.subTime = subTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getSubTimeLimit() {
        return subTimeLimit;
    }

    public void setSubTimeLimit(int subTimeLimit) {
        this.subTimeLimit = subTimeLimit;
    }

    public int getSubMemoryLimit() {
        return subMemoryLimit;
    }

    public void setSubMemoryLimit(int subMemoryLimit) {
        this.subMemoryLimit = subMemoryLimit;
    }

    public int getSubLength() {
        return subLength;
    }

    public void setSubLength(int subLength) {
        this.subLength = subLength;
    }
}
