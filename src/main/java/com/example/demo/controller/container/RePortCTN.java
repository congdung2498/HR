package com.example.demo.controller.container;

import com.example.demo.model.Salary;

public class RePortCTN {
    private String hoten;
    private Salary salary;
    private String soTaiKhoan;
    private String nganHangHuong;

    public RePortCTN() {
    }

    public String getSoTaiKhoan() {
        return soTaiKhoan;
    }

    public void setSoTaiKhoan(String soTaiKhoan) {
        this.soTaiKhoan = soTaiKhoan;
    }

    public String getNganHangHuong() {
        return nganHangHuong;
    }

    public void setNganHangHuong(String nganHangHuong) {
        this.nganHangHuong = nganHangHuong;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public Salary getSalary() {
        return salary;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
    }
}
