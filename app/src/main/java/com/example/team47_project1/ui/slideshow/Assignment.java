package com.example.team47_project1.ui.slideshow;
import com.example.team47_project1.ui.gallery.Course;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
public class Assignment {
    private String assignmentname;
    private int daydue;
    private int monthdue;
    private Course course;
    public Assignment(String assignmentname, int day, int month, Course course) {
        this.assignmentname = assignmentname;
        this.daydue = day;
        this.monthdue = month;
        this.course = course;
    }
    public String getName() {
        return this.assignmentname;
    }
    public int getDayDate() {
        return this.daydue;
    }
    public int getMonthdue() {
        return this.monthdue;
    }
    public Course getCourse(){
        return this.course;
    }

    public void setName(String name) {
        this.assignmentname = name;
    }
    public void setDayDate(int day) {
        this.daydue = day;
    }
    public void setMonthdue(int month) {
        this.monthdue = month;
    }
    public void setCourse(Course course) {
        this.course = course;
    }
    @Override
    public String toString() {
        return "Assignment:" +
                "Title:" + this.assignmentname + "\'"+
                "Due:" + this.monthdue +"/"+this.daydue+"\'"+
                "Course:"+this.course.getCourseName()+"\'";
    }
    public ArrayList<Assignment> sortbyduedate(ArrayList<Assignment> array) {
        Assignment item;
        for(int i =0; i < (array.size() -1); i++) {
            for(int j =1; j< array.size(); j++) {
                if(array.get(i).getMonthdue() > array.get(j).getMonthdue()) {
                    item = array.get(i);
                    array.set(i,array.get(j));
                    array.set(j,item);
                } else if (array.get(i).getMonthdue() == array.get(j).getMonthdue()) {
                    if(array.get(i).getDayDate()>array.get(j).getDayDate()) {
                        item = array.get(i);
                        array.set(i ,array.get(j));
                        array.set(j,item);
                    }
                }
            }
        }
        return array;
    }
    public ArrayList<Assignment> sortbycourse(ArrayList<Assignment> array) {
        Assignment item;
        for(int i =0; i < (array.size() -1); i++) {
            for(int j =1; j< array.size(); j++) {
                if(array.get(i).getCourse().getCourseName().compareTo(array.get(j).getCourse().getCourseName())>0) {
                    item = array.get(i);
                    array.set(i,array.get(j));
                    array.set(j,item);
                }
            }
        }
        return array;
    }



}
