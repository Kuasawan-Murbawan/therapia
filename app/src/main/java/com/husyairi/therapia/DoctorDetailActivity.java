package com.husyairi.therapia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DoctorDetailActivity extends AppCompatActivity {

    TextView detailDesc, detailTreatment, detailTime, detailDate, detailUsername, detailLocation, detailPostingDate;
    ImageView detailImage;

    Button acceptJob, medHist, distanceBtn, completeBtn, generateReportBtn;

    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_detail);
        getSupportActionBar().hide();

        completeBtn = findViewById(R.id.completeButton);
        completeBtn.setVisibility(View.INVISIBLE);
        generateReportBtn = findViewById(R.id.generateReportButton);
        generateReportBtn.setVisibility(View.INVISIBLE);


        detailDate = findViewById(R.id.detailDate);
        detailDesc = findViewById(R.id.detailDesc);
        detailTreatment = findViewById(R.id.detailTreatment);
        detailImage = findViewById(R.id.detailImage);
        detailTime = findViewById(R.id.detailTime);
        detailUsername = findViewById(R.id.detail_username);
        detailLocation = findViewById(R.id.detailLocation);
        detailPostingDate = findViewById(R.id.postingDate);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            detailDesc.setText(bundle.getString("Description"));
            detailTreatment.setText(bundle.getString("Treatment"));
            detailDate.setText(bundle.getString("Date"));
            detailTime.setText(bundle.getString("Time"));
            detailUsername.setText(bundle.getString("Username"));
            detailLocation.setText(bundle.getString("Location"));
            detailPostingDate.setText(bundle.getString("Posting Date"));

            Glide.with(this).load(bundle.getString("Image")).into(detailImage);
        }

        medHist = findViewById(R.id.medical_history_button);
        medHist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MedicalHistory.class);
                startActivity(intent);
                finish();
            }
        });

        acceptJob = findViewById(R.id.accept_job_button);

        String treatment = bundle.getString("Treatment");
        String username = bundle.getString("Username");
        String sanitizedEmail = username + "@patient,com";

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();


        acceptJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DatabaseReference databaseReference = FirebaseDatabase.getInstance().
                        getReference(sanitizedEmail).child(treatment);

                databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            DataClass dataClass = task.getResult().getValue(DataClass.class);
                            if (dataClass != null) {
                                // Update the jobAccepted field
                                dataClass.setJobAccepted(user.getEmail());

                                // Update the value in the database
                                databaseReference.setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(DoctorDetailActivity.this, "Patient had been notified!", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), DoctorHomepage.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }

                                });
                            } else {
                                Toast.makeText(DoctorDetailActivity.this, "Data not found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Handle error retrieving data
                            Toast.makeText(DoctorDetailActivity.this, "Task is not successful :(", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        if(bundle.getString("hasAccept").equals("null")){

        } else if (bundle.getBoolean("hasComplete") == true) {
            completeBtn.setVisibility(View.GONE);
            acceptJob.setVisibility(View.GONE);
            generateReportBtn.setVisibility(View.VISIBLE);
        } else {
            acceptJob.setVisibility(View.GONE);
            completeBtn.setVisibility(View.VISIBLE);
        }

        distanceBtn = findViewById(R.id.distance_button);
        String location = bundle.getString("Location");
        distanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() ,DoctorDistance.class);
                intent.putExtra("Location", location);
                startActivity(intent);
                finish();
            }
        });

        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DatabaseReference databaseReference = FirebaseDatabase.getInstance().
                        getReference(sanitizedEmail).child(treatment);

                databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            DataClass dataClass = task.getResult().getValue(DataClass.class);
                            if (dataClass != null) {
                                // Update the jobAccepted field
                                dataClass.setHasComplete(true);

                                // Update the value in the database
                                databaseReference.setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(DoctorDetailActivity.this, "Session has completed", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), DoctorActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }

                                });
                            } else {
                                Toast.makeText(DoctorDetailActivity.this, "Data not found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Handle error retrieving data
                            Toast.makeText(DoctorDetailActivity.this, "Task is not successful :(", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }


}