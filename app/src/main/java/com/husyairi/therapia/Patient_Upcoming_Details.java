package com.husyairi.therapia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Patient_Upcoming_Details extends AppCompatActivity {

    TextView detailDesc, detailTreatment, detailTime, detailDate, detailDoctor;
    ImageView detailImage;

    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_upcoming_details);
        getSupportActionBar().hide();

        detailDate = findViewById(R.id.detailDate);
        detailDesc = findViewById(R.id.detailDesc);
        detailTreatment = findViewById(R.id.detailTreatment);
        detailImage = findViewById(R.id.detailImage);
        detailTime = findViewById(R.id.detailTime);
        detailDoctor = findViewById(R.id.detail_doctor);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            detailDesc.setText(bundle.getString("Description"));
            detailTreatment.setText(bundle.getString("Treatment"));
            detailDate.setText(bundle.getString("Date"));
            detailTime.setText(bundle.getString("Time"));
            detailDoctor.setText(bundle.getString("Doctor"));

            Glide.with(this).load(bundle.getString("Image")).into(detailImage);
        }
    }
}