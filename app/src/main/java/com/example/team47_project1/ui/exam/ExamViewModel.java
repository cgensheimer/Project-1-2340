package com.example.team47_project1.ui.exam;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class ExamViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public ExamViewModel() {

        mText = new MutableLiveData<>();
        mText.setValue("Add exams from the edit menu!");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
