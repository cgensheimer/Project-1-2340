package com.example.team47_project1.ui.gallery;

public class Course {

    private String courseName;
    private String time;
    private String instructor;

    // Constructor
    public Course(String courseName, String time, String instructor) {

        this.courseName = courseName;
        this.time = time;
        this.instructor = instructor;
    }

    // Getters and Setters
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

    // Optionally, override toString() for easy printing
    @Override
    public String toString() {
        return "Course{" +
                "courseName='" + courseName + '\'' +
                ", time='" + time + '\'' +
                ", instructor='" + instructor + '\'' +
                '}';
    }
}

