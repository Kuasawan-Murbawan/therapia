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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DoctorDetailActivity extends AppCompatActivity {

    TextView detailDesc, detailTreatment, detailTime, detailDate, detailUsername;
    ImageView detailImage;

    Button acceptJob, medHist;

    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_detail);
        getSupportActionBar().hide();

        detailDate = findViewById(R.id.detailDate);
        detailDesc = findViewById(R.id.detailDesc);
        detailTreatment = findViewById(R.id.detailTreatment);
        detailImage = findViewById(R.id.detailImage);
        detailTime = findViewById(R.id.detailTime);
        detailUsername = findViewById(R.id.detail_username);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            detailDesc.setText(bundle.getString("Description"));
            detailTreatment.setText(bundle.getString("Treatment"));
            detailDate.setText(bundle.getString("Date"));
            detailTime.setText(bundle.getString("Time"));
            detailUsername.setText(bundle.getString("Username"));

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

        acceptJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String treatment = bundle.getString("Treatment");
                String username = bundle.getString("Username");
                String sanitizedEmail = username + "@patient,com";

                auth = FirebaseAuth.getInstance();
                user = auth.getCurrentUser();


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