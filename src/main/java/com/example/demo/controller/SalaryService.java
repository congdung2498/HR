package com.example.demo.controller;

import com.example.demo.controller.container.RePortCTN;
import com.example.demo.dao.ManageStaffSQL;
import com.example.demo.dao.SalarySQL;
import com.example.demo.model.Profile;
import com.example.demo.model.Salary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/rest")
public class SalaryService {
    SalarySQL salarySQL = new SalarySQL();
    ManageStaffSQL manageStaffSQL = new ManageStaffSQL();

    @PostMapping("/user/getSalaryByMaCvMonth")
    public ResponseEntity getSataffInfo(@RequestBody Salary salary, Authentication authentication) {

        Collection<? extends GrantedAuthority> authorities
                = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities){
            if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
                if(!salary.getMaNV().equals(authentication.getName())){
                    return new ResponseEntity<String>("UnAuthorized", HttpStatus.FORBIDDEN);
                }

            }
        }

        Salary salaryOut = salarySQL.getSalaryByMaNvMonth(salary.getMaNV(), salary.getThang());
        Profile profile = manageStaffSQL.getProfileByMaNV(salary.getMaNV());

        RePortCTN rePortCTN = new RePortCTN();
        rePortCTN.setHoten(profile.getHoVaTen());
        rePortCTN.setSalary(salaryOut);
        rePortCTN.setNganHangHuong(profile.getNganHangHuong());
        rePortCTN.setSoTaiKhoan(profile.getSoTaiKhoan());

        // gen salary excel

        return ResponseEntity.ok(rePortCTN);
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/getAllManv")
    public ResponseEntity getAllManv() {
        return ResponseEntity.ok(manageStaffSQL.getAllManv());
    }

    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @GetMapping("/getAllMonthByMaNV")
    public ResponseEntity getAllMonthByMaNV(@RequestParam String maNV,Authentication authentication) {
        return ResponseEntity.ok(manageStaffSQL.getAllMonthByMaNV(maNV,authentication));
    }

    @Secured({"ROLE_ADMIN","ROLE_USER"})
    @GetMapping("/getSalarybyYear")
    public ResponseEntity getSalarybyYear(@RequestParam String maNV,@RequestParam String from,@RequestParam String to,Authentication authentication) {

        Collection<? extends GrantedAuthority> authorities
                = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities){
            if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
                if(!maNV.equals(authentication.getName())){
                    return new ResponseEntity<String>("UnAuthorized", HttpStatus.FORBIDDEN);
                }

            }
        }

        return ResponseEntity.ok(manageStaffSQL.getSalarybyYear(maNV,from,to));
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/deleteSalary")
    public ResponseEntity deleteSalary(@RequestParam String maNV, @RequestParam String thang) {
        Salary salaryOut = salarySQL.getSalaryByMaNvMonth(maNV, thang);
        return ResponseEntity.ok(salarySQL.delete(salaryOut.getiD()));
    }

}
