package com.kssabd.prayertimes.datetime;

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
    public String timeParseToAMPM(String timeToParse){

        String hour = timeToParse.substring(0,2);
        String min = timeToParse.substring(3,5);

        long hourInt = Integer.parseInt(hour);

        if(hourInt > 11){

            hourInt = hourInt - 12;

            if(hourInt == 10 || hourInt == 11){

                return String.valueOf(hourInt) + ":" + min + " PM";

            }

            if(hourInt == 0){

                return "12" + ":" + min + " PM";

            }

            else {

                return "0" + String.valueOf(hourInt) + ":" + min + " PM";

            }

        }

        else {

            if(hourInt == 0){

                return "12" + ":" + min + " AM";

            }

            return hour + ":" + min + " AM";

        }

    }
    public String militoHour(int milisec){
        String hourString,minuteString;
        String time = "00:00";
        int minutes = milisec/60000;
        int hour = minutes/60;
        if(hour<10){
            hourString = "0"+hour;
        }
        else
            hourString = String.valueOf(hour);
        int min = minutes%60;
        if(min<10){
           minuteString = "0"+min;
        }
        else
            minuteString = String.valueOf(min);


        time = hourString+":"+minuteString;
        return time;
    }

}
