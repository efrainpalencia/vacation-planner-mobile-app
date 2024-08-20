package com.example.d308vacationplanner.entities;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.d308vacationplanner.database.converters.Converters;

import java.util.Date;

@Entity(tableName = "excursions")
public class Excursion {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "excursion_id")
    private int excursionId;

    @ColumnInfo(name = "title")
    @TypeConverters(Converters.class)
    private String title;

    @ColumnInfo(name = "date")
    @TypeConverters(Converters.class)
    private Date date;


    public void setVacationId(int vacationId) {
        this.vacationId = vacationId;
    }

    @ColumnInfo(name = "vacation_id")
    private int vacationId;

    public Excursion(int excursionId, String title,  Date date, int vacationId) {
        this.excursionId = excursionId;
        this.title = title;
        this.date = date;
        this.vacationId = vacationId;
    }

    public int getExcursionId() {
        return excursionId;
    }

    public void setExcursionId(int excursionId) {
        this.excursionId = excursionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getVacationId() {
        return vacationId;
    }
}