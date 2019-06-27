package com.example.demo.controller;

import com.example.demo.controller.container.UploadImgCTN;
import com.example.demo.dao.ImportDataSQL;
import com.example.demo.dao.ManageStaffSQL;
import com.example.demo.dao.TimeSheetSQL;
import com.example.demo.model.*;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import com.example.demo.utility.ExportExcel;
import com.example.demo.utility.object.GenNewAccount;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.crypto.BadPaddingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import java.io.*;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;
@Secured("ROLE_ADMIN")
@RestController
@RequestMapping("/rest/upload")
public class ImportDataService {
    ImportDataSQL importDataSQL = new ImportDataSQL();
    ManageStaffSQL manageStaffSQL =new ManageStaffSQL();
    TimeSheetSQL timeSheetSQL = new TimeSheetSQL();
    @Autowired
    private UserService userService;

    @PostMapping("/timesheet")
    public ImportResult uploadTimeSheet(@RequestParam("file") MultipartFile file) {
        ImportResult result = new ImportResult();
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet datatypeSheet = workbook.getSheetAt(0);

            Iterator<Row> iterator = datatypeSheet.iterator();
            iterator.next();
            iterator.next();
            iterator.next();
            ArrayList<TimeSheet> timeSheetList = new ArrayList<>();
            int success = 0;
            int modifi = 0;
            int skip = 0;
            int notStaff = 0;
            int error = 0;




            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                if (currentRow.getCell(0) == null || String.valueOf(currentRow.getCell(0))=="") {
                    break;
                }

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

                Date date = null;
                Date starttime = null;
                Date endtime = null;
                try {
                    date = dateFormat.parse(  String.valueOf(currentRow.getCell(4)));

                    if(currentRow.getCell(6)!=null && String.valueOf(currentRow.getCell(6))!=""){
                        starttime = timeFormat.parse(  String.valueOf(currentRow.getCell(6)));

                    }
                    else starttime = null;
                    if(currentRow.getCell(7)!=null && String.valueOf(currentRow.getCell(7))!=""){
                        endtime = timeFormat.parse(  String.valueOf(currentRow.getCell(7)));
                    }
                    else endtime =null;
                } catch (ParseException e) {

                }
                String manv = String.valueOf(currentRow.getCell(1));
                ManageStaffSQL manageStaffSQL = new ManageStaffSQL();
                int ProfileID = manageStaffSQL.getIdProfileByMaNV(manv);
                if(ProfileID == 0){
                    notStaff++;
                }else{
                    Profile profile = new Profile();
                    profile.setiD(ProfileID);
                    TimeSheet timeSheet  = timeSheetSQL.getTimeSheetbyMaNV(manv,date);
                    int timeSheetID = 0;
                    if(timeSheet!= null && timeSheet.getId()!= 0){
                        timeSheetID = timeSheet.getId();
                        timeSheet = new TimeSheet(
                                timeSheetID,
                                String.valueOf(currentRow.getCell(1)),
                                String.valueOf(currentRow.getCell(2)),
                                date,
                                0.0,
                                0.0,
                                profile);
                    }
                    else{
                        timeSheet = new TimeSheet(
                                0,
                                String.valueOf(currentRow.getCell(1)),
                                String.valueOf(currentRow.getCell(2)),
                                date,
                                0.0,
                                0.0,
                                profile);
                        timeSheetID = timeSheetSQL.createTimeSheet(timeSheet);
                        timeSheet.setId(timeSheetID);
                    }
                    TimePeriod timePeriod = new TimePeriod();
                    timePeriod.setStarttime(starttime);
                    timePeriod.setEndtime(endtime);

                    int imp = importDataSQL.importTimeSheet(timeSheet,timePeriod);
                    if (imp == 1) {
                        success++;
                    }else if(imp==2){
                        skip++;
                    }else if(imp==3){
                        notStaff++;
                    }else if(imp==4){
                        error++;
                    }

                    timeSheetList.add(timeSheet);
                }




            }

