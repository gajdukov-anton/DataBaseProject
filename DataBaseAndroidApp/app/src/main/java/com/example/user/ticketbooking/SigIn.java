package com.example.user.ticketbooking;

import android.app.DatePickerDialog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class SigIn extends AppCompatActivity {

    Calendar dateAndTime=Calendar.getInstance();
    TextView currentDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sig_in);
    }

    public void sigIn(View view) {
            this.finish();
    }

    private boolean checkDateOfBirth(String date) {
        return true;
    }
}
