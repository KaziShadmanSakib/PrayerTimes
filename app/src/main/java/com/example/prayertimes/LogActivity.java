package com.example.prayertimes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

/*
        //calender popUp
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                month++;
                String monthString = intToString(month);
                String dayString = intToString(dayOfMonth);
                String date = year+monthString+dayString;
                Toast toast=Toast.makeText(getApplicationContext(),date,Toast.LENGTH_SHORT);
                toast.show();



                if(databaseHandler.getContact(date)._date == null){

                    doPopUp(true,date);

                }
                else{
                    doPopUp(false,date);


                }


            }
        });

        /*App bar config
        getSupportActionBar().setTitle("Log");
        */




    }
    private void showDots(){

        List<Contact> allContact =  databaseHandler.getAllContacts();
        List<CalendarDateCount>allCalendarDatesCount = new ArrayList<>();
        List<EventDay> events = new ArrayList<>();
        int count;


        for(Contact contact : allContact){

            count = prayerCount(contact);

            String stringDate = stringToFormattedString(contact.getDate());
            Calendar calendar = stringtoCalendar(stringDate);

            CalendarDateCount calendarDateCount = new CalendarDateCount(calendar,count);
            allCalendarDatesCount.add(calendarDateCount);
        }
        for(CalendarDateCount calendarDateCount : allCalendarDatesCount){
            int prayerCount = calendarDateCount._count;
            if(prayerCount == 1){
                events.add(new EventDay(calendarDateCount._calendar, R.drawable.one_dot));
            }
            else if(prayerCount == 2){
                events.add(new EventDay(calendarDateCount._calendar, R.drawable.two_dots));
            }
            else if(prayerCount == 3){
                events.add(new EventDay(calendarDateCount._calendar, R.drawable.three_dots));
            }
            else if(prayerCount == 4){
                events.add(new EventDay(calendarDateCount._calendar, R.drawable.four_dots));
            }
            else if(prayerCount == 5){
                events.add(new EventDay(calendarDateCount._calendar, R.drawable.five_dots));
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


    private void doPopUp(boolean isNull, String date){

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup, null);

        //using data from database to create the checkbox
        checkBox(popupView,isNull,date);


        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        View view = this.calendarView;
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }




    private void checkBox(View v, boolean isNull, String date){

        allPrayersCheckedList = new CheckedTextView[5];
        allPrayersCheckedList[0] = (CheckedTextView) v.findViewById(R.id.checkedTextview1);
        allPrayersCheckedList[1] = (CheckedTextView) v.findViewById(R.id.checkedTextview2);
        allPrayersCheckedList[2] = (CheckedTextView) v.findViewById(R.id.checkedTextview3);
        allPrayersCheckedList[3] = (CheckedTextView) v.findViewById(R.id.checkedTextview4);
        allPrayersCheckedList[4] = (CheckedTextView) v.findViewById(R.id.checkedTextview5);


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



    private String intToString(int digit) {
        String s;
        if(digit>9)
                   s = String.valueOf(digit);
        else
            s = "0"+String.valueOf(digit);
        return s;
    }
}
class CalendarDateCount{
    Calendar _calendar;
    Integer _count;
    public CalendarDateCount(){

    }
    public CalendarDateCount(Calendar calendar,int  _count){
        this._calendar = calendar;
        this._count = _count;
    }

    public Calendar get_calendar() {
        return _calendar;
    }

    public Integer get_count() {
        return _count;
    }
}