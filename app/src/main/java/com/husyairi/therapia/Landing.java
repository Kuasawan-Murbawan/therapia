package com.husyairi.therapia;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Landing extends AppCompatActivity implements View.OnClickListener {

    private ImageView therapiaLogo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        therapiaLogo = findViewById(R.id.therapia_logo);
        therapiaLogo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}
