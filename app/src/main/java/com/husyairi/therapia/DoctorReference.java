package com.husyairi.therapia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DoctorReference extends AppCompatActivity {

    TextView textEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_reference);
        getSupportActionBar().hide();

        Bundle bundle = getIntent().getExtras();

        textEmail = findViewById(R.id.doctor_email);

        textEmail.setText(bundle.getString("Doctor"));
    }
}