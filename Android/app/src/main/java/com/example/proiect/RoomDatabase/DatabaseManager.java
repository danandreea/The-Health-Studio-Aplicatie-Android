package com.example.proiect.RoomDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.proiect.RoomDatabase.dao.MealDao;
import com.example.proiect.RoomDatabase.dao.NutritionDao;
import com.example.proiect.RoomDatabase.model.Meals;
import com.example.proiect.RoomDatabase.model.Nutrition;
import com.example.proiect.util.DateConverter;


@Database(entities = {Meals.class}, exportSchema = false, version = 1)
@TypeConverters({DateConverter.class})
public abstract class DatabaseManager extends RoomDatabase {

    private static DatabaseManager databaseManager;

    public static DatabaseManager getInstance(Context context) {
        if (databaseManager == null) {
            synchronized (DatabaseManager.class) {
                if (databaseManager == null) {
                    databaseManager = Room.databaseBuilder(context, DatabaseManager.class, "food_db").fallbackToDestructiveMigration().build();
                }
            }
        }
        return databaseManager;
    }

    public abstract MealDao getMealDao();


}