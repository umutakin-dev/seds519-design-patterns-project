package com.group3.backend.strategy;

import com.group3.backend.model.Course;

import java.util.List;

public interface TimeSelectionStrategy {
    boolean isValidTimeSlot(Course course, List<Course> existingCourses);
}
