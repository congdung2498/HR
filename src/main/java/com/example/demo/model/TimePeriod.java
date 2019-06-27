package com.example.demo.model;

import java.sql.Time;
import java.util.Date;

public class TimePeriod {
    private int id;

    private Date starttime;

    private Date endtime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public TimePeriod(){

    }

    public TimePeriod(int id, Date starttime, Date endtime){
        this.id = id;
        this.starttime = starttime;
        this.endtime = endtime;
    }
}
