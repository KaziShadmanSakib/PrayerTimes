package com.example.prayertimes;

import java.util.Calendar;

public class CalendarDateCount{
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
