package com.bruteforces.demo.entity;

public class Problem {
    private int proId;
    private String proTitle;
    private String proInfo;
    private String proInput;
    private String proOutput;
    private String proInputSample;
    private String ProOutputSample;
    private int proTimeLimit;
    private int proMemoryLimit;
    private int proSubmitted;
    private int proAccepted;
    private int proDataNum;
    private String proData;
    private int isHidden;

    public Problem() {

    }

    public Problem(int proId, String proTitle, String proInfo, String proInput, String proOutput, String proInputSample, String proOutputSample, int proTimeLimit, int proMemoryLimit, int proSubmitted, int proAccepted, int proDataNum, String proData, int isHidden) {
        this.proId = proId;
        this.proTitle = proTitle;
        this.proInfo = proInfo;
        this.proInput = proInput;
        this.proOutput = proOutput;
        this.proInputSample = proInputSample;
        ProOutputSample = proOutputSample;
        this.proTimeLimit = proTimeLimit;
        this.proMemoryLimit = proMemoryLimit;
        this.proSubmitted = proSubmitted;
        this.proAccepted = proAccepted;
        this.proDataNum = proDataNum;
        this.proData = proData;
        this.isHidden = isHidden;
    }

    public int getProId() {
        return proId;
    }

    public void setProId(int proId) {
        this.proId = proId;
    }

    public String getProTitle() {
        return proTitle;
    }

    public void setProTitle(String proTitle) {
        this.proTitle = proTitle;
    }

    public String getProInfo() {
        return proInfo;
    }

    public void setProInfo(String proInfo) {
        this.proInfo = proInfo;
    }

    public String getProInput() {
        return proInput;
    }

    public void setProInput(String proInput) {
        this.proInput = proInput;
    }

    public String getProOutput() {
        return proOutput;
    }

    public void setProOutput(String proOutput) {
        this.proOutput = proOutput;
    }

    public String getProInputSample() {
        return proInputSample;
    }

    public void setProInputSample(String proInputSample) {
        this.proInputSample = proInputSample;
    }

    public String getProOutputSample() {
        return ProOutputSample;
    }

    public void setProOutputSample(String proOutputSample) {
        ProOutputSample = proOutputSample;
    }

    public int getProTimeLimit() {
        return proTimeLimit;
    }

    public void setProTimeLimit(int proTimeLimit) {
        this.proTimeLimit = proTimeLimit;
    }

    public int getProMemoryLimit() {
        return proMemoryLimit;
    }

    public void setProMemoryLimit(int proMemoryLimit) {
        this.proMemoryLimit = proMemoryLimit;
    }

    public int getProSubmitted() {
        return proSubmitted;
    }

    public void setProSubmitted(int proSubmitted) {
        this.proSubmitted = proSubmitted;
    }

    public int getProAccepted() {
        return proAccepted;
    }

    public void setProAccepted(int proAccepted) {
        this.proAccepted = proAccepted;
    }

    public int getProDataNum() {
        return proDataNum;
    }

    public void setProDataNum(int proDataNum) {
        this.proDataNum = proDataNum;
    }

    public String getProData() {
        return proData;
    }

    public void setProData(String proData) {
        this.proData = proData;
    }

    public int getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(int isHidden) {
        this.isHidden = isHidden;
    }

    public void updateSubmissionNum() {
        ++proSubmitted;
    }
    public void updateAccpetedNum() {
        ++proAccepted;
    }
}