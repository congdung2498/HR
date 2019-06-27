package com.example.demo.dao;


import com.example.demo.dao.config.MyConnectionSql;
import com.example.demo.dto.ListCategoryDto;
import com.example.demo.dto.ListRecruitmentDto;
import com.example.demo.model.Category;
import com.example.demo.model.Recruitment;

import java.sql.*;
import java.util.ArrayList;

public class CategorySQL {
    static MyConnectionSql myConnectionSQL = new MyConnectionSql();
    public static Connection connection = myConnectionSQL.getConnection();

    public ListCategoryDto getCateTable(String search, String offset, String limit) {
        ListCategoryDto listCategoryDto = new ListCategoryDto();
        int total = 0 ;
        ArrayList<Category> categories = new ArrayList<Category>();
        try {
            Statement statement = connection.createStatement();
            String sql = "select * from Category where Name like '%"+search+"%' limit "+offset+","+ limit;
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Category category = new Category(
                        resultSet.getInt(1),
                        resultSet.getString(2)
                        );

                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            Statement statement = connection.createStatement();
            String sql = "select count(ID) from Category ";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                total = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        listCategoryDto.setRows(categories);
        listCategoryDto.setTotal(total);
        listCategoryDto.setTotalNotFiltered(total);
        return listCategoryDto;
    }


    public boolean createCategory(Category category) {
        try {

            PreparedStatement ps = connection.prepareStatement("insert into Category (Name) values(" +
                    "?" + ")");
            ps.setString(1, category.getName());

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public Category getCategorybyID(int id) {
        Category category = new Category();
        try {
            Statement statement = connection.createStatement();
            String sql = "select * from Category where ID = '" + id + "'";
            System.out.println(sql);
            ResultSet resultSet = statement.executeQuery(sql);

            if (!resultSet.next()) {
                return category;
            }
            category = new Category(
                    resultSet.getInt(1),
                    resultSet.getString(2)
            );


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return category;
    }
    public boolean updateCategory(Category category) {
        try {
            Statement statement = connection.createStatement();
            String sql1 = " update Category set Name = '"+category.getName()+"' where ID ="+category.getId();
            System.out.println(sql1);
            statement.executeUpdate(sql1);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
    public boolean deleteCategory(int id) {
        try {
            Statement statement = connection.createStatement();
            String sql1 = "delete from Category where ID ="+" '"+id +"'";
            System.out.println(sql1);
            statement.executeUpdate(sql1);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
