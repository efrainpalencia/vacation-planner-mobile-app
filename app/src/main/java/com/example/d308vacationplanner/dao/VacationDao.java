package com.example.d308vacationplanner.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.d308vacationplanner.entities.Vacation;

import java.util.List;

@Dao
public interface VacationDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addVacation(Vacation vacation);

    @Update
    void updateVacation(Vacation vacation);

    @Delete
    void deleteVacation(Vacation vacation);

    @Query("SELECT * FROM vacations ORDER BY vacation_id ASC")
    List<Vacation> getAllVacations();
}
