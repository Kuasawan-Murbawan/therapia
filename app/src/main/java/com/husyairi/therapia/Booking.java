package com.husyairi.therapia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Booking extends AppCompatActivity implements View.OnClickListener{
    private ImageView home_icon_navbar;

    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        home_icon_navbar = findViewById(R.id.home_icon_navbar);
        home_icon_navbar.setOnClickListener(this);

        fab = findViewById(R.id.addpost);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Booking.this, UploadPosting.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent;

        switch(view.getId()){

            case R.id.home_icon_navbar:
                intent = new Intent(this, PatientHomepage.class);
                break;

            default:
                intent = null;
        }

        if(intent != null){
            startActivity(intent);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}