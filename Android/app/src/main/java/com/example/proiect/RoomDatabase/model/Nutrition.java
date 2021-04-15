package com.example.proiect.RoomDatabase.model;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "nutritions", foreignKeys = @ForeignKey(entity = Meals.class, parentColumns = "id", childColumns = "idN", onDelete = CASCADE), indices = @Index("id"))
public class Nutrition implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int idN;

    private float carbs;
    private float fat;
    private float protein;

    public Nutrition(int id, float carbs, float fat, float protein) {
        this.id = id;
        this.carbs = carbs;
        this.fat = fat;
        this.protein = protein;
    }

    public int getIdN() {
        return idN;
    }

    public void setIdN(int idN) {
        this.idN = idN;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getCarbs() {
        return carbs;
    }

    public void setCarbs(float carbs) {
        this.carbs = carbs;
    }

    public float getFat() {
        return fat;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    public float getProtein() {
        return protein;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }

    @Override
    public String toString() {
        return "Nutrition{" +
                "carbs=" + carbs +
                ", fat=" + fat +
                ", protein=" + protein +
                '}';
    }
}
