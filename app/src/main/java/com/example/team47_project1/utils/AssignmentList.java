package com.example.team47_project1.utils;

import com.example.team47_project1.ui.slideshow.Assignment;

import java.util.ArrayList;

public class AssignmentList {
    private ArrayList<Assignment> assignments;
    public AssignmentList() {
        assignments = new ArrayList<Assignment>();
    }
    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
    }
    public ArrayList<Assignment> getAssignments() {
        return assignments;
    }
    public void setAssignments(ArrayList<Assignment> assignments) {
        this.assignments = assignments;
    }
    public Assignment getAssignment(int index) {
        return assignments.get(index);
    }
    public void removeAssignment(int index) {
        assignments.remove(index);
    }
    public void removeAssignment(Assignment assignment) {
        assignments.remove(assignment);
    }
    public int size() {
        return assignments.size();
    }
    public void clear() {
        assignments.clear();
    }


    public void sortbyduedate(ArrayList<Assignment> array) {
        Assignment item;
        for (int i = 0; i < array.size() - 1; i++) {
            for (int j = i + 1; j < array.size(); j++) {
                if (array.get(i).getMonthdue() > array.get(j).getMonthdue() ||
                        (array.get(i).getMonthdue() == array.get(j).getMonthdue() &&
                                array.get(i).getDayDate() > array.get(j).getDayDate())) {
                    item = array.get(i);
                    array.set(i, array.get(j));
                    array.set(j, item);
                }
            }
        }
    }

    public void sortbyduedate1(ArrayList<Assignment> array) {
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

    }
    public void sortbycourse(ArrayList<Assignment> array) {
        Assignment item;
        for (int i = 0; i < array.size() - 1; i++) {
            for (int j = i + 1; j < array.size(); j++) {
                if (array.get(i).getCourse().getCourseName().compareTo(array.get(j).getCourse().getCourseName()) > 0) {
                    item = array.get(i);
                    array.set(i, array.get(j));
                    array.set(j, item);
                }
            }
        }
    }

    public void sortbycourse1(ArrayList<Assignment> array) {
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

    }
}
