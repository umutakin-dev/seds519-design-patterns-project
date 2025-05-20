package com.group3.backend.factory;

import com.group3.backend.decorator.Scheduler;
import com.group3.backend.model.Course;

import java.util.List;

public abstract class ScheduleFactory {
    public abstract Scheduler createScheduler(List<Course> scheduledCourses);
}
