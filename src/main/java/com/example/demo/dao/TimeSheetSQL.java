package com.example.demo.dao;

import com.example.demo.dao.config.MyConnectionSql;
import com.example.demo.model.News;
import com.example.demo.model.TimePeriod;
import com.example.demo.model.TimeSheet;
import com.example.demo.model.UploadFileResponse;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TimeSheetSQL {
    static MyConnectionSql myConnectionSQL = new MyConnectionSql();
    public static Connection connection = myConnectionSQL.getConnection();

    ManageStaffSQL manageStaffSQL = new ManageStaffSQL();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    public int createTimeSheet(TimeSheet timeSheet) {
        try {

            String sql = "insert into TimeSheet (ProfileID,Date) values("+timeSheet.getProfile().getiD()+" , '"+ dateFormat.format(timeSheet.getDate())+"')";
            PreparedStatement ps = connection.prepareStatement(sql);

            System.out.println(sql);
            ps.executeUpdate();
            ps.close();

            Statement statement = connection.createStatement();
            String sql1 = "select max(id) from TimeSheet";
            ResultSet resultSet = statement.executeQuery(sql1);

            if (!resultSet.next()) {
                return 0;
            }
            return resultSet.getInt(1);


        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }

    public TimeSheet getTimeSheetbyMaNV(String MaNV, Date date) {
        int profileID = manageStaffSQL.getIdProfileByMaNV(MaNV);
        TimeSheet timeSheet = new TimeSheet();
        try {
            Statement statement = connection.createStatement();
            String sql = "select * from TimeSheet where ProfileID = '" + profileID + "' and Date = '"+dateFormat.format(date)+"'";
            System.out.println(sql);
            ResultSet resultSet = statement.executeQuery(sql);

            if (!resultSet.next()) {
                return timeSheet;
            }
            timeSheet.setId(resultSet.getInt(1));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return timeSheet;
    }

    public ArrayList<TimeSheet> getTimeSheetbyStaffMonth (String maNV, String month ,String year){

        ArrayList<TimeSheet> timeSheets = new ArrayList<>();
        try {
            int profileID = manageStaffSQL.getIdProfileByMaNV(maNV);
            Statement statement = connection.createStatement();
            String sql = "select * from TimeSheet where Month(Date) = "+month+" and YEAR(Date) = "+year+" and ProfileID = " + profileID;
            System.out.println(sql);
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                try{
                    TimeSheet timeheet = new TimeSheet(
                            resultSet.getInt(1),
                            null,
                            null,
                            dateFormat.parse(resultSet.getString(3)),
                            resultSet.getDouble(4),
                            resultSet.getDouble(5),
                            null);

                    timeSheets.add(timeheet);
                }
                catch (ParseException e){

                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (TimeSheet timeSheet : timeSheets)
        {
            try{
                Statement statement = connection.createStatement();
                ArrayList<TimePeriod> timePeriods = new ArrayList<TimePeriod>();
                String sql2 = "select * from TimePeriod where TimeSheetID = " + timeSheet.getId();
                ResultSet resultSet2 = statement.executeQuery(sql2);
                while (resultSet2.next()) {

                    try {
                        Date starttime;
                        Date endtime;
                        if(resultSet2.getString(3)!=null ){
                            starttime =   timeFormat.parse(resultSet2.getString(3));
                        }else starttime = null;
                        if(resultSet2.getString(4)!=null ){

                            endtime =   timeFormat.parse(resultSet2.getString(4));
                        }else endtime = null;

                        TimePeriod timePeriod = new TimePeriod(
                                resultSet2.getInt(1),
                                starttime,
                                endtime
                        );
                        timePeriods.add(timePeriod);
                    }
                    catch (ParseException e){

                    }
                }
                timeSheet.setListTime(timePeriods);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return timeSheets;
    }
//    public boolean updateCategory(Category category) {
//        try {
//            Statement statement = connection.createStatement();
//            String sql1 = " update Category set Name = '"+category.getName()+"' where ID ="+category.getId();
//            System.out.println(sql1);
//            statement.executeUpdate(sql1);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//
//        return true;
//    }
//    public boolean deleteCategory(int id) {
//        try {
//            Statement statement = connection.createStatement();
//            String sql1 = "delete from Category where ID ="+" '"+id +"'";
//            System.out.println(sql1);
//            statement.executeUpdate(sql1);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//
//        return true;
//    }
}
