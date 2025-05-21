package com.group3.backend.strategy;

import java.util.List;

import com.group3.backend.model.Course;

public class MaxCoursesPerDayStrategy implements TimeSelectionStrategy {
    private final int maxPerDay;
    
    public MaxCoursesPerDayStrategy(int maxPerDay) {
        this.maxPerDay = maxPerDay;
    }
 
    @Override
    public boolean isValidTimeSlot(Course course, List<Course> existingCourses) {
        long count = existingCourses.stream()
            .filter(c -> c.getDay().equals(course.getDay()))
            .count();
        return count < maxPerDay;
    }
}