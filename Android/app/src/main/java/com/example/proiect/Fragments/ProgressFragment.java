package com.example.proiect.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.example.proiect.util.BarChartView;
import com.example.proiect.R;
import com.example.proiect.RoomDatabase.model.Meals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProgressFragment extends Fragment {


    public ProgressFragment()
{}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_progress, container, false);


        Bundle args=getArguments();
        ArrayList<Meals> listBreakfast = (ArrayList<Meals>)args.getSerializable("listBreakfast");
        ArrayList<Meals> listLunch = (ArrayList<Meals>)args.getSerializable("listLunch");
        ArrayList<Meals> listDinner = (ArrayList<Meals>)args.getSerializable("listDinner");

        ArrayList<Meals> list=(ArrayList<Meals>) args.getSerializable("list");
        LinearLayout layout;
        Map<String, Integer> source;


        source=getSource(list);
        layout = view.findViewById(R.id.layoutBar);
        layout.addView(new BarChartView(getActivity().getApplicationContext(), source));

        List<Double> lstcaloriesBreakfast = new ArrayList<>();
        List<Double> lstCaloriesLunch = new ArrayList<>();
        List<Double> lstCaloriesDinner= new ArrayList<>();

        for(Meals meal: listBreakfast)
            lstcaloriesBreakfast.add(Double.valueOf(meal.getCalories()));
        for(Meals meal: listLunch)
            lstCaloriesLunch.add(Double.valueOf(meal.getCalories()));
        for(Meals meal: listDinner)
            lstCaloriesDinner.add(Double.valueOf(meal.getCalories()));

        XYPlot plot = view.findViewById(R.id.mySimpleXYPlot);

        plot.setTitle("Calories graphic");


        XYSeries series1 = new SimpleXYSeries(lstcaloriesBreakfast, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Breakfast");
        plot.addSeries(series1, new LineAndPointFormatter(getActivity().getApplicationContext(), R.layout.f1));

        XYSeries series2 = new SimpleXYSeries(lstCaloriesLunch, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Lunch");
        plot.addSeries(series2, new LineAndPointFormatter(getActivity().getApplicationContext(), R.layout.f2));

        XYSeries series3 = new SimpleXYSeries(lstCaloriesDinner, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Dinner");
        plot.addSeries(series3, new LineAndPointFormatter(getActivity().getApplicationContext(), R.layout.f3));



        return view;
    }

    private Map<String, Integer> getSource(List<Meals> meals)
    {
        if(meals==null || meals.isEmpty())
            return new HashMap<>();
        else
        {
            Map<String, Integer> results = new HashMap<>();
            for(Meals meal: meals)
                if(results.containsKey(meal.getMealtype()))
                    results.put(meal.getMealtype(), results.get(meal.getMealtype())+1);
                else
                    results.put(meal.getMealtype(), 1);
            return results;
        }
    }

}