            result.setSuccess(success);
            result.setSkip(skip);
            result.setError(error);
            result.setModifi(modifi);
            result.setAccError(notStaff);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Done");

        return result;
    }


    @PostMapping("/staff")
    public ImportResult uploadStaff(@RequestParam("file") MultipartFile file) {
        ImportResult result = new ImportResult();
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet datatypeSheet = workbook.getSheetAt(0);

            Iterator<Row> iterator = datatypeSheet.iterator();
            iterator.next();

            ArrayList<Profile> profileList = new ArrayList<>();
            ArrayList<User> genNewUsers = new ArrayList<>();
            int success = 0;
            int skip = 0;
            int error = 0;
            int accError = 0;
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                if (currentRow.getCell(0) == null || String.valueOf(currentRow.getCell(0))=="") {
                    break;
                }
                Profile profile = new Profile(
                        String.valueOf(currentRow.getCell(1)),
                        String.valueOf(currentRow.getCell(2)),
                        String.valueOf(currentRow.getCell(3)),
                        String.valueOf(currentRow.getCell(4)),
                        String.valueOf(currentRow.getCell(5)),
                        String.valueOf(currentRow.getCell(6)),
                        String.valueOf(currentRow.getCell(7)),
                        String.valueOf(currentRow.getCell(8)),
                        String.valueOf(currentRow.getCell(9)),
                        String.valueOf(currentRow.getCell(10)),
                        String.valueOf(currentRow.getCell(11)),
                        String.valueOf(currentRow.getCell(12)));

                profileList.add(profile);
                Random rnd = new Random();
                int number = rnd.nextInt(999999);
                // this will convert any number sequence into 6 character.
                String genPass =  String.format("%06d", number);
//                String genPass = String.format("%06d", (int) Math.random());
                String encodedString = Base64.getEncoder().encodeToString(genPass.getBytes());

//                byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
//                String decodedString = new String(decodedBytes);
//                System.out.println(decodedString); // GP Coder

//                String encodedStringWithoutPadding =
//                        Base64.getEncoder().withoutPadding().encodeToString(str.getBytes());
//                System.out.println(encodedStringWithoutPadding); // R1AgQ29kZXI

                int imp = importDataSQL.importStaffProfileByExcel(profile, encodedString);
                if (imp == 0) {
                    success++;
                    User user = new User(
                            profile.getMaNV(),
                            encodedString,
                            "ROLE_USER",
                            profile.getHoVaTen()

                    );
                    genNewUsers.add(user);
                }else if(imp==1){
                    skip++;
                }else if(imp==2){
                    error++;
                }else if(imp==3){
                    accError++;
                }
            }

            ExportExcel exportExcel = new ExportExcel();
            exportExcel.exportExcelNewAccount(genNewUsers);

            for (Profile profile : profileList) {
                System.out.println(profile.toString());
            }
            result.setSuccess(success);
            result.setSkip(skip);
            result.setError(error);
            result.setAccError(accError);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @PostMapping("/candidate")
    public String uploadCandidate(@RequestParam("file") MultipartFile file) {
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet datatypeSheet = workbook.getSheetAt(0);

            Iterator<Row> iterator = datatypeSheet.iterator();
            iterator.next();

            ArrayList<Recruitment> profileList = new ArrayList<>();
            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                if (currentRow.getCell(0) == null || String.valueOf(currentRow.getCell(0))=="") {
                    break;
                }
                Recruitment recruitment = new Recruitment(
                        String.valueOf(currentRow.getCell(1)),
                        String.valueOf(currentRow.getCell(2)),
                        String.valueOf(currentRow.getCell(3)),
                        String.valueOf(currentRow.getCell(4)),
                        String.valueOf(currentRow.getCell(5)),
                        String.valueOf(currentRow.getCell(6)),
                        String.valueOf(currentRow.getCell(7)),
                        String.valueOf(currentRow.getCell(8)),
                        String.valueOf(currentRow.getCell(9)),
                        String.valueOf(currentRow.getCell(10)),
                        String.valueOf(currentRow.getCell(11)),
                        String.valueOf(currentRow.getCell(12)),
                        String.valueOf(currentRow.getCell(13)),
                        String.valueOf(currentRow.getCell(14)),
                        String.valueOf(currentRow.getCell(15)),
                        String.valueOf(currentRow.getCell(16)).equals("pass") ? true : false,
                        String.valueOf(currentRow.getCell(17)),
                        String.valueOf(currentRow.getCell(18)).equals("pass") ? true : false,
                        currentRow.getCell(19) == null ? 0 : Integer.parseInt(String.valueOf(currentRow.getCell(19))),
                        String.valueOf(currentRow.getCell(20)),
                        String.valueOf(currentRow.getCell(21)));

                profileList.add(recruitment);
                importDataSQL.importCVProfile(recruitment);
            }

            for (Recruitment recruitment : profileList) {
                System.out.println(recruitment.toString());
            }

            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Done");

        return "Sucess";
    }

    @PostMapping("/salary")
    public ArrayList<Salary> uploadSalary(@RequestParam("file") MultipartFile file) {
        ArrayList<Salary> emtyList = new ArrayList<>();
        try {
            Workbook workbook = new XSSFWorkbook(file.getInputStream());
            Sheet datatypeSheet = workbook.getSheetAt(0);

            Iterator<Row> iterator = datatypeSheet.iterator();
            iterator.next();

            ArrayList<Salary> profileList = new ArrayList<>();

            while (iterator.hasNext()) {
                Row currentRow = iterator.next();
                if (currentRow.getCell(0) == null || String.valueOf(currentRow.getCell(0))=="") {
                    break;
                }

                // excel to object
                Salary salary = new Salary();

                if (currentRow.getCell(1) == null) {
                    salary.setMaNV("");
                } else {
                    salary.setMaNV(String.valueOf(currentRow.getCell(1).getStringCellValue()));
                }if (currentRow.getCell(2) == null) {
                    salary.setHoVaTen("");
                } else {
                    salary.setHoVaTen(String.valueOf(currentRow.getCell(2).getStringCellValue()));
                }
                if (currentRow.getCell(3) == null) {
                    salary.setThang("");
                } else {
                    salary.setThang(String.valueOf(currentRow.getCell(3).getStringCellValue()));
                }
                if (currentRow.getCell(4) == null) {
                    salary.setLuongChucDanh(0.0);
                } else {
                    salary.setLuongChucDanh(Double.parseDouble(String.valueOf(currentRow.getCell(4))));
                }
                if (currentRow.getCell(5) == null) {
                    salary.setLuongThamNien(0.0);
                } else {
                    salary.setLuongThamNien(Double.parseDouble(String.valueOf(currentRow.getCell(5))));
                }
                if (currentRow.getCell(6) == null) {
                    salary.setLuongLePhep(0.0);
                } else {
                    salary.setLuongLePhep(Double.parseDouble(String.valueOf(currentRow.getCell(6))));
                }
                if (currentRow.getCell(7) == null) {
                    salary.setBoiDuongTruc(0.0);
                } else {
                    salary.setBoiDuongTruc(Double.parseDouble(String.valueOf(currentRow.getCell(7))));
                }
                if (currentRow.getCell(8) == null) {
                    salary.setDieuChinhBoSung(0.0);
                } else {
                    salary.setDieuChinhBoSung(Double.parseDouble(String.valueOf(currentRow.getCell(8))));
                }
                if (currentRow.getCell(9) == null) {
                    salary.setPhuCapDoanThe(0.0);
                } else {
                    salary.setPhuCapDoanThe(Double.parseDouble(String.valueOf(currentRow.getCell(9))));
                }
                if (currentRow.getCell(10) == null) {
                    salary.setPhuCapAnCa(0.0);
                } else {
                    salary.setPhuCapAnCa(Double.parseDouble(String.valueOf(currentRow.getCell(10))));
                }
                if (currentRow.getCell(11) == null) {
                    salary.setPhuCapDienThoai(0.0);
                } else {
                    salary.setPhuCapDienThoai(Double.parseDouble(String.valueOf(currentRow.getCell(11))));
                }
                if (currentRow.getCell(12) == null) {
                    salary.setTongThuNhap(0.0);
                } else {
                    salary.setTongThuNhap(Double.parseDouble(String.valueOf(currentRow.getCell(12).getNumericCellValue())));
                }
                if (currentRow.getCell(13) == null) {
                    salary.setBHXH(0.0);
                } else {
                    salary.setBHXH(Double.parseDouble(String.valueOf(currentRow.getCell(13))));
                }
                if (currentRow.getCell(14) == null) {
                    salary.setBHYT(0.0);
                } else {
                    salary.setBHYT(Double.parseDouble(String.valueOf(currentRow.getCell(14))));
                }
                if (currentRow.getCell(15) == null) {
                    salary.setBHTN(0.0);
                } else {
                    salary.setBHTN(Double.parseDouble(String.valueOf(currentRow.getCell(15))));
                }
                if (currentRow.getCell(16) == null) {
                    salary.setKinhPhiCongDoan(0.0);
                } else {
                    salary.setKinhPhiCongDoan(Double.parseDouble(String.valueOf(currentRow.getCell(16))));
                }
                if (currentRow.getCell(17) == null) {
                    salary.setThueTNCN(0.0);
                } else {
                    salary.setThueTNCN(Double.parseDouble(String.valueOf(currentRow.getCell(17))));
                }
                if (currentRow.getCell(18) == null) {
                    salary.setTongKhauTru(0.0);
                } else {
                    salary.setTongKhauTru(Double.parseDouble(String.valueOf(currentRow.getCell(18).getNumericCellValue())));
                }
                if (currentRow.getCell(19) == null) {
                    salary.setSoTienChuyenKhoan(0.0);
                } else {
                    salary.setSoTienChuyenKhoan(Double.parseDouble(String.valueOf(currentRow.getCell(19).getNumericCellValue())));
                }
                if (currentRow.getCell(20) == null) {
                    salary.setCongCheDo(0.0);
                } else {
                    salary.setCongCheDo(Double.parseDouble(String.valueOf(currentRow.getCell(20))));
                }
                if (currentRow.getCell(21) == null) {
                    salary.setCongThucTe(0.0);
                } else {
                    salary.setCongThucTe(Double.parseDouble(String.valueOf(currentRow.getCell(21))));
                }
                if (currentRow.getCell(22) == null) {
                    salary.setNgayNghiLePhep(0.0);
                } else {
                    salary.setNgayNghiLePhep(Double.parseDouble(String.valueOf(currentRow.getCell(22))));
                }
                if (currentRow.getCell(23) == null) {
                    salary.setNgayTruc(0.0);
                } else {
                    salary.setNgayTruc(Double.parseDouble(String.valueOf(currentRow.getCell(23))));
                }
                if (currentRow.getCell(24) == null) {
                    salary.setKI(null);
                } else {
                    salary.setKI(String.valueOf(currentRow.getCell(24).getStringCellValue()));
                }
                if(manageStaffSQL.checkMaNV(salary.getMaNV())){
                    emtyList.add(salary);
                }
                profileList.add(salary);
                importDataSQL.importSalary(salary);
            }

            for (Salary salary : profileList) {
                System.out.println(salary.toString());
            }

            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Done");

        return emtyList;
    }
    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @PostMapping("/user/avata")
    public String uploadAvata(@RequestBody UploadImgCTN uploadImgCTN) {
        System.out.println(uploadImgCTN.getMaNV() + "...." + uploadImgCTN.getImg());
        if( importDataSQL.updateImg(uploadImgCTN.getImg(), uploadImgCTN.getMaNV())){
            String str_img = importDataSQL.getImageByMaNV(uploadImgCTN.getMaNV());
            return str_img;
        }
       else return "false";
    }
}
