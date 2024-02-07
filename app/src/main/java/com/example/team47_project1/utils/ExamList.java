package com.example.team47_project1.utils;

import com.example.team47_project1.ui.exam.Exam;

import java.util.ArrayList;

public class ExamList {
    private ArrayList<Exam> exams;
    public ExamList() {
        exams = new ArrayList<Exam>();
    }
    public void addExam(Exam exam) {
        exams.add(exam);
    }
    public ArrayList<Exam> getExams() {
        return exams;
    }
    public void setExams(ArrayList<Exam> exams) {
        this.exams = exams;
    }
    public Exam getExam(int index) {
        return exams.get(index);
    }
    public void removeExam(int index) {
        exams.remove(index);
    }
    public void removeExam(Exam exam) {
        exams.remove(exam);
    }
    public int size() {
        return exams.size();
    }
    public void clear() {
        exams.clear();
    }
}
