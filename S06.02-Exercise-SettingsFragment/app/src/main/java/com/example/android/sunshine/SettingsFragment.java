package com.example.android.sunshine;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.List;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener{

    // Do steps 5 - 11 within SettingsFragment
    //  (10) Implement OnSharedPreferenceChangeListener from SettingsFragment

    //  (8) Create a method called setPreferenceSummary that accepts a Preference and an Object and sets the summary of the preference

    //  (5) Override onCreatePreferences and add the preference xml file using addPreferencesFromResource

    // Do step 9 within onCreatePreference
    //  (9) Set the preference summary on each preference that isn't a CheckBoxPreference

    //  (13) Unregister SettingsFragment (this) as a SharedPreferenceChangedListener in onStop

    //  (12) Register SettingsFragment (this) as a SharedPreferenceChangedListener in onStart

    //  (11) Override onSharedPreferenceChanged to update non CheckBoxPreferences when they are changed
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_general);

        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen prefScreen = getPreferenceScreen();

        int numOfPreferences = prefScreen.getPreferenceCount();
        for (int i  = 0; i < numOfPreferences;i++){
            Preference preference = prefScreen.getPreference(i);

            if (! (preference instanceof CheckBoxPreference)){
                String value = sharedPreferences.getString(preference.getKey(), "");
                setPreferenceSummary(preference, value);
            }
        }

    }

    public void setPreferenceSummary(Preference preferenceSummary, Object value){
        String stringValue = value.toString();
        String key = preferenceSummary.getKey();

        if (preferenceSummary instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preferenceSummary;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if (prefIndex >= 0){
                preferenceSummary.setSummary(listPreference.getEntries()[prefIndex]);
            }
        }else{
            preferenceSummary.setSummary(stringValue);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (preference != null && !(preference instanceof CheckBoxPreference)) {
            setPreferenceSummary(preference, sharedPreferences.getString(key,""));
        }
    }
}
