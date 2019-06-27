package com.example.demo.model;

import java.util.ArrayList;
import java.util.Date;

public class TimeSheet {
    int id;
    String maNV;
    Date date;
    Double total;
    Profile profile;
    String name;
    Double work;
    ArrayList<TimePeriod> ListTime;
    public TimeSheet(){

    }

    public TimeSheet(int id,String manv,String name, Date date, Double total, Double work, Profile profile){
        this.id = id;
        this.date = date;
        this.total = total;
        this.profile = profile;
        this.maNV = manv;
        this.name = name;
        this.work = work;
    }

    public ArrayList<TimePeriod> getListTime() {
        return ListTime;
    }

    public void setListTime(ArrayList<TimePeriod> listTime) {
        ListTime = listTime;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getWork() {
        return work;
    }

    public void setWork(Double work) {
        this.work = work;
    }

    @Override
    public String toString() {
        return "TimeSheet{" +
                "iD='" + id + '\'' +
                ", maNV='" + maNV + '\'' +
                ", date='" + date + '\'' +
                ", total='" + total + '\'' +
                '}';
    }
}
