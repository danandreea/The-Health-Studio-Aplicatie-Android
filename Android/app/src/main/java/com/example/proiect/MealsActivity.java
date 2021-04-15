package com.example.proiect;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.proiect.RoomDatabase.model.Meals;
import com.example.proiect.RoomDatabase.service.MealService;
import com.example.proiect.asyncTask.Callback;
import com.example.proiect.util.MealAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class MealsActivity extends AppCompatActivity {

    private static final int ADD_MEAL_REQUEST_CODE = 201;
    private static final int UPDATE_MEAL_REQUEST_CODE = 202;


    private ListView lvMeals;
    private ArrayList<Meals> meals = new ArrayList<>();
    private MealService mealService;


    FloatingActionButton fab, fabback;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meals);
        initComponents();
        mealService = new MealService(getApplicationContext());
        mealService.getAll(getAllFromDbCallback());

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Meals meal = (Meals) data.getSerializableExtra(AddMealFood.MEALFOOD_KEY);
            if (requestCode == ADD_MEAL_REQUEST_CODE) {
                mealService.insert(insertIntoDbCallback(), meal);
            } else if (requestCode == UPDATE_MEAL_REQUEST_CODE) {
                mealService.update(updateToDbCallback(), meal);
            }
        }
    }


    private void initComponents() {
        lvMeals = findViewById(R.id.lv_meals);
        fab = findViewById(R.id.fab_add_meals);
        fabback = findViewById(R.id.fab_back_home);
        addAdapter();
        fab.setOnClickListener(addMealFoodEventListener());
        fabback.setOnClickListener(BackHomeEventListener());
        lvMeals.setOnItemClickListener(updateEventListener());
        lvMeals.setOnItemLongClickListener(deleteEventListener());
    }

    private View.OnClickListener addMealFoodEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddMealFood.class);
                startActivityForResult(intent, ADD_MEAL_REQUEST_CODE);
            }
        };
    }

    private View.OnClickListener BackHomeEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MealsActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        };
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

    private Callback<Meals> insertIntoDbCallback() {
        return new Callback<Meals>() {
            @Override
            public void runResultOnUiThread(Meals result) {
                if (result != null) {
                    meals.add(result);
                    notifyAdapter();
                }
            }
        };
    }

    private Callback<Meals> updateToDbCallback() {
        return new Callback<Meals>() {
            @Override
            public void runResultOnUiThread(Meals result) {
                if (result != null) {
                    for (Meals meal : meals) {
                        if (meal.getId() == result.getId()) {
                            meal.setDate(result.getDate());
                            meal.setMealtype(result.getMealtype());
                            meal.setName(result.getName());
                            meal.setGr(result.getGr());
                            meal.setCalories(result.getCalories());
                            break;
                        }
                    }
                    notifyAdapter();
                }
            }
        };
    }

    private Callback<Integer> deleteToDbCallback(final int position) {
        return new Callback<Integer>() {
            @Override
            public void runResultOnUiThread(Integer result) {
                if (result != -1) {
                    meals.remove(position);
                    notifyAdapter();
                }
            }
        };
    }

    private AdapterView.OnItemClickListener updateEventListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(), AddMealFood.class);
                intent.putExtra(AddMealFood.MEALFOOD_KEY, meals.get(position));
                startActivityForResult(intent, UPDATE_MEAL_REQUEST_CODE);

            }
        };
    }

    private AdapterView.OnItemLongClickListener deleteEventListener() {
        return new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mealService.delete(deleteToDbCallback(position), meals.get(position));
                return true;
            }
        };
    }

    private void addAdapter() {
        MealAdapter adapter = new MealAdapter(getApplicationContext(), R.layout.lv_meals_row,
                meals, getLayoutInflater());
        lvMeals.setAdapter(adapter);
    }

    private void notifyAdapter() {
        MealAdapter adapter = (MealAdapter) lvMeals.getAdapter();
        adapter.notifyDataSetChanged();
    }
}