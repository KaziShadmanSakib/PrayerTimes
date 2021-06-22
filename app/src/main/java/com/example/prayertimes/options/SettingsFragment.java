package com.example.prayertimes.options;


import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.provider.Settings;


import androidx.annotation.Nullable;

import com.example.prayertimes.R;
import com.example.prayertimes.notification.DoNotification;
import com.example.prayertimes.others.PrefConfig;
@SuppressWarnings("deprecation")
public class SettingsFragment extends PreferenceFragment {
    CheckBoxPreference checkBoxPreference;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);



        checkBoxPreference = (CheckBoxPreference) findPreference("pref_notification");
        if(PrefConfig.loadNotificationIndex(getContext())==1)checkBoxPreference.setChecked(true);
        else
            checkBoxPreference.setChecked(true);


        internetPref();
        locationPref();
        notificationPref();
    }

    private void notificationPref() {
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
        myPref.setOnPreferenceClickListener(preference -> {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            return true;
        });
    }


    private void internetPref() {
        Preference myPref = (Preference) findPreference("pref_network");
        myPref.setOnPreferenceClickListener(preference -> {
            startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
            return true;
        });
    }


}
