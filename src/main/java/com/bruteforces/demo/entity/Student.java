package com.bruteforces.demo.entity;

public class Student {
    private int stuId; // 学生ID
    private String stuUsername; // 学生账户名
    private String stuPassword; // 登录密码
    private int stuSubmitted; // 提交数
    private int stuSolved; // 通过数
    private String stuEmail; // 邮箱
    private String stuPhone; // 手机号

    public Student() {

    }

    public Student(int stuId, String stuUsername, String stuPassword, int stuSubmitted, int stuSolved, String stuEmail, String stuPhone) {
        this.stuId = stuId;
        this.stuUsername = stuUsername;
        this.stuPassword = stuPassword;
        this.stuEmail = stuEmail;
        this.stuPhone = stuPhone;
        this.stuSubmitted = stuSubmitted;
        this.stuSolved = stuSolved;
    }

    public Student (Student stu) {
        this.stuId = stu.getStuId();
        this.stuUsername = stu.getStuUsername();
        this.stuPassword = stu.getStuPassword();
        this.stuEmail = stu.getStuEmail();
        this.stuPhone = stu.getStuPhone();
        this.stuSubmitted = stu.getStuSubmitted();
        this.stuSolved = stu.getStuSolved();
    }

    public int getStuId() {
        return stuId;
    }

    public void setStuId(int stuId) {
        this.stuId = stuId;
    }

    public String getStuUsername() {
        return stuUsername;
    }

    public void setStuUsername(String stuUsername) {
        this.stuUsername = stuUsername;
    }

    public String getStuPassword() {
        return stuPassword;
    }

    public void setStuPassword(String stuPassword) {
        this.stuPassword = stuPassword;
    }

    public String getStuEmail() {
        return stuEmail;
    }

    public void setStuEmail(String stuEmail) {
        this.stuEmail = stuEmail;
    }

    public String getStuPhone() {
        return stuPhone;
    }

    public void setStuPhone(String stuPhone) {
        this.stuPhone = stuPhone;
    }

    public int getStuSubmitted() {
        return stuSubmitted;
    }

    public void setStuSubmitted(int stuSubmitted) {
        this.stuSubmitted = stuSubmitted;
    }

    public int getStuSolved() {
        return stuSolved;
    }

    public void setStuSolved(int stuSolved) {
        this.stuSolved = stuSolved;
    }

    public void updateSolvedNum() {
        ++stuSolved;
    }

    public void updateSubmissionNum() {
        ++stuSubmitted;
    }
}
