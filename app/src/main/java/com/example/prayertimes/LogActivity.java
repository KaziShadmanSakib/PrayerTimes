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
import android.widget.CalendarView;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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


        calendarView = (CalendarView)findViewById(R.id.calendarView);



        //openDB
        databaseHandler = new DatabaseHandler(this);


        //calender popUp
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                month++;
                String monthString = intToString(month);
                String dayString = intToString(dayOfMonth);
                String date = year+monthString+dayString;


                if(databaseHandler.getContact(date)._date == null){

                    Toast toast=Toast.makeText(getApplicationContext(),date,Toast.LENGTH_SHORT);
                    toast.show();

                    doPopUp(true,date);

                }
                else{
                    doPopUp(false,date);


                }


            }
        });

        /*App bar config */
        getSupportActionBar().setTitle("Log");



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