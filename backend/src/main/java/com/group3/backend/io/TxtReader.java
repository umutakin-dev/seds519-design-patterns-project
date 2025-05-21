package com.group3.backend.io;

import com.group3.backend.model.Course;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TxtReader implements AdapterFileReader {
    @Override
    public List<Course> readSchedule() throws IOException {
        List<Course> courses = new ArrayList<>();
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader("")))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] data = line.split(";");
                String courseName = data[0];
                String time = data[1];
                String day = data[2];
                Course course = new Course(day, courseName, time);
                courses.add(course);
            }
        }
        return courses;
    }
}
