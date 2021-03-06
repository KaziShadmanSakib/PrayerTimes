package com.kssabd.prayertimes.options;


import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;


import androidx.annotation.Nullable;

import com.kssabd.prayertimes.R;
import com.kssabd.prayertimes.database.DatabaseHandler;
import com.kssabd.prayertimes.datetime.JasonFetcher;
import com.kssabd.prayertimes.notification.DoNotification;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("deprecation")
public class SettingsFragment extends PreferenceFragment {

    SwitchPreference notificationPreference;
    SwitchPreference locationPreference;
    CheckBoxPreference logAccessCheckBoxPreference;
    ListPreference notification_preference;
    ListPreference mazhabPreference;
    SwitchPreference extraTimePref;
    SwitchPreference sehriAlarmSwitchPreference;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);


        setPreferences();

        sehriAlarmTimePref();
        sehriAlarmPref();
        extraMinutePref();
        extratimePref();
        mazhabPref();
        deleteLog();
        logAccessPref();
        internetPref();
        locationPref();
        setLocation();
        notificationPref();
        feedbackPref();
        notificationImportancePref();
    }

    private void setPreferences() {
        sehriAlarmSwitchPreference = (SwitchPreference)findPreference("pref_sehri_alarm");
        extraTimePref = (SwitchPreference)findPreference("pref_extra");
        locationPreference = (SwitchPreference)findPreference("pref_location_check") ;
        notification_preference = (ListPreference) findPreference("notification_importance");
        notificationPreference = (SwitchPreference) findPreference("pref_notification");
        logAccessCheckBoxPreference = (CheckBoxPreference)findPreference("pref_change_log") ;
        mazhabPreference = (ListPreference)findPreference("mazhab_type");
    }

    private void sehriAlarmTimePref() {
        String s = String.valueOf(PrefConfig.loadSehriAlarmtime(getContext()));

        EditTextPreference sehriAlarmTime = (EditTextPreference)findPreference("pref_extra_sehri_time_input") ;

        if(sehriAlarmTime!=null){

            sehriAlarmTime.setSummary(s);
            sehriAlarmTime.setText(s);

            sehriAlarmTime.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {

                    String minutes = newValue.toString();


                    if(minutes!=null){
                        int min = Integer.parseInt(minutes);

                        if(min>=10) {

                            PrefConfig.saveSehriAlarmTime(getContext(),min);
                            sehriAlarmTime.setSummary(String.valueOf(min));
                            sehriAlarmTime.setText(String.valueOf(min));
                            DoNotification doNotification = new DoNotification(getContext());
                            doNotification.setNotification();

                        }
                        else{
                            Toast toast = Toast.makeText(getContext(), "Input is too low", Toast.LENGTH_SHORT);
                            toast.show();
                        }


                    }





                    return true;
                }
            });




        }
    }

    private void sehriAlarmPref() {

        if(sehriAlarmSwitchPreference!=null){
            if(PrefConfig.loadSehriAlarmConfig(getContext())==1){
                sehriAlarmSwitchPreference.setChecked(true);
                DoNotification doNotification = new DoNotification(getContext());
                doNotification.setNotification();
            }
            else
                sehriAlarmSwitchPreference.setChecked(false);


            sehriAlarmSwitchPreference.setOnPreferenceChangeListener((preference, newValue) -> {
                if(sehriAlarmSwitchPreference.isChecked()){
                    PrefConfig.saveSehriAlarmConfig(getContext(),0);

                }
                else if(!sehriAlarmSwitchPreference.isChecked()){
                    PrefConfig.saveSehriAlarmConfig(getContext(),1);

                }
                return true;
            });
        }

    }

    private void extraMinutePref() {

        String s = String.valueOf(PrefConfig.loadExtraMinutesCount(getContext()));
        EditTextPreference extraMinuteEditText  = (EditTextPreference) findPreference("pref_extra_time_input");


        if(extraMinuteEditText!=null){

            extraMinuteEditText.setSummary(s);
            extraMinuteEditText.setText(s);

            extraMinuteEditText.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {

                    String minutes = newValue.toString();


                    if(minutes!=null){
                        int min = Integer.parseInt(minutes);

                        if(min>=0&&min<11) {

                                PrefConfig.saveExtraMinutes(getContext(),min);
                                extraMinuteEditText.setSummary(String.valueOf(min));
                                extraMinuteEditText.setText(String.valueOf(min));

                        }
                        else{
                                Toast toast = Toast.makeText(getContext(), "Input value is out of range", Toast.LENGTH_SHORT);
                                toast.show();
                        }


                    }





                    return true;
                }
            });




        }
    }

    private void extratimePref() {
        if(extraTimePref!=null){
            if(PrefConfig.loadExtraTimePref(getContext())==1)extraTimePref.setChecked(true);
            else
                extraTimePref.setChecked(false);


            extraTimePref.setOnPreferenceChangeListener((preference, newValue) -> {
                if(extraTimePref.isChecked()){
                    PrefConfig.saveExtraTimePref(getContext(),0);


                }
                else if(!extraTimePref.isChecked()){
                    PrefConfig.saveExtraTimePref(getContext(),1);
                }
                return true;
            });
        }


    }

    private void mazhabPref() {
        if(mazhabPreference!=null){

            if(PrefConfig.loadMazhabType(getContext())==1){
                mazhabPreference.setSummary("Hanafi");
            }

            else{
                mazhabPreference.setSummary("Shafeii");
            }



            mazhabPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {

                    setNotificationImportance((String)newValue);
                    return false;
                }

                private void setNotificationImportance(String s) {



                    if(s.equals("Hanafi")){

                        PrefConfig.saveMazhabType(getContext(),1);
                        mazhabPreference.setSummary("Hanafi");
                    }
                    else{
                        PrefConfig.saveMazhabType(getContext(),0);
                        mazhabPreference.setSummary("Shafeii");
                    }
                    JasonFetcher jasonFetcher = new JasonFetcher(getContext());
                    jasonFetcher.getData();
                }

            });
        }

    }


    private void deleteLog() {
        Preference myPref = findPreference("pref_delete_log");
        if(myPref!=null){
            myPref.setOnPreferenceClickListener(preference -> {
                {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Are You Sure")
                            .setMessage("All the Calendar log Data will be removed")
                            .setCancelable(false)
                            .setPositiveButton("Yes", (dialog, which) -> {

                                DatabaseHandler databaseHandler = new DatabaseHandler(getContext());
                                databaseHandler.deleteAll();

                            }).setNegativeButton("No", null)
                            .show();
                }
                return true;
            });
        }
    }

    private void logAccessPref() {

        if(logAccessCheckBoxPreference!=null){
            if(PrefConfig.loadLogAccessPref(getContext())==1)logAccessCheckBoxPreference.setChecked(true);
            else
                logAccessCheckBoxPreference.setChecked(false);


            logAccessCheckBoxPreference.setOnPreferenceChangeListener((preference, newValue) -> {
                if(logAccessCheckBoxPreference.isChecked()){
                    PrefConfig.saveLogAccessPref(getContext(),0);

                }
                else if(!logAccessCheckBoxPreference.isChecked()){
                    PrefConfig.saveLogAccessPref(getContext(),1);

                }
                return true;
            });
        }
    }

    private void notificationImportancePref() {


        if(notification_preference!=null){

            if(PrefConfig.loadCurrentNotificationType(getContext())==1){
                notification_preference.setSummary("Make sound");
            }
            else if(PrefConfig.loadCurrentNotificationType(getContext())==2){
                notification_preference.setSummary("Make Sound and popup");
            }
            else{
                notification_preference.setSummary("No sound or visual interruption");
            }



            notification_preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {

                    setNotificationImportance((String)newValue);
                    return false;
                }

                private void setNotificationImportance(String s) {

                    if(s.equals("High")){

                        PrefConfig.saveCurrentNotificationType(getContext(),2);
                        notification_preference.setSummary("Make Sound and popup");
                    }
                    else if(s.equals("Low")){
                        PrefConfig.saveCurrentNotificationType(getContext(),0);
                        notification_preference.setSummary("No sound or visual interruption");
                    }
                    else{
                        PrefConfig.saveCurrentNotificationType(getContext(),1);
                        notification_preference.setSummary("Make sound");
                    }
                    DoNotification doNotification = new DoNotification(getContext());
                    doNotification.setNotification();
                }

            });
        }
    }

    private void notificationPref() {

        if(notificationPreference!=null){
            if(PrefConfig.loadNotificationIndex(getContext())==1)notificationPreference.setChecked(true);
            else
                notificationPreference.setChecked(false);

            DoNotification doNotification = new DoNotification(getContext());
            notificationPreference.setOnPreferenceChangeListener((preference, newValue) -> {
                if(notificationPreference.isChecked()){
                    PrefConfig.saveNotificationIndex(getContext(),0);
                    doNotification.cancelAlarm();

                }
                else if(!notificationPreference.isChecked()){
                    PrefConfig.saveNotificationIndex(getContext(),1);
                    doNotification.setNotification();
                }
                return true;
            });
        }



    }


    private void locationPref() {

        if(locationPreference!=null){
            if(PrefConfig.loadLocationType(getContext())==1)locationPreference.setChecked(true);
            else
               locationPreference.setChecked(false);


            locationPreference.setOnPreferenceChangeListener((preference, newValue) -> {
                if(locationPreference.isChecked()){
                    PrefConfig.saveLocationIndex(getContext(),0);
                    PrefConfig.saveCurrentCountry(getContext(),"Bangladesh");
                    PrefConfig.saveCurrentCity(getContext(),"Dhaka");

                }
                else if(!locationPreference.isChecked()){
                    PrefConfig.saveLocationIndex(getContext(),1);

                }
                return true;
            });
        }





        Preference myPref = findPreference("pref_location");
        if(myPref!=null){
            myPref.setOnPreferenceClickListener(preference -> {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                return true;
            });
        }

    }

    private void setLocation() {


        String s = PrefConfig.loadCurrentCity(getContext()) +","+PrefConfig.loadCurrentCountry(getContext());
        EditTextPreference locationEditText  = (EditTextPreference) findPreference("pref_location_input");


        if(locationEditText!=null){

            locationEditText.setSummary(s);
            locationEditText.setText(s);

             locationEditText.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                 @Override
                 public boolean onPreferenceChange(Preference preference, Object newValue) {

                     String location = newValue.toString();
                     List<String> locationList = Arrays.asList(location.split("\\s*,\\s*"));



                     JasonFetcher jasonFetcher = new JasonFetcher(getContext());
                     if(locationList.size()>1){
                         jasonFetcher.getTempData(locationList.get(0),locationList.get(1));


                         new Handler(Looper.getMainLooper()).postDelayed(() -> {
                             if(jasonFetcher.isNoError()){
                                 Log.i("uga","why");
                                 PrefConfig.saveCurrentCity(getContext(),locationList.get(0));
                                 PrefConfig.saveCurrentCountry(getContext(),locationList.get(1));

                             }
                             else {
                                 Toast toast = Toast.makeText(getContext(),"Invalid Country or City",Toast.LENGTH_SHORT);
                                 toast.show();
                             }
                         }, 1500);

                     }
                     else{
                         Toast toast = Toast.makeText(getContext(),"Invalid Country or City",Toast.LENGTH_SHORT);
                         toast.show();
                     }




                     return true;
                 }
             });




        }
    }



    private void internetPref() {
        Preference myPref = (Preference) findPreference("pref_network");
        if(myPref!=null){
            myPref.setOnPreferenceClickListener(preference -> {
                startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                return true;
            });
        }

    }
    private void feedbackPref() {
        Preference myPref = (Preference)findPreference("pref_feedback");
        if(myPref!=null){
            myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Open Browser")
                            .setMessage("It will open your default browser")
                            .setCancelable(false)
                            .setPositiveButton("Yes", (dialog, which) -> {

                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/KaziShadmanSakib/PrayerTimes/issues/new"));
                                startActivity(browserIntent);

                            }).setNegativeButton("No", null)
                            .show();

                    return false;
                }
            });
        }

    }


}
