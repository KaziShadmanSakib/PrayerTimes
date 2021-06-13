package com.example.prayertimes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LogActivity extends AppCompatActivity {

    private DatabaseHandler databaseHandler;
    Button backMain;
    CalendarView calendarView;
    CheckedTextView checkedTextView;
    CheckedTextView allPrayersCheckedList[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        /* Initialize and Assign Variable for Bottom Navigation */

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        /* Set Home Selected */

        bottomNavigationView.setSelectedItemId(R.id.log);

        /* Perform Item Selected Listener */

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.log:
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.duas:
                        startActivity(new Intent(getApplicationContext(), Duas.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });






        //openDB
        databaseHandler = new DatabaseHandler(this);

        calendarView = (CalendarView)findViewById(R.id.calendarView);

        showDots();

        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();
                String clickedDate = calendartoString(clickedDayCalendar);
                changeToCalendarDateLog(clickedDate);

            }
        });





    }
    private void changeToCalendarDateLog(String date){
        Intent i = new Intent(this,CalendarDateLog.class);
        i.putExtra("clickedDate",date);
        startActivity(i);
    }

    private String calendartoString(Calendar cal){


        cal.add(Calendar.DATE, 1);

        Date date = cal.getTime();

        SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMdd");

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
            Calendar calendar = stringtoCalendar(stringDate);

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

    private Calendar stringtoCalendar(String stringDate) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = formatter.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
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
        String finalDate = day+"-"+month+"-"+year;
        return  finalDate;
    }




}
