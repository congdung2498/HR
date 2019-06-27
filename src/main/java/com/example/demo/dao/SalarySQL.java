package com.example.demo.dao;

import com.example.demo.dao.config.MyConnectionSql;
import com.example.demo.model.Salary;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SalarySQL {
    static MyConnectionSql myConnectionSQL = new MyConnectionSql();
    public static Connection connection = myConnectionSQL.getConnection();

    public Salary getSalaryByMaNvMonth(String maNV, String month) {
        Salary salary = new Salary();
        try {
            int profileID = convertMaNvToProfileId(maNV);
            if (profileID == 0 || month.equals("")) {
                return salary;
            }

            Statement statement = connection.createStatement();
            String sql = "select *, DATE_FORMAT(Thang,'%m/%Y') from Salary where ProfileID = " + profileID + " and Thang = STR_TO_DATE('"+month+"', '%m/%Y')";
            System.out.println(sql);
            ResultSet resultSet = statement.executeQuery(sql);

            if (!resultSet.next()) {
                return salary;
            }

            String hoVaTen = getHotenBymaNV(maNV);

            salary = new Salary(
                    resultSet.getInt(1),
                    hoVaTen,
                    resultSet.getString(25),
                    Double.parseDouble(resultSet.getString(4)),
                    Double.parseDouble(resultSet.getString(5)),
                    Double.parseDouble(resultSet.getString(6)),
                    Double.parseDouble(resultSet.getString(7)),
                    Double.parseDouble(resultSet.getString(8)),
                    Double.parseDouble(resultSet.getString(9)),
                    Double.parseDouble(resultSet.getString(10)),
                    Double.parseDouble(resultSet.getString(11)),
                    Double.parseDouble(resultSet.getString(12)),
                    Double.parseDouble(resultSet.getString(13)),
                    Double.parseDouble(resultSet.getString(14)),
                    Double.parseDouble(resultSet.getString(15)),
                    Double.parseDouble(resultSet.getString(16)),
                    Double.parseDouble(resultSet.getString(17)),
                    Double.parseDouble(resultSet.getString(18)),
                    Double.parseDouble(resultSet.getString(19)),
                    Double.parseDouble(resultSet.getString(20)),
                    Double.parseDouble(resultSet.getString(21)),
                    Double.parseDouble(resultSet.getString(22)),
                    Double.parseDouble(resultSet.getString(23)),
                    resultSet.getString(24));
        } catch (
                SQLException e) {
            e.printStackTrace();
        }

        return salary;
    }

    public String getHotenBymaNV(String maNV) {
        String hoten = "";
        try {
            Statement statement = connection.createStatement();
            String sql = "select HoVaTen from Profile WHERE MaNV = '" + maNV + "'";
            ResultSet resultSet = statement.executeQuery(sql);

            if(!resultSet.next()){
                return hoten;
            }

            hoten = resultSet.getString(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hoten;
    }

    public int convertMaNvToProfileId(String maNV) {
        int profileID = 0;
        try {
            Statement statement = connection.createStatement();
            String sql = "select ID from Profile WHERE MaNV = '" + maNV + "'";
            ResultSet resultSet = statement.executeQuery(sql);

            if (!resultSet.next()) {
                return profileID;
            }
            profileID = resultSet.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return profileID;
    }
    public boolean delete(int id) {
        try {
            Statement statement = connection.createStatement();
            String sql1 = "delete from Salary where ID="+id;
            statement.executeUpdate(sql1);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
