package com.husyairi.therapia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import java.util.List;

public class PatientActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView home_icon_navbar, profile_icon_navbar, booking_icon_navbar;
    RecyclerView recview_upcoming, recview_history;

    List<DataClass> upcomingAppointments = new ArrayList<>();
    List<DataClass> historyAppointments = new ArrayList<>();

    DatabaseReference databaseReference;

    ValueEventListener eventListener;

    FirebaseUser user;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        getSupportActionBar().hide();

        home_icon_navbar = findViewById(R.id.home_icon_navbar);
        home_icon_navbar.setOnClickListener(this);

        profile_icon_navbar = findViewById(R.id.profile_icon_navbar);
        profile_icon_navbar.setOnClickListener(this);

        booking_icon_navbar = findViewById(R.id.booking_icon_navbar);
        booking_icon_navbar.setOnClickListener(this);

        GridLayoutManager gridLayoutManagerUpcoming = new GridLayoutManager(PatientActivity.this, 1);
        recview_upcoming = findViewById(R.id.recview_upcoming);
        recview_upcoming.setLayoutManager(gridLayoutManagerUpcoming);

        AlertDialog.Builder builder = new AlertDialog.Builder(PatientActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        String sanitizedEmail = user.getEmail().replace('.', ',');

        MyAdapter adapterUpcoming = new MyAdapter(PatientActivity.this, upcomingAppointments);
        recview_upcoming.setAdapter(adapterUpcoming);
        databaseReference = FirebaseDatabase.getInstance().getReference(sanitizedEmail);

        GridLayoutManager gridLayoutManagerHistory = new GridLayoutManager(PatientActivity.this, 1);
        recview_history = findViewById(R.id.recview_history);
        recview_history.setLayoutManager(gridLayoutManagerHistory);
        MyAdapter adapterHistory = new MyAdapter(PatientActivity.this, historyAppointments);
        recview_history.setAdapter(adapterHistory);

        dialog.show();
        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                upcomingAppointments.clear();
                historyAppointments.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    DataClass dataClass = itemSnapshot.getValue(DataClass.class);
                    if ("null".equals(dataClass.getJobAccepted()) == false && !dataClass.getHasComplete()) { // Filter for accepted jobs
                        upcomingAppointments.add(dataClass);
                    } else if (dataClass.getHasComplete()) {
                        historyAppointments.add(dataClass);
                    } else {
                        Log.i("null", "This jobAccepted is null");
                    }
                }
                adapterUpcoming.notifyDataSetChanged();
                adapterHistory.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                dialog.dismiss();
            }
        });

        }

    @Override
    public void onClick(View view) {
        Intent intent;

        switch(view.getId()){

            case R.id.home_icon_navbar:
                intent = new Intent(getApplicationContext(), PatientHomepage.class);
                break;

            case R.id.profile_icon_navbar:
                intent = new Intent(getApplicationContext(), PatientProfile.class);
                break;

            case R.id.booking_icon_navbar:
                intent = new Intent(getApplicationContext(), Booking.class);
                break;

            default:
                intent = null;
        }

        if(intent != null){
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

}