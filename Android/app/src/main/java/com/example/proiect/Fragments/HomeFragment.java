package com.example.proiect.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.proiect.R;
import com.example.proiect.RoomDatabase.model.Meals;
import com.example.proiect.RoomDatabase.service.MealService;
import com.example.proiect.util.WorkoutsAdapter;
import com.example.proiect.WorkoutsDetails;
import com.example.proiect.asyncTask.Callback;
import com.example.proiect.util.MealAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class HomeFragment extends Fragment {

    private TextView dmy, day;
    private ListView lvworkouts, lvmeals;
    private ArrayList<WorkoutsDetails> workouts=new ArrayList<WorkoutsDetails>();
    private ArrayList<Meals> meals = new ArrayList<>();
    private MealService mealService1;
    FloatingActionButton fab;
    private TextView tv_counter, tv_nbcalories;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tv_counter=view.findViewById(R.id.tv_water_SP);
        tv_counter.setText(WaterFragment.GlobalCounter);

        day = view.findViewById(R.id.day);
        dmy = view.findViewById(R.id.dmy);
        fab = view.findViewById(R.id.fab_graphic);
        fab.setOnClickListener(seeProfile());
        lvworkouts = view.findViewById(R.id.lv_today_workouts);
        lvmeals=view.findViewById(R.id.lv_today_meals);
        addAdapter();


        Date currentTime = Calendar.getInstance().getTime();
        String formattedDate = DateFormat.getDateInstance(DateFormat.FULL).format(currentTime);

        String[] splitDate = formattedDate.split(",");

        day.setText(splitDate[0].toUpperCase());
        dmy.setText(splitDate[1]);

        tv_nbcalories=view.findViewById(R.id.tv_nbcalories);
        tv_nbcalories.setText(String.valueOf(getTotal(meals)));


        mealService1 = new MealService(getActivity().getApplicationContext());
        mealService1.getAllToday(getAllFromDbCallback());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args=getArguments();
        if (args == null)
          return;

        WorkoutsDetails workout=args.getParcelable("clickvalue");
        workouts.add(workout);

        WorkoutsAdapter adapter=new WorkoutsAdapter(getActivity(), workouts);
        lvworkouts.setAdapter(adapter);

    }


    private Callback<List<Meals>> getAllFromDbCallback() {
        return new Callback<List<Meals>>() {
            @Override
            public void runResultOnUiThread(List<Meals> result) {
                if (result != null) {
                    meals.clear();
                    meals.addAll(result);
                    notifyAdapter();
                }
            }
        };
    }

    private void addAdapter() {
        MealAdapter adapter = new MealAdapter(getActivity().getApplicationContext(), R.layout.lv_meals_row,
                meals, getLayoutInflater());
        lvmeals.setAdapter(adapter);
    }

    private void notifyAdapter() {
        MealAdapter adapter = (MealAdapter) lvmeals.getAdapter();
        adapter.notifyDataSetChanged();
        tv_nbcalories.setText(String.valueOf(getTotal(meals)));
    }

    private View.OnClickListener seeProfile() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(new ProfileFragment());

            }
        };

    }

    private void changeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        fragmentTransaction.commit();
    }

    public int getTotal(ArrayList<Meals> list){

        int total=0;
        for(int i=0;i<list.size();i++){
            total=total+(list.get(i).getCalories());
        }
        return total;
    }

}