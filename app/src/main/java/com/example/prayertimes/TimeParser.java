package com.example.prayertimes;

public class TimeParser {

    private long parsedTime;

    public long timeParserMethod(String timeToParse){

        String hour = timeToParse.substring(0,2);
        String min = timeToParse.substring(3,5);

        long hourInt = Integer.parseInt(hour);
        long minInt = Integer.parseInt(min);

        parsedTime = hourInt * 3600000;
        parsedTime = parsedTime + (minInt * 60000);

        return parsedTime;
    }

    public long timeParserMethodForCurrentTime(String timeToParse){

        String hour = timeToParse.substring(0,2);
        String min = timeToParse.substring(3,5);
        String sec = timeToParse.substring(6,8);

        long hourInt = Integer.parseInt(hour);
        long minInt = Integer.parseInt(min);
        long secInt = Integer.parseInt(sec);

        parsedTime = hourInt * 3600000;
        parsedTime = parsedTime + (minInt * 60000);
        parsedTime = parsedTime + (secInt * 1000);

        return parsedTime;
    }

}
