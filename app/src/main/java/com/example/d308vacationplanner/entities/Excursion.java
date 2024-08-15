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
    private Long excursionId;

    @ColumnInfo(name = "date")
    @TypeConverters(Converters.class)
    private Date date;


    public void setVacationId(Long vacationId) {
        this.vacationId = vacationId;
    }

    @ColumnInfo(name = "vacation_id")
    private Long vacationId;

    public Excursion(Long excursionId, Date date, Long vacationId) {
        this.excursionId = excursionId;
        this.date = date;
        this.vacationId = vacationId;
    }

    public Long getExcursionId() {
        return excursionId;
    }

    public void setExcursionId(Long excursionId) {
        this.excursionId = excursionId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getVacationId() {
        return vacationId;
    }
}