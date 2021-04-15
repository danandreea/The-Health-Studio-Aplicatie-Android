package com.example.proiect;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.proiect.RoomDatabase.model.Meals;
import com.example.proiect.util.DateConverter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class AddMealFood extends AppCompatActivity {

    public static final String MEALFOOD_KEY = "mealfoodKey";
    private TextInputEditText tietDate;
    private Spinner spnType;
    private TextInputEditText tietName;
    private TextInputEditText tietGr;
    private TextInputEditText tietCalories;
    private Button btn_add;
    private FloatingActionButton fabback;

    private Intent intent;
    private Meals meal = null;

    public AddMealFood()
    {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_meal);
        initComponents();
        intent = getIntent();
        if (intent.hasExtra(MEALFOOD_KEY)) {
            meal = (Meals) intent.getSerializableExtra(MEALFOOD_KEY);
            buildViewsFromMeals(meal);
        }
    }

    private void initComponents() {
        tietDate = findViewById(R.id.tiet_add_meal_date);
        spnType = findViewById(R.id.spn_add_meal_type);
        tietName = findViewById(R.id.tiet_add_name);
        tietGr = findViewById(R.id.tiet_add_gr);
        tietCalories = findViewById(R.id.tiet_add_calories);
        btn_add = findViewById(R.id.btnAdd);
        fabback=findViewById(R.id.fab_back_lv);
        addCategoryAdapter();
        btn_add.setOnClickListener(saveMealsEventListener());
        fabback.setOnClickListener(BackLVEventListener());
    }

    private View.OnClickListener BackLVEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddMealFood.this, MealsActivity.class);
                startActivity(intent);
                finish();
            }
        };
    }

    private void addCategoryAdapter() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.add_meal_type_values,
                android.R.layout.simple_spinner_dropdown_item);
        spnType.setAdapter(adapter);
    }

    private View.OnClickListener saveMealsEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    createFromViews();
                    intent.putExtra(MEALFOOD_KEY, meal);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        };
    }

    private boolean validate() {
        if (tietDate.getText() == null || tietDate.getText().toString().trim().isEmpty()
                || DateConverter.fromString(tietDate.getText().toString()) == null) {
            Toast.makeText(getApplicationContext(),
                    "Invalid date! Accepted format dd/MM/yyyy",
                    Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        if (tietGr.getText() == null || tietGr.getText().toString().isEmpty()
                || Integer.parseInt(tietGr.getText().toString()) < 0) {
            Toast.makeText(getApplicationContext(),
                    "Invalid serving size",
                    Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        if (tietCalories.getText() == null || tietCalories.getText().toString().isEmpty()
                || Integer.parseInt(tietCalories.getText().toString()) < 0) {
            Toast.makeText(getApplicationContext(),
                    "Invalid number of calories",
                    Toast.LENGTH_SHORT)
                    .show();
            return false;
        }

        if (tietName.getText() == null || tietName.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "Please enter the name of the food",
                    Toast.LENGTH_SHORT)
                    .show();
            return false;
        }
        return true;
    }

    private void createFromViews() {
        Date date = DateConverter.fromString(tietDate.getText().toString());
        String name = tietName.getText().toString();
        String type = spnType.getSelectedItem().toString();
        int gr = Integer.parseInt(tietGr.getText().toString());
        int calories = Integer.parseInt(tietCalories.getText().toString());
        if (meal == null) {
            meal = new Meals(date, type, name, gr,calories);
        } else {
            meal.setDate(date);
            meal.setName(name);
            meal.setMealtype(type);
            meal.setGr(gr);
            meal.setCalories(calories);
        }
    }

    private void selectType(Meals meal) {
        ArrayAdapter adapter = (ArrayAdapter) spnType.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            String item = (String) adapter.getItem(i);
            if (item != null && item.equals(meal.getMealtype())) {
                spnType.setSelection(i);
                break;
            }
        }
    }

    private void buildViewsFromMeals(Meals meal) {
        if (meal == null) {
            return;
        }
        if (meal.getDate() != null) {
            tietDate.setText(DateConverter.fromDate(meal.getDate()));
        }
        if (meal.getName() != null) {
            tietName.setText(meal.getName());
        }
        if (meal.getGr() != 0) {
            tietGr.setText(String.valueOf(meal.getGr()));
        }
        if (meal.getCalories() != 0) {
            tietCalories.setText(String.valueOf(meal.getCalories()));
        }
        selectType(meal);
    }

}
