package com.example.proiect.RoomDatabase.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.proiect.RoomDatabase.model.Meals;
import com.example.proiect.util.DateConverter;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Dao
public interface MealDao {

    @Query("select * from meals")
    List<Meals> getAll();


    @Query("select * from meals where date= :time")
    List<Meals> getAllToday(String time);

    @Insert
    long insert(Meals meal);

    @Update
    int update(Meals meal);

    @Delete
    int delete(Meals meal);
}
