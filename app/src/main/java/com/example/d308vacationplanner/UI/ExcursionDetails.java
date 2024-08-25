package com.example.d308vacationplanner.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.d308vacationplanner.R;
import com.example.d308vacationplanner.database.Repository;
import com.example.d308vacationplanner.entities.Excursion;
import com.example.d308vacationplanner.entities.Vacation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class ExcursionDetails extends AppCompatActivity {
    Repository repository;
    Excursion currentExcursion;

    int vacId;
    int excId;

    String excursionTitle;
    String oldDate;

    String editMinDate;
    String editMaxDate;
    Date formattedMinDate;
    Date formattedMaxDate;

    EditText editExcursionTitle;
    TextView editExcursionDate;

    DatePickerDialog.OnDateSetListener excursionDate;
    Calendar calendarMin;
    Calendar calendarMax;
    final Calendar myCalendarDate = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_excursion_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        repository = new Repository(getApplication());
        excursionTitle = getIntent().getStringExtra("excursionTitle");
        editExcursionTitle = findViewById(R.id.excursion_title_text_input);
        editExcursionTitle.setText(excursionTitle);
        excId = getIntent().getIntExtra("id", -1);
        vacId = getIntent().getIntExtra("vacId", -1);
        System.out.println(vacId);

        editExcursionDate = findViewById(R.id.excursion_date);
        String formatter = "M/d/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(formatter, Locale.US);

        ArrayList<Vacation> vacationArrayList = new ArrayList<>(repository.getAllVacations());
        ArrayList<Integer> vacationIdList = new ArrayList<>();
        for (Vacation vacation : vacationArrayList) {
            vacationIdList.add(vacation.getVacationId());
        }
        ArrayAdapter<Integer> vacationIdAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, vacationIdList);
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(vacationIdAdapter);

        // Exposes the start and end date, creates a Calender instance for DatePicker min and max
        for (Vacation vacation : vacationArrayList) {
            if (vacation.getVacationId() == vacId) {
                editMinDate = String.valueOf(vacation.getStartDate());
                editMaxDate = String.valueOf(vacation.getEndDate());
                SimpleDateFormat originalFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
                try {
                    // formats Date
                    Date dateMin = originalFormat.parse(editMinDate);
                    assert dateMin != null;

                    // creates Calender instance
                    calendarMin = Calendar.getInstance();
                    calendarMin.setTime(dateMin);

                    // formats date
                    Date dateMax = originalFormat.parse(editMaxDate);
                    assert dateMax != null;

                    // create Calender instance
                    calendarMax = Calendar.getInstance();
                    calendarMax.setTime(dateMax);


                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                System.out.printf("Min date: %s\nMax date: %s\n", calendarMin, calendarMax);

            }

        }

        oldDate = getIntent().getStringExtra("date");
        if (oldDate != null) {
            SimpleDateFormat originalFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
            SimpleDateFormat targetFormat = new SimpleDateFormat("M/d/yyyy", Locale.US);

            try {
                Date date = originalFormat.parse(oldDate);
                assert date != null;
                String formattedDate = targetFormat.format(date);
                editExcursionDate = findViewById(R.id.excursion_date);
                editExcursionDate.setText(formattedDate);
                System.out.println("Formatted Date: " + formattedDate);
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }
        }

        editExcursionDate = findViewById(R.id.excursion_date);
        editExcursionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalDate date = LocalDate.now();
                String info = editExcursionDate.getText().toString();
                if (info.isEmpty()) {
                    info = String.valueOf(calendarMin.getTimeInMillis());
                }
                try {
                    myCalendarDate.setTime(Objects.requireNonNull(sdf.parse(info)));
                } catch (ParseException e) {
                    System.out.println(e.getMessage());
                }


                DatePickerDialog startDatePicker = new DatePickerDialog(ExcursionDetails.this, excursionDate, myCalendarDate.get(Calendar.YEAR), myCalendarDate.get(Calendar.MONTH), myCalendarDate.get(Calendar.DAY_OF_MONTH));
                startDatePicker.getDatePicker().setMinDate(calendarMin.getTimeInMillis());
                startDatePicker.getDatePicker().setMaxDate(calendarMax.getTimeInMillis());
                startDatePicker.show();

            }
        });

        excursionDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                myCalendarDate.set(Calendar.YEAR, year);
                myCalendarDate.set(Calendar.MONTH, month);
                myCalendarDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();

            }
        };
    }

    private void updateLabel() {
        String formatter = "M/d/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(formatter, Locale.US);

        editExcursionDate.setText(sdf.format(myCalendarDate.getTime()));

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursion_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
            return true;
        }

        if (item.getItemId() == R.id.excursion_save) {
            Excursion excursion;
            SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy", Locale.US);
            Date excursionDate = null;

            try {
                // Convert TextView text to Date
                excursionDate = sdf.parse(editExcursionDate.getText().toString());
//                System.out.println("excursion date: " + excursionDate);
            } catch (ParseException e) {
                System.out.println(e.getMessage());
            }

            if (excId == -1) {
                if (repository.getAllExcursions().isEmpty()) {
                    excId = 1;
                    excursion = new Excursion(excId, editExcursionTitle.getText().toString(), excursionDate, vacId);
                    repository.insert(excursion);

                    Toast.makeText(ExcursionDetails.this, excursion.getExcursionTitle() + " was updated", Toast.LENGTH_LONG).show();
                    Intent intentOnResume = new Intent(this, VacationDetails.class);
                    startActivity(intentOnResume);
                    return true;
                } else {
                    excId = repository.getAllExcursions().get(repository.getAllExcursions().size() - 1).getExcursionId() + 1;
                    excursion = new Excursion(excId, editExcursionTitle.getText().toString(), excursionDate, vacId);
                    repository.insert(excursion);

                    Toast.makeText(ExcursionDetails.this, excursion.getExcursionTitle() + " was saved", Toast.LENGTH_LONG).show();
                    Intent intentOnResume = new Intent(this, VacationDetails.class);
                    startActivity(intentOnResume);
                    return true;
                }
            } else {
                excursion = new Excursion(excId, editExcursionTitle.getText().toString(), excursionDate, vacId);
                repository.update(excursion);

                Toast.makeText(ExcursionDetails.this, excursion.getExcursionTitle() + " was updated", Toast.LENGTH_LONG).show();
                Intent intentOnResume = new Intent(this, VacationDetails.class);
                startActivity(intentOnResume);
                return true;
            }
        }

        if (item.getItemId() == R.id.excursion_delete) {
            for (Excursion exc : repository.getAllExcursions()) {
                if (exc.getExcursionId() == excId) {
                    currentExcursion = exc;
                    repository.delete(currentExcursion);
                    Toast.makeText(ExcursionDetails.this, currentExcursion.getExcursionTitle() + " was deleted", Toast.LENGTH_LONG).show();
                }
            }
            Intent intentOnResume = new Intent(this, VacationDetails.class);
            startActivity(intentOnResume);
        }

        if (item.getItemId() == R.id.excursion_notify) {
            String dateFromScreen = editExcursionDate.getText().toString();
            String formatter = "M/d/yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(formatter, Locale.US);
            Date myDate = null;
            try {
                myDate = (sdf.parse(dateFromScreen));
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            assert myDate != null;

            Long triggerDate = myDate.getTime();
            String message = String.format("Your %s excursion is starting", excursionTitle);

            Intent intent = new Intent(ExcursionDetails.this, AppReceiver.class);
            intent.putExtra("key", message);
            PendingIntent sender = PendingIntent.getBroadcast(ExcursionDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerDate, sender);

            Intent intentOnResume = new Intent(this, VacationDetails.class);
            startActivity(intentOnResume);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
