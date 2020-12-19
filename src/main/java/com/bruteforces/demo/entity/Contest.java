package com.bruteforces.demo.entity;

import java.sql.Timestamp;

public class Contest {

    private int conId;
    private String conTitle;
    private int adminId;
    private Timestamp startTime;
    private Timestamp endTime;
    private int participate;

    public Contest() {

    }

    public Contest(int conId, String conTitle, int adminId, Timestamp startTime, Timestamp endTime, int participate) {
        this.conId = conId;
        this.conTitle = conTitle;
        this.adminId = adminId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.participate = participate;
    }

    public int getConId() {
        return conId;
    }

    public void setConId(int conId) {
        this.conId = conId;
    }

    public String getConTitle() {
        return conTitle;
    }

    public void setConTitle(String conTitle) {
        this.conTitle = conTitle;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public int getParticipate() {
        return participate;
    }

    public void setParticipate(int participate) {
        this.participate = participate;
    }

    public void addParticipant() {
        ++participate;
    }
}
