package com.example.demo.dao;

import com.example.demo.dao.config.MyConnectionSql;
import com.example.demo.dto.ListProfileDto;
import com.example.demo.model.Profile;
import com.example.demo.model.Salary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;

public class ManageStaffSQL {
    static MyConnectionSql myConnectionSQL = new MyConnectionSql();
    public static Connection connection = myConnectionSQL.getConnection();

    public ListProfileDto getProfileTable(String search, String offset, String limit){

        ListProfileDto listDto = new ListProfileDto();
        ArrayList<Profile> profileArrayList = new ArrayList<>();
        int total =0;
        try {
            Statement statement = connection.createStatement();
            String sql = "select * from Profile where HoVaTen like '%"+search+"%' or MaNV like  '%"+search+"%' limit "+offset+","+ limit;
            System.out.println(sql);
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Profile profile = new Profile(
                        resultSet.getInt(1),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(14));

                profileArrayList.add(profile);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            Statement statement = connection.createStatement();
            String sql = "select count(iD) from Profile ";
            System.out.println(sql);
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
               total = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        listDto.setTotal(total);
        listDto.setTotalNotFiltered(total);
        listDto.setRows(profileArrayList);
        return listDto;
    }

    public  ArrayList<Profile> getProfile1(){
        ArrayList<Profile> profileArrayList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "select ID,MaNV,HoVaTen,NgaySinh,DonVi,ChucDanh from Profile";
            System.out.println(sql);
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Profile profile = new Profile(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6));

                profileArrayList.add(profile);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return profileArrayList;
    }

//    public ArrayList<Profile> getProfileAllField() {
//        ArrayList<Profile> profileArrayList = new ArrayList<>();
//        try {
//            Statement statement = connection.createStatement();
//            String sql = "select * from Profile";
//            ResultSet resultSet = statement.executeQuery(sql);
//
//            while (resultSet.next()) {
//                Profile profile = new Profile(
//                        resultSet.getInt(1),
//                        resultSet.getString(3),
//                        resultSet.getString(4),
//                        resultSet.getString(5),
//                        resultSet.getString(6),
//                        resultSet.getString(7),
//                        resultSet.getString(8),
//                        resultSet.getString(9),
//                        resultSet.getString(10),
//                        resultSet.getString(11),
//                        resultSet.getString(12),
//                        resultSet.getString(13),
//                        resultSet.getString(14),
//                        resultSet.getInt(15),
//                        resultSet.getString(16));
//
//                profileArrayList.add(profile);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return profileArrayList;
//    }

