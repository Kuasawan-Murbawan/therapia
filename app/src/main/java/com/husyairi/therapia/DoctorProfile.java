package com.husyairi.therapia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DoctorProfile extends AppCompatActivity implements View.OnClickListener {

    ImageView home_icon_navbar, activity_icon_navbar;
    Button logoutButton;
    FirebaseAuth auth;
    FirebaseUser user;
    TextView textEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_profile);
        getSupportActionBar().hide();

        logoutButton = findViewById(R.id.logout_button_doctor);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Landing.class);
                startActivity(intent);
                finish();
            }
        });

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        textEmail = findViewById(R.id.doctor_email);
        if(user == null){
            Intent intent = new Intent(getApplicationContext(), LoginDoctor.class);
            startActivity(intent);
            finish();
        }else{
            textEmail.setText(user.getEmail());
        }

        home_icon_navbar = findViewById(R.id.home_icon_navbar);
        home_icon_navbar.setOnClickListener(this);

        activity_icon_navbar = findViewById(R.id.activity_icon_navbar);
        activity_icon_navbar.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent;

        switch(view.getId()){

            case R.id.home_icon_navbar:
                intent = new Intent(this, DoctorHomepage.class);
                break;

            case R.id.activity_icon_navbar:
                    intent = new Intent(this, DoctorActivity.class);
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