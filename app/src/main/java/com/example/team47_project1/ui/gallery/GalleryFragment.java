package com.example.team47_project1.ui.gallery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.team47_project1.R;
import com.example.team47_project1.databinding.FragmentGalleryBinding;
import com.example.team47_project1.ui.exam.Exam;
import com.example.team47_project1.utils.CourseList;
import com.example.team47_project1.utils.StoreData;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private StoreData storage;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TextView textView = binding.textGallery;
        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        storage = new StoreData(getContext());
        return root;
    }

    // button to add

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayout linearLayout = view.findViewById(R.id.course_list);

        /*
        Course testCourse = new Course(0, "testCourse", "1:00pm", "Joe Mama");
        Course testCourse2 = new Course(1, "testCourse2", "2:00pm", "Tim Burr");

        addCourse(testCourse, linearLayout);
        addCourse(testCourse2, linearLayout);
         */

        /*
        StoreData storage = new StoreData(getContext());
        boolean hasMoreCourses = true;
        int counter = 0;
        do {
            Course course = storage.getData("course" + counter, Course.class);
            if (course == null) {
                Log.d("course info", "did not find course");
                hasMoreCourses = false;
            } else {
                Log.d("course info", "found course");
                addCourse(course, linearLayout, counter);
                counter++;
            }
        } while (hasMoreCourses);
        */
        StoreData storage = new StoreData(getContext());
        CourseList courseList = storage.getData("courses", CourseList.class);
        if (courseList != null) {
            if (courseList.size() == 0) {
                binding.textGallery.setVisibility(View.VISIBLE);
            } else {
                binding.textGallery.setVisibility(View.GONE);
            }
            for (int i = 0; i < courseList.size(); i++) {
                addCourse(courseList.getCourse(i), linearLayout);
            }
        }
    }

    public void addCourse(Course course, LinearLayout linearLayout) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View courseView = inflater.inflate(R.layout.course, null);

        TextView courseName = courseView.findViewById(R.id.course_name);
        EditText courseNameEdit = courseView.findViewById(R.id.edit_course_name);
        TextView courseTime = courseView.findViewById(R.id.course_time);
        EditText courseTimeEdit = courseView.findViewById(R.id.edit_course_time);
        TextView courseInstruct = courseView.findViewById(R.id.course_instructor);
        EditText courseInstructEdit = courseView.findViewById(R.id.edit_course_instructor);

        courseName.setText(course.getCourseName());
        courseNameEdit.setText(course.getCourseName());
        courseTime.setText(course.getTime());
        courseTimeEdit.setText(course.getTime());
        courseInstruct.setText(course.getInstructor());
        courseInstructEdit.setText(course.getInstructor());

        /*
        ImageButton editButton = courseView.findViewById(R.id.course_edit);
        ImageButton confirmButton = courseView.findViewById(R.id.course_confirm);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editCourse(courseView);
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmCourse(courseView, courseList, idNum);
            }
        });
         */

        linearLayout.addView(courseView);
    }
    /*
    public void editCourse(View courseView) {
        TextView courseName = courseView.findViewById(R.id.course_name);
        EditText courseNameEdit = courseView.findViewById(R.id.edit_course_name);
        TextView courseTime = courseView.findViewById(R.id.course_time);
        EditText courseTimeEdit = courseView.findViewById(R.id.edit_course_time);
        TextView courseInstruct = courseView.findViewById(R.id.course_instructor);
        EditText courseInstructEdit = courseView.findViewById(R.id.edit_course_instructor);
        ImageButton editButton = courseView.findViewById(R.id.course_edit);
        ImageButton confirmButton = courseView.findViewById(R.id.course_confirm);

        courseName.setVisibility(View.GONE);
        courseTime.setVisibility(View.GONE);
        courseInstruct.setVisibility(View.GONE);
        editButton.setVisibility(View.GONE);

        courseNameEdit.setVisibility(View.VISIBLE);
        courseTimeEdit.setVisibility(View.VISIBLE);
        courseInstructEdit.setVisibility(View.VISIBLE);
        confirmButton.setVisibility(View.VISIBLE);
    }

    public void confirmCourse(View courseView, CourseList courseList, int idNum) {
        TextView courseName = courseView.findViewById(R.id.course_name);
        EditText courseNameEdit = courseView.findViewById(R.id.edit_course_name);
        TextView courseTime = courseView.findViewById(R.id.course_time);
        EditText courseTimeEdit = courseView.findViewById(R.id.edit_course_time);
        TextView courseInstruct = courseView.findViewById(R.id.course_instructor);
        EditText courseInstructEdit = courseView.findViewById(R.id.edit_course_instructor);
        ImageButton editButton = courseView.findViewById(R.id.course_edit);
        ImageButton confirmButton = courseView.findViewById(R.id.course_confirm);

        courseName.setVisibility(View.VISIBLE);
        courseTime.setVisibility(View.VISIBLE);
        courseInstruct.setVisibility(View.VISIBLE);
        editButton.setVisibility(View.VISIBLE);

        courseNameEdit.setVisibility(View.GONE);
        courseTimeEdit.setVisibility(View.GONE);
        courseInstructEdit.setVisibility(View.GONE);
        confirmButton.setVisibility(View.GONE);

        courseName.setText(courseNameEdit.getText().toString());
        courseTime.setText(courseTimeEdit.getText().toString());
        courseInstruct.setText(courseInstructEdit.getText().toString());

        Course editedCourse = new Course(courseName.getText().toString(),
                courseTime.getText().toString(), courseInstruct.getText().toString());

        //storage.saveData("course" + idNum, editedCourse);
    }
    */



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}