package com.husyairi.therapia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DoctorHomepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_homepage);
        getSupportActionBar().hide();

    }
}