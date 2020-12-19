package com.bruteforces.demo.utils;

public class SubmitResult {
    public long time;
    public long mem;
    public String result;

    public SubmitResult() {

    }

    public SubmitResult(SubmitResult sR) {
        this.time = sR.getTime();
        this.mem = sR.getMem();
        this.result = sR.getResult();
    }

    public SubmitResult(long time, long mem, String result) {
        this.time = time;
        this.mem = mem;
        this.result = result;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getMem() {
        return mem;
    }

    public void setMem(long mem) {
        this.mem = mem;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String toString() {
        return "{'time': "+time+", 'memory': "+mem+", 'result': '"+result+"'}";
    }
}
