package com.example.demo.controller.container;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public class UploadImgCTN {
    private String img;
    private String maNV;

    public UploadImgCTN() {
    }

    public UploadImgCTN(String img, String maNV) {
        this.img = img;
        this.maNV = maNV;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }
}
