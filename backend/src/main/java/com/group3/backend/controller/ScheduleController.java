package com.group3.backend.controller;

import com.group3.backend.io.AdapterFileReader;
import com.group3.backend.io.ExcelReader;
import com.group3.backend.io.PdfReader;
import com.group3.backend.model.Course;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

@RestController
public class ScheduleController {
    private AdapterFileReader reader;

    @GetMapping(value = "/html", produces = "text/html; charset=UTF-8")
    public String getHtml() {
        System.out.println("HTML Response");
        String html = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <title>Spring Boot HTML Response</title>
                </head>
                <body>
                    <h1>Merhaba, Spring Boot'tan HTML döndüm!</h1>
                </body>
                </html>
                """;
        return html;
    }

    @GetMapping(value = "/file")
    public List<Course> getScheduleAsHtml(@RequestParam("type") String fileType) throws IOException {
        if (fileType.equals("pdf")) {
            reader = new PdfReader();
            return reader.readSchedule();
        }

        else if (fileType.equals("excel")) {
            reader = new ExcelReader();
            return reader.readSchedule();
        }

        else {
            return null;
        }

    }
}
