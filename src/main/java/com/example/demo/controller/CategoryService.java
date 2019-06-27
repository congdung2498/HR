package com.example.demo.controller;

import com.example.demo.dao.CategorySQL;
import com.example.demo.dao.RecruitmentSQL;
import com.example.demo.model.Category;
import com.example.demo.model.Recruitment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@Secured("ROLE_ADMIN")
@RestController
@RequestMapping("/rest")
public class CategoryService {
    CategorySQL categorySQL = new CategorySQL();

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping("/getCategory")
    public ResponseEntity getCategory(@RequestParam String search,@RequestParam String order,@RequestParam String offset,@RequestParam String limit){
        return ResponseEntity.ok(categorySQL.getCateTable(search,offset,limit));
    }
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @PostMapping("/getCatebyID")
    public ResponseEntity getCatebyID(@RequestBody Category category){
        return ResponseEntity.ok(categorySQL.getCategorybyID(category.getId()));
    }

    @PostMapping("/createCategory")
    public ResponseEntity createCategory(@RequestBody  Category category){
        return ResponseEntity.ok(categorySQL.createCategory(category));
    }

    @PostMapping("/updateCategory")
    public ResponseEntity updateCategory(@RequestBody  Category category){
        return ResponseEntity.ok(categorySQL.updateCategory(category));
    }
    @PostMapping("/deleteCategory")
    public ResponseEntity deleteCategory(@RequestBody Category category) {
        return ResponseEntity.ok(categorySQL.deleteCategory(category.getId()));
    }
}
