package com.example.team47_project1.ui.home;

import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.team47_project1.R;
import com.example.team47_project1.databinding.FragmentHomeBinding;
import com.example.team47_project1.utils.StoreData;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ArrayList<View> allItems;
    private ArrayList<String> deleteList;
    private ArrayList<String> ids;
    private StoreData storage;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        storage = new StoreData(getContext());

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        Button removeButton = view.findViewById(R.id.remove_thing);
        removeButton.setEnabled(false);

        allItems = new ArrayList<>();
        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Reference the LinearLayout using its ID
        LinearLayout linearLayout = view.findViewById(R.id.classes_container);

        int counter = 0;
        //Log.d("getting from: ", storage.getData("todo" + counter, String.class));
        while (storage.getData("todo" + counter, String.class) != null) {
            Log.d("getting from: ", storage.getData("todo" + counter, String.class));
            EditText editText = new EditText(getContext());
            editText.setText(storage.getData("todo" + counter, String.class));
            addToDoItem(editText, linearLayout);
            counter++;
        }

        // Reference the Button
        Button addButton = view.findViewById(R.id.add_thing);
        Button removeButton = view.findViewById(R.id.remove_thing);
        EditText thing = view.findViewById(R.id.thing_to_do);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d("MyApp", "Button Clicked");
                boolean added = addToDoItem(thing, linearLayout);
                if (added) {
                    removeButton.setEnabled(true);
                    removeButton.setClickable(true);
                }
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d("MyApp", "Button Clicked");
                removeToDoItems(linearLayout);
            }
        });
    }

    public boolean addToDoItem(EditText thingToAdd, LinearLayout linearLayout) {
        if (!TextUtils.isEmpty(thingToAdd.getText().toString())) {
            // set text
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View listItem = inflater.inflate(R.layout.list_item, null);
            TextView textView = listItem.findViewById(R.id.textView);
            EditText editText = listItem.findViewById(R.id.editText);
            textView.setText(thingToAdd.getText().toString());
            editText.setText(thingToAdd.getText().toString());
            thingToAdd.setText("");

            // adding view to the list
            linearLayout.addView(listItem);
            allItems.add(listItem);

            // activate buttons
            ImageButton editButton = listItem.findViewById(R.id.editButton);
            ImageButton confirmButton = listItem.findViewById(R.id.confirmButton);
            editButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editToDoItem(listItem);
                }
            });
            confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    confirmToDoItem(listItem);
                }
            });
            return true;
        } else {
            thingToAdd.setError("Please add a to do item.");
            return false;
        }
    }

    public void removeToDoItems(LinearLayout linearLayout) { //this does not work
        View[] toRemove = new View[allItems.size()];
        int counter = 0;
        for (View i : allItems) {
            CheckBox checkBox = i.findViewById(R.id.checkBox);
            TextView text = i.findViewById(R.id.textView);
            //Log.d("bruh bruh bruh", text.getText().toString());
            if (checkBox.isChecked()) {
                linearLayout.removeView(i);
                toRemove[counter] = i;
                counter++;
            }
        }
        for (View i : toRemove) {
            allItems.remove(i);
        }
    }

    public void editToDoItem(View item) {
        TextView textView = item.findViewById(R.id.textView);
        EditText editText = item.findViewById(R.id.editText);
        ImageButton editButton = item.findViewById(R.id.editButton);
        ImageButton confirmButton = item.findViewById(R.id.confirmButton);

        textView.setVisibility(View.GONE);
        editText.setVisibility(View.VISIBLE);
        editButton.setVisibility(View.GONE);
        confirmButton.setVisibility(View.VISIBLE);
    }

    public void confirmToDoItem(View item) {
        TextView textView = item.findViewById(R.id.textView);
        EditText editText = item.findViewById(R.id.editText);
        ImageButton editButton = item.findViewById(R.id.editButton);
        ImageButton confirmButton = item.findViewById(R.id.confirmButton);

        textView.setVisibility(View.VISIBLE);
        editText.setVisibility(View.GONE);
        editButton.setVisibility(View.VISIBLE);
        confirmButton.setVisibility(View.GONE);

        textView.setText(editText.getText().toString());
    }

    public void save() {
        int counter = 0;
        for (int i = 0; i < 100; i++) {
            storage.deleteData("todo" + i);
        }
        for (View i : allItems) {
            if (i != null) {
                TextView textView = i.findViewById(R.id.textView);
                String text = textView.getText().toString();
                Log.d("saving: ", text);
                Log.d("saving as: ", "todo" + counter);
                storage.saveData("todo" + counter, text);
                counter++;
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        save();
        binding = null;
    }
    /*
    @Override
    public void onStop() {
        super.onStop();
        save();
    }*/
}