package com.example.proiect.RoomDatabase.model;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.proiect.util.DateConverter;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "meals")
public class Meals implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name="date")
    private Date date;
    @ColumnInfo(name="mealtype")
    private String mealtype;
    @ColumnInfo(name="name")
    private String name;
    @ColumnInfo(name="gr")
    private int gr;
    @ColumnInfo(name="calories")
    private int calories;


    public Meals(long id, Date date, String mealtype, String name, int gr, int calories) {
        this.id = id;
        this.date = date;
        this.mealtype = mealtype;
        this.name = name;
        this.gr = gr;
        this.calories = calories;
    }

    @Ignore
    public Meals(Date date, String mealtype, String name, int gr, int calories) {
        this.date = date;
        this.mealtype = mealtype;
        this.name = name;
        this.gr = gr;
        this.calories = calories;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMealtype() {
        return mealtype;
    }

    public void setMealtype(String mealtype) {
        this.mealtype = mealtype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGr() {
        return gr;
    }

    public void setGr(int gr) {
        this.gr = gr;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return
                "Date=" +  DateConverter.fromDate(date)  +
                ", mealtype='" + mealtype + '\'' +
                ", name='" + name + '\'' +
                ", gr=" + gr +
                ", calories=" + calories +
                '}';
    }


}
