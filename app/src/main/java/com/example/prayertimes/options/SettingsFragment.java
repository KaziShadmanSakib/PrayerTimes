package com.example.prayertimes.options;


import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.provider.Settings;


import androidx.annotation.Nullable;

import com.example.prayertimes.R;
import com.example.prayertimes.others.PrefConfig;

public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);

        internetPref();
        locationPref();
        notificationPref();
    }
    @SuppressWarnings("deprecation")
    private void notificationPref() {
        CheckBoxPreference checkBoxPreference = (CheckBoxPreference) findPreference("pref_notification");
        checkBoxPreference.setOnPreferenceChangeListener((preference, newValue) -> {
            if(checkBoxPreference.isChecked()){
                PrefConfig.saveNotificationIndex(getContext(),0);
            }
            else if(!checkBoxPreference.isChecked()){
                PrefConfig.saveNotificationIndex(getContext(),1);
            }
            return true;
        });


    }

    @SuppressWarnings("deprecation")
    private void locationPref() {
        Preference myPref = findPreference("pref_location");
        myPref.setOnPreferenceClickListener(preference -> {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            return true;
        });
    }

    @SuppressWarnings("deprecation")
    private void internetPref() {
        Preference myPref = (Preference) findPreference("pref_network");
        myPref.setOnPreferenceClickListener(preference -> {
            startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
            return true;
        });
    }


}
