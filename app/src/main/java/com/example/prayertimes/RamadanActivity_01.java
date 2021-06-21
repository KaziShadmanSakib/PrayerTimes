package com.example.prayertimes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class RamadanActivity_01 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ramadan01);


        /*App bar config */

        getSupportActionBar().setTitle("Ramadan");

        /* Back Button */

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }
}

