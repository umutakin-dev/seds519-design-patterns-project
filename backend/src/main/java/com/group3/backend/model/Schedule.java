package com.group3.backend.model;

import java.util.List;

public class Schedule {
    private List<Course> courses;

    public Schedule() {
    }

    public Schedule(List<Course> courses) {
        this.courses = courses;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
