package com.example.prayertimes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckedTextView;

public class CalendarDateLog extends AppCompatActivity {
    DatabaseHandler databaseHandler;
    String date;
    CheckedTextView allPrayersCheckedList[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_date_log);

        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle!=null)
        {
            date =(String) bundle.get("clickedDate");
            date = formattedDate(date);

        }
        databaseHandler = new DatabaseHandler(this);

        if(databaseHandler.getContact(date)._date == null){

            checkBox(true,date);

        }
        else{
            checkBox(false,date);


        }
    }

    private String formattedDate(String date) {
        int intDate = Integer.parseInt(date);
        intDate -= 1;
        date = String.valueOf(intDate);
        return date;
    }


    private void checkBox(boolean isNull, String date){

        allPrayersCheckedList = new CheckedTextView[5];
        allPrayersCheckedList[0] = (CheckedTextView) findViewById(R.id.checkedTextview1);
        allPrayersCheckedList[1] = (CheckedTextView) findViewById(R.id.checkedTextview2);
        allPrayersCheckedList[2] = (CheckedTextView) findViewById(R.id.checkedTextview3);
        allPrayersCheckedList[3] = (CheckedTextView) findViewById(R.id.checkedTextview4);
        allPrayersCheckedList[4] = (CheckedTextView) findViewById(R.id.checkedTextview5);


        if(isNull==true){
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