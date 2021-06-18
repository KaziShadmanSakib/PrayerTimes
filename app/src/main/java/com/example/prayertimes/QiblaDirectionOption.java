package com.example.prayertimes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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


        /* Setting Qibla Direction */

        ImageView compass = findViewById(R.id.imageCompass);
        ImageView needle = findViewById(R.id.needle);
        TextView heading = findViewById(R.id.heading);

        float longi = PrefConfig.loadLongitude(this);
        float lati = PrefConfig.loadLatitude(this);
        float alti = PrefConfig.loadAltitude(this);

        QiblaDirectionCompass qiblaDirectionCompass = new QiblaDirectionCompass(this, compass, needle, heading, longi, lati, alti);


    }

}