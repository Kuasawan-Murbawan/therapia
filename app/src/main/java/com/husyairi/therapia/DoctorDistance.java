package com.husyairi.therapia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DoctorDistance extends AppCompatActivity {

    EditText doctorLocation;
    TextView patientLocation;

    Button calculateDistanceBtn, backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_distance);
        getSupportActionBar().hide();

        Bundle bundle = getIntent().getExtras();
        String userDestination = bundle.getString("Location");


        doctorLocation = findViewById(R.id.doctorLocation);
        patientLocation = findViewById(R.id.patientLocation);
        backBtn = findViewById(R.id.backButton);

        patientLocation.setText(userDestination);

        calculateDistanceBtn = findViewById(R.id.showMap);

        calculateDistanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userLocation = doctorLocation.getText().toString();


                if(userLocation.equals("") || userDestination.equals("")){
                    Toast.makeText(DoctorDistance.this, "Please enter location", Toast.LENGTH_SHORT).show();
                }
                else{
                    getDirections(userLocation, userDestination);
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DoctorHomepage.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void getDirections(String userLocation, String userDestination) {

        try {
            Uri uri = Uri.parse("https://www.google.com/maps/dir/" + userLocation + "/" + userDestination);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }catch (ActivityNotFoundException e){
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

    }


}