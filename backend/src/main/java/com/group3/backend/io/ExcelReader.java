package com.group3.backend.io;

import com.group3.backend.model.Course;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExcelReader implements AdapterFileReader {
    @Override
    public List<Course> readSchedule() {
        List<Course> courses = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/backend/src/main/resources/sources/2024-25CourseSchedule.xlsx");
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0);

            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row == null) continue;

                String timeSlot = rowIndex >= 1 && rowIndex <= 4 ? "08:45-09.30"  : rowIndex >= 5 && rowIndex <= 11 ? "09:45-10:30" : rowIndex >= 12 && rowIndex <= 20 ? "10:45-11:30" : "11:45-12:30";

                for (int col = 1; col < row.getLastCellNum(); col += 3) {
                    String classroom = getCellValue(row.getCell(col));
                    String courseName = getCellValue(row.getCell(col + 1));
                    String instructor = getCellValue(row.getCell(col + 2));

                    if (!courseName.isEmpty()) {
                        String day = getCellValue(headerRow.getCell(col)); // Day from the header
                        courses.add(new Course(day, courseName, timeSlot, instructor, classroom));
                    }
                }
            }


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return courses;
    }

    private String getCellValue(Cell cell) {
        if (cell == null) return "";
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> String.valueOf(cell.getNumericCellValue());
            default -> "";
        };
    }

}
