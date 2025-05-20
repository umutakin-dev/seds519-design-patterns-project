package com.group3.backend.decorator;

import com.group3.backend.model.Course;

import java.util.List;

public abstract class SchedulerDecorator implements Scheduler {
    protected Scheduler wrapped;

    public SchedulerDecorator(Scheduler wrapped) {
        this.wrapped = wrapped;
    }

    public void schedule(Course course) {
        wrapped.schedule(course);
    }

    public List<Course> getCourses() {
        return wrapped.getCourses();
    }
}
