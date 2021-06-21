package com.example.prayertimes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.provider.Settings;
import android.view.View;

import androidx.annotation.Nullable;

public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);

        internetPref();
        locationPref();

    }

    @SuppressWarnings("deprecation")
    private void locationPref() {
        Preference myPref = findPreference("pref_location");
        myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                return true;
            }
        });
    }

    @SuppressWarnings("deprecation")
    private void internetPref() {
        Preference myPref = (Preference) findPreference("pref_network");
        myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
                return true;
            }
        });
    }


}
