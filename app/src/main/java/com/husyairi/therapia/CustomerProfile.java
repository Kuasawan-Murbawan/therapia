package com.husyairi.therapia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class CustomerProfile extends AppCompatActivity implements View.OnClickListener {

    private ImageView home_icon_navbar;
    FirebaseAuth auth;
    Button logoutButton;
    FirebaseUser user;

    TextView textEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);

        home_icon_navbar = findViewById(R.id.home_icon_navbar);
        home_icon_navbar.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
        logoutButton = findViewById(R.id.logout_button);
        user = auth.getCurrentUser();
        textEmail = findViewById(R.id.email);

        if(user == null){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
        else{
            textEmail.setText(user.getEmail());
        }

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
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