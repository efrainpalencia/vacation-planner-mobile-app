package com.example.d308vacationplanner.UI;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class VacationDetails extends AppCompatActivity {
    Repository repository;

    String title;
    String hotel;
    String oldStartDate;
    String oldEndDate;
    int vacationId;
    EditText editTitle;
    EditText editHotel;
    TextView editStartDate;
    TextView editEndDate;

    DatePickerDialog.OnDateSetListener startDate;
    final Calendar myCalenderStart = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener endDate;
    final Calendar myCalenderEnd = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacation_details);
        title = getIntent().getStringExtra("title");
        editTitle = findViewById(R.id.title_text_input);
        editTitle.setText(title);
        hotel = getIntent().getStringExtra("hotel");
        editHotel = findViewById(R.id.hotel_text_input);
        editHotel.setText(hotel);


        oldStartDate = getIntent().getStringExtra("startDate");
        if (oldStartDate != null) {
            SimpleDateFormat originalFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
            SimpleDateFormat targetFormat = new SimpleDateFormat("M/d/yyyy", Locale.US);

            try {
                Date date = originalFormat.parse(oldStartDate);
                assert date != null;
                String formattedDate = targetFormat.format(date);
                editStartDate = findViewById(R.id.start_text_input);
                editStartDate.setText(formattedDate);
//                System.out.println("Formatted Date: " + formattedDate);
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }
        }

        oldEndDate = getIntent().getStringExtra("endDate");
        if (oldEndDate != null) {
            SimpleDateFormat originalFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
            SimpleDateFormat targetFormat = new SimpleDateFormat("M/d/yyyy", Locale.US);

            try {
                Date date = originalFormat.parse(oldEndDate);
                assert date != null;
                String formattedDate = targetFormat.format(date);
                editEndDate = findViewById(R.id.end_text_input);
                editEndDate.setText(formattedDate);
//                System.out.println("Formatted Date: " + formattedDate);
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy", Locale.US);
        editStartDate = findViewById(R.id.start_text_input);
        editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalDate date = LocalDate.now();
                String info = editStartDate.getText().toString();
                if (info.isEmpty()) {info = String.valueOf(date);}
                try {
                    myCalenderStart.setTime(Objects.requireNonNull(sdf.parse(info)));
                } catch (ParseException e) {
                    System.out.println(e.getMessage());
                }

                new DatePickerDialog(VacationDetails.this, startDate, myCalenderStart.get(Calendar.YEAR), myCalenderStart.get(Calendar.MONTH), myCalenderStart.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        startDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                myCalenderStart.set(Calendar.YEAR, year);
                myCalenderStart.set(Calendar.MONTH, month);
                myCalenderStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();
            }
        };

        editEndDate = findViewById(R.id.end_text_input);
        editEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalDate date = LocalDate.now();
                String info = editEndDate.getText().toString();
                if (info.isEmpty()) {info = String.valueOf(date);}

                try {
                    myCalenderEnd.setTime(Objects.requireNonNull(sdf.parse(info)));
                } catch (ParseException e) {
                    System.out.println(e.getMessage());
                }

                new DatePickerDialog(VacationDetails.this, endDate, myCalenderEnd.get(Calendar.YEAR), myCalenderEnd.get(Calendar.MONTH), myCalenderEnd.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        endDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                myCalenderEnd.set(Calendar.YEAR, year);
                myCalenderEnd.set(Calendar.MONTH, month);
                myCalenderEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEnd();
            }
        };
        vacationId = getIntent().getIntExtra("id", -1);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(VacationDetails.this, ExcursionDetails.class);
            intent.putExtra("vacId", vacationId);
            startActivity(intent);


        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerView = findViewById(R.id.excursionRecyclerView);
        repository = new Repository(getApplication());
        List<Excursion> allExcursions = repository.getAllExcursions();
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        excursionAdapter.setExcursions(allExcursions);
        List<Excursion> filteredExcursions = new ArrayList<>();
        for (Excursion e : repository.getAllExcursions()) {
            if (e.getVacationId() == vacationId) filteredExcursions.add(e);
        }
        excursionAdapter.setExcursions(filteredExcursions);
    }

    private void updateLabelStart() {
        String formatter = "M/d/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(formatter, Locale.US);

        editStartDate.setText(sdf.format(myCalenderStart.getTime()));

    }

    private void updateLabelEnd() {
        String formatter = "M/d/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(formatter, Locale.US);

        editEndDate.setText(sdf.format(myCalenderEnd.getTime()));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            this.finish();
            return true;}
        if (item.getItemId() == R.id.vacation_save) {
            Vacation vacation;
            SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy", Locale.US);

            Date startDate = null;
            Date endDate = null;

            try {
                // Convert TextView text to Date
                startDate = sdf.parse(editStartDate.getText().toString());
                System.out.println(startDate);
                endDate = sdf.parse(editEndDate.getText().toString());
                System.out.println(endDate);
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }

            if (vacationId == -1) {
                if (repository.getAllVacations().isEmpty()) {
                    vacationId = 1;
                } else {
                    vacationId = repository.getAllVacations().get(repository.getAllVacations().size() - 1).getVacationId() + 1;
                }
                vacation = new Vacation(vacationId, editTitle.getText().toString(), editHotel.getText().toString(), startDate, endDate);
                repository.insert(vacation);
            } else {
                vacation = new Vacation(vacationId, editTitle.getText().toString(), editHotel.getText().toString(), startDate, endDate);
                repository.update(vacation);
            }
            return true;
        }

//        if (item.getItemId() == R.id.vacation_delete) {
//            Vacation vacation;
//
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {

        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.excursionRecyclerView);
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursion> filteredExcursions = new ArrayList<>();
        for (Excursion e : repository.getAllExcursions()) {
            if (e.getVacationId() == vacationId) filteredExcursions.add(e);
        }
        excursionAdapter.setExcursions(filteredExcursions);

        //Toast.makeText(ProductDetails.this,"refresh list",Toast.LENGTH_LONG).show();
    }
}