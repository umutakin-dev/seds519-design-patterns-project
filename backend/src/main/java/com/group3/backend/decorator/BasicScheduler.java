package com.group3.backend.decorator;

import com.group3.backend.model.Course;
import com.group3.backend.strategy.TimeSelectionStrategy;
import java.util.Comparator;
import java.time.DayOfWeek;
import java.time.LocalTime;

import java.util.List;

public class BasicScheduler implements Scheduler {
    private final List<Course> scheduledCourses;
    private final TimeSelectionStrategy strategy;

    public BasicScheduler(List<Course> scheduledCourses, TimeSelectionStrategy strategy) {
        this.scheduledCourses = scheduledCourses;
        this.strategy = strategy;
    }

    @Override
    public void schedule(Course course) {
        if (strategy.isValidTimeSlot(course, scheduledCourses)) {
            scheduledCourses.add(course);
        }
    }

    @Override
    public List<Course> getCourses() {
        scheduledCourses.sort(Comparator
                .comparing((Course c) -> DayOfWeek.valueOf(c.getDay().toUpperCase()))
                .thenComparing(c -> LocalTime.parse(c.getTime())));
        return scheduledCourses;
    }
}
