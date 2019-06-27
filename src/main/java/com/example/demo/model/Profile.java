package com.example.demo.model;

import java.util.ArrayList;

public class Profile {
    private int iD;
    private User user;
    private String maNV;
    private String hoVaTen;
    private String ngaySinh;
    private String donVi;
    private String soDT;
    private String soCMND;
    private String ngayCap;
    private String noiCap;
    private String mail;
    private String soTaiKhoan;
    private String nganHangHuong;
    private String chucDanh;
    private int status;
    private String avata;
    private ArrayList<Comment> comments = new ArrayList<Comment>();
    private ArrayList<Salary> salaries = new ArrayList<Salary>();
    private ArrayList<DateTimeKeeping> dateTimeKeepings = new ArrayList<DateTimeKeeping>();
    private ArrayList<MonthTimeKeeping> monthTimeKeepings = new ArrayList<MonthTimeKeeping>();

    public Profile() {
    }

    public Profile(int iD, String maNV, String hoVaTen, String ngaySinh, String donVi, String soDT, String soCMND, String noiCap,String ngayCap, String mail, String soTaiKhoan, String nganHangHuong, String chucDanh, int status, String avata) {
        this.iD = iD;
        this.maNV = maNV;
        this.hoVaTen = hoVaTen;
        this.ngaySinh = ngaySinh;
        this.donVi = donVi;
        this.soDT = soDT;
        this.soCMND = soCMND;
        this.ngayCap = ngayCap;
        this.noiCap = noiCap;
        this.mail = mail;
        this.soTaiKhoan = soTaiKhoan;
        this.nganHangHuong = nganHangHuong;
        this.chucDanh = chucDanh;
        this.status = status;
        this.avata = avata;
    }


    public Profile(int iD, String maNV, String hoVaTen, String ngaySinh, String donVi, String chucDanh) {
        this.iD = iD;
        this.maNV = maNV;
        this.hoVaTen = hoVaTen;
        this.ngaySinh = ngaySinh;
        this.donVi = donVi;
        this.chucDanh = chucDanh;
    }

    public Profile(String maNV, String hoVaTen, String ngaySinh, String donVi, String soDT, String soCMND, String noiCap ,String ngayCap, String mail, String soTaiKhoan, String nganHangHuong, String chucDanh) {
        this.maNV = maNV;
        this.hoVaTen = hoVaTen;
        this.ngaySinh = ngaySinh;
        this.donVi = donVi;
        this.soDT = soDT;
        this.soCMND = soCMND;
        this.ngayCap = ngayCap;
        this.noiCap = noiCap;
        this.mail = mail;
        this.soTaiKhoan = soTaiKhoan;
        this.nganHangHuong = nganHangHuong;
        this.chucDanh = chucDanh;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "iD='" + iD + '\'' +
                ", maNV='" + maNV + '\'' +
                ", hoVaTen='" + hoVaTen + '\'' +
                ", ngaySinh='" + ngaySinh + '\'' +
                ", donVi='" + donVi + '\'' +
                ", soDT='" + soDT + '\'' +
                ", soCMND='" + soCMND + '\'' +
                ", ngayCap='" + ngayCap + '\'' +
                ", noiCap='" + noiCap + '\'' +
                ", mail='" + mail + '\'' +
                ", soTaiKhoan='" + soTaiKhoan + '\'' +
                ", nganHangHuong='" + nganHangHuong + '\'' +
                ", chucDanh='" + chucDanh + '\'' +
                ", comments=" + comments +
                ", account=" + user +
                ", salaries=" + salaries +
                ", dateTimeKeepings=" + dateTimeKeepings +
                ", monthTimeKeepings=" + monthTimeKeepings +
                '}';
    }

    public int getiD() {
        return iD;
    }

    public void setiD(int iD) {
        this.iD = iD;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setAvata(String avata) {
        this.avata = avata;
    }

    public String getAvata() {
        return avata;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getHoVaTen() {
        return hoVaTen;
    }

    public void setHoVaTen(String hoVaTen) {
        this.hoVaTen = hoVaTen;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getDonVi() {
        return donVi;
    }

    public void setDonVi(String donVi) {
        this.donVi = donVi;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getSoCMND() {
        return soCMND;
    }

    public void setSoCMND(String soCMND) {
        this.soCMND = soCMND;
    }

    public String getNgayCap() {
        return ngayCap;
    }

    public void setNgayCap(String ngayCap) {
        this.ngayCap = ngayCap;
    }

    public String getNoiCap() {
        return noiCap;
    }

    public void setNoiCap(String noiCap) {
        this.noiCap = noiCap;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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

    public String getChucDanh() {
        return chucDanh;
    }

    public void setChucDanh(String chucDanh) {
        this.chucDanh = chucDanh;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User account) {
        this.user = account;
    }

    public ArrayList<Salary> getSalaries() {
        return salaries;
    }

    public void setSalaries(ArrayList<Salary> salaries) {
        this.salaries = salaries;
    }

    public ArrayList<DateTimeKeeping> getDateTimeKeepings() {
        return dateTimeKeepings;
    }

    public void setDateTimeKeepings(ArrayList<DateTimeKeeping> dateTimeKeepings) {
        this.dateTimeKeepings = dateTimeKeepings;
    }

    public ArrayList<MonthTimeKeeping> getMonthTimeKeepings() {
        return monthTimeKeepings;
    }

    public void setMonthTimeKeepings(ArrayList<MonthTimeKeeping> monthTimeKeepings) {
        this.monthTimeKeepings = monthTimeKeepings;
    }
}