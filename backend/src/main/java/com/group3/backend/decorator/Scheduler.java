package com.group3.backend.decorator;

import com.group3.backend.model.Course;

import java.util.List;

public interface Scheduler {
    void schedule(Course course);

    List<Course> getCourses();

}
