package com.example.demo.dao;

import com.example.demo.dao.config.MyConnectionSql;
import com.example.demo.dto.ListRecruitmentDto;
import com.example.demo.model.Profile;
import com.example.demo.model.Recruitment;

import java.sql.*;
import java.util.ArrayList;

public class RecruitmentSQL {
    static MyConnectionSql myConnectionSQL = new MyConnectionSql();
    public static Connection connection = myConnectionSQL.getConnection();

    public ListRecruitmentDto getRecruitment1(String search, String offset, String limit) {
        ListRecruitmentDto listRecruitmentDtos = new ListRecruitmentDto();
        int total = 0 ;
        ArrayList<Recruitment> recruitments = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String sql = "select * from Recruitment where HoVaTen like '%"+search+"%' or MaCV like  '%"+search+"%'  or ViTri like  '%"+search+"%' limit "+offset+","+ limit;
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                Recruitment recruitment = new Recruitment(
                        resultSet.getString(2),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(10),
                        resultSet.getString(11));

                recruitments.add(recruitment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            Statement statement = connection.createStatement();
            String sql = "select count(ID) from Recruitment ";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                total = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        listRecruitmentDtos.setRows(recruitments);
        listRecruitmentDtos.setTotal(total);
        listRecruitmentDtos.setTotalNotFiltered(total);
        return listRecruitmentDtos;
    }

    public boolean checkMaCV(String maCV) {
        try {
            Statement statement = connection.createStatement();
            String sql = "select MaCV from Recruitment WHERE MaCV = '" + maCV + "'";
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

    public boolean createRecruitment(Recruitment recruitment) {
        try {

            PreparedStatement ps = connection.prepareStatement("insert into Recruitment (MaCV,Hunter,HoVaTen,NamSinh,SoDT,Email,Truong,Nganh,DoiTuong,ViTri,DonViPV,NguoiPV,NguonCV,NguoiGT,NgayPVVong1,KetQuaV1,NgayPVVong2,KetQuaV2,KetQuaCuoi,NhanXet,Note) values(" +
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
            ps.setString(1, recruitment.getMaCV());
            ps.setString(2, recruitment.getHunter());
            ps.setString(3, recruitment.getHoVaTen());
            ps.setString(4, recruitment.getNamSinh());
            ps.setString(5, recruitment.getSoDT());
            ps.setString(6, recruitment.getEmail());
            ps.setString(7, recruitment.getTruong());
            ps.setString(8, recruitment.getNganh());
            ps.setString(9, recruitment.getDoiTuong());
            ps.setString(10, recruitment.getViTri());
            ps.setString(11, recruitment.getDonViPV());
            ps.setString(12, recruitment.getNguoiPV());
            ps.setString(13, recruitment.getNguonCV());
            ps.setString(14, recruitment.getNguoiGT());
            ps.setString(15, recruitment.getNgayPVVong1());
            ps.setBoolean(16, recruitment.isKetQuaV1());
            ps.setString(17, recruitment.getNgayPVVong2());
            ps.setBoolean(18, recruitment.isKetQuaV2());
            ps.setInt(19, recruitment.getKetQuaCuoi());
            ps.setString(20, recruitment.getNhanXet());
            ps.setString(21, recruitment.getNote());

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public Recruitment getRecruitmentByMaCV(String maCV) {
        Recruitment recruitment = new Recruitment();
        try {
            Statement statement = connection.createStatement();
            String sql = "select * from Recruitment where MaCV = '" + maCV + "'";
            ResultSet resultSet = statement.executeQuery(sql);

            if (!resultSet.next()) {
                return recruitment;
            }
            recruitment = new Recruitment(
                    resultSet.getString(2),
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
                    resultSet.getString(15),
                    resultSet.getString(16),
                    resultSet.getString(17).equals("1") ? true : false,
                    resultSet.getString(18),
                    resultSet.getString(19).equals("1") ? true : false,
                    resultSet.getInt(20),
                    resultSet.getString(21),
                    resultSet.getString(22)
            );


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return recruitment;
    }
    public boolean updateRecruitment(Recruitment recruitment) {
        try {

            PreparedStatement ps = connection.prepareStatement("update Recruitment set " +
                    "MaCV =  ?," +
                    "Hunter = ?," +
                    "HoVaTen = ?," +
                    "NamSinh = ?," +
                    "SoDT = ?," +
                    "Email = ?," +
                    "Truong = ?," +
                    "Nganh = ?," +
                    "DoiTuong = ?," +
                    "ViTri = ?," +
                    "DonViPV = ?," +
                    "NguoiPV = ?," +
                    "NguonCV = ?," +
                    "NguoiGT = ?," +
                    "NgayPVVong1 = ?," +
                    "KetQuaV1 = ?," +
                    "NgayPVVong2 = ?," +
                    "KetQuaV2 = ?," +
                    "KetQuaCuoi = ?," +
                    "NhanXet = ?," +
                    "Note = ?" +
                    " where MaCV = ?");
            ps.setString(1, recruitment.getMaCV());
            ps.setString(2, recruitment.getHunter());
            ps.setString(3, recruitment.getHoVaTen());
            ps.setString(4, recruitment.getNamSinh());
            ps.setString(5, recruitment.getSoDT());
            ps.setString(6, recruitment.getEmail());
            ps.setString(7, recruitment.getTruong());
            ps.setString(8, recruitment.getNganh());
            ps.setString(9, recruitment.getDoiTuong());
            ps.setString(10, recruitment.getViTri());
            ps.setString(11, recruitment.getDonViPV());
            ps.setString(12, recruitment.getNguoiPV());
            ps.setString(13, recruitment.getNguonCV());
            ps.setString(14, recruitment.getNguoiGT());
            ps.setString(15, recruitment.getNgayPVVong1());
            ps.setBoolean(16, recruitment.isKetQuaV1());
            ps.setString(17, recruitment.getNgayPVVong2());
            ps.setBoolean(18, recruitment.isKetQuaV2());
            ps.setInt(19, recruitment.getKetQuaCuoi());
            ps.setString(20, recruitment.getNhanXet());
            ps.setString(21, recruitment.getNote());
            ps.setString(22, recruitment.getMaCV());

            ps.executeUpdate();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
    public boolean deleteRecruitment(String id) {
        try {
            Statement statement = connection.createStatement();
            String sql1 = "delete from Recruitment where MaCV ="+" '"+id +"'";
            System.out.println(sql1);
            statement.executeUpdate(sql1);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
}
