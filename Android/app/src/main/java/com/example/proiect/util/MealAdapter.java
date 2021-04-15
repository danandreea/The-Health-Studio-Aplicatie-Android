package com.example.proiect.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.proiect.R;
import com.example.proiect.RoomDatabase.model.Meals;

import java.util.Date;
import java.util.List;

public class MealAdapter extends ArrayAdapter<Meals> {

    private Context context;
    private int resource;
    private List<Meals> meals;
    private LayoutInflater inflater;

    public MealAdapter(@NonNull Context context, int resource, List<Meals> meals, LayoutInflater inflater) {
        super(context, resource, meals);
        this.context = context;
        this.resource = resource;
        this.meals = meals;
        this.inflater = inflater;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource, parent, false);
        Meals meal = meals.get(position);
        if (meal != null) {
            addDate(view, meal.getDate());
            addMealType(view, meal.getMealtype());
            addName(view, meal.getName());
            addGR(view, meal.getGr());
            addCalories(view, meal.getCalories());
        }
        return view;
    }

    private void addDate(View view, Date date) {
        TextView textView = view.findViewById(R.id.tv_lv_meal_row_date);
        addTextViewContent(textView, DateConverter.fromDate(date));
    }

    private void addMealType(View view, String type) {
        TextView textView = view.findViewById(R.id.tv_lv_meal_row_type);
        addTextViewContent(textView, type);
    }
    private void addName(View view, String name) {
        TextView textView = view.findViewById(R.id.tv_lv_meal_row_name);
        addTextViewContent(textView, name);
    }

    private void addGR(View view,int gr) {
        TextView textView = view.findViewById(R.id.tv_lv_meal_row_gr);
        textView.setText(String.valueOf(gr));
    }
    private void addCalories(View view,int calories) {
        TextView textView = view.findViewById(R.id.tv_lv_meal_row_calories);
        textView.setText(String.valueOf(calories));
    }

    private void addTextViewContent(TextView textView, String value) {
        if (value != null && !value.isEmpty()) {
            textView.setText(value);
        } else {
            textView.setText("-");
        }
    }
}
