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
import android.widget.RelativeLayout;

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

    RelativeLayout homepage_icon_navbar, profile_icon_navbar, activity_icon_navbar;

    RecyclerView doc_recview_upcoming, recview_history;

    List<DataClass> dataListUpcoming = new ArrayList<>();
    List<DataClass> dataListHistory = new ArrayList<>();

    DatabaseReference databaseReference;

    ValueEventListener eventListener;

    FirebaseUser user;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        getSupportActionBar().hide();

        homepage_icon_navbar = findViewById(R.id.home_layout);
        homepage_icon_navbar.setOnClickListener(this);

        profile_icon_navbar = findViewById(R.id.profile_layout);
        profile_icon_navbar.setOnClickListener(this);

        activity_icon_navbar = findViewById(R.id.activity_layout);
        activity_icon_navbar.setOnClickListener(this);

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

        GridLayoutManager gridLayoutManagerHistory = new GridLayoutManager(DoctorActivity.this, 1);
        recview_history = findViewById(R.id.recview_history);
        recview_history.setLayoutManager(gridLayoutManagerHistory);
        DoctorAdapter adapterHistory = new DoctorAdapter(DoctorActivity.this, dataListHistory);
        recview_history.setAdapter(adapterHistory);

        List<String> userEmails = Arrays.asList("user1@patient,com", "user2@patient,com", "user3@patient,com");

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(); // Add this line to initialize databaseReference
        dialog.show();

        dataListUpcoming.clear();
        dataListHistory.clear();
        adapterUpcoming.notifyDataSetChanged();
        adapterHistory.notifyDataSetChanged();

        for (String userEmail : userEmails) {
            DatabaseReference databaseReference = firebaseDatabase.getReference(userEmail);
            dialog.show();

            ValueEventListener eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot)
                {

                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                        DataClass dataClass = itemSnapshot.getValue(DataClass.class);
                        if (doctorEmail.equals(dataClass.getJobAccepted())){
                            if (dataClass.getHasComplete()==false) { // Filter for accepted jobs
                                dataListUpcoming.add(dataClass);
                            } else if (dataClass.getHasComplete()) {
                                dataListHistory.add(dataClass);
                            } else {
                                Log.i("null", "This jobAccepted is null");
                            }
                        }
                    }
                    adapterUpcoming.notifyDataSetChanged();
                    adapterHistory.notifyDataSetChanged();
                    dialog.dismiss();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle database error here
                }
            });
        }

        adapterUpcoming.notifyDataSetChanged();
        adapterHistory.notifyDataSetChanged();

    }

    @Override
    public void onClick(View view) {
        Intent intent;

        switch(view.getId()){
            case R.id.home_layout:
                intent = new Intent(getApplicationContext(), DoctorHomepage.class);
                break;

            case R.id.profile_layout:
                intent = new Intent(getApplicationContext(), DoctorProfile.class);
                break;

            case R.id.activity_layout:
                    intent = new Intent(getApplicationContext(), DoctorActivity.class);
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