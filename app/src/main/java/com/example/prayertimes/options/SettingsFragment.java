package com.example.prayertimes.options;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.Nullable;

import com.example.prayertimes.R;
import com.example.prayertimes.database.DatabaseHandler;
import com.example.prayertimes.datetime.JasonFetcher;
import com.example.prayertimes.notification.DoNotification;
import com.example.prayertimes.others.LoadingDialog;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("deprecation")
public class SettingsFragment extends PreferenceFragment {

    SwitchPreference notificationPreference;
    SwitchPreference locationPreference;
    CheckBoxPreference logAccessCheckBoxPreference;
    ListPreference notification_preference;
    ListPreference mazhabPreference;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);

        locationPreference = (SwitchPreference)findPreference("pref_location_check") ;
        notification_preference = (ListPreference) findPreference("notification_importance");
        notificationPreference = (SwitchPreference) findPreference("pref_notification");
        logAccessCheckBoxPreference = (CheckBoxPreference)findPreference("pref_change_log") ;
        mazhabPreference = (ListPreference)findPreference("mazhab_type");

        mazhabPref();
        deleteLog();
        logAccessPref();
        internetPref();
        locationPref();
        setLocation();
        notificationPref();
        soundPref();
        notificationImportancePref();
    }

    private void mazhabPref() {
        if(mazhabPreference!=null){

            if(PrefConfig.loadMazhabType(getContext())==0){
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

                        PrefConfig.saveMazhabType(getContext(),0);
                        mazhabPreference.setSummary("Hanafi");
                    }
                    else{
                        PrefConfig.saveMazhabType(getContext(),1);
                        mazhabPreference.setSummary("Shafeii");
                    }
                    JasonFetcher jasonFetcher = new JasonFetcher(getContext());
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
                         LoadingDialog loadingDialog = new LoadingDialog(getActivity());
                         loadingDialog.startLoadingDialog();
                         new Handler(Looper.getMainLooper()).postDelayed(() -> {

                             if(jasonFetcher.isNoError()){
                                 PrefConfig.saveCurrentCity(getContext(),locationList.get(0));
                                 PrefConfig.saveCurrentCountry(getContext(),locationList.get(1));
                                 locationEditText.setSummary(locationList.get(0)+", "+locationList.get(1));
                             }
                             else {
                                 Toast toast = Toast.makeText(getContext(),"Invalid Country or City",Toast.LENGTH_SHORT);
                                 toast.show();
                             }
                             loadingDialog.dismissDialog();
                         }, 2000);
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
