package com.example.d308vacationplanner.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.d308vacationplanner.database.converters.Converters;

import java.util.Date;

@Entity(tableName = "vacations")
public class Vacation {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "vacation_id")
    private int vacationId;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "hotel")
    private String hotel;

    @ColumnInfo(name = "start_date")
    @TypeConverters(Converters.class)
    private Date startDate;

    @ColumnInfo(name = "end_date")
    @TypeConverters(Converters.class)
    private Date endDate;

    public Vacation(int vacationId, @NonNull String title, String hotel, Date startDate, Date endDate) {
        this.vacationId = vacationId;
        this.title = title;
        this.hotel = hotel;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getVacationId() {
        return vacationId;
    }

    public void setVacationId(int vacationId) {
        this.vacationId = vacationId;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
