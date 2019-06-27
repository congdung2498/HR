package com.example.demo.dao;

import com.example.demo.dao.config.MyConnectionSql;
import com.example.demo.model.*;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ImportDataSQL {
    static MyConnectionSql myConnectionSQL = new MyConnectionSql();
    public static Connection connection = myConnectionSQL.getConnection();

    public int importStaffProfileByExcel(Profile profile, String password) { // return 0 - success , 1 - skip , 2 - error, 3 - accError
        if (importStaffProfile(profile)==1) {
            return 1;
        }
        if (importStaffProfile(profile)==2) {
            return 2;
        }
        if (!createAccount(profile.getMaNV(), password,  "ROLE_USER" , profile.getHoVaTen())) {
            return 3;
        }
        int accountId = getIdAccountByMaNV(profile.getMaNV());
        if (accountId != 0) {
            updateAccountIdProfile(accountId, profile.getMaNV());
        }

        return 0;
    }

    public int importStaffProfile(Profile profile) {      // return 0 - success , 1 - skip , 2 - error
        try {
            Statement statement = connection.createStatement();
            if (checkExistStaff(profile.getMaNV(), profile.getSoCMND()) == true) {
                return 1;
            } else {
                String sql = "insert into Profile (MaNV,HoVaTen,NgaySinh,DonVi,SoDT,SoCMND,NgayCap,NoiCap,Mail,SoTaiKhoan,NganHangHuong,ChucDanh) values (" +
                        "'" + profile.getMaNV() + "'," +
                        "'" + profile.getHoVaTen() + "'," +
                        "'" + profile.getNgaySinh() + "'," +
                        "'" + profile.getDonVi() + "'," +
                        "'" + profile.getSoDT() + "'," +
                        "'" + profile.getSoCMND() + "'," +
                        "'" + profile.getNgayCap() + "'," +
                        "'" + profile.getNoiCap() + "'," +
                        "'" + profile.getMail() + "'," +
                        "'" + profile.getSoTaiKhoan() + "'," +
                        "'" + profile.getNganHangHuong() + "'," +
                        "'" + profile.getChucDanh() + "')";
                statement.executeUpdate(sql);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return 2;
        }

        return 0;
    }

    public Boolean createAccount(String userName, String passWord , String role, String hovaten) {
        try {
            Statement statement = connection.createStatement();
            String sql = "insert into user (username,password,role,hovaten) values ('" + userName + "','" + passWord + "','" + role + "','" + hovaten + "')";
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public Boolean updateAccountIdProfile(int userID, String maNV) {
        try {
            Statement statement = connection.createStatement();
            String sql = "update Profile set UserID = " + userID + " where maNV = '" + maNV + "'";
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public int getIdAccountByMaNV(String maNV) {
        int accountId = 0;
        try {
            Statement statement = connection.createStatement();
            String sql = "select id from user WHERE username = '" + maNV + "'";
            ResultSet resultSet = statement.executeQuery(sql);

            if (!resultSet.next()) {
                return accountId;
            } else {
                accountId = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accountId;
    }

    public boolean checkExistStaff(String maNV, String SoCMND) {
        try {
            Statement statement = connection.createStatement();
            String sql = "select * from Profile WHERE MaNV = '" + maNV + "' or SoCMND = '" + SoCMND + "'";
            ResultSet resultSet = statement.executeQuery(sql);

            if (!resultSet.next()) {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }


    public boolean importCVProfile(Recruitment recruitment) {
        try {
            Statement statement = connection.createStatement();
            if (checkExistCV(recruitment.getMaCV()) == true) {
                return false;
            } else {

                int ketQuaVong1, ketQuaVong2;
                if (recruitment.isKetQuaV1() == true)
                    ketQuaVong1 = 1;
                else ketQuaVong1 = 0;
                if (recruitment.isKetQuaV2() == true)
                    ketQuaVong2 = 1;
                else ketQuaVong2 = 0;

                String sql = "insert into Recruitment (MaCV, Hunter, HoVaTen, NamSinh, SoDT, Email, Truong, Nganh, DoiTuong, ViTri," +
                        " DonViPV, NguoiPV, NguonCV, NguoiGT, NgayPVVong1, KetQuaV1, NgayPVVong2, KetQuaV2, KetQuaCuoi, NhanXet, Note)" +
                        " values (" +
                        "'" + recruitment.getMaCV() + "'," +
                        "'" + recruitment.getHunter() + "'," +
                        "'" + recruitment.getHoVaTen() + "'," +
                        "'" + recruitment.getNamSinh() + "'," +
                        "'" + recruitment.getSoDT() + "'," +
                        "'" + recruitment.getEmail() + "'," +
                        "'" + recruitment.getTruong() + "'," +
                        "'" + recruitment.getNganh() + "'," +
                        "'" + recruitment.getDoiTuong() + "'," +
                        "'" + recruitment.getViTri() + "'," +
                        "'" + recruitment.getDonViPV() + "'," +
                        "'" + recruitment.getNguoiPV() + "'," +
                        "'" + recruitment.getNguonCV() + "'," +
                        "'" + recruitment.getNguoiGT() + "'," +
                        "'" + recruitment.getNgayPVVong1() + "'," +
                        ketQuaVong1 + "," +
                        "'" + recruitment.getNgayPVVong2() + "'," +
                        ketQuaVong2 + "," +
                        recruitment.getKetQuaCuoi() + "," +
                        "'" + recruitment.getNhanXet() + "'," +
                        "'" + recruitment.getNote() + "')";

                statement.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean checkExistCV(String maCV) {
        try {
            Statement statement = connection.createStatement();
            String sql = "select * from Recruitment WHERE MaCV = '" + maCV + "'";
            ResultSet resultSet = statement.executeQuery(sql);

            if (!resultSet.next()) {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    public boolean importSalary(Salary salary) {
        try {
            int profileID = convertMaNvToProfileId(salary.getMaNV());
            if (profileID == 0 || salary.getThang().equals("")) {
                return false;
            }

            Statement statement = connection.createStatement();
            if (checkExistSalary(profileID, salary.getThang()) == true) {
                return false;
            } else {

                String sql = "insert into Salary (ProfileID,Thang, LuongChucDanh, ThamNien, LePhep, BoiDuongTruc," +
                        "DieuChinhBoSung, PhuCapDoanThe, PhuCapAnCa, PhuCapDienThoai, TongThuNhap, BHXH," +
                        "BHYT, BHTN, KinhPhiCongDoan, ThueTNCN, TongKhauTru, SoTienChuyenKhoan," +
                        "CongCheDo, CongThucTe, NgayNghiLePhep, NgayTruc, KI)" +
                        " values (" +
                        profileID + "," +
                        "STR_TO_DATE('"+salary.getThang()+"', '%m/%Y') ," +
                        salary.getLuongChucDanh() + "," +
                        salary.getLuongThamNien() + "," +
                        salary.getLuongLePhep() + "," +
                        salary.getBoiDuongTruc() + "," +
                        salary.getDieuChinhBoSung() + "," +
                        salary.getPhuCapDoanThe() + "," +
                        salary.getPhuCapAnCa() + "," +
                        salary.getPhuCapDienThoai() + "," +
                        salary.getTongThuNhap() + "," +
                        salary.getBHXH() + "," +
                        salary.getBHYT() + "," +
                        salary.getBHTN() + "," +
                        salary.getKinhPhiCongDoan() + "," +
                        salary.getThueTNCN() + "," +
                        salary.getTongKhauTru() + "," +
                        salary.getSoTienChuyenKhoan() + "," +
                        salary.getCongCheDo() + "," +
                        salary.getCongThucTe() + "," +
                        salary.getNgayNghiLePhep() + "," +
                        salary.getNgayTruc() + "," +
                        "'" + salary.getKI() + "')";
                statement.executeUpdate(sql);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean checkExistSalary(int profileID, String thang) {
        try {
            Statement statement = connection.createStatement();
            String sql = "select * from Salary WHERE ProfileID = " + profileID + " and Thang = " +   "STR_TO_DATE('"+thang+"', '%m/%Y') ";
            ResultSet resultSet = statement.executeQuery(sql);
            if (!resultSet.next()) {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

    public int convertMaNvToProfileId(String maNV) {
        int profileID = 0;
        try {
            Statement statement = connection.createStatement();
            String sql = "select * from Profile WHERE MaNV = '" + maNV + "'";
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

    public boolean updateImg(String strImg, String maNV) {
        try {
            int profileID = convertMaNvToProfileId(maNV);
            if (profileID == 0) {
                return false;
            }

            PreparedStatement ps = connection.prepareStatement("update Profile set Avata =  ? where id = ?");
            ps.setString(1, strImg);
            ps.setInt(2, profileID);
            ps.executeUpdate();

            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public String getImageByMaNV(String maNV) {
        String str_img = "";
        try {
            Statement statement = connection.createStatement();
            String sql = "select Avata from Profile WHERE MaNV = '" + maNV + "'";
            ResultSet resultSet = statement.executeQuery(sql);

            if (!resultSet.next()) {
                return str_img;
            }

            str_img = resultSet.getString(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return str_img;
    }
    public int importTimeSheet(TimeSheet timeSheet, TimePeriod timePeriod){// return 1- add , 2 - skip , 3 - notstaff
        try {
            Statement statement = connection.createStatement();

            int check = checkTimeSheet(timeSheet,timePeriod.getStarttime(),timePeriod.getEndtime());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

            if (check ==1) {       //them moi

                if(timePeriod.getStarttime() !=null && timePeriod.getEndtime() !=null ){     //co gio ra vao

                    if(timePeriod.getStarttime().after(timePeriod.getEndtime())){        // doi cho
                        Date date = timePeriod.getStarttime();
                        timePeriod.setStarttime(timePeriod.getEndtime());
                        timePeriod.setEndtime(date);
                    }
                    try {
                        if(timePeriod.getStarttime().before( timeFormat.parse("10:00")) && timePeriod.getEndtime().after( timeFormat.parse("15:30"))){
                            timeSheet.setWork(1.0);    //truoc 10h & sau 15h30 -> 1
                        }
                        else {
                            if(timePeriod.getStarttime().before( timeFormat.parse("10:00")) && timePeriod.getEndtime().before( timeFormat.parse("15:30"))){
                                timeSheet.setWork(0.5);   //truoc 10h & truoc 15h30  -> 0.5
                            }
                            else{
                                if(timePeriod.getStarttime().after( timeFormat.parse("10:00")) && timePeriod.getEndtime().after( timeFormat.parse("15:30"))){
                                    timeSheet.setWork(0.5);  //sau 10h & sau 15h30  -> 0.5
                                }
                                else {
                                    if(timePeriod.getStarttime().after( timeFormat.parse("10:00")) && timePeriod.getEndtime().before( timeFormat.parse("15:30"))){
                                        timeSheet.setWork(0.0);  //sau 10h & truoc 15h30  -> 0.0
                                    }
                                }
                            }
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                else { // ko co gio ra hoac vao
                    if(timePeriod.getStarttime() !=null && timePeriod.getEndtime() ==null ){ // ko co gio ra
                        try {
                            if(timePeriod.getStarttime().before( timeFormat.parse("10:00"))){
                                timeSheet.setWork(0.5);    //truoc 10h & ko check out -> 0.5
                            }
                            else {
                                if(timePeriod.getStarttime().after( timeFormat.parse("10:00"))){
                                    timeSheet.setWork(0.5);   //sau 10h &  ko check out -> 0
                                }
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String sql2 = "update TimeSheet set Total = '"+totalTime(timePeriod.getStarttime(),timePeriod.getEndtime())+"' , work ='"+timeSheet.getWork()+"' where ID = "+timeSheet.getId()+"";
                        statement.executeUpdate(sql2);

                        String sql3 = "insert into TimePeriod (TimeSheetID,StartTime,EndTime) values("+timeSheet.getId()+", '"+timeFormat.format(timePeriod.getStarttime())+"', "+timePeriod.getEndtime()+")";
                        statement.executeUpdate(sql3);
                        return 1;
                    }
                    else if(timePeriod.getStarttime() ==null && timePeriod.getEndtime() !=null ){ // ko co gio vao
                        try {
                            if(timePeriod.getEndtime().after( timeFormat.parse("15:30"))){
                                timeSheet.setWork(0.5);    //ko check in & check out sau 15h30 ->0.5
                            }
                            else {
                                if(timePeriod.getEndtime().before( timeFormat.parse("15:30"))){
                                    timeSheet.setWork(0.0);   // ko check in & check out truoc 15h30 -> 0
                                }
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String sql2 = "update TimeSheet set Total = '"+totalTime(timePeriod.getStarttime(),timePeriod.getEndtime())+"' , work ='"+timeSheet.getWork()+"' where ID = "+timeSheet.getId()+"";
                        statement.executeUpdate(sql2);

                        String sql3 = "insert into TimePeriod (TimeSheetID,StartTime,EndTime) values("+timeSheet.getId()+", "+timePeriod.getStarttime()+", '"+timeFormat.format(timePeriod.getEndtime())+"')";
                        statement.executeUpdate(sql3);
                        return 1;
                    }
                    else if(timePeriod.getStarttime() ==null && timePeriod.getEndtime() ==null ){ // ko co gio ra vao
                        timeSheet.setWork(0.0);
                        String sql2 = "update TimeSheet set Total = '"+totalTime(timePeriod.getStarttime(),timePeriod.getEndtime())+"' , work ='"+timeSheet.getWork()+"' where ID = "+timeSheet.getId()+"";
                        statement.executeUpdate(sql2);

                        String sql3 = "insert into TimePeriod (TimeSheetID,StartTime,EndTime) values("+timeSheet.getId()+", "+timePeriod.getStarttime()+", "+timePeriod.getEndtime()+")";
                        statement.executeUpdate(sql3);
                        return 1;
                    }

                }
                String sql2 = "update TimeSheet set Total = '"+totalTime(timePeriod.getStarttime(),timePeriod.getEndtime())+"' , work ='"+timeSheet.getWork()+"' where ID = "+timeSheet.getId()+"";
                statement.executeUpdate(sql2);

                String sql3 = "insert into TimePeriod (TimeSheetID,StartTime,EndTime) values("+timeSheet.getId()+", '"+timeFormat.format(timePeriod.getStarttime())+"', '"+timeFormat.format(timePeriod.getEndtime())+"')";
                statement.executeUpdate(sql3);
                return 1;
            }
                else {
                    if(check == 0){       // cos nhieu timeperiod
                        ArrayList<TimePeriod> ListTimePeriod = new ArrayList<>();
                        String clause1;
                        String clause2;
                        if(timePeriod.getStarttime() !=null && timePeriod.getEndtime() !=null ){     //co gio ra vao

                            if(timePeriod.getStarttime().after(timePeriod.getEndtime())){        // doi cho
                                Date date = timePeriod.getStarttime();
                                timePeriod.setStarttime(timePeriod.getEndtime());
                                timePeriod.setEndtime(date);
                            }
                        }
                        try {
                            if(timePeriod.getStarttime()==null ){
                                clause1 = "StartTime is not null";
                            }
                            else  clause1 = "StartTime <> '"+timeFormat.format(timePeriod.getStarttime())+"'";
                            if(timePeriod.getEndtime()==null){
                                clause2 = "EndTime is not null";
                            }
                            else clause2 = "EndTime <> '"+timeFormat.format(timePeriod.getEndtime())+"'";

                            String sql = "select * from TimePeriod where TimeSheetID = '" + timeSheet.getId() + "'and StartTime is not null and EndTime is not null and "+clause1+" and "+clause2;

                            ResultSet resultSet = statement.executeQuery(sql);

                            while (resultSet.next()) {
                                try {
                                    TimePeriod time = new TimePeriod(
                                            resultSet.getInt(1),
                                            timeFormat.parse(String.valueOf(resultSet.getString(3))),
                                            timeFormat.parse(String.valueOf(resultSet.getString(4))));

                                    ListTimePeriod.add(time);
                                }
                                catch (ParseException e){}
                                if(timePeriod.getStarttime()!= null && timePeriod.getEndtime()!=null){
                                    ListTimePeriod.add(timePeriod);
                                }
                                Date minH = ListTimePeriod.get(0).getStarttime();
                                Date maxH = ListTimePeriod.get(0).getEndtime();
                                for( int i=0 ; i<ListTimePeriod.size(); i++){
                                    if(ListTimePeriod.get(i).getStarttime().before(minH)){
                                        minH = ListTimePeriod.get(i).getStarttime();
                                    }
                                    if(ListTimePeriod.get(i).getEndtime().after(maxH)){
                                        maxH = ListTimePeriod.get(i).getEndtime();
                                    }
                                }
                                try {
                                    if(minH.before( timeFormat.parse("10:00")) && maxH.after( timeFormat.parse("15:30"))){
                                        timeSheet.setWork(1.0);    //truoc 10h & sau 15h30 -> 1
                                    }
                                    else {
                                        if(minH.before( timeFormat.parse("10:00")) && maxH.before( timeFormat.parse("15:30"))){
                                            timeSheet.setWork(0.5);   //truoc 10h & truoc 15h30  -> 0.5
                                        }
                                        else{
                                            if(minH.after( timeFormat.parse("10:00")) && maxH.after( timeFormat.parse("15:30"))){
                                                timeSheet.setWork(0.5);  //sau 10h & sau 15h30  -> 0.5
                                            }
                                            else {
                                                if(minH.after( timeFormat.parse("10:00")) && maxH.before( timeFormat.parse("15:30"))){
                                                    timeSheet.setWork(0.0);  //sau 10h & truoc 15h30  -> 0.0
                                                }
                                            }
                                        }
                                    }
                                    String sql2 = "update TimeSheet set Total = '"+totalTime(minH,maxH)+"' , work ='"+timeSheet.getWork()+"' where ID = "+timeSheet.getId()+"";
                                    statement.executeUpdate(sql2);

                                    if(timePeriod.getStarttime()!=null){
                                        clause1 ="'"+timeFormat.format(timePeriod.getStarttime())+"'";
                                    }else{
                                        clause1 = null;
                                    }
                                    if(timePeriod.getEndtime()!=null){
                                        clause2 ="'"+timeFormat.format(timePeriod.getEndtime())+"'";
                                    }
                                    else {
                                        clause2 = null;
                                    }
                                    String sql3 = "insert into TimePeriod (TimeSheetID,StartTime,EndTime) values("+timeSheet.getId()+", "+clause1+", "+clause2+")";
                                    statement.executeUpdate(sql3);
                                    return 1;
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }


                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    else return check;
                }




        } catch (SQLException e) {
            e.printStackTrace();
            return 4;
        }
        return  4;
    }

    public Double totalTime(Date startTime, Date endTime){
        if(startTime == null || endTime == null) return 0.0;
        Double secs = (double)(endTime.getTime() - startTime.getTime()) / 1000;
        double hours = secs / 3600;
        return hours;
    }

    public int checkTimeSheet(TimeSheet timeSheet, java.util.Date starttime, Date endtime) { //0-nhieuTimeperiod 1-add  2-skip 3-notexitStaff
        try {
            Statement statement = connection.createStatement();
            String sql = "select ID from Profile where MaNV = '" + timeSheet.getMaNV() + "'";
            ResultSet resultSet = statement.executeQuery(sql);

            if (!resultSet.next()) {
                return 3;
            } else {
                int profileID =  resultSet.getInt(1);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
                String clause1;
                String clause2;
                if(starttime !=null & endtime !=null){
                    if(starttime.after(endtime)){        // doi cho
                        Date date = starttime;
                        starttime = endtime;
                        endtime = date;
                    }
                }
                if(starttime==null ){
                    clause1 = "StartTime is null";
                }
                else  clause1 = "StartTime = '"+timeFormat.format(starttime)+"'";
                if(endtime==null){
                    clause2 = "EndTime is null";
                }
                else clause2 = "EndTime = '"+timeFormat.format(endtime)+"'";

                String sql2 = "select ID from TimePeriod where TimeSheetID = '" + timeSheet.getId() + "' and "+clause1+" and "+clause2;
                ResultSet resultSet2 = statement.executeQuery(sql2);
                if (resultSet2.next()) {
                    return 2;
                }
                else {
                    if(starttime==null ){
                        clause1 = "StartTime is not null";
                    }
                    else  clause1 = "StartTime <> '"+timeFormat.format(starttime)+"'";
                    if(endtime==null){
                        clause2 = "EndTime is not null";
                    }
                    else clause2 = "EndTime <> '"+timeFormat.format(endtime)+"'";

                    String sql3 = "select ID from TimePeriod where TimeSheetID = '" + timeSheet.getId() + "' and "+clause1+" and "+clause2;
                    ResultSet resultSet3 = statement.executeQuery(sql3);
                    if (resultSet3.next()) {
                        return 0;
                    }
                    else return 1;
                }

            }



        } catch (SQLException e) {
            e.printStackTrace();
            return 4;
        }
    }
}
