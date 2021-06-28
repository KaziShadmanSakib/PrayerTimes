package com.example.prayertimes.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.example.prayertimes.R;
import com.example.prayertimes.activity.dua.Duas;
import com.example.prayertimes.database.CalendarDateCount;
import com.example.prayertimes.database.Contact;
import com.example.prayertimes.database.DatabaseHandler;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class LogActivity extends AppCompatActivity {

    private DatabaseHandler databaseHandler;
    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        setBottomNavigation();



        /*App bar config */
        Objects.requireNonNull(getSupportActionBar()).setTitle("Log");




        //openDB
        databaseHandler = new DatabaseHandler(this);

        //create calender View with dots
        calendarView = findViewById(R.id.calendarView);


            new Thread(() -> {

               try {
                   showDots();
               }
               catch (Exception e){
                   e.printStackTrace();
               }
            }).start();



        //new window on Clicking date
        calendarView.setOnDayClickListener(eventDay -> {
            Calendar clickedDayCalendar = eventDay.getCalendar();
            String clickedDate = calendarToString(clickedDayCalendar);
            goToCalendarDateLog(clickedDate);

        });



    }

    private void setBottomNavigation() {
        /* Initialize and Assign Variable for Bottom Navigation */

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        /* Set Log Selected */

        bottomNavigationView.setSelectedItemId(R.id.log);

        /* Perform Item Selected Listener */

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if(item.getItemId() == R.id.log){

                return  true;

            }

            else if(item.getItemId() == R.id.home){

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(0,0);
                return true;

            }

            else if(item.getItemId() == R.id.duas){

                startActivity(new Intent(getApplicationContext(), Duas.class));
                overridePendingTransition(0,0);
                return  true;

            }

            else {

                return  false;

            }
        });
    }

    private void goToCalendarDateLog(String date){
        Intent i = new Intent(this, CalendarDateLog.class);
        i.putExtra("clickedDate",date);
        startActivity(i);
    }

    private String calendarToString(Calendar cal){


        cal.add(Calendar.DATE, 1);

        Date date = cal.getTime();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");

        String inActiveDate = null;

        try {

            inActiveDate = format1.format(date);



        } catch (Exception e1) {



            e1.printStackTrace();

        }
        return inActiveDate;
    }


    private void showDots(){

        List<Contact> allContact =  databaseHandler.getAllContacts();
        List<CalendarDateCount>allCalendarDatesCount = new ArrayList<>();
        List<EventDay> events = new ArrayList<>();
        int count;


        //taking all data from the list and create a date/count list

        for(Contact contact : allContact){

            count = prayerCount(contact);

            String stringDate = stringToFormattedString(contact.getDate());
            Calendar calendar = stringToCalendar(stringDate);

            CalendarDateCount calendarDateCount = new CalendarDateCount(calendar,count);
            allCalendarDatesCount.add(calendarDateCount);
        }

        //put the dots according to count

        for(CalendarDateCount calendarDateCount : allCalendarDatesCount){
            int prayerCount = calendarDateCount.get_count();
            if(prayerCount == 1){
                events.add(new EventDay(calendarDateCount.get_calendar(), R.drawable.one_dot));
            }
            else if(prayerCount == 2){
                events.add(new EventDay(calendarDateCount.get_calendar(), R.drawable.two_dots));
            }
            else if(prayerCount == 3){
                events.add(new EventDay(calendarDateCount.get_calendar(), R.drawable.three_dots));
            }
            else if(prayerCount == 4){
                events.add(new EventDay(calendarDateCount.get_calendar(), R.drawable.four_dots));
            }
            else if(prayerCount == 5){
                events.add(new EventDay(calendarDateCount.get_calendar(), R.drawable.five_dots));
            }


        }
        calendarView.setEvents(events);
    }



    private int prayerCount(Contact contact) {
        int count=0;
        if(contact.getFajr())count++;
        if(contact.getDhuhr())count++;
        if(contact.getAsar())count++;
        if(contact.getMagrib())count++;
        if(contact.getIsha())count++;
        return count;
    }

    private Calendar stringToCalendar(String stringDate) {

        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = formatter.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        assert date != null;
        calendar.setTime(date);
        return calendar;
    }

    private String stringToFormattedString(String date) {
        int intDate = Integer.parseInt(date);
        int day = intDate%100;
        intDate = intDate/100;
        int month = intDate%100;
        intDate = intDate/100;
        int year = intDate;
        return day+"-"+month+"-"+year;
    }










}

