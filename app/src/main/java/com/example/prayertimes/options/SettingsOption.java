package com.example.prayertimes.options;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import com.example.prayertimes.R;

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

        if(findViewById(R.id.frameLayout)!=null){
            if(savedInstanceState!=null)return;
            else
            {
                getFragmentManager().beginTransaction().add(R.id.frameLayout,new SettingsFragment()).commit();
            }
        }


    }

    /* Going to Internet Settings */

    public void onClickInternetSettings(View view){

        startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));


    }

    /* Going to Location Settings */

    public void onClickLocationSettings(View view){

        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));


    }

}