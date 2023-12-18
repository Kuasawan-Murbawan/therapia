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

public class CustomerProfile extends AppCompatActivity implements View.OnClickListener {

    private ImageView home_icon_navbar, booking_icon_navbar;
    FirebaseAuth auth;
    Button logoutButton;
    FirebaseUser user;

    TextView textEmail, username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);
        getSupportActionBar().hide();

        home_icon_navbar = findViewById(R.id.home_icon_navbar);
        home_icon_navbar.setOnClickListener(this);

        booking_icon_navbar = findViewById(R.id.booking_icon_navbar);
        booking_icon_navbar.setOnClickListener(this);

        username = findViewById(R.id.username);

        auth = FirebaseAuth.getInstance();
        logoutButton = findViewById(R.id.logout_button);
        user = auth.getCurrentUser();
        textEmail = findViewById(R.id.email);

        if(user == null){
            Intent intent = new Intent(getApplicationContext(), LoginPatient.class);
            startActivity(intent);
            finish();
        }
        else{
            textEmail.setText(user.getEmail());
            username.setText(user.getEmail().split("@")[0]);
        }

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Landing.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void onClick(View view){
        Intent intent;

        switch(view.getId()){

            case R.id.home_icon_navbar:
                intent = new Intent(this, PatientHomepage.class);
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