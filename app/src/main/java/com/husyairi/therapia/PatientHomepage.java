package com.husyairi.therapia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class PatientHomepage extends AppCompatActivity implements View.OnClickListener {

    private ImageView profile_icon_navbar;
    private ImageView booking_icon_navbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_homepage);

        profile_icon_navbar = findViewById(R.id.profile_icon_navbar);
        profile_icon_navbar.setOnClickListener(this);

        booking_icon_navbar = findViewById(R.id.booking_icon_navbar);
        booking_icon_navbar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;

        switch(view.getId()) {
            case R.id.profile_icon_navbar:
                intent = new Intent(this, CustomerProfile.class);
                break;

            case R.id.booking_icon_navbar:
                intent = new Intent(this, Booking.class);
                break;

            default:
                intent = null;
        }

        if(intent != null) {
            startActivity(intent);
        }
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}