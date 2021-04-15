package com.example.proiect.RoomDatabase.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.proiect.RoomDatabase.model.Nutrition;

import java.util.List;

@Dao
public interface NutritionDao {

    @Insert
    void insert(Nutrition nutr);

    @Insert
    void insert(List<Nutrition> nutritionList);

    @Query("select * from nutritions")
    List<Nutrition> getAll();

    @Query("delete from nutritions")
    void deleteAll();

    @Delete
    void delete(Nutrition nutrition);

    @Query("select * from nutritions where id= :id")
    List<Nutrition> getNutritionFromMeals(long id);
}
