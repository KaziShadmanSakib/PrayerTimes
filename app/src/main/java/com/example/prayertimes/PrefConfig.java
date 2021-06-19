package com.example.prayertimes;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefConfig {

    private static final String My_Preference_Name = "com.example.prayertimes";
    private static final String Pref_City_Key = "pref_city_key";
    private static final String Pref_Country_Key = "pref_country_key";
    private static final String Pref_Fajr_Key = "pref_fajr_key";
    private static final String Pref_Dhuhr_Key = "pref_dhuhr_key";
    private static final String Pref_Asar_Key = "pref_asar_key";
    private static final String Pref_Magrib_Key = "pref_magrib_key";
    private static final String Pref_Isha_Key = "pref_isha_key";
    private static final String Pref_Sunrise_Key = "pref_sunrise_key";
    private static final String Pref_Sunset_Key = "pref_sunset_key";
    private static final String Pref_Imsak_Key = "pref_imsak_key";
    private static final String Pref_CurrentTime_Key = "pref_currentTime_key";
    private static final String Pref_Timer_Value_Key  = "pref_timer_value_key";
    private static final String Pref_Longitude_Key  = "pref_longitude_key";
    private static final String Pref_Latitude_Key  = "pref_latitude_key";
    private static final String Pref_Altitude_Key  = "pref_altitude_key";
    private static final String Pref_Quote_Key  = "pref_quote_key";


    public static void saveQuoteOfTheDay(Context context, String quote){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Pref_Quote_Key, quote);
        editor.commit();

    }

    public static String loadQuoteOfTheDay(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getString(Pref_Quote_Key, "Quote");

    }
    
    
    public static void saveLongitude(Context context, float longitude){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putFloat(Pref_Longitude_Key, longitude);
        editor.commit();

    }

    public static void saveLatitude(Context context, float latitude){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putFloat(Pref_Latitude_Key, latitude);
        editor.commit();

    }

    public static void saveAltitude(Context context, float altitude){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putFloat(Pref_Altitude_Key, altitude);
        editor.commit();

    }

    public static void saveCurrentTime(Context context, String currentTime){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Pref_CurrentTime_Key, currentTime);
        editor.commit();

    }


    public static void saveCurrentCity(Context context, String city){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Pref_City_Key, city);
        editor.commit();

    }

    public static void saveCurrentCountry(Context context, String country){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Pref_Country_Key, country);
        editor.commit();

    }

    public static void saveFajrTime(Context context, String fajrTime){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Pref_Fajr_Key, fajrTime);
        editor.commit();

    }

    public static void saveDhuhrTime(Context context, String dhuhrTime){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Pref_Dhuhr_Key, dhuhrTime);
        editor.commit();

    }

    public static void saveAsarTime(Context context, String asarTime){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Pref_Asar_Key, asarTime);
        editor.commit();

    }

    public static void saveMagribTime(Context context, String magribTime){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Pref_Magrib_Key, magribTime);
        editor.commit();

    }

    public static void saveIshaTime(Context context, String ishaTime){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Pref_Isha_Key, ishaTime);
        editor.commit();

    }

    public static void saveImsakTime(Context context, String imsakTime){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Pref_Imsak_Key, imsakTime);
        editor.commit();

    }

    public static void saveSunriseTime(Context context, String sunrise){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Pref_Sunrise_Key, sunrise);
        editor.commit();

    }

    public static void saveSunsetTime(Context context, String sunset){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Pref_Sunset_Key, sunset);
        editor.commit();

    }

    public static String loadCurrentTime(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getString(Pref_CurrentTime_Key, "Current Time");

    }

    public static String loadCurrentCity(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getString(Pref_City_Key, "City");

    }

    public static String loadCurrentCountry(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getString(Pref_Country_Key, "Country");

    }

    public static String loadFajrTime(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getString(Pref_Fajr_Key, "Fajr");

    }

    public static String loadDhuhrTime(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getString(Pref_Dhuhr_Key, "Dhuhr");

    }

    public static String loadAsarTime(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getString(Pref_Asar_Key, "Asar");

    }

    public static String loadMagribTime(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getString(Pref_Magrib_Key, "Magrib");

    }

    public static String loadIshaTime(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getString(Pref_Isha_Key, "Isha");

    }

    public static String loadImsakTime(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getString(Pref_Imsak_Key, "Imsak");

    }

    public static String loadSunriseTime(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getString(Pref_Sunrise_Key, "Sunrise");

    }

    public static String loadSunsetTime(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getString(Pref_Sunset_Key, "Sunset");

    }
    public static void savefirstTime(Context context, String firstTime){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("first_time_key", firstTime);
        editor.commit();

    }
    public static String loadFirstTime(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getString("first_time_key", "FirstTime");

    }

    public static float loadLongitude(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getFloat(Pref_Longitude_Key, 0);

    }

    public static float loadLatitude(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getFloat(Pref_Latitude_Key, 0);

    }

    public static float loadAltitude(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getFloat(Pref_Altitude_Key, 0);

    }
}
