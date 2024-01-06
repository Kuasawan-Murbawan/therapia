package com.husyairi.therapia;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.media.ImageReader;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class UploadPosting extends AppCompatActivity {

    ImageView uploadImage;
    Button saveButton;
    EditText uploadDesc, uploadLocation;
    Spinner chooseTreatment;
    Uri uri;

    DatePicker uploadDate;

    TimePicker uploadTime;
    FirebaseUser user;
    FirebaseAuth auth;


    String imageURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_posting);
        getSupportActionBar().hide();

        FirebaseApp.initializeApp(this);


        uploadImage = findViewById(R.id.uploadImage);
        saveButton = findViewById(R.id.saveButton);
        uploadDesc = findViewById(R.id.uploadDesc);
        uploadLocation = findViewById(R.id.uploadLoc);
        chooseTreatment = findViewById(R.id.chooseTreatment);
        uploadDate = findViewById(R.id.uploadDate);
        uploadTime = findViewById(R.id.uploadTime);


        // disable dates before today
        Calendar today = Calendar.getInstance();
        long now = today.getTimeInMillis();
        uploadDate.setMinDate(now);


        // Prompt user to pick a local image
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(  // create a button to start the activity
                new ActivityResultContracts.StartActivityForResult(),  // specify what kind of activity to start (photo picker)
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            Intent data = result.getData();
                            uri = data.getData();   // get the photo and store it inside uri
                            uploadImage.setImageURI(uri);
                        }else{
                            Toast.makeText(UploadPosting.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

    }

    public void saveData(){
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Android Images")
                .child(uri.getLastPathSegment());

        AlertDialog.Builder builder = new AlertDialog.Builder(UploadPosting.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isComplete());
                Uri urlImage = uriTask.getResult();
                imageURL = urlImage.toString();
                uploadData();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });

    }

    public void uploadData() {
        String treatment = chooseTreatment.getSelectedItem().toString();
        String desc = uploadDesc.getText().toString();
        String location = uploadLocation.getText().toString();

        // For Date & Time
        int day = uploadDate.getDayOfMonth();
        int month = uploadDate.getMonth() + 1;
        int year = uploadDate.getYear();

        int hour = uploadTime.getHour();
        int minute = uploadTime.getMinute();

        // Create a formatted date and time string
        String formattedDate = String.format("%02d/%02d/%04d", month, day, year);
        String formattedTime = String.format("%02d:%02d", hour, minute);

        String jobAccepted = "null";

        LocalDate currentDate = null;
        String formattedCurrDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentDate = LocalDate.now();
            formattedCurrDate = currentDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        }

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        String sanitizedEmail = user.getEmail().replace('.', ',');

        String username = sanitizedEmail.split("@")[0];

        Boolean hasComplete = false;

        DataClass dataClass = new DataClass(username, treatment, desc, location, imageURL, formattedDate, formattedTime, jobAccepted, formattedCurrDate, hasComplete);

        FirebaseDatabase.getInstance().getReference(sanitizedEmail).child(treatment)
                .setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UploadPosting.this, "Noice, Saved!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UploadPosting.this, e.getMessage().toString()
                                , Toast.LENGTH_SHORT).show();
                    }
                });
    }

}






