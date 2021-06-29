package com.kssabd.prayertimes.options;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.kssabd.prayertimes.R;

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


}