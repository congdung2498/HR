package com.example.demo.dto;

import com.example.demo.model.News;

import java.util.ArrayList;

public class ListNewsDto {
    private int total;

    private int totalNotFiltered;

    private ArrayList<News> rows ;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalNotFiltered() {
        return totalNotFiltered;
    }

    public void setTotalNotFiltered(int totalNotFiltered) {
        this.totalNotFiltered = totalNotFiltered;
    }

    public ArrayList<News> getRows() {
        return rows;
    }

    public void setRows(ArrayList<News> rows) {
        this.rows = rows;
    }
}
