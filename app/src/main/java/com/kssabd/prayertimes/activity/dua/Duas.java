package com.kssabd.prayertimes.activity.dua;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.kssabd.prayertimes.R;
import com.kssabd.prayertimes.activity.LogActivity;
import com.kssabd.prayertimes.activity.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
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
        ParentList.add("Mosque");
        ParentList.add("Home");
        ParentList.add("Sleeping");
        ParentList.add("Food");

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

    String[] mosqueDuaName = {

            "When going to the Mosque", "Upon Entering Mosque", "Upon Leaving Mosque"


    };

    String[] homeDuaName = {

            "When Leaving Home", "Upon Entering Home", "When going up the Stairs / Ascending a high place",
            "When coming down of Stairs / Descending from high place"

    };

    String[] sleepingDuaName = {

            "When you wake up", "Before sleeping"

    };

    String[] foodDuaName = {

            "When beginning a meal", "If one forgets to mention Allah at the beginning", "After finishing a meal",
            "When given a drink" , "Upon drinking milk", "When dining at someone's house", "For someone who offers you a meal",
            "When opening your fast", "For someone who provides you with Iftar"


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

            else if(HoldItem.equals("Mosque")){

                loadChild(mosqueDuaName);

            }

            else if(HoldItem.equals("Home")){

                loadChild(homeDuaName);

            }

            else if(HoldItem.equals("Sleeping")){

                loadChild(sleepingDuaName);

            }

            else if(HoldItem.equals("Food")){

                loadChild(foodDuaName);

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

                        changeIntent(4,0);
                        break;
                    case "On the sighting of the crescent moon":

                        changeIntent(5, 0);
                        break;

                    case "Talbiyah":

                        changeIntent(6, 0);
                        break;

                    case "Takbeer when passing the black stone":

                        changeIntent(7, 0);
                        break;

                    case "Upon reaching the Yemeni corner during Tawaf":

                        changeIntent(8, 0);
                        break;

                    case "When going to Mount Safa":

                        changeIntent(9, 0);
                        break;

                    case "When at Mount Safa and Mount Marwah":

                        changeIntent(10, 0);
                        break;


                    case "For reciting at Al-Mash'ar Al-Haraam (Muzdalifah)":

                        changeIntent(11, 0);
                        break;

                    case "When throwing stones at Jamaraat":

                        changeIntent(12, 0);
                        break;

                    case "At Arafat":

                        changeIntent(13, 0);
                        break;

                    case "When sacrificing an animal":

                        changeIntent(14, 0);
                        break;

                    case "When going to the Mosque":

                        changeIntent(15, 0);

                        break;

                    case "Upon Entering Mosque":

                        changeIntent(16, 17);

                        break;

                    case "Upon Leaving Mosque":

                        changeIntent(18, 19);

                        break;

                    case "When Leaving Home":

                        changeIntent(20, 21);

                        break;

                    case "Upon Entering Home":

                        changeIntent(22, 0);

                        break;

                    case "When going up the Stairs / Ascending a high place":

                        changeIntent(23, 0);

                        break;

                    case "When coming down of Stairs / Descending from high place":

                        changeIntent(24, 0);

                        break;

                    case "When you wake up":

                        changeIntent(25, 0);

                        break;

                    case "Before sleeping":

                        changeIntent(26, 0);

                        break;

                    case "When beginning a meal":

                        changeIntent(27, 0);

                        break;

                    case "If one forgets to mention Allah at the beginning":

                        changeIntent(28, 0);

                        break;

                    case "After finishing a meal":

                        changeIntent(29,0);

                        break;

                    case "When given a drink":

                        changeIntent(30, 0);

                        break;

                    case "Upon drinking milk":

                        changeIntent(31, 0);

                        break;

                    case "When dining at someone's house":

                        changeIntent(32, 0);

                        break;

                    case "For someone who offers you a meal":

                        changeIntent(33, 0);

                        break;

                }

                return true;
            }
        });


    }

    private void changeIntent(int i,int j) {
        Intent intent;
        intent = new Intent(Duas.this, SetDuaOfEachOption.class);
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
                if(item.getItemId()==R.id.log){
                    startActivity(new Intent(getApplicationContext(), LogActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                }
                else if(item.getItemId()==R.id.home){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                }
                else if(item.getItemId()==R.id.duas){

                    return true;
                }

                return false;
            }
        });
    }
}