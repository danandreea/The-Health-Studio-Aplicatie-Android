package com.example.proiect;

import android.content.Intent;
import android.os.Bundle;

import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.proiect.Fragments.HomeFragment;
import com.example.proiect.Fragments.ProfileFragment;
import com.example.proiect.Fragments.ProgressFragment;
import com.example.proiect.Fragments.WaterFragment;
import com.example.proiect.Fragments.WorkoutsFragment;
import com.example.proiect.RoomDatabase.model.Meals;
import com.example.proiect.RoomDatabase.service.MealService;
import com.example.proiect.asyncTask.Callback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    FloatingActionButton fab;
    List<Meals> meals = new ArrayList<Meals>();
    private MealService mealService;


    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home);
        configNav();
        changeFragment(new HomeFragment());
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ProfileActivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mealService = new MealService(getApplicationContext());
        mealService.getAll(getAllFromDbCallback());
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
       // getMenuInflater().inflate(R.menu.main_menu, menu);
        return false;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
       switch(item.getItemId())
       {
           case R.id.nav_home:
           {
               changeFragment(new HomeFragment());
               break;
           }
           case R.id.nav_profile:
           {
               changeFragment(new ProfileFragment());
               break;
           }
           case R.id.nav_meals:
           {
               Intent intent = new Intent(ProfileActivity.this, MealsActivity.class);
               startActivity(intent);
           }
           case R.id.nav_workouts:
           {
               changeFragment(new WorkoutsFragment());
               break;
           }
           case R.id.nav_water:
           {
               changeFragment(new WaterFragment());
               break;
           }
           case R.id.nav_progress:
           {
               ArrayList<Meals> list = new ArrayList<>();
               ArrayList<Meals> listBreakfast = new ArrayList<>();
               ArrayList<Meals> listLunch = new ArrayList<>();
               ArrayList<Meals> listDinner = new ArrayList<>();
               for(Meals meal: meals) {
                   list.add(meal);
                   if (meal.getMealtype().toUpperCase().equals("BREAKFAST"))
                       listBreakfast.add(meal);
                   else if (meal.getMealtype().toUpperCase().equals("LUNCH"))
                       listLunch.add(meal);
                   else listDinner.add(meal);
               }

               ProgressFragment fragment=new ProgressFragment();
               Bundle args=new Bundle();
               args.putSerializable("listBreakfast", listBreakfast);
               args.putSerializable("listLunch", listLunch);
               args.putSerializable("listDinner", listDinner);
               args.putSerializable("list", list);

               fragment.setArguments(args);

               getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,fragment).commit();

           }
       }

       drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void configNav()
    {

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout=(DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView=(NavigationView) findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.nav_opened, R.string.nav_closed);
        drawerLayout.addDrawerListener(toggle);


        navigationView.setNavigationItemSelectedListener(this);
        toggle.syncState();


    }

    private void changeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        fragmentTransaction.commit();
    }

    private Callback<List<Meals>> getAllFromDbCallback() {
        return new Callback<List<Meals>>() {
            @Override
            public void runResultOnUiThread(List<Meals> result) {
                if (result != null) {
                    meals.clear();
                    meals.addAll(result);
                }
            }
        };
    }

}
