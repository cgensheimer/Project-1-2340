package com.example.team47_project1.ui.input;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.team47_project1.MainActivity;
import com.example.team47_project1.R;
import com.example.team47_project1.databinding.FragmentInputBinding;
import com.example.team47_project1.ui.exam.Exam;
import com.example.team47_project1.ui.gallery.Course;
import com.example.team47_project1.ui.input.InputViewModel;
import com.example.team47_project1.ui.slideshow.Assignment;
import com.example.team47_project1.utils.AssignmentList;
import com.example.team47_project1.utils.CourseList;
import com.example.team47_project1.utils.ExamList;
import com.example.team47_project1.utils.StoreData;
import com.example.team47_project1.utils.User;
import com.example.team47_project1.MainActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class InputFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private OnUsernameChangedListener usernameChangedListener;
    private OnExamChangedListener examChangedListener;

    private FragmentInputBinding binding;
    private Button submitButton;
    private Button deleteButton;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        InputViewModel inputViewModel =
                new ViewModelProvider(this).get(InputViewModel.class);

        binding = FragmentInputBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textInput;
//        inputViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        Spinner spinner = (Spinner) root.findViewById(R.id.type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.type_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        submitButton = view.findViewById(R.id.submit_button);
        deleteButton = view.findViewById(R.id.delete_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDataBasedOnType();
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteDataBasedOnType();
            }
        });
        populateCoursesSpinner();
    }

    private int isExisting(String name, Object object1) {
        if (object1 instanceof CourseList) {
            ArrayList<Course> courses = ((CourseList) object1).getCourses();
            for (int i = 0; i < courses.size(); i++) {
                if (courses.get(i).getCourseName().equals(name)) {
                    return i; // Return the correct index
                }
            }
        } else if (object1 instanceof ExamList) {
            ArrayList<Exam> exams = ((ExamList) object1).getExams();
            for (int i = 0; i < exams.size(); i++) {
                if (exams.get(i).getName().equals(name)) {
                    return i; // Return the correct index
                }
            }
        } else if (object1 instanceof AssignmentList) {
            ArrayList<Assignment> assignments = ((AssignmentList) object1).getAssignments();
            for (int i = 0; i < assignments.size(); i++) {
                if (assignments.get(i).getName().equals(name)) {
                    return i; // Return the correct index
                }
            }
        }
        return -1; // Indicates not found
    }


    private void populateCoursesSpinner() {
        StoreData storage = new StoreData(getContext());
        CourseList courseList;
        ArrayList<String> courseNames = new ArrayList<>();

        try {
            // Try to read the saved courses
            courseList = storage.getData("courses", CourseList.class);
            if (courseList != null) {
                for (Course course : courseList.getCourses()) {
                    courseNames.add(course.getCourseName());
                }
            }
        } catch (Exception e) {
            Log.e("InputFragment", "Failed to load courses", e);
            // Handle exception or show error message
        }

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, courseNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        binding.assCourse.setAdapter(dataAdapter);
    }

    public void createCourse(View view) {
        StoreData storage = new StoreData(getContext());
        Integer number = 0;
        try {
            storage.saveData("numCourses", number);
        } catch (Exception e) {
        }
        storage.getData("numCourses", Integer.class);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // An item is selected. You can retrieve the selected item using
        // parent.getItemAtPosition(position).
        binding.classesContainer.setVisibility(View.GONE);
        binding.examContainer.setVisibility(View.GONE);
        binding.assignmentContainer.setVisibility(View.GONE);
        binding.nameContainer.setVisibility(View.GONE);
        String selectedItem = parent.getItemAtPosition(position).toString();
        Toast.makeText(getContext(), "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
        submitButton.setVisibility(View.VISIBLE);
        deleteButton.setVisibility(View.VISIBLE);
        populateCoursesSpinner();
        switch (selectedItem) {
            case "Name":
                binding.nameContainer.setVisibility(View.VISIBLE);
                break;
            case "Assignment":
                binding.assignmentContainer.setVisibility(View.VISIBLE);
                break;
            case "Class":
                binding.classesContainer.setVisibility(View.VISIBLE);
                break;
            case "Exam":
                binding.examContainer.setVisibility(View.VISIBLE);
                break;
        }
    }
    public void onNothingSelected(AdapterView<?> parent) {
        binding.classesContainer.setVisibility(View.GONE);
        binding.examContainer.setVisibility(View.GONE);
        submitButton.setVisibility(View.GONE);
        deleteButton.setVisibility(View.GONE);
    }

    private void saveDataBasedOnType(){
        StoreData storage = new StoreData(getContext());
        switch (binding.type.getSelectedItem().toString()) {
            case "Name":
                saveName(storage);
                binding.userName.setText("");
                break;
            case "Assignment":
                saveAssignment(storage);
                binding.assName.setText("");
                binding.assDay.setText("");
                binding.assMonth.setText("");
                break;
            case "Class":
                saveCourse(storage);
                binding.courseName.setText("");
                binding.courseTime.setText("");
                binding.courseInstructor.setText("");

                break;
            case "Exam":
                saveExam(storage);
                binding.examName.setText("");
                binding.examTime.setText("");
                binding.examLocation.setText("");
                binding.examDay.setText("");
                binding.examMonth.setText("");
                break;
        }
    }
    private void deleteDataBasedOnType(){
        StoreData storage = new StoreData(getContext());
        switch (binding.type.getSelectedItem().toString()) {
            case "Name":
                deleteName(storage);
                binding.userName.setText("");
                AssignmentList asses = storage.getData("assignments", AssignmentList.class);
                ArrayList<Assignment> assignments = asses.getAssignments();
                for (Assignment ass : assignments) {
                    Log.d("input", ass.getName());
                    Log.d("input", ass.getCourse().toString());
                    Log.d("input", String.valueOf(ass.getMonthdue()));
                    Log.d("input", String.valueOf(ass.getDayDate()));
                }
                break;
            case "Assignment":
                deleteAssignment(storage);
                binding.assName.setText("");
                binding.assDay.setText("");
                binding.assMonth.setText("");
                break;
            case "Class":
                deleteCourse(storage);
                binding.courseName.setText("");
                binding.courseTime.setText("");
                binding.courseInstructor.setText("");
                break;
            case "Exam":
                deleteExam(storage);
                binding.examName.setText("");
                binding.examTime.setText("");
                binding.examLocation.setText("");
                binding.examDay.setText("");
                binding.examMonth.setText("");
                break;
        }
    }
    private void deleteName(StoreData storage) {
        User user1 = storage.getData("user1", User.class);
        user1.setUsername("");
        storage.saveData("user1", user1);
        if (usernameChangedListener != null) {
            usernameChangedListener.onUsernameChanged();
        }
    }
    private void deleteCourse(StoreData storage) {
        try {
            CourseList courseList = storage.getData("courses", CourseList.class);
            int i = isExisting(binding.courseName.getText().toString(), courseList);
            if (i == -1) {
                Toast.makeText(getContext(), "Not Found", Toast.LENGTH_SHORT).show();
                return;
            }
            courseList.removeCourse(i);
            storage.saveData("courses", courseList);
        } catch (Exception e) {
            Log.e("InputFragment", "Failed to delete courses", e);
        }
    }
    private void deleteExam(StoreData storage) {
        String examName = binding.examName.getText().toString();
        try {
            ExamList examList = storage.getData("exams", ExamList.class);
            int i = isExisting(examName, examList);
            if (i == -1) {
                Toast.makeText(getContext(), "Not Found", Toast.LENGTH_SHORT).show();
                return;
            }
            examList.removeExam(i);
            storage.saveData("exams", examList);
        } catch (Exception e) {
            Log.e("InputFragment", "Failed to delete exams", e);
        }

    }
    private void deleteAssignment(StoreData storage) {
        String assignmentName = binding.assName.getText().toString();
        try {
            AssignmentList assignmentList = storage.getData("assignments", AssignmentList.class);
            int i = isExisting(assignmentName, assignmentList);
            if (i == -1) {
                Toast.makeText(getContext(), "Not Found", Toast.LENGTH_SHORT).show();
                return;
            }
            assignmentList.removeAssignment(i);
            storage.saveData("assignments", assignmentList);
        } catch (Exception e) {
            Log.e("InputFragment", "Failed to delete assignments", e);
        }

    }

    private void saveName(StoreData storage) {
        User user1 = storage.getData("user1", User.class);
        String name = binding.userName.getText().toString();
        user1.setUsername(name);
        storage.saveData("user1", user1);
        if (usernameChangedListener != null) {
            usernameChangedListener.onUsernameChanged();
        }
    }
    public interface OnUsernameChangedListener {
        void onUsernameChanged();
    }
    public interface OnExamChangedListener {
        void onExamChanged();
    }
    private void saveCourse(StoreData storage) {
        String courseName = binding.courseName.getText().toString();
        String time = binding.courseTime.getText().toString();
        String instructor = binding.courseInstructor.getText().toString();
        try {
            CourseList courseList = storage.getData("courses", CourseList.class);
            int i = isExisting(courseName, courseList);
            if (i != -1) {
                courseList.getCourse(i).setInstructor(instructor);
                courseList.getCourse(i).setTime(time);
                storage.saveData("courses", courseList);
            } else {
                courseList.addCourse(new Course(courseName, time, instructor));
                storage.saveData("courses", courseList);
            }
        } catch (Exception e) {
            CourseList courseList = new CourseList();
            courseList.addCourse(new Course(courseName, time, instructor));
            storage.saveData("courses", courseList);
        }
    }
    private void saveExam(StoreData storage) {

        String examName = binding.examName.getText().toString();
        String examTime = binding.examTime.getText().toString();
        String examLocation = binding.examLocation.getText().toString();
        int examDay = Integer.parseInt(binding.examDay.getText().toString());
        int examMonth = Integer.parseInt(binding.examMonth.getText().toString());
        try {
            ExamList examList = storage.getData("exams", ExamList.class);
            int i = isExisting(examName, examList);
            if (i != -1) {
                examList.getExam(i).setExamTime(examTime);
                examList.getExam(i).setExamLocation(examLocation);
                examList.getExam(i).setDaydue(examDay);
                examList.getExam(i).setMonthdue(examMonth);
                storage.saveData("exams", examList);
            } else {
                examList.addExam(new Exam(examName, examTime, examLocation, examDay, examMonth));
                storage.saveData("exams", examList);
            }
        } catch (Exception e) {
            ExamList examList = new ExamList();
            storage.saveData("exams", examList);
        }
        if (examChangedListener != null) {
            examChangedListener.onExamChanged();
        }
    }
    private void saveAssignment(StoreData storage) {
        String assignmentName = binding.assName.getText().toString();
        Course assignmentCourse = null;
        int day = Integer.parseInt(binding.assDay.getText().toString());
        int month = Integer.parseInt(binding.assMonth.getText().toString());
        String courseName = (String) binding.assCourse.getSelectedItem();
        CourseList courses = null;
        try {
            courses = storage.getData("courses", CourseList.class);
            for (Course course : courses.getCourses()) {
                if (course.getCourseName().equals(courseName)) {
                    assignmentCourse = course;
                    break;
                }
            }
        } catch (Exception e) {
            Log.e("InputFragment", "Failed to load courses", e);
        }
        if (assignmentCourse == null) {
            Toast.makeText(getContext(), "Select associated course", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            AssignmentList assignmentList = storage.getData("assignments", AssignmentList.class);
            int i = isExisting(assignmentName, assignmentList);
            if (i != -1 && assignmentList.getAssignment(i).getCourse().getCourseName().equals(courseName)) {
                assignmentList.getAssignment(i).setDayDate(day);
                assignmentList.getAssignment(i).setMonthdue(month);
                assignmentList.getAssignment(i).setCourse(assignmentCourse);
                storage.saveData("assignments", assignmentList);
            } else {
                assignmentList.addAssignment(new Assignment(assignmentName, day, month, assignmentCourse));
                storage.saveData("assignments", assignmentList);
            }
        } catch (Exception e) {
            AssignmentList assignmentList = new AssignmentList();
            assignmentList.addAssignment(new Assignment(assignmentName, day, month, assignmentCourse));
            storage.saveData("assignments", assignmentList);
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnUsernameChangedListener) {
            usernameChangedListener = (OnUsernameChangedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnUsernameChangedListener");
        }
    }
    public void onDetach() {
        super.onDetach();
        usernameChangedListener = null;
        examChangedListener = null;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}