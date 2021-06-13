package com.example.prayertimes;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
// Database Version
    private static final int DATABASE_VERSION = 3;

    // Database Name
    private static final String DATABASE_NAME = "allPrayers";

    // Contacts table name
    private static final String TABLE_Prayers = "prayerLog";

    // Contacts Table Columns names
    private static final String KEY_Date = "Date";
    private static final String KEY_Fajr = "Fajr";
    private static final String KEY_Dhuhr = "Dhuhr";
    private static final String KEY_Asar = "Asar";
    private static final String KEY_Magrib = "Magrib";
    private static final String KEY_Isha = "Isha";
    private String ALL_Prayers[] = {"Fajr","Dhuhr","Asar","Magrib","Isha"};



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_prayerLog_TABLE = "CREATE TABLE " + TABLE_Prayers + "("
                + KEY_Date + " TEXT PRIMARY KEY," + KEY_Fajr + " TEXT,"
                + KEY_Dhuhr + " TEXT," + KEY_Asar + " TEXT," + KEY_Magrib + " TEXT," + KEY_Isha + " TEXT" + ")";
        db.execSQL(CREATE_prayerLog_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Prayers);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

// Adding new contact
    void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_Date, contact.getDate());
        values.put(KEY_Fajr, contact.getFajr());
        values.put(KEY_Dhuhr, contact.getDhuhr());
        values.put(KEY_Asar, contact.getAsar());
        values.put(KEY_Magrib, contact.getMagrib());
        values.put(KEY_Isha, contact.getIsha());


        // Inserting Row
        db.insert(TABLE_Prayers, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    Contact getContact(String date) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_Prayers, new String[] { KEY_Date,
                        KEY_Fajr, KEY_Dhuhr,KEY_Asar,KEY_Magrib,KEY_Isha }, KEY_Date + "=?",
                new String[] { date }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        if(cursor.getCount()==0){
            Contact contact = new Contact();
            return contact;
        }


        Contact contact = new Contact(cursor.getString(0),
                stringToBoolean(cursor.getString(1)) ,
                stringToBoolean(cursor.getString(2)) ,
                stringToBoolean(cursor.getString(3)) ,
                stringToBoolean(cursor.getString(4)) ,
                stringToBoolean(cursor.getString(5))
        );
        // return contact
        return contact;
    }

    // Getting All Contacts
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_Prayers;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.getCount()==0){
            Contact contact = new Contact();
            contactList.add(contact);
            return contactList;
        }

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact(cursor.getString(0),
                        stringToBoolean(cursor.getString(1)) ,
                        stringToBoolean(cursor.getString(2)) ,
                        stringToBoolean(cursor.getString(3)) ,
                        stringToBoolean(cursor.getString(4)) ,
                        stringToBoolean(cursor.getString(5))
                );


                // Adding contact to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        // return contact list
        return contactList;
    }



    // Updating single contact
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_Fajr, contact.getFajr());
        values.put(KEY_Dhuhr, contact.getDhuhr());
        values.put(KEY_Asar, contact.getAsar());
        values.put(KEY_Magrib, contact.getMagrib());
        values.put(KEY_Isha, contact.getIsha());


        // updating row
        return db.update(TABLE_Prayers, values, KEY_Date + " = ?",
                new String[] { contact.getDate() });
    }
    public void updateCell(String date, int prayerNo,boolean isChecked){
        String  strSQL;
        if(isChecked){
            strSQL = "UPDATE " + TABLE_Prayers + " SET " +ALL_Prayers[prayerNo] + " = FALSE WHERE Date = " + date;
        }
        else{
            strSQL = "UPDATE " + TABLE_Prayers + " SET " +ALL_Prayers[prayerNo] + " = TRUE WHERE Date = " + date;
        }



        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(strSQL);
    }

    Boolean stringToBoolean(String s){
        if(Integer.parseInt(s) ==0 )return false;
        else
            return true;
    }
    public void updateDatabase(String date, int index,boolean isChecked) {




       // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
       // String date = simpleDateFormat.format(calendar.getTime());


        if (getContact(date)._date == null) {
            try {
                Contact contact = new Contact(date, false, false, false, false, false);
                addContact(contact);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            if(isChecked) {
                updateCell(date, index,isChecked);
            }
            else{
                updateCell(date, index,isChecked);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


    /*
        // Deleting single contact
        public void deleteContact(Contact contact) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_Prayers, KEY_ID + " = ?",
                    new String[] { String.valueOf(contact.getID()) });
            db.close();
        }
     */
