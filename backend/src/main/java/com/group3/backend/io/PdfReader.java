package com.group3.backend.io;

import com.group3.backend.model.Course;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PdfReader implements AdapterFileReader {
    @Override
    public List<Course> readSchedule() {
        List<Course> entries = new ArrayList<>();
        try (PDDocument document = PDDocument.load(new File(System.getProperty("user.dir") + "/backend/src/main/resources/sources/2024-25CourseSchedule.pdf"))) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            pdfStripper.setSortByPosition(true);
            String text = pdfStripper.getText(document);

            String[] lines = text.split("\n");
            String currentTimeSlot = "Unknown";

            // Pattern to detect time slots like 08:45-9:30
            Pattern timePattern = Pattern.compile("\\d{2}:\\d{2}-\\d{1,2}:\\d{2}");
            // Pattern to detect course blocks like D1 ENG102 SA
            Pattern coursePattern = Pattern.compile("(D[0-5]|L1)\\s+([A-Z]{3,}[0-9]{3})\\s*([A-ZÇĞİÖŞÜa-zçğıöşü]*)");

            for (String line : lines) {
                line = line.trim();

                // Check if the line updates the current time slot
                Matcher timeMatcher = timePattern.matcher(line);
                if (timeMatcher.find()) {
                    currentTimeSlot = timeMatcher.group();
                }

                // Now extract all courses from the line
                Matcher courseMatcher = coursePattern.matcher(line);
                while (courseMatcher.find()) {
                    String classroom = courseMatcher.group(1);
                    String courseCode = courseMatcher.group(2);
                    String instructor = courseMatcher.group(3).isEmpty() ? "-" : courseMatcher.group(3);

                    Course course = new Course();
                    course.setDay("Auto");
                    course.setTime(currentTimeSlot);
                    course.setClassroom(classroom);
                    course.setCourseName(courseCode);
                    course.setInstructor(instructor);
                    entries.add(course);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entries;
    }


}
