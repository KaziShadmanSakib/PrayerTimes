package com.example.prayertimes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.Objects;

public class QiblaDirectionOption extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qibla_direction_option);

        /* Back Button */

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        /*App bar config */

        Objects.requireNonNull(getSupportActionBar()).setTitle("Qibla Direction");

    }
}