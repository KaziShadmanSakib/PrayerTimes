package com.example.prayertimes.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckedTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prayertimes.database.Contact;
import com.example.prayertimes.database.DatabaseHandler;
import com.example.prayertimes.datetime.PrayerTimeInMiliSecond;
import com.example.prayertimes.R;
import com.example.prayertimes.options.PrefConfig;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


public class CalendarDateLog extends AppCompatActivity {
    DatabaseHandler databaseHandler;
    String date;
    int currentDate;
    int clickedDate;
    CheckedTextView[] allPrayersCheckedList;
    TextView dateTextView;
    int currentMiliSec;

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
            clickedDate = Integer.valueOf(date);

            date = modifiedDate(date);

        }



        dateTextView = findViewById(R.id.datetextView);
        dateTextView.setText("Selected Date : " + formattedDate(date));


        databaseHandler = new DatabaseHandler(this);
        currentDateSet();

        //set checkbox ids
        createCheckBoxId();
        if(currentDate+1==clickedDate){
            Log.i("uga",String.valueOf(PrefConfig.loadCurrentPrayerIndex(this)-1));

            allPrayersCheckedList[PrefConfig.loadCurrentPrayerIndex(this)-1].setBackgroundResource(R.drawable.selected_rect_shape);


        }

        //create checkbox
        if(databaseHandler.getContact(date).getDate() == null){

            checkBox(true,date);

        }
        else{
            checkBox(false,date);

        }


        //change checkbox on Click and update DB
        for(int i = 0; i < 5; i++ ){
            int finalI = i;
            allPrayersCheckedList[i].setOnClickListener(view -> {

                if((currentDate-clickedDate)>99){
                    if(PrefConfig.loadLogAccessPref(this)==0){
                        new AlertDialog.Builder(this)
                                .setTitle("Are You Sure")
                                .setMessage("You can stop the warning from settings")
                                .setCancelable(false)
                                .setPositiveButton("Yes", (dialog, which) -> {

                                    updateChecked(finalI);

                                }).setNegativeButton("No", null)
                                .show();
                    }


                    else{
                        updateChecked(finalI);
                    }
                }

                else if(currentDate+1>clickedDate){

                    updateChecked(finalI);

                }



                else if(currentDate+1==clickedDate){
                    setCOnditionForCurrentDate(finalI);
                }



                else{
                    Toast toast = Toast.makeText(this,"Wait for the day to come",Toast.LENGTH_SHORT);
                    toast.show();
                }


            });
        }


    }

    private void setCOnditionForCurrentDate(int finalI) {
        int[] prayerTime = getData();
        if(currentMiliSec>prayerTime[finalI]){
            updateChecked(finalI);
        }
        else{
            Toast toast = Toast.makeText(this,"Prayer Waqt hasn't started",Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    private void updateChecked(int finalI) {
        if(allPrayersCheckedList[finalI].isChecked()){
            databaseHandler.updateCell(date, finalI,true);
        }
        else{
            databaseHandler.updateCell(date, finalI,false);
        }
        allPrayersCheckedList[finalI].toggle();
    }

    private void currentDateSet() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String date = simpleDateFormat.format(calendar.getTime());
        currentDate = Integer.parseInt(date);
    }


    private void createCheckBoxId() {
        allPrayersCheckedList = new CheckedTextView[5];
        allPrayersCheckedList[0] =  findViewById(R.id.checkedTextview1);
        allPrayersCheckedList[1] =  findViewById(R.id.checkedTextview2);
        allPrayersCheckedList[2] =  findViewById(R.id.checkedTextview3);
        allPrayersCheckedList[3] =  findViewById(R.id.checkedTextview4);
        allPrayersCheckedList[4] =  findViewById(R.id.checkedTextview5);
    }


    private String modifiedDate(String date) {
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
            allPrayersCheckedList[0].setChecked(contact.getFajr());
            allPrayersCheckedList[1].setChecked(contact.getDhuhr());
            allPrayersCheckedList[2].setChecked(contact.getAsar());
            allPrayersCheckedList[3].setChecked(contact.getMagrib());
            allPrayersCheckedList[4].setChecked(contact.getIsha());
        }
    }


    public String formattedDate(String s){
        int intDate = Integer.parseInt(s);
        int day = intDate%100;
        intDate = intDate/100;
        int month = intDate%100;
        intDate = intDate/100;
        int year = intDate;
        return day+"/"+month+"/"+year;
    }
    private int[] getData() {

        int[] prayerMiliSec = new int[5];
        PrayerTimeInMiliSecond prayerTimeToMiliSecond = new PrayerTimeInMiliSecond(this);
        prayerTimeToMiliSecond.toMiliSec();

        Date currentTime = Calendar.getInstance().getTime();
        currentMiliSec = prayerTimeToMiliSecond.getCurrentTimeInMiliSec(currentTime);
        prayerMiliSec[0]=prayerTimeToMiliSecond.getFajrInMili();
        prayerMiliSec[1]=prayerTimeToMiliSecond.getDhuhrInMili();
        prayerMiliSec[2]=prayerTimeToMiliSecond.getAsarInMili();
        prayerMiliSec[3]=prayerTimeToMiliSecond.getMagribInMili();
        prayerMiliSec[4]=prayerTimeToMiliSecond.getIshaInMili();
        return prayerMiliSec;



    }


}
