package com.example.demo.dto;

import com.example.demo.model.Category;

import java.util.ArrayList;

public class ListCategoryDto {

    private int total;

    private int totalNotFiltered;

    private ArrayList<Category> rows ;

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

    public ArrayList<Category> getRows() {
        return rows;
    }

    public void setRows(ArrayList<Category> rows) {
        this.rows = rows;
    }
}
