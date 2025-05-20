package com.group3.backend.factory;

import com.group3.backend.decorator.Scheduler;
import com.group3.backend.model.Course;

import java.util.List;

public class UndergraduateScheduleFactory extends ScheduleFactory{
    @Override
    public Scheduler createScheduler(List<Course> scheduledCourses) {
        return null;
    }
}
