package com.example.prayertimes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckedTextView;

import java.util.Objects;


public class CalendarDateLog extends AppCompatActivity {
    DatabaseHandler databaseHandler;
    String date;
    CheckedTextView[] allPrayersCheckedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_date_log);

        /*App bar config */

        Objects.requireNonNull(getSupportActionBar()).setTitle("Prayer Times");

        /* Back Button */

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //getDate from LogActivity
        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null)
        {
            date =(String) bundle.get("clickedDate");
            date = formattedDate(date);

        }


        databaseHandler = new DatabaseHandler(this);

        //set checkbox ids
        createCheckBoxId();

        //create checkbox
        if(databaseHandler.getContact(date)._date == null){

            checkBox(true,date);

        }
        else{
            checkBox(false,date);

        }

        //change checkbox on Click and update DB
        for(int i = 0; i < 5; i++ ){
            int finalI = i;
            allPrayersCheckedList[i].setOnClickListener(view -> {
                if(allPrayersCheckedList[finalI].isChecked()){
                    databaseHandler.updateDatabase(date, finalI,true);
                }
                else{
                    databaseHandler.updateDatabase(date, finalI,false);
                }
                allPrayersCheckedList[finalI].toggle();
            });
        }


    }


    private void createCheckBoxId() {
        allPrayersCheckedList = new CheckedTextView[5];
        allPrayersCheckedList[0] =  findViewById(R.id.checkedTextview1);
        allPrayersCheckedList[1] =  findViewById(R.id.checkedTextview2);
        allPrayersCheckedList[2] =  findViewById(R.id.checkedTextview3);
        allPrayersCheckedList[3] =  findViewById(R.id.checkedTextview4);
        allPrayersCheckedList[4] =  findViewById(R.id.checkedTextview5);
    }


    private String formattedDate(String date) {
        int intDate = Integer.parseInt(date);
        intDate -= 1;
        date = String.valueOf(intDate);
        return date;
    }


    private void checkBox(boolean isNull, String date){


        if(isNull){
            for(int i = 0; i < 5; i++ ){
                allPrayersCheckedList[i].setChecked(false);
            }

        }

        else{

            Contact contact = databaseHandler.getContact(date);
            allPrayersCheckedList[0].setChecked(contact._fajr);
            allPrayersCheckedList[1].setChecked(contact._dhuhr);
            allPrayersCheckedList[2].setChecked(contact._asar);
            allPrayersCheckedList[3].setChecked(contact._magrib);
            allPrayersCheckedList[4].setChecked(contact._isha);
        }
    }



}
