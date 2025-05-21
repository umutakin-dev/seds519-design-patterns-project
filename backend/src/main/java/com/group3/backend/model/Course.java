package com.group3.backend.model;

public class Course {
    private String day;
    private String courseName;
    private String time;
    private String instructor;
    private String classroom;

    public Course() {

    }

    public Course(String day, String courseName, String time, String instructor) {
        this.day = day;
        this.courseName = courseName;
        this.time = time;
        this.instructor = instructor;
    }

    public Course(String day, String courseName, String time, String instructor, String classroom) {
        this.day = day;
        this.courseName = courseName;
        this.time = time;
        this.instructor = instructor;
        this.classroom = classroom;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }
}
