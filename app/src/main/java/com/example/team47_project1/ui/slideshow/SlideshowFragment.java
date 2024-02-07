package com.example.team47_project1.ui.slideshow;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.team47_project1.R;
import com.example.team47_project1.databinding.FragmentSlideshowBinding;
import com.example.team47_project1.utils.AssignmentList;
import com.example.team47_project1.utils.StoreData;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SlideshowFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private FragmentSlideshowBinding binding;
    ArrayList<Assignment> array;
    Spinner spinner;
    private StoreData store;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //CANT VIEW ASSIGNMENTS UNTIL SORT OPTION SELECTED???
        binding.assignContainer.setVisibility(View.GONE);

        spinner = (Spinner) root.findViewById(R.id.sortby);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.sortbyarray,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        TextView textView = binding.textAss;
        slideshowViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        return root;
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayout linearLayout = view.findViewById(R.id.assign_container);
        store = new StoreData(getContext());
        AssignmentList assignments = store.getData("assignments",AssignmentList.class);
        if (assignments != null) {
            if (assignments.size() == 0) {
                binding.textAss.setVisibility(View.VISIBLE);
            } else {
                binding.textAss.setVisibility(View.GONE);
            }
            array = assignments.getAssignments();
            //DO A FOR EACH ON THE ARRAY LIST CALL LOAD VIEW ITEM AND ADD IT TO THE LAYOUT
            for(Assignment n : array) {
                View viewtoadd = LoadItemView(n);
                linearLayout.addView(viewtoadd);
            }
        }
    }
    public View LoadItemView(Assignment assign) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View assignview = inflater.inflate(R.layout.assignmentitem, null);
        TextView assignname = assignview.findViewById(R.id.hwnametostring);
        TextView courseattached = assignview.findViewById(R.id.courseattached);
        TextView monthdue = assignview.findViewById(R.id.monthduetv);
        TextView daydue = assignview.findViewById(R.id.dayduetv);

        assignname.setText(assign.getName());
        courseattached.setText(assign.getCourse().getCourseName());
        monthdue.setText(Integer.toString(assign.getMonthdue()) + "/");
        daydue.setText(Integer.toString(assign.getDayDate()));
        return assignview;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = parent.getItemAtPosition(position).toString();
        Toast.makeText(getContext(), "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
        sortBy(selectedItem);
    }

    private void sortBy(String sort) {
        StoreData store = new StoreData(getContext());
        AssignmentList assignments = store.getData("assignments",AssignmentList.class);
        switch (sort) {
            case "Sort By":
                binding.assignContainer.setVisibility(View.GONE);
                break;
            case "Course Name":
                ArrayList<Assignment> courses = assignments.getAssignments();
                assignments.sortbycourse(courses);
                array = assignments.getAssignments();
                updateView(array);
                binding.assignContainer.setVisibility(View.VISIBLE);
                break;
            case "Due Date":
                ArrayList<Assignment> courses1 = assignments.getAssignments();
                assignments.sortbyduedate(courses1);
                array = assignments.getAssignments();
                updateView(array);
                binding.assignContainer.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void updateView(ArrayList<Assignment> array) {
        LinearLayout linearLayout = getView().findViewById(R.id.assign_container);
        linearLayout.removeAllViews();
        for(Assignment n : array) {
            View viewtoadd = LoadItemView(n);
            linearLayout.addView(viewtoadd);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    }

