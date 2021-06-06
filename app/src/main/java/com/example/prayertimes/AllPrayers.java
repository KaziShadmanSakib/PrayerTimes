package com.example.prayertimes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AllPrayers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_prayers);

        /*App bar config */
        getSupportActionBar().setTitle("All Prayers");

        /* Back Button */
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}