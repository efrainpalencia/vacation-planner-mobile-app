package com.example.d308vacationplanner.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.d308vacationplanner.entities.Excursion;
import com.example.d308vacationplanner.entities.Vacation;

import java.util.List;

@Dao
public interface ExcursionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addExcursion(Excursion excursion);

    @Update
    void updateExcursion(Excursion excursion);

    @Delete
    void deleteExcursion(Excursion excursion);

    @Query("SELECT * FROM EXCURSIONS ORDER BY excursion_id ASC")
    List<Excursion> getAllExcursions();

    @Query("SELECT * FROM EXCURSIONS WHERE vacation_id=:vac ORDER BY excursion_id ASC")
    List<Excursion> getAssociatedExcursions(Long vac);
}
