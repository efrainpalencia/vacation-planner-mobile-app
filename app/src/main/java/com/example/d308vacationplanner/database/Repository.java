package com.example.d308vacationplanner.database;

import android.app.Application;

import com.example.d308vacationplanner.dao.ExcursionDao;
import com.example.d308vacationplanner.dao.VacationDao;
import com.example.d308vacationplanner.entities.Excursion;
import com.example.d308vacationplanner.entities.Vacation;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {

    private VacationDao mVacationDao;
    private ExcursionDao mExcursionDao;

    private List<Vacation> mAllVacations;
    private List<Excursion> mAllExcursions;

    private static int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public Repository(Application application) {
        VacationDatabaseBuilder db = VacationDatabaseBuilder.getDatabase(application);
        mVacationDao = db.vacationDao();
        mExcursionDao = db.excursionDao();
    }

    public List<Vacation>getAllVacations() {
        databaseExecutor.execute(() -> {
            mAllVacations = mVacationDao.getAllVacations();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return mAllVacations;
    }

    public void insert(Vacation vacation) {
        databaseExecutor.execute(() -> {
            mVacationDao.addVacation(vacation);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Vacation vacation) {
        databaseExecutor.execute(() -> {
            mVacationDao.updateVacation(vacation);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Vacation vacation) {
        databaseExecutor.execute(() -> {
            mVacationDao.deleteVacation(vacation);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Excursion>getAssociatedExcursions(int excursionId) {
        databaseExecutor.execute(() -> {
            mAllExcursions = mExcursionDao.getAssociatedExcursions(excursionId);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return mAllExcursions;
    }

    public List<Excursion>getAllExcursions() {
        databaseExecutor.execute(() -> {
            mAllExcursions = mExcursionDao.getAllExcursions();
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return mAllExcursions;
    }

    public void insert(Excursion excursion) {
        databaseExecutor.execute(() -> {
            mExcursionDao.addExcursion(excursion);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Excursion excursion) {
        databaseExecutor.execute(() -> {
            mExcursionDao.updateExcursion(excursion);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(Excursion excursion) {
        databaseExecutor.execute(() -> {
            mExcursionDao.deleteExcursion(excursion);
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
