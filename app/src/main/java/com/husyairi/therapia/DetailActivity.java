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
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class DetailActivity extends AppCompatActivity {

    TextView detailDesc, detailTreatment, detailTime, detailDate, detailPostingDate, detailDoctor;
    ImageView detailImage;

    FirebaseUser user;
    FirebaseAuth auth;

    Button editBtn, deleteBtn, reportBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().hide();

        reportBtn = findViewById(R.id.reportButton);
        reportBtn.setVisibility(View.INVISIBLE);

        detailDate = findViewById(R.id.detailDate);
        detailDesc = findViewById(R.id.detailDesc);
        detailTreatment = findViewById(R.id.detailTreatment);
        detailImage = findViewById(R.id.detailImage);
        detailTime = findViewById(R.id.detailTime);
        detailPostingDate = findViewById(R.id.postingDate);
        detailDoctor = findViewById(R.id.detailDoctor);
        deleteBtn = findViewById(R.id.deleteBtn);
        editBtn = findViewById(R.id.editBtn);


        Bundle bundle = getIntent().getExtras();

        if (bundle.getBoolean("hasComplete")) {
            editBtn.setVisibility(View.GONE);
            deleteBtn.setVisibility(View.GONE);
            reportBtn.setVisibility(View.VISIBLE);
        }

        if (bundle != null) {
            detailDesc.setText(bundle.getString("Description"));
            detailTreatment.setText(bundle.getString("Treatment"));
            detailDate.setText(bundle.getString("Date"));
            detailTime.setText(bundle.getString("Time"));
            detailPostingDate.setText(bundle.getString("PostingDate"));
            detailDoctor.setText(bundle.getString("DoctorName"));
            Glide.with(this).load(bundle.getString("Image")).into(detailImage);

        }

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        String sanitizedEmail = user.getEmail().replace('.', ',');
        String treatment = bundle.getString("Treatment");

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePost(sanitizedEmail, treatment);
            }
        });



        String doctorName = detailDoctor.getText().toString();

        detailDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (doctorName.equals("null")) {
                    Toast.makeText(DetailActivity.this, "Wating for a doctor..", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), DoctorReference.class);
                    intent.putExtra("Doctor", doctorName);
                    startActivity(intent);
                    finish();
                }
            }
        });



    }

    private void deletePost(String sanitizedEmail, String treatment) {

        FirebaseDatabase.getInstance().getReference(sanitizedEmail).child(treatment).
                removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(DetailActivity.this, "Post deleted", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), Booking.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(DetailActivity.this, "Error deleting post", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}