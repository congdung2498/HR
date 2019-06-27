package com.example.demo.dto;

import com.example.demo.model.Profile;
import com.example.demo.model.User;
import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;

public class ListProfileDto {


    private int total;

    private int totalNotFiltered;
    private ArrayList<Profile> rows ;

    public ListProfileDto(){

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

    public ArrayList<Profile> getRows() {
        return rows;
    }

    public void setRows(ArrayList<Profile> rows) {
        this.rows = rows;
    }
}
