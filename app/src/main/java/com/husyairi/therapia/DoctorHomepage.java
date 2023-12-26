package com.husyairi.therapia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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

public class DoctorHomepage extends AppCompatActivity implements View.OnClickListener {

    RecyclerView docRecyclerView;

    FirebaseAuth auth;

    ImageView profile_icon_navbar;

    List<DataClass> dataList = new ArrayList<>();

    DatabaseReference databaseReference;

    ValueEventListener eventListener;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_homepage);
        getSupportActionBar().hide();

        profile_icon_navbar = findViewById(R.id.profile_icon_navbar);
        profile_icon_navbar.setOnClickListener(this);

        docRecyclerView = findViewById(R.id.doctor_recyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(DoctorHomepage.this, 1);
        docRecyclerView.setLayoutManager(gridLayoutManager);

        AlertDialog.Builder builder = new AlertDialog.Builder(DoctorHomepage.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        auth = FirebaseAuth.getInstance();


        DoctorAdapter adapter = new DoctorAdapter(DoctorHomepage.this, dataList);
        docRecyclerView.setAdapter(adapter);

        List<String> userEmails = Arrays.asList("user1@patient.com", "user2@patient.com");
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        dataList.clear(); // Clear the list once before fetching data

        for (String userEmail : userEmails) {
            String sanitizedEmail = userEmail.replace('.', ',');
            DatabaseReference databaseReference = firebaseDatabase.getReference(sanitizedEmail);
            dialog.show();

            ValueEventListener eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                        DataClass dataClass = itemSnapshot.getValue(DataClass.class);
                        dataList.add(dataClass);
                    }
                    adapter.notifyDataSetChanged();
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
            case R.id.profile_icon_navbar:
                intent = new Intent(this, DoctorProfile.class);
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