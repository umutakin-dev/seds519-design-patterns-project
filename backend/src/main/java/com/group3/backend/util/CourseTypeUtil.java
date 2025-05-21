package com.group3.backend.util;

import com.group3.backend.model.Course;

public class CourseTypeUtil {
    public static boolean isGraduateCourse(Course course) {
        String code = course.getCourseName(); // e.g., "CENG632"
        String numberPart = code.replaceAll("\\D+", ""); // "632"
        if (!numberPart.isEmpty()) {
            int level = Character.getNumericValue(numberPart.charAt(0));
            return level >= 5;
        }
        return false;
    }
}
