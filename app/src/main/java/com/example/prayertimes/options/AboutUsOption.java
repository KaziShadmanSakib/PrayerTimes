package com.example.prayertimes.options;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.prayertimes.R;

import java.util.Objects;

public class AboutUsOption extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us_option);

        /* Back Button */

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);


        /* App bar config */

        Objects.requireNonNull(getSupportActionBar()).setTitle("About Us");

    }
}