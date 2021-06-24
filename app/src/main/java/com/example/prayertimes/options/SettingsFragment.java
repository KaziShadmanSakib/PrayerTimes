package com.example.prayertimes.options;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.provider.Settings;
import android.util.Log;


import androidx.annotation.Nullable;

import com.example.prayertimes.R;
import com.example.prayertimes.database.DatabaseHandler;
import com.example.prayertimes.notification.DoNotification;
import com.example.prayertimes.others.PrefConfig;
@SuppressWarnings("deprecation")
public class SettingsFragment extends PreferenceFragment {
    CheckBoxPreference notificationCheckBoxPreference;
    CheckBoxPreference logAccessCheckBoxPreference;
    ListPreference notification_preference;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);


        notification_preference = (ListPreference) findPreference("notification_importance");
        notificationCheckBoxPreference = (CheckBoxPreference) findPreference("pref_notification");
        logAccessCheckBoxPreference = (CheckBoxPreference)findPreference("pref_change_log") ;

        deleteLog();
        logAccessPref();
        internetPref();
        locationPref();
        notificationPref();
        soundPref();
        notificationImportancePref();
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

        if(notificationCheckBoxPreference!=null){
            if(PrefConfig.loadNotificationIndex(getContext())==1)notificationCheckBoxPreference.setChecked(true);
            else
                notificationCheckBoxPreference.setChecked(false);

            DoNotification doNotification = new DoNotification(getContext());
            notificationCheckBoxPreference.setOnPreferenceChangeListener((preference, newValue) -> {
                if(notificationCheckBoxPreference.isChecked()){
                    PrefConfig.saveNotificationIndex(getContext(),0);
                    doNotification.cancelAlarm();

                }
                else if(!notificationCheckBoxPreference.isChecked()){
                    PrefConfig.saveNotificationIndex(getContext(),1);
                    doNotification.setNotification();
                }
                return true;
            });
        }



    }


    private void locationPref() {
        Preference myPref = findPreference("pref_location");
        if(myPref!=null){
            myPref.setOnPreferenceClickListener(preference -> {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                return true;
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
    private void soundPref() {
        Preference myPref = (Preference)findPreference("pref_sound");
        if(myPref!=null){
            myPref.setOnPreferenceClickListener(preference -> {
                startActivity(new Intent(Settings.ACTION_SOUND_SETTINGS));
                return true;
            });
        }

    }


}
