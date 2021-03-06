package com.kssabd.prayertimes.options;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefConfig {

    private static final String My_Preference_Name = "com.kssabd.prayertimes";
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

    private static final String Pref_Fajr_AM_PM_Key = "pref_fajr_am_pm_key";
    private static final String Pref_Dhuhr_AM_PM_Key = "pref_dhuhr_am_pm_key";
    private static final String Pref_Asar_AM_PM_Key = "pref_asar_am_pm_key";
    private static final String Pref_Magrib_AM_PM_Key = "pref_magrib_am_pm_key";
    private static final String Pref_Isha_AM_PM_Key = "pref_isha_am_pm_key";
    private static final String Pref_Sunrise_AM_PM_Key = "pref_sunrise_am_pm_key";
    private static final String Pref_Sunset_AM_PM_Key = "pref_sunset_am_pm_key";
    private static final String Pref_Imsak_AM_PM_Key = "pref_imsak_am_pm_key";

    private static final String Pref_ProgressBar_Key = "pref_progressbar_key";
    private static final String Pref_StartTimeProgressBar_Key = "pref_start_time_progress_bar_key";



    private static final String Pref_Index_Key = "pref_index_key";
    private static final String Pref_notification_Key = "pref_notification_key";
    private static final String Pref_location_set_Key = "pref_location_set_key";
    private static final String Pref_notification_type_Key = "pref_notification_type_key";
    private static final String Pref_log_access_Key = "pref_log_access_key";
    private static final String Pref_Mazhab_Key = "pref_mazhab_key";
    private static final String Pref_extra_time_Key = "pref_extra_time_key";
    private static final String Pref_extra_minutes_Key = "pref_extra_minutes_key";
    private static final String Pref_sehri_alarm_Key = "pref_sehri_alarm_key";
    private static final String Pref_sehri_alarm_time_key = "pref_sehri_alarm_time_key";

    public static int loadSehriAlarmtime(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getInt(Pref_sehri_alarm_time_key, 30);

    }
    public static void saveSehriAlarmTime(Context context, int type){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(Pref_sehri_alarm_time_key, type);
        editor.apply();

    }

    public static int loadSehriAlarmConfig(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getInt(Pref_sehri_alarm_Key, 0);

    }
    public static void saveSehriAlarmConfig(Context context, int type){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(Pref_sehri_alarm_Key, type);
        editor.apply();

    }


    public static int loadMazhabType(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getInt(Pref_Mazhab_Key, 1);

    }
    public static void saveMazhabType(Context context, int type){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(Pref_Mazhab_Key, type);
        editor.apply();

    }
    public static int loadExtraTimePref(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getInt(Pref_extra_time_Key, 0);

    }
    public static void saveExtraTimePref(Context context, int type){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(Pref_extra_time_Key, type);
        editor.apply();

    }
    public static int loadExtraMinutesCount(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getInt(Pref_extra_minutes_Key, 0);

    }
    public static void saveExtraMinutes(Context context, int count){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(Pref_extra_minutes_Key, count);
        editor.apply();

    }

    public static int loadLocationType(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getInt(Pref_location_set_Key, 0);

    }
    public static void saveLocationIndex(Context context, int type){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(Pref_location_set_Key, type);
        editor.apply();

    }

    public static int loadStartTimeProgressBar(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getInt(Pref_StartTimeProgressBar_Key, 1000);

    }

    public static void saveStartTimeProgressBar(Context context, int value){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(Pref_StartTimeProgressBar_Key, value);
        editor.apply();

    }


    public static int loadProgressBar(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getInt(Pref_ProgressBar_Key, 0);

    }

    public static void saveProgressBar(Context context, int value){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(Pref_ProgressBar_Key, value);
        editor.commit();

    }

    public static void saveLogAccessPref(Context context, int index){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(Pref_log_access_Key, index);
        editor.apply();

    }

    public static int loadLogAccessPref(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getInt(Pref_log_access_Key, 0);

    }

    public static void saveCurrentNotificationType(Context context, int index){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(Pref_notification_type_Key, index);
        editor.apply();

    }

    public static int loadCurrentNotificationType(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getInt(Pref_notification_type_Key, 1);

    }
    public static void saveCurrentPrayerIndex(Context context, int index){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(Pref_Index_Key, index);
        editor.apply();

    }

    public static int loadCurrentPrayerIndex(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getInt(Pref_Index_Key, 5);

    }
    public static void saveNotificationIndex(Context context, int index){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(Pref_notification_Key, index);
        editor.apply();

    }

    public static int loadNotificationIndex(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getInt(Pref_notification_Key, 1);

    }



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

    public static void saveFajrTimeAMPM(Context context, String fajrTimeAMPM){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Pref_Fajr_AM_PM_Key, fajrTimeAMPM);
        editor.commit();

    }

    public static void saveDhuhrTimeAMPM(Context context, String dhuhrTimeAMPM){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Pref_Dhuhr_AM_PM_Key, dhuhrTimeAMPM);
        editor.commit();

    }

    public static void saveAsarTimeAMPM(Context context, String asarTimeAMPM){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Pref_Asar_AM_PM_Key, asarTimeAMPM);
        editor.commit();

    }

    public static void saveMagribTimeAMPM(Context context, String magribTimeAMPM){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Pref_Magrib_AM_PM_Key, magribTimeAMPM);
        editor.commit();

    }

    public static void saveIshaTimeAMPM(Context context, String ishaTimeAMPM){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Pref_Isha_AM_PM_Key, ishaTimeAMPM);
        editor.commit();

    }

    public static void saveImsakTimeAMPM(Context context, String imsakTimeAMPM){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Pref_Imsak_AM_PM_Key, imsakTimeAMPM);
        editor.commit();

    }

    public static void saveSunriseTimeAMPM(Context context, String sunriseAMPM){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Pref_Sunrise_AM_PM_Key, sunriseAMPM);
        editor.commit();

    }

    public static void saveSunsetTimeAMPM(Context context, String sunsetAMPM){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Pref_Sunset_AM_PM_Key, sunsetAMPM);
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
        return pref.getString(Pref_CurrentTime_Key, "05:06");

    }

    public static String loadCurrentCity(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getString(Pref_City_Key, "Dhaka");

    }

    public static String loadCurrentCountry(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getString(Pref_Country_Key, "Bangladesh");

    }

    public static String loadFajrTime(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getString(Pref_Fajr_Key, "03:48");

    }

    public static String loadDhuhrTime(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getString(Pref_Dhuhr_Key, "12:03");

    }

    public static String loadAsarTime(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getString(Pref_Asar_Key, "16:44");

    }

    public static String loadMagribTime(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getString(Pref_Magrib_Key, "18:51");

    }

    public static String loadIshaTime(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getString(Pref_Isha_Key, "20:19");

    }

    public static String loadImsakTime(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getString(Pref_Imsak_Key, "03:38");

    }

    public static String loadSunriseTime(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getString(Pref_Sunrise_Key, "05:15");

    }

    public static String loadSunsetTime(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getString(Pref_Sunset_Key, "06:51");

    }

    public static String loadFajrTimeAMPM(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getString(Pref_Fajr_AM_PM_Key, "03:48 AM");

    }

    public static String loadDhuhrTimeAMPM(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getString(Pref_Dhuhr_AM_PM_Key, "12:03 PM");

    }

    public static String loadAsarTimeAMPM(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getString(Pref_Asar_AM_PM_Key, "04:44 PM");

    }

    public static String loadMagribTimeAMPM(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getString(Pref_Magrib_AM_PM_Key, "06:51 PM");

    }

    public static String loadIshaTimeAMPM(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getString(Pref_Isha_AM_PM_Key, "08:19 PM");

    }

    public static String loadImsakTimeAMPM(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getString(Pref_Imsak_AM_PM_Key, "03:48 AM");

    }

    public static String loadSunriseTimeAMPM(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getString(Pref_Sunrise_AM_PM_Key, "05:15 AM");

    }

    public static String loadSunsetTimeAMPM(Context context){

        SharedPreferences pref = context.getSharedPreferences(My_Preference_Name, Context.MODE_PRIVATE);
        return pref.getString(Pref_Sunset_AM_PM_Key, "06:51 PM");

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
