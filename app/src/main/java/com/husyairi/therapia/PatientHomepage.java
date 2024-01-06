package com.husyairi.therapia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PatientHomepage extends AppCompatActivity implements View.OnClickListener {


    private TextView username;

    RelativeLayout upload_posting_layout, upcoming_qa_layout, profile_qa_layout, chat_qa_layout,  profile_icon_navbar, booking_icon_navbar, activity_icon_navbar;
    FirebaseUser user;
    FirebaseAuth auth;

    private ViewPager2 viewPager;
    private ImageCarouselAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_homepage);
        getSupportActionBar().hide();

        checkAuthorization();

        profile_icon_navbar = findViewById(R.id.profile_layout);
        profile_icon_navbar.setOnClickListener(this);

        booking_icon_navbar = findViewById(R.id.booking_layout);
        booking_icon_navbar.setOnClickListener(this);

        activity_icon_navbar = findViewById(R.id.activity_layout);
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

        upload_posting_layout = findViewById(R.id.upload_posting_qa_layout);
        upload_posting_layout.setOnClickListener(this);

        upcoming_qa_layout = findViewById(R.id.upcom_qa_layout);
        upcoming_qa_layout.setOnClickListener(this);

        profile_qa_layout = findViewById(R.id.profile_qa_layout);
        profile_qa_layout.setOnClickListener(this);

        chat_qa_layout = findViewById(R.id.chat_qa_layout);
        chat_qa_layout.setOnClickListener(this);


    }

    private void checkAuthorization() {

        String currentUserEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        // Allow access only for specified users
        if ("doctor1@doctor.com".equals(currentUserEmail) || "doctor2@doctor.com".equals(currentUserEmail)) {

            // Redirect to an appropriate activity or display a message
            Toast.makeText(PatientHomepage.this, "Please register!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Landing.class));
            finish();
            return;
        }

    }

    @Override
    public void onClick(View view) {
        Intent intent;

        switch(view.getId()) {
            case R.id.profile_layout:
                intent = new Intent(this, PatientProfile.class);
                break;

            case R.id.booking_layout:
                intent = new Intent(this, Booking.class);
                break;

            case R.id.activity_layout:
                intent = new Intent(this, PatientActivity.class);
                break;

            case R.id.upload_posting_qa_layout:
                intent = new Intent(getApplicationContext(),UploadPosting.class);
                break;

            case R.id.upcom_qa_layout:
                intent = new Intent(getApplicationContext(), PatientActivity.class);
                break;

            case R.id.profile_qa_layout:
                intent = new Intent(getApplicationContext(), PatientProfile.class);
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