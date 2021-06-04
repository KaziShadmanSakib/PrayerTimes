package com.example.prayertimes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Initialize and Assign Variable for Bottom Navigation */

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        /* Set Home Selected */

        bottomNavigationView.setSelectedItemId(R.id.home);

        /* Perform Item Selected Listener */

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.log:
                        startActivity(new Intent(getApplicationContext(), Log.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        return true;
                    case R.id.duas:
                        startActivity(new Intent(getApplicationContext(), Duas.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        /*App bar config */
        getSupportActionBar().setTitle("Home");
    }
}