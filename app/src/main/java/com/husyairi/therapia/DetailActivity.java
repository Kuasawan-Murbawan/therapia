package com.husyairi.therapia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    TextView detailDesc, detailTreatment, detailTime, detailDate;
    ImageView detailImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().hide();

        detailDate = findViewById(R.id.detailDate);
        detailDesc = findViewById(R.id.detailDesc);
        detailTreatment = findViewById(R.id.detailTreatment);
        detailImage = findViewById(R.id.detailImage);
        detailTime = findViewById(R.id.detailTime);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            detailDesc.setText(bundle.getString("Description"));
            detailTreatment.setText(bundle.getString("Treatment"));
            detailDate.setText(bundle.getString("Date"));
            detailTime.setText(bundle.getString("Time"));
            Glide.with(this).load(bundle.getString("Image")).into(detailImage);

        }
    }
}