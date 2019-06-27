package com.example.demo.controller;

import com.example.demo.dao.RecruitmentSQL;
import com.example.demo.model.Profile;
import com.example.demo.model.Recruitment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
@Secured("ROLE_ADMIN")
@RestController
@RequestMapping("/rest")
public class RecruitmentService {
    RecruitmentSQL recruitmentSQL = new RecruitmentSQL();

    @GetMapping("/getCVInfo")
    public ResponseEntity getCVInfo(@RequestParam String search,@RequestParam String order,@RequestParam String offset,@RequestParam String limit){
        return ResponseEntity.ok(recruitmentSQL.getRecruitment1(search,offset,limit));
    }

    @PostMapping("/getCVInfoByMaCV")
    public ResponseEntity getCVInfoByMaCV(@RequestBody Recruitment recruitment){
        return ResponseEntity.ok(recruitmentSQL.getRecruitmentByMaCV(recruitment.getMaCV()));
    }

    @PostMapping("/createRecuitment")
    public ResponseEntity createProfile(@RequestBody Recruitment recruitment){
        return ResponseEntity.ok(recruitmentSQL.createRecruitment(recruitment));
    }

    @PostMapping("/updateRecruitment")
    public ResponseEntity updateRecruitment(@RequestBody Recruitment recruitment){
        return ResponseEntity.ok(recruitmentSQL.updateRecruitment(recruitment));
    }
    @PostMapping("/deleteRecruitment")
    public ResponseEntity deleteRecruitment(@RequestBody Recruitment recruitment) {
        return ResponseEntity.ok(recruitmentSQL.deleteRecruitment(recruitment.getMaCV()));
    }
    @GetMapping("/checkMaCV")
    @ResponseBody
    public Boolean checkMaCV(@RequestParam String code){
        return recruitmentSQL.checkMaCV(code);
    }
}
