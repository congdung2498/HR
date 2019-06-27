package com.example.demo.dao.config;
import java.sql.Connection;
import java.sql.DriverManager;


public class MyConnectionSql {
    public static Connection connection;
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/HR_ViettelPost?useUnicode=yes&characterEncoding=UTF-8&autoReconnect=true&useSSL=false",
                    "root", "123456");
        } catch (Exception e) {
            System.out.println(e);
        }
        return connection;
    }

}