    public Profile getProfileByMaNV(String maNV) {


        Profile profile = new Profile();
        try {
            Statement statement = connection.createStatement();
            String sql = "select * from Profile where MaNV = '" + maNV + "'";
            ResultSet resultSet = statement.executeQuery(sql);

            if (!resultSet.next()) {
                return profile;
            } else {
                profile = new Profile(
                        resultSet.getInt(1),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getString(8),
                        resultSet.getString(9),
                        resultSet.getString(10),
                        resultSet.getString(11),
                        resultSet.getString(12),
                        resultSet.getString(13),
                        resultSet.getString(14),
                        resultSet.getInt(15),
                        resultSet.getString(16));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return profile;
    }

    public boolean updateProfile(Profile profile) {


        try {
            int profileID = getIdProfileByMaNV(profile.getMaNV());

            PreparedStatement ps = connection.prepareStatement("update Profile set " +
                    "MaNV =  ?," +
                    "HoVaTen = ?," +
                    "NgaySinh = ?," +
                    "DonVi = ?," +
                    "SoDT = ?," +
                    "ChucDanh = ?," +
                    "SoCMND = ?," +
                    "NgayCap = ?," +
                    "NoiCap = ?," +
                    "SoTaiKhoan = ?," +
                    "NganHangHuong = ?" +
                    " where id = ?");
            ps.setString(1, profile.getMaNV());
            ps.setString(2, profile.getHoVaTen());
            ps.setString(3, profile.getNgaySinh());
            ps.setString(4, profile.getDonVi());
            ps.setString(5, profile.getSoDT());
            ps.setString(6, profile.getChucDanh());
            ps.setString(7, profile.getSoCMND());
            ps.setString(8, profile.getNgayCap());
            ps.setString(9, profile.getNoiCap());
            ps.setString(10, profile.getSoTaiKhoan());
            ps.setString(11, profile.getNganHangHuong());
            ps.setInt(12, profileID);

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
    public boolean createProfile(Profile profile) {
        try {

            PreparedStatement ps = connection.prepareStatement("insert into Profile (MaNV,HoVaTen,NgaySinh,DonVi,SoDT,ChucDanh,SoCMND,NgayCap,NoiCap,SoTaiKhoan,NganHangHuong) values(" +
                    "?," +
                    "?," +
                    "?," +
                    "?," +
                    "?," +
                    "?," +
                    "?," +
                    "?," +
                    "?," +
                    "?," +
                    "?" + ")");
            ps.setString(1, profile.getMaNV());
            ps.setString(2, profile.getHoVaTen());
            ps.setString(3, profile.getNgaySinh());
            ps.setString(4, profile.getDonVi());
            ps.setString(5, profile.getSoDT());
            ps.setString(6, profile.getChucDanh());
            ps.setString(7, profile.getSoCMND());
            ps.setString(8, profile.getNgayCap());
            ps.setString(9, profile.getNoiCap());
            ps.setString(10, profile.getSoTaiKhoan());
            ps.setString(11, profile.getNganHangHuong());

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
    public int getIdProfileByMaNV(String maNV) {
        int profileId = 0;
        try {
            Statement statement = connection.createStatement();
            String sql = "select id from Profile WHERE MaNV = '" + maNV + "'";
            ResultSet resultSet = statement.executeQuery(sql);

            if (!resultSet.next()) {
                return profileId;
            } else {
                profileId = resultSet.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return profileId;
    }

    public String getMaNVbyProfileID(int ID) {
        String MaNV = "";
        try {
            Statement statement = connection.createStatement();
            String sql = "select MaNV from Profile WHERE ID = '" + ID + "'";
            ResultSet resultSet = statement.executeQuery(sql);

            if (!resultSet.next()) {
                return MaNV;
            } else {
                MaNV = resultSet.getString(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return MaNV;
    }
    public boolean checkMaNV(String maNV) {
        try {
            Statement statement = connection.createStatement();
            String sql = "select MaNV from Profile WHERE MaNV = '" + maNV + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            if (!resultSet.next()) {
                return true;
            } else {
               return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public ArrayList<String> getAllManv() {
        ArrayList<String> list = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "select * from Profile";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                list.add(resultSet.getString(0));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<Salary>  getSalarybyYear(String maNV,String from,String to) {


        ArrayList<Salary> list = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT TongThuNhap,SoTienChuyenKhoan, DATE_FORMAT(Thang,'%m/%Y') FROM HR_ViettelPost.Salary where ProfileId in " +
                    "(select id from Profile where MaNV = '" + maNV + "')"+
                    "and Thang between  STR_TO_DATE('"+from+"', '%m/%Y') and STR_TO_DATE('"+to+"', '%m/%Y') order by Thang";
            System.out.println(sql);
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Salary salary = new Salary();
                salary.setTongThuNhap(resultSet.getDouble(1));
                salary.setSoTienChuyenKhoan(resultSet.getDouble(2));
                salary.setThang(resultSet.getString(3));
                list.add(salary);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    public ArrayList<String> getAllMonthByMaNV(String maNV,Authentication authentication) {

        Collection<? extends GrantedAuthority> authorities
                = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities){
            if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
                if(!maNV.equals(authentication.getName())){
                    return null;
                }

            }

        }

        ArrayList<String> list = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT  DATE_FORMAT(Thang,'%m/%Y') FROM HR_ViettelPost.Salary where ProfileId in " +
                    "(select id from Profile where MaNV = '" + maNV + "' ) order by Thang;";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                list.add(resultSet.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    public boolean deleteStaff(int id) {
        String MaNV = getMaNVbyProfileID(id);
        try {
            Statement statement = connection.createStatement();
            String sql1 = "delete from Profile where ID="+id;
            statement.executeUpdate(sql1);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        try {
            Statement statement = connection.createStatement();
            String sql1 = "delete from user where username= '" + MaNV + "'";
            statement.executeUpdate(sql1);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
