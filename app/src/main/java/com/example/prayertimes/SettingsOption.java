package com.example.prayertimes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.Objects;

public class SettingsOption extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_option);

        /* Back Button */

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        /*App bar config */

        Objects.requireNonNull(getSupportActionBar()).setTitle("Settings");

    }
}