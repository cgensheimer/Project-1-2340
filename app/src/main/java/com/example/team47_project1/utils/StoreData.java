package com.example.team47_project1.utils;
// code for shared preferences and storing data manager

import android.content.Context;
import android.content.SharedPreferences;
import com.example.team47_project1.ui.gallery.Course;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

// HOW TO USE
// Storing data
// StoreData storeData = new StoreData(getContext());
// storeData.saveData("key", data);
// Getting data
// StoreData storeData = new StoreData(getContext());
// Type type = new TypeToken<List<Course>>(){}.getType();
// List<Class> data = storeData.getData("key", type);
// or if we have a Assignmetns class
// Assignments class1 = new Assignments("name", "due", "course", "description");
// storeData.saveData("key", class1);
// retrieve
// Assignments class1 = storeData.getData("key", Assignments.class);

public class StoreData {
    private static final String PREF_NAME = "app_prefs";
    private final SharedPreferences sharedPreferences;
    private final Gson gson;

    public StoreData(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
    }

    public <T> void saveData(String key, T data) {
        String json = gson.toJson(data);
        sharedPreferences.edit().putString(key, json).apply();
    }

    public <T> T getData(String key, Type typeOfT) {
        String json = sharedPreferences.getString(key, null);
        if (json == null) {
            return null;
        }
        return gson.fromJson(json, typeOfT);
    }
    public void deleteData(String key) {
        sharedPreferences.edit().remove(key).apply();
    }
}