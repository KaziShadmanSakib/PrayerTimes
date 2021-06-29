package com.kssabd.prayertimes.datetime;

public class JsonParser {

    private String urlString ;
    private String fazrNamazTime;
    private String sunrise;
    private String dhuhrNamazTime;
    private String asarNamazTime;
    private String sunset;
    private String magribNamazTime;
    private String ishaNamazTime;
    private String imsakTime;

    public void setUrlString(String urlString){
        this.urlString = urlString;
    }

    public String fazrTime(){

        int intIndex = urlString.indexOf("Fajr");

        fazrNamazTime = urlString.substring(intIndex+7, intIndex+12);

        return fazrNamazTime;
    }

    public String sunrise(){

        int intIndex = urlString.indexOf("Sunrise");

        sunrise = urlString.substring(intIndex+10, intIndex+15);

        return sunrise;

    }

    public String dhuhrTime(){

        int intIndex = urlString.indexOf("Dhuhr");

        dhuhrNamazTime = urlString.substring(intIndex+8, intIndex+13);

        return dhuhrNamazTime;
    }

    public String asarTime(){

        int intIndex = urlString.indexOf("Asr");

        asarNamazTime = urlString.substring(intIndex+6, intIndex+11);

        return asarNamazTime;
    }

    public String sunset(){

        int intIndex = urlString.indexOf("Sunset");

        sunset = urlString.substring(intIndex+9, intIndex+14);

        return sunset;
    }

    public String magribTime(){

        int intIndex = urlString.indexOf("Maghrib");

        magribNamazTime = urlString.substring(intIndex+10, intIndex+15);

        return magribNamazTime;
    }

    public String ishaTime(){

        int intIndex = urlString.indexOf("Isha");

        ishaNamazTime = urlString.substring(intIndex+7, intIndex+12);

        return ishaNamazTime;
    }

    public String imsakTime(){

        int intIndex = urlString.indexOf("Imsak");

        imsakTime = urlString.substring(intIndex+8, intIndex+13);

        return imsakTime;
    }

}
