package com.example.d308vacationplanner.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.d308vacationplanner.dao.ExcursionDao;
import com.example.d308vacationplanner.dao.VacationDao;
import com.example.d308vacationplanner.database.converters.Converters;
import com.example.d308vacationplanner.entities.Excursion;
import com.example.d308vacationplanner.entities.Vacation;

@Database(entities = {Vacation.class, Excursion.class}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class VacationDatabaseBuilder extends RoomDatabase {
    public abstract VacationDao vacationDao();
    public abstract ExcursionDao excursionDao();
    private static volatile VacationDatabaseBuilder INSTANCE;

    static VacationDatabaseBuilder getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (VacationDatabaseBuilder.class){
                if(INSTANCE==null){
                    INSTANCE= Room.databaseBuilder(context.getApplicationContext(),VacationDatabaseBuilder.class,"MyVacationDatabase.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
