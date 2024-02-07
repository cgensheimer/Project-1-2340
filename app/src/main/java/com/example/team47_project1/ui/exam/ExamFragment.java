package com.example.team47_project1.ui.exam;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.team47_project1.R;
import com.example.team47_project1.databinding.FragmentExamBinding;
import com.example.team47_project1.ui.gallery.Course;
import com.example.team47_project1.ui.input.InputFragment;
import com.example.team47_project1.utils.ExamList;
import com.example.team47_project1.utils.StoreData;

import java.util.ArrayList;

public class ExamFragment extends Fragment implements InputFragment.OnExamChangedListener {
    private FragmentExamBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ExamViewModel examViewModel =
                new ViewModelProvider(this).get(ExamViewModel.class);

        binding = FragmentExamBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TextView textView = binding.textExam;
        examViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayout linearLayout = view.findViewById(R.id.exams);
        StoreData storage = new StoreData(getContext());
        ExamList examList = storage.getData("exams", ExamList.class);
        if (examList != null) {
            if (examList.size() == 0) {
                binding.textExam.setVisibility(View.VISIBLE);
            } else {
                binding.textExam.setVisibility(View.GONE);
            }
            Log.d("exam status", "exam list not null");
            Log.d("exam list size: ", "" + examList.size());
            for (int i = 0; i < examList.size(); i++) {
                Log.d("exam status", "trying to add exam");
                addExam(examList.getExam(i), linearLayout, i != 0);
            }
        }
    }
    @Override
    public void onExamChanged() {
        //Log.d("exam status", "exam changed!");
        addExam(new Exam("Location", "Time", "Name", 1, 1), binding.exams, true);
    }

    public void addExam(Exam exam, LinearLayout linearLayout, boolean visibleLine) {
        //Log.d("exam status", "adding exam!");
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View examView = inflater.inflate(R.layout.exam, null);

        TextView examName = examView.findViewById(R.id.exam_name);
        TextView examTime = examView.findViewById(R.id.exam_time);
        TextView examLoc = examView.findViewById(R.id.exam_location);
        TextView examDate = examView.findViewById(R.id.exam_date);

        // note: for some reason the examName doesn't like spaces
        examName.setText(exam.getName());
        examTime.setText(exam.getExamTime());
        examLoc.setText(exam.getExamLocation());
        // parse int day and month and return as string day / month
        examDate.setText(String.format("%d/%d", exam.getDaydue(), exam.getMonthdue()));
        examView.findViewById(R.id.separator_line).setVisibility(visibleLine ? View.VISIBLE : View.GONE);

        linearLayout.addView(examView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
