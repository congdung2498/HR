package com.example.demo.utility;


import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
public class UpLoadFiles {
    private String uploadDir;

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }
}
