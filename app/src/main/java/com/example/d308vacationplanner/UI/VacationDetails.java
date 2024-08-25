package com.example.d308vacationplanner.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

    String vacationTitle;
    String hotel;
    String oldStartDate;
    String oldEndDate;

    int vacationId;
    Vacation currentVacation;

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
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Excursion> filteredExcursions = new ArrayList<>();
        for (Excursion e : repository.getAllExcursions()) {
            if (e.getVacationId() == vacationId) filteredExcursions.add(e);
        }
        excursionAdapter.setExcursions(filteredExcursions);

        vacationTitle = getIntent().getStringExtra("title");
        editTitle = findViewById(R.id.title_text_input);
        editTitle.setText(vacationTitle);
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
        editStartDate.setOnClickListener(view -> {
            LocalDate date = LocalDate.now();
            String info = editStartDate.getText().toString();
            if (info.isEmpty()) {
                info = String.valueOf(date);
            }
            try {
                myCalenderStart.setTime(Objects.requireNonNull(sdf.parse(info)));
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }

            DatePickerDialog startDatePicker = new DatePickerDialog(VacationDetails.this, startDate, myCalenderStart.get(Calendar.YEAR), myCalenderStart.get(Calendar.MONTH), myCalenderStart.get(Calendar.DAY_OF_MONTH));
            startDatePicker.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
            startDatePicker.show();

        });
        startDate = (datePicker, year, month, dayOfMonth) -> {
            myCalenderStart.set(Calendar.YEAR, year);
            myCalenderStart.set(Calendar.MONTH, month);
            myCalenderStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabelStart();

        };

        editEndDate = findViewById(R.id.end_text_input);
        editEndDate.setOnClickListener(view -> {

          DatePickerDialog endDatePicker = new DatePickerDialog(VacationDetails.this, endDate, myCalenderEnd.get(Calendar.YEAR), myCalenderEnd.get(Calendar.MONTH), myCalenderEnd.get(Calendar.DAY_OF_MONTH));
            endDatePicker.getDatePicker().setMinDate(myCalenderStart.getTimeInMillis());
            endDatePicker.show();
        });
        endDate = (datePicker, year, month, dayOfMonth) -> {
            myCalenderEnd.set(Calendar.YEAR, year);
            myCalenderEnd.set(Calendar.MONTH, month);
            myCalenderEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelEnd();
        };
        vacationId = getIntent().getIntExtra("id", -1);

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
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }
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
                Toast.makeText(VacationDetails.this, vacation.getVacationTitle() + " was saved", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, VacationList.class);
                startActivity(intent);
            } else {
                vacation = new Vacation(vacationId, editTitle.getText().toString(), editHotel.getText().toString(), startDate, endDate);
                repository.update(vacation);
                Toast.makeText(VacationDetails.this, vacation.getVacationTitle() + " was updated", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, VacationList.class);
                startActivity(intent);
            }
            return true;
        }

        if (item.getItemId() == R.id.vacation_delete) {
            for (Vacation vac : repository.getAllVacations()) {
                if (vac.getVacationId() == vacationId) {
                    currentVacation = vac;
                    repository.delete(currentVacation);
                    Toast.makeText(VacationDetails.this, currentVacation.getVacationTitle() + " was deleted", Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(this, VacationList.class);
                    startActivity(intent);
                }
            }
            return true;
        }

        if (item.getItemId() == R.id.vacation_start_date_notify) {
            String dateFromScreen = editStartDate.getText().toString();
            String formatter = "M/d/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(formatter, Locale.US);
            Date myStartDate = null;

                try {
                    myStartDate = (sdf.parse(dateFromScreen));
                } catch (ParseException e) {
                    System.out.println(e.getMessage());
                }
            assert myStartDate != null;

            Long triggerStartDate = myStartDate.getTime();
            String message = String.format("Your %s vacation is starting", vacationTitle);

            Intent intent = new Intent(VacationDetails.this, AppReceiver.class);
            intent.putExtra("key", message);
            PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerStartDate, sender);

            Toast.makeText(VacationDetails.this, "You will be notified at the start of your " + vacationTitle + " vacation", Toast.LENGTH_LONG).show();

            return true;
        }

        if (item.getItemId() == R.id.vacation_end_date_notify) {
            String dateFromScreen = editEndDate.getText().toString();
            String formatter = "M/d/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(formatter, Locale.US);
            Date myEndDate = null;

            try {
                myEndDate = (sdf.parse(dateFromScreen));
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }
            assert myEndDate != null;

            Long triggerEndDate = myEndDate.getTime();
            String message = String.format("Your %s vacation is ending", vacationTitle);
            Intent intent = new Intent(VacationDetails.this, AppReceiver.class);
            intent.putExtra("key", message);
            PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerEndDate, sender);

            Toast.makeText(VacationDetails.this, "You will be notified at the end of your " + vacationTitle + " vacation", Toast.LENGTH_LONG).show();

            return true;
        }

        if (item.getItemId() == R.id.vacation_share) {
            SimpleDateFormat originalFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
            SimpleDateFormat newFormat = new SimpleDateFormat("EEE MMM dd yyyy", Locale.US);
            String newStartDate = null;
            String newEndDate = null;
            try {
                Date startDate = originalFormat.parse(oldStartDate);
                Date endDate = originalFormat.parse(oldEndDate);
                assert startDate != null;
                assert endDate != null;
                newStartDate = newFormat.format(startDate);
                newEndDate = newFormat.format(endDate);
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }
            String message = String.format("Hi! Check out this message from Tripfest - Vacation Planner:\nVacation Details\nDestination:\n %s\n\nHotel Accommodations:\n%s\n\nStart date:\n%s\n\nEnd Date:\n%s\n\n", vacationTitle, hotel, newStartDate, newEndDate);

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, message);
            sendIntent.putExtra(Intent.EXTRA_TITLE, "Text Message from Tripfest");
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);

            return true;

        }

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

//        Toast.makeText(VacationDetails.this,"refresh list",Toast.LENGTH_LONG).show();
    }
}