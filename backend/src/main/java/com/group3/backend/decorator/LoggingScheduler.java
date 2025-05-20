package com.group3.backend.decorator;

import com.group3.backend.model.Course;

public class LoggingScheduler extends SchedulerDecorator {
    public LoggingScheduler(Scheduler wrapped) {
        super(wrapped);
    }

    @Override
    public void schedule(Course course) {
        System.out.println("Scheduling: " + course);
        super.schedule(course);
    }
}
