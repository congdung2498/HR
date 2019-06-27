package com.example.demo.utility;

import com.example.demo.model.User;
import com.example.demo.utility.object.GenNewAccount;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

public class ExportExcel {
    public boolean exportExcelNewAccount(ArrayList<User> genNewUsers){

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Customer_Info");
        int rowNum = 0;
        XSSFRow firstRow = sheet.createRow(rowNum++);
        Cell cell01 = firstRow.createCell(1);
        cell01.setCellValue("Họ Và Tên");
        Cell cell02 = firstRow.createCell(2);
        cell02.setCellValue("Username");
        Cell cell03 = firstRow.createCell(3);
        cell03.setCellValue("Password");

        for (User user : genNewUsers) {
            XSSFRow row = sheet.createRow(rowNum++);

            Cell cell2 = row.createCell(1);
            cell2.setCellValue(user.getHovaten());
            Cell cell3 = row.createCell(2);
            cell3.setCellValue(user.getUserName());
            Cell cell4 = row.createCell(3);
            byte[] decodedBytes = Base64.getDecoder().decode(user.getPassword());
            String decodedString = new String(decodedBytes);
            cell4.setCellValue(decodedString);
        }

        try {
            FileOutputStream outputStream = new FileOutputStream("GenNewAccount.xlsx");
            workbook.write(outputStream);
            workbook.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }
}
