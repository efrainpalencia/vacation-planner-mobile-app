package com.example.d308vacationplanner.UI;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308vacationplanner.R;
import com.example.d308vacationplanner.database.Repository;
import com.example.d308vacationplanner.entities.Excursion;
import com.example.d308vacationplanner.entities.Vacation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VacationList extends AppCompatActivity {
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacation_list);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(VacationList.this, VacationDetails.class);
            startActivity(intent);
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        repository = new Repository(getApplication());
        List<Vacation> checkVacationList = repository.getAllVacations();
        TextView emptyView = findViewById(R.id.empty_view);
        if (checkVacationList.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
            RecyclerView recyclerView = findViewById(R.id.vacationRecyclerView);
            recyclerView.setVisibility(View.GONE);
            repository = new Repository(getApplication());
            List<Vacation> allVacations = repository.getAllVacations();
            final VacationAdapter vacationAdapter = new VacationAdapter(this);
            recyclerView.setAdapter(vacationAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            vacationAdapter.setVacations(allVacations);

        } else {
            emptyView.setVisibility(View.GONE);
            RecyclerView recyclerView = findViewById(R.id.vacationRecyclerView);
            repository = new Repository(getApplication());
            List<Vacation> allVacations = repository.getAllVacations();
            final VacationAdapter vacationAdapter = new VacationAdapter(this);
            recyclerView.setAdapter(vacationAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            vacationAdapter.setVacations(allVacations);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_vacation_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
        if (item.getItemId() == R.id.item_01) {
            Repository repo = new Repository(getApplication());
            Vacation vacation = new Vacation(1, "Wedding Anniversary trip to Cap Cana", "The Sanctuary", createDate(2024, 7, 1), createDate(2024, 7, 7));
            repo.insert(vacation);
            Excursion excursion = new Excursion(1, "Snorkeling", createDate(2024, 7,3), 1);
            repo.insert(excursion);

            Intent intent = new Intent(VacationList.this, VacationList.class);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public Date createDate(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        Date date = calendar.getTime();

        return date;
    };

    @Override
    protected void onResume() {

        super.onResume();
        repository = new Repository(getApplication());
        List<Vacation> checkVacationList = repository.getAllVacations();
        TextView emptyView = findViewById(R.id.empty_view);
        if (checkVacationList.isEmpty()) {
            emptyView.setVisibility(View.VISIBLE);
            RecyclerView recyclerView = findViewById(R.id.vacationRecyclerView);
            recyclerView.setVisibility(View.GONE);
            repository = new Repository(getApplication());
            List<Vacation> allVacations = repository.getAllVacations();
            final VacationAdapter vacationAdapter = new VacationAdapter(this);
            recyclerView.setAdapter(vacationAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            vacationAdapter.setVacations(allVacations);

        } else {
            emptyView.setVisibility(View.GONE);
            RecyclerView recyclerView = findViewById(R.id.vacationRecyclerView);
            repository = new Repository(getApplication());
            List<Vacation> allVacations = repository.getAllVacations();
            final VacationAdapter vacationAdapter = new VacationAdapter(this);
            recyclerView.setAdapter(vacationAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            vacationAdapter.setVacations(allVacations);
        }

    }
}