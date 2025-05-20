package com.group3.backend.strategy;

import com.group3.backend.model.Course;

import java.util.List;

public class NoOverlapStrategy implements TimeSelectionStrategy {
    @Override
    public boolean isValidTimeSlot(Course course, List<Course> existingCourses) {
        for (Course c : existingCourses) {
            if (c.getDay().equals(course.getDay()) && c.getTime().equals(course.getTime())) {
                return false;
            }
        }
        return true;
    }
}
