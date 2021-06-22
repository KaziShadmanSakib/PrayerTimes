package com.example.prayertimes.database;

public class Contact {

    //private variables
    String _date ;
    Boolean _fajr;
    Boolean _dhuhr;
    Boolean _asar;
    Boolean _magrib;
    Boolean _isha;
    Boolean _prayerName;



    // Empty constructor
    public Contact(){

    }
    // constructor
    public Contact(String date,Boolean fajr,Boolean dhuhr,Boolean asar,Boolean magrib,Boolean isha){
        this._date = date;
        this._fajr = fajr;
        this._dhuhr = dhuhr;
        this._asar = asar;
        this._magrib = magrib;
        this._isha = isha;
    }

    // constructor
    public Contact(String date, Boolean prayerName){
        this._date = date;
        this._prayerName = prayerName;
    }
    // getting ID
    public String getDate(){
        return this._date;
    }

    // setting id
    public void setDate(String date){
        this._date = date;
    }

    // getting fajr
    public Boolean getFajr(){
        return this._fajr;
    }

    // setting fajr
    public void setFajr(Boolean fajr){
        this._fajr = fajr;
    }



    public Boolean getDhuhr(){
        return this._dhuhr;
    }

    // setting dhuhr
    public void setDhuhr(Boolean dhuhr){
        this._dhuhr = dhuhr;
    }


    public Boolean getAsar(){
        return this._asar;
    }

    // setting asar
    public void setAsar(Boolean asar){
        this._asar = asar;
    }


    public Boolean getMagrib(){
        return this._magrib;
    }

    // setting magrib
    public void setMagrib(Boolean magrib){
        this._magrib = magrib;
    }


    public Boolean getIsha(){
        return this._isha;
    }

    // setting Isha
    public void setIsha(Boolean isha){
        this._isha = isha;
    }




}
