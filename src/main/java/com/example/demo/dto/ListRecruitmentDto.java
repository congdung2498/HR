package com.example.demo.dto;

import com.example.demo.model.Recruitment;

import java.util.ArrayList;

public class ListRecruitmentDto {


    private int total;

    private int totalNotFiltered;
    private ArrayList<Recruitment> rows ;

    public ListRecruitmentDto(){

    }

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

    public ArrayList<Recruitment> getRows() {
        return rows;
    }

    public void setRows(ArrayList<Recruitment> rows) {
        this.rows = rows;
    }
}
