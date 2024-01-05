package com.husyairi.therapia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class DoctorDistance extends AppCompatActivity {

    EditText doctorLocation;
    TextView patientLocation;

    ImageView currentLocBtn, clearBtn;

    Button calculateDistanceBtn, backBtn;

    private FusedLocationProviderClient fusedLocationProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_distance);
        getSupportActionBar().hide();

        Bundle bundle = getIntent().getExtras();
        String userDestination = bundle.getString("Location");

        doctorLocation = findViewById(R.id.doctorLocation);
        patientLocation = findViewById(R.id.patientLocation);
        backBtn = findViewById(R.id.backButton);

        currentLocBtn = findViewById(R.id.currentLocButton);
        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(this);

        currentLocBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(DoctorDistance.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getCurrentLocation();
                } else {
                    ActivityCompat.requestPermissions(DoctorDistance.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });

        patientLocation.setText(userDestination);

        calculateDistanceBtn = findViewById(R.id.showMap);

        calculateDistanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userLocation = doctorLocation.getText().toString();


                if (userLocation.equals("") || userDestination.equals("")) {
                    Toast.makeText(DoctorDistance.this, "Please enter location", Toast.LENGTH_SHORT).show();
                } else {
                    getDirections(userLocation, userDestination);
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DoctorHomepage.class);
                startActivity(intent);
                finish();
            }
        });

        clearBtn = findViewById(R.id.clearButton);

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doctorLocation.setText(null);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with the call
                // ... (the code on line 109, if applicable)
            } else {
                // Permission denied, handle accordingly
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getCurrentLocation() {

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return;
        }
        fusedLocationProvider.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    String userLocation = null;
                    userLocation = String.format("%f,%f", latitude, longitude);
                    doctorLocation.setText(userLocation);
                } else {
                    Toast.makeText(DoctorDistance.this, "Current location not found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getDirections(String userLocation, String userDestination) {

        try {
            Uri uri = Uri.parse("https://www.google.com/maps/dir/" + userLocation + "/" + userDestination);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }catch (ActivityNotFoundException e){
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

    }


}