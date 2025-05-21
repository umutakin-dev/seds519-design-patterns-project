package com.group3.backend.factory;

import com.group3.backend.decorator.Scheduler;
import com.group3.backend.model.Course;

import java.util.ArrayList;
import java.util.List;

public abstract class ScheduleFactory {
    // public abstract Scheduler createScheduler(List<Course> scheduledCourses);
    protected abstract Scheduler createScheduler(List<Course> scheduledCourses);

    protected abstract boolean accept(Course course);

    public List<Course> build(List<Course> allCourses) {
        List<Course> scheduled = new ArrayList<>();
        Scheduler sched = createScheduler(scheduled);

        for (Course c : allCourses) {
            if (accept(c)) {
                sched.schedule(c);
            }
        }
        return sched.getCourses();
    }
}
