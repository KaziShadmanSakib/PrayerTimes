package com.example.prayertimes;

public class TimeParser {

    private int parsedTime;
    private String fajr, sunrise, dhuhr, asar, sunset, magrib, isha;

    public void setFajr(String fajr){
        this.fajr = fajr;
    }

    public void setSunrise(String sunrise){
        this.sunrise = sunrise;
    }

    public void setDhuhr(String dhuhr){
        this.dhuhr = dhuhr;
    }

    public void setAsar(String asar){
        this.asar = asar;
    }

    public void setSunset(String sunset){
        this.sunset = sunset;
    }

    public void setMagrib(String magrib){
        this.magrib = magrib;
    }

    public void setIsha(String isha){
        this.isha = isha;
    }

    public int timeParserMethod(String timeToParse){

        //String hour = timeToParse.charAt(0) + timeToParse.charAt(1);

        return parsedTime;
    }

}
