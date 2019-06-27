package com.example.demo.controller;

import com.example.demo.dao.ManageStaffSQL;
import com.example.demo.model.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

@RestController
@RequestMapping("/rest")
public class ManageStaffService {
    ManageStaffSQL manageStaffSQL = new ManageStaffSQL();

    @Secured("ROLE_ADMIN")
    @GetMapping("/getStaffInfoTable")
    @ResponseBody
    public ResponseEntity getSataffInfoTable(@RequestParam String search,@RequestParam String order,@RequestParam String offset,@RequestParam String limit){
        return ResponseEntity.ok(manageStaffSQL.getProfileTable(search,offset,limit));
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/getStaffInfo")
    @ResponseBody
    public ResponseEntity getSataffInfo(){
        return ResponseEntity.ok(manageStaffSQL.getProfile1());
    }
//    @GetMapping("/getStaffAllInfo")
//    public ResponseEntity getSataffAllInfo(){
//
//        return ResponseEntity.ok(manageStaffSQL.getProfileAllField());
//    }
    @Secured("ROLE_ADMIN")
    @GetMapping("/checkMaNV")
    @ResponseBody
    public Boolean checkMaNV(@RequestParam String code){
        return manageStaffSQL.checkMaNV(code);
    }
    @Secured({"ROLE_USER","ROLE_ADMIN"})//->for both security roles
    @PostMapping("/getStaffInfoByMaNV")
    public ResponseEntity getSataffInfoByMaNV(@RequestBody Profile profile, Authentication authentication){
        Collection<? extends GrantedAuthority> authorities
                = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities){
            if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
                if(!profile.getMaNV().equals(authentication.getName())){
                    return new ResponseEntity<String>("UnAuthorized", HttpStatus.FORBIDDEN);
                }

            }
        }
        return ResponseEntity.ok(manageStaffSQL.getProfileByMaNV(profile.getMaNV()));
    }
    @Secured("ROLE_ADMIN")
    @PostMapping("/createProfile")
    public ResponseEntity createProfile(@RequestBody Profile profile){
        return ResponseEntity.ok(manageStaffSQL.createProfile(profile));
    }
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @PostMapping("/updateProfile")
    public ResponseEntity updateProfile(@RequestBody Profile profile, Authentication authentication){
        Collection<? extends GrantedAuthority> authorities
                = authentication.getAuthorities();
        for (GrantedAuthority grantedAuthority : authorities){
            if (grantedAuthority.getAuthority().equals("ROLE_USER")) {
                if(!profile.getMaNV().equals(authentication.getName())){
                    return new ResponseEntity<String>("UnAuthorized", HttpStatus.FORBIDDEN);
                }

            }
        }
        return ResponseEntity.ok(manageStaffSQL.updateProfile(profile));
    }
    @Secured("ROLE_ADMIN")
    @PostMapping("/deleteStaff")
    public ResponseEntity deleteStaff(@RequestBody Profile profile) {
        return ResponseEntity.ok(manageStaffSQL.deleteStaff(profile.getiD()));
    }
}
