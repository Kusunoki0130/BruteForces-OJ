package com.bruteforces.demo.entity;

import java.sql.Timestamp;

public class ConProStu {

    private String cpsId;
    private int conId;
    private int proId;
    private int stuId;
    private int ifAccepted;
    private int attempTimes;
    private Timestamp solveTime;

    public ConProStu() {

    }

    public ConProStu(String cpsId, int conId, int proId, int stuId, int ifAccepted, int attempTimes, Timestamp solveTime) {
        this.cpsId = cpsId;
        this.conId = conId;
        this.proId = proId;
        this.stuId = stuId;
        this.ifAccepted = ifAccepted;
        this.attempTimes = attempTimes;
        this.solveTime = solveTime;
    }

    public String getCpsId() {
        return cpsId;
    }

    public void setCpsId(String cpsId) {
        this.cpsId = cpsId;
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

    public int getIfAccepted() {
        return ifAccepted;
    }

    public void setIfAccepted(int ifAccepted) {
        this.ifAccepted = ifAccepted;
    }

    public int getAttempTimes() {
        return attempTimes;
    }

    public void setAttempTimes(int attempTimes) {
        this.attempTimes = attempTimes;
    }

    public Timestamp getSolveTime() {
        return solveTime;
    }

    public void setSolveTime(Timestamp solveTime) {
        this.solveTime = solveTime;
    }

    public void addAttempTimes() {
        ++attempTimes;
    }

}
