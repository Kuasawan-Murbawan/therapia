package com.husyairi.therapia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectUser extends AppCompatActivity {

    Button patientBtn, doctorBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);

        patientBtn = findViewById(R.id.patient);
        doctorBtn = findViewById(R.id.doctor);

        patientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginPatient.class);
                startActivity(intent);
                finish();
            }
        });

        doctorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginDoctor.class);
                startActivity(intent);
                finish();
            }
        });

    }
}