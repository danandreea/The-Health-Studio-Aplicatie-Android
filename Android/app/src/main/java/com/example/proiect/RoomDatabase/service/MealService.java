package com.example.proiect.RoomDatabase.service;

import android.content.Context;

import com.example.proiect.RoomDatabase.DatabaseManager;
import com.example.proiect.RoomDatabase.dao.MealDao;
import com.example.proiect.RoomDatabase.model.Meals;
import com.example.proiect.asyncTask.AsyncTaskRunner;
import com.example.proiect.asyncTask.Callback;
import com.example.proiect.util.DateConverter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

public class MealService {

    private final MealDao mealDao;
    private final AsyncTaskRunner taskRunner;

    public MealService(Context context) {
        mealDao = DatabaseManager.getInstance(context).getMealDao();
        taskRunner = new AsyncTaskRunner();
    }

    public void getAll(Callback<List<Meals>> callback) {
        Callable<List<Meals>> callable = new Callable<List<Meals>>() {
            @Override
            public List<Meals> call() {
                return mealDao.getAll();
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    Date currentTime = Calendar.getInstance().getTime();
    String time = DateConverter.fromDate(currentTime);

    public void getAllToday(Callback<List<Meals>> callback) {
        Callable<List<Meals>> callable = new Callable<List<Meals>>() {
            @Override
            public List<Meals> call() {
                return mealDao.getAllToday(time);
            }
        };
        taskRunner.executeAsync(callable, callback);
    }


    public void insert(Callback<Meals> callback, final Meals meal) {
        Callable<Meals> callable = new Callable<Meals>() {
            @Override
            public Meals call() {
                if (meal == null) {
                    return null;
                }
                long id = mealDao.insert(meal);
                if (id == -1) {
                    return null;
                }
                meal.setId(id);
                return meal;
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void update(Callback<Meals> callback, final Meals meal) {
        Callable<Meals> callable = new Callable<Meals>() {
            @Override
            public Meals call() {
                if (meal == null) {
                    return null;
                }
                int count =mealDao.update(meal);
                if (count < 1) {
                    return null;
                }
                return meal;
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void delete(Callback<Integer> callback, final Meals meal) {
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() {
                if (meal == null) {
                    return -1;
                }
                return mealDao.delete(meal);
            }
        };
        taskRunner.executeAsync(callable, callback);
    }
}
