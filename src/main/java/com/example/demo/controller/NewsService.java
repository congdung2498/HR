package com.example.demo.controller;

import com.example.demo.dao.ManageStaffSQL;
import com.example.demo.dao.NewsSQL;
import com.example.demo.model.Comment;
import com.example.demo.model.News;
import com.example.demo.model.Profile;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.util.Collection;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/rest")
public class NewsService {
    NewsSQL newsSQL = new NewsSQL();
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping("/getNews")
    public ResponseEntity getNews(){
        return ResponseEntity.ok(newsSQL.getNews());

    }
    @Secured("ROLE_ADMIN")
    @GetMapping("/getNewsTable")
    public ResponseEntity getNewsTable(@RequestParam String search,@RequestParam String order,@RequestParam String offset,@RequestParam String limit){
        return ResponseEntity.ok(newsSQL.getNewsTable(search,offset,limit));

    }
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping("/getTrend")
    public ResponseEntity getTrend(){
        return ResponseEntity.ok(newsSQL.getTrend());
    }

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping("/getNotice")
    public ResponseEntity getNotie(){
        return ResponseEntity.ok(newsSQL.getNotice());
    }

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping("/getNewsbyID")
    public ResponseEntity getNewsbyID(@RequestParam int id) {
        return ResponseEntity.ok(newsSQL.getNewsbyID(id));
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/addNews")
    public ResponseEntity addNews(@RequestBody News news) {
        return ResponseEntity.ok(newsSQL.addNews(news));
    }

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @PostMapping("/comment")
    public ResponseEntity addComment(@RequestBody Comment comment,Authentication authentication) {
        return newsSQL.addComment(comment,authentication);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/editNews")
    public ResponseEntity editNews(@RequestBody News news) {
        return ResponseEntity.ok(newsSQL.editNews(news));
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/deleteNews")
    public ResponseEntity deleteNews(@RequestParam int id) {
        return ResponseEntity.ok(newsSQL.deleteNews(id));
    }

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @PostMapping("/deleteComment")
    public ResponseEntity deleteComment(@RequestParam int id, Authentication authentication) {
        return newsSQL.deleteComment(id, authentication);
    }

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping("/getNewbyCate")
    public ResponseEntity getNewsbyCateID(@RequestParam int cateID) {
        return ResponseEntity.ok(newsSQL.getNewsbyCateID(cateID));
    }

    @RequestMapping(value = "/uploadImg/{newsID}", method = RequestMethod.POST)
    public ResponseEntity upImg(@RequestParam("file") MultipartFile file,@PathVariable int newsID) throws Exception {
        StringBuffer sb=new StringBuffer(file.getOriginalFilename());
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        // this will convert any number sequence into 6 character.
        sb.insert(file.getOriginalFilename().indexOf("."),String.format("%06d", number));
        String fileName = sb.toString();
        File convFile = new File("News/Image/"+fileName);
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        System.out.println(convFile.getName());
        return ResponseEntity.ok(newsSQL.setImg(convFile.getName(),newsID));
    }
    @RequestMapping(value = "/getImg/{name:.+}", method = RequestMethod.GET,
            produces = MediaType.ALL_VALUE)
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String name) throws IOException {
        System.out.println(name);

        Resource resource = new FileSystemResource("News/Image/"+name);
        return ResponseEntity
                .ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(new InputStreamResource(resource.getInputStream()));
    }

}
