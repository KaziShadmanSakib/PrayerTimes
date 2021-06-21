package com.example.prayertimes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Duas extends AppCompatActivity {

    List<String> ChildList;
    Map<String, List<String>> ParentListDuas;
    ExpandableListView expandableListView;

    List<String> ParentList = new ArrayList<>();
    {

        ParentList.add("Ramadan");
        ParentList.add("Hajj / Umrah");

    }

    String[] ramadanDuaName = {

            "When opening your fast", "For the night of destiny (Lailatul Qadr)",
            "For someone who provides you with Iftar", "On the sighting of the crescent moon"

    };

    String[] hajjUmrahDuaName = {

            "Talbiyah", "Takbeer when passing the black stone",
            "Upon reaching the Yemeni corner during Tawaf", "When going to Mount Safa",
            "When at Mount Safa and Mount Marwah", "For reciting at Al-Mash'ar Al-Haraam (Muzdalifah)",
            "When throwing stones at Jamaraat", "At Arafat", "When sacrificing an animal"

    };

    String[] defaultName = {

            "PrayerTimes"

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duas);



        setBottomNavigation();

        /*App bar config */
        getSupportActionBar().setTitle("Duas");

        /* Setting Duas */

        setDuas();


    }

    private void setDuas() {

        ParentListDuas = new LinkedHashMap<>();

        for(String HoldItem : ParentList){

            if(HoldItem.equals("Ramadan")){

                loadChild(ramadanDuaName);

            }

            else if(HoldItem.equals("Hajj / Umrah")){

                loadChild(hajjUmrahDuaName);

            }

            else {

                loadChild(defaultName);

            }

            ParentListDuas.put(HoldItem, ChildList);

        }

        expandableListView = findViewById(R.id.expandListView);

        final ExpandableListAdapter expandableListAdapter = new ListAdapter(this, ParentList, ParentListDuas);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                final String selected = (String) expandableListAdapter.getChild(groupPosition, childPosition);



                switch (selected){

                    case "When opening your fast":

                        changeIntent(1,2);
                        break;
                    case "For the night of destiny (Lailatul Qadr)":

                        changeIntent(3,0);
                        break;
                    case "For someone who provides you with Iftar":

                        changeIntent(4,5);
                        break;


                }

                return true;
            }
        });


    }

    private void changeIntent(int i,int j) {
        Intent intent;
        intent = new Intent(Duas.this, RamadanActivity_01.class);
        intent.putExtra("index",i);
        intent.putExtra("index2",j);
        startActivity(intent);

    }

    private void loadChild(String[] ParentElementName) {

        ChildList = new ArrayList<>();
        Collections.addAll(ChildList, ParentElementName);


    }


    private void setBottomNavigation() {
        /* Initialize and Assign Variable for Bottom Navigation */

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        /* Set Dua Selected */

        bottomNavigationView.setSelectedItemId(R.id.duas);

        /* Perform Item Selected Listener */

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.log:
                        startActivity(new Intent(getApplicationContext(), LogActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.duas:
                        return true;
                }
                return false;
            }
        });
    }
}