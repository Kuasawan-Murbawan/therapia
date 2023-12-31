package com.husyairi.therapia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DoctorActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView homepage_icon_navbar, profile_icon_navbar;

    RecyclerView doc_recview_upcoming;

    List<DataClass> dataListUpcoming = new ArrayList<>();

    DatabaseReference databaseReference;

    ValueEventListener eventListener;

    FirebaseUser user;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        getSupportActionBar().hide();

        homepage_icon_navbar = findViewById(R.id.home_icon_navbar);
        homepage_icon_navbar.setOnClickListener(this);

        profile_icon_navbar = findViewById(R.id.profile_icon_navbar);
        profile_icon_navbar.setOnClickListener(this);

        GridLayoutManager gridLayoutManagerUpcom = new GridLayoutManager(DoctorActivity.this, 1);
        doc_recview_upcoming = findViewById(R.id.doc_recview_upcoming);
        doc_recview_upcoming.setLayoutManager(gridLayoutManagerUpcom);

        AlertDialog.Builder builder = new AlertDialog.Builder(DoctorActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        String doctorEmail = user.getEmail();

        DoctorAdapter adapterUpcoming = new DoctorAdapter(DoctorActivity.this, dataListUpcoming);
        doc_recview_upcoming.setAdapter(adapterUpcoming);

        List<String> userEmails = Arrays.asList("user1@patient.com", "user2@patient.com");

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(); // Add this line to initialize databaseReference
        dataListUpcoming.clear();
        dialog.show();

        for (String userEmail : userEmails) {
            String sanitizedEmail = userEmail.replace('.', ',');
            DatabaseReference databaseReference = firebaseDatabase.getReference(sanitizedEmail);
            dialog.show();

            ValueEventListener eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                        DataClass dataClass = itemSnapshot.getValue(DataClass.class);
                        if (doctorEmail.equals(dataClass.getJobAccepted())) {
                            dataListUpcoming.add(dataClass);
                        }
                    }
                    adapterUpcoming.notifyDataSetChanged();
                    dialog.dismiss();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle database error here
                }
            });
        }

    }

    @Override
    public void onClick(View view) {
        Intent intent;

        switch(view.getId()){
            case R.id.home_icon_navbar:
                intent = new Intent(getApplicationContext(), DoctorHomepage.class);
                break;

            case R.id.profile_icon_navbar:
                intent = new Intent(getApplicationContext(), DoctorProfile.class);
                break;

            default:
                intent = null;
        }
        if (intent != null){
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}