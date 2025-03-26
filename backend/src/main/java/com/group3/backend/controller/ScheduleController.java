package com.group3.backend.controller;

import com.group3.backend.io.AdapterFileReader;
import com.group3.backend.io.ExcelReader;
import com.group3.backend.io.FileReaderConverter;
import com.group3.backend.io.PdfReader;
import com.group3.backend.model.Course;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.*;

@RestController
@CrossOrigin("*")
public class ScheduleController {
    private AdapterFileReader reader;

    @GetMapping(value = "/file", produces = "text/html; charset=UTF-8")
    public String getScheduleAsHtml(@RequestParam String fileType) throws IOException {
        reader = FileReaderConverter.getFileReader(fileType);

        if (reader != null) {
            List<Course> courses = reader.readSchedule();

            List<String> days = List.of("Pazartesi", "Salı", "Çarşamba", "Perşembe", "Cuma");
            List<String> hours = List.of("08:45-09:30", "09:45-10:30", "10:45-11:30", "11:45-12:30");

            StringBuilder html = new StringBuilder("""
            <!DOCTYPE html>
            <html lang="en">
            <head>
              <meta charset="UTF-8">
              <title>Weekly Course Schedule - 2024-2025 Spring</title>
              <style>
                body { font-family: Arial, sans-serif; background-color: #f9f9f9; padding: 20px; }
                h1 { text-align: center; color: #333; }
                table { width: 100%; border-collapse: collapse; background-color: #fff; }
                th, td { border: 1px solid #ddd; padding: 8px; text-align: left; vertical-align: top; }
                th { background-color: #0c2340; color: white; }
                .time-slot { font-weight: bold; background-color: #f1f1f1; }
                .course-name { font-weight: bold; }
                .instructor, .classroom { font-size: 0.85em; color: #555; }
              </style>
            </head>
            <body>
              <h1>Weekly Course Schedule - 2024-2025 Spring</h1>
              <table>
                <tr>
                  <th>Hours</th>
                  <th>Monday</th>
                  <th>Tuesday</th>
                  <th>Wednesday</th>
                  <th>Thursday</th>
                  <th>Friday</th>
                </tr>
            """);

            for (String hour : hours) {
                html.append("<tr>\n");
                html.append("<td class=\"time-slot\">").append(hour).append("</td>\n");

                for (String day : days) {
                    html.append("<td>");
                    courses.stream()
                            .filter(c -> c.getDay().equalsIgnoreCase(day) && c.getTime().equalsIgnoreCase(hour))
                            .forEach(c -> html.append("<span class=\"course-name\">")
                                    .append(c.getCourseName()).append("</span><br>")
                                    .append("<span class=\"instructor\">").append(c.getInstructor()).append("</span><br>")
                                    .append("<span class=\"classroom\">").append(c.getClassroom()).append("</span><br><br>"));
                    html.append("</td>\n");
                }
                html.append("</tr>\n");
            }

            html.append("""
              </table>
            </body>
            </html>
            """);

            return html.toString();
        }
        else {
            return "";
        }
    }
}
