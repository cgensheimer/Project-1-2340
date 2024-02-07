package com.example.team47_project1.ui.exam;

import com.example.team47_project1.ui.gallery.Course;

public class Exam {
    private String examLocation;
    private String examTime;
    private int daydue;
    private int monthdue;
    private String name; // Assuming this is the name of the exam

    // Constructor
    public Exam(String examLocation, String examTime, String name, int daydue, int monthdue) {
        this.daydue = daydue;
        this.monthdue = monthdue;
        this.examLocation = examLocation;
        this.examTime = examTime;
        this.name = name;
    }

    // Getters and Setters
    public String getExamLocation() {
        return examLocation;
    }

    public int getDaydue() {
        return daydue;
    }

    public int getMonthdue() {
        return monthdue;
    }

    public void setExamLocation(String examLocation) {
        this.examLocation = examLocation;
    }

    public void setDaydue(int daydue) {
        this.daydue = daydue;
    }

    public void setMonthdue(int monthdue) {
        this.monthdue = monthdue;
    }

    public String getExamTime() {
        return examTime;
    }

    public void setExamTime(String examTime) {
        this.examTime = examTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Optionally, override toString() for easy printing

}
