package com.example.d308vacationplanner.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
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
    private String excursionTitle;

    @ColumnInfo(name = "date")
    @TypeConverters(Converters.class)
    private Date date;


    public void setVacationId(int vacationId) {
        this.vacationId = vacationId;
    }

    @ColumnInfo(name = "vacation_id")
    private int vacationId;

    public Excursion(int excursionId, String excursionTitle, Date date, int vacationId) {
        this.excursionId = excursionId;
        this.excursionTitle = excursionTitle;
        this.date = date;
        this.vacationId = vacationId;
    }

    public int getExcursionId() {
        return excursionId;
    }

    public void setExcursionId(int excursionId) {
        this.excursionId = excursionId;
    }

    public String getExcursionTitle() {
        return excursionTitle;
    }

    public void setExcursionTitle(String excursionTitle) {
        this.excursionTitle = excursionTitle;
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