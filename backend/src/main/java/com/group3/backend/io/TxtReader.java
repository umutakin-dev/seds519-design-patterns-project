package com.group3.backend.io;

import com.group3.backend.model.Course;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TxtReader implements AdapterFileReader {
    @Override
    public List<Course> readSchedule() throws IOException {
        List<Course> courses = new ArrayList<>();

        try {
            // 1. Locate the folder on the classpath:
            File dir = new File(
                    getClass()
                            .getClassLoader()
                            .getResource("sources")
                            .toURI());

            // 2. Filter only .txt files
            File[] files = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".txt"));

            if (files != null) {
                for (File file : files) {
                    // derive instructor name from filename, e.g. "AYigit.txt" → "AYigit"
                    String instructor = file.getName().replaceFirst("\\.txt$", "");

                    // 3. Read each file line by line
                    try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(file)))) {
                        while (scanner.hasNextLine()) {
                            String line = scanner.nextLine().trim();
                            if (line.isEmpty())
                                continue;

                            String[] data = line.split(";");
                            String courseName = data[0];
                            String day = data[1];
                            String time = data[2];

                            courses.add(new Course(day, courseName, time, instructor));
                        }
                    }
                }
            }
        } catch (URISyntaxException e) {
            throw new IOException("Cannot load schedule files from resources/sources", e);
        }

        return courses;
    }
}
