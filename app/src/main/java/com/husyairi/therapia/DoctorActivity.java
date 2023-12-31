package com.husyairi.therapia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class DoctorActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView homepage_icon_navbar, profile_icon_navbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        getSupportActionBar().hide();

        homepage_icon_navbar = findViewById(R.id.home_icon_navbar);
        homepage_icon_navbar.setOnClickListener(this);

        profile_icon_navbar = findViewById(R.id.profile_icon_navbar);
        profile_icon_navbar.setOnClickListener(this);
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