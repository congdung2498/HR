package com.example.demo.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Controller
public class DownloadFile {

    @RequestMapping(value = "/download1", method = RequestMethod.GET)
    public void download1(HttpServletResponse response) throws IOException {

        try {
            File file = ResourceUtils.getFile("GenNewAccount.xlsx");

            byte[] data = FileUtils.readFileToByteArray(file);
            // Thiết lập thông tin trả về
            response.setContentType("application/octet-stream");
            response.setHeader("Content-disposition", "attachment; filename=" + file.getName());
            response.setContentLength(data.length);
            InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(data));
            FileCopyUtils.copy(inputStream, response.getOutputStream());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}