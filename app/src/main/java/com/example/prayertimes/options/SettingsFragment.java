package com.example.prayertimes.options;


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
import com.example.prayertimes.notification.DoNotification;
import com.example.prayertimes.others.PrefConfig;
@SuppressWarnings("deprecation")
public class SettingsFragment extends PreferenceFragment {
    CheckBoxPreference checkBoxPreference;
    ListPreference notification_preference;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);


        notification_preference = (ListPreference) findPreference("notification_importance");
        checkBoxPreference = (CheckBoxPreference) findPreference("pref_notification");



        internetPref();
        locationPref();
        notificationPref();
        soundPref();
        notificationImportancePref();
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
        if(PrefConfig.loadNotificationIndex(getContext())==1)checkBoxPreference.setChecked(true);
        else
            checkBoxPreference.setChecked(true);

        DoNotification doNotification = new DoNotification(getContext());
        checkBoxPreference.setOnPreferenceChangeListener((preference, newValue) -> {
            if(checkBoxPreference.isChecked()){
                PrefConfig.saveNotificationIndex(getContext(),0);
                doNotification.cancelAlarm();

            }
            else if(!checkBoxPreference.isChecked()){
                PrefConfig.saveNotificationIndex(getContext(),1);
                doNotification.setNotification();
            }
            return true;
        });


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
