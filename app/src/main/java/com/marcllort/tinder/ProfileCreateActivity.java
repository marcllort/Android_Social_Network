package com.marcllort.tinder;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class ProfileCreateActivity extends Activity {

    private EditText dateText;
    private EditText mapText;
    private FloatingActionButton saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        dateSelector();
        saveButton();

    }

    private void saveButton() {

        saveButton = findViewById(R.id.btn_save);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void mapSelector() {
        mapText = findViewById(R.id.et_location);
        mapText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    private void dateSelector() {                                                                           // Selector de data amb datepicker, i posteriorment la adapta a un edittext
        final Calendar myCalendar = Calendar.getInstance(Locale.US);
        dateText = (EditText) findViewById(R.id.birth);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

                dateText.setText(sdf.format(myCalendar.getTime()));
            }

        };

        dateText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println("nadfklsaffsafas");
                new DatePickerDialog(ProfileCreateActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }



}
