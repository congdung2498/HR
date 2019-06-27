package com.example.demo.dto;

import com.example.demo.model.User;

import java.util.ArrayList;

public class ListUserDto {
    private long total;

    private long totalNotFiltered;

    private ArrayList<User> rows ;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getTotalNotFiltered() {
        return totalNotFiltered;
    }

    public void setTotalNotFiltered(long totalNotFiltered) {
        this.totalNotFiltered = totalNotFiltered;
    }

    public ArrayList<User> getRows() {
        return rows;
    }

    public void setRows(ArrayList<User> rows) {
        this.rows = rows;
    }
}
