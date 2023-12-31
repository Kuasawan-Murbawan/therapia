package com.husyairi.therapia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PatientHomepage extends AppCompatActivity implements View.OnClickListener {

    private ImageView profile_icon_navbar, booking_icon_navbar, activity_icon_navbar;

    private TextView username;

    FirebaseUser user;
    FirebaseAuth auth;

    private ViewPager2 viewPager;
    private ImageCarouselAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_homepage);
        getSupportActionBar().hide();


        profile_icon_navbar = findViewById(R.id.profile_icon_navbar);
        profile_icon_navbar.setOnClickListener(this);

        booking_icon_navbar = findViewById(R.id.booking_icon_navbar);
        booking_icon_navbar.setOnClickListener(this);

        activity_icon_navbar = findViewById(R.id.activity_icon_navbar);
        activity_icon_navbar.setOnClickListener(this);

        username = findViewById(R.id.username);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if(user == null){
            Intent intent = new Intent(getApplicationContext(), LoginPatient.class);
            startActivity(intent);
            finish();
        }else{
            username.setText(user.getEmail().split("@")[0]);
        }

        viewPager = findViewById(R.id.imageViewPager);
        imageAdapter = new ImageCarouselAdapter(this, new int[]{R.drawable.imagepromo1, R.drawable.image3});
        viewPager.setAdapter(imageAdapter);


    }

    @Override
    public void onClick(View view) {
        Intent intent;

        switch(view.getId()) {
            case R.id.profile_icon_navbar:
                intent = new Intent(this, PatientProfile.class);
                break;

            case R.id.booking_icon_navbar:
                intent = new Intent(this, Booking.class);
                break;

            case R.id.activity_icon_navbar:
                intent = new Intent(this, PatientActivity.class);
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