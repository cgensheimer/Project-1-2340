package com.example.team47_project1.utils;

import com.example.team47_project1.ui.gallery.Course;

import java.util.ArrayList;

public class CourseList {
    private ArrayList<Course> courses;
    public CourseList() {
        courses = new ArrayList<Course>();
    }
    public void addCourse(Course course) {
        courses.add(course);
    }
    public ArrayList<Course> getCourses() {
        return courses;
    }
    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }
    public Course getCourse(int index) {
        return courses.get(index);
    }
    public void removeCourse(int index) {
        courses.remove(index);
    }
    public void removeCourse(Course course) {
        courses.remove(course);
    }
    public int size() {
        return courses.size();
    }
    public void clear() {
        courses.clear();
    }
}
