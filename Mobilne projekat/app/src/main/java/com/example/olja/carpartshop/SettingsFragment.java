package com.example.olja.carpartshop;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.widget.Toast;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String autoValue = sharedPreferences.getString("defaultbackground", "whitepic");

        if(autoValue.equals("whitepic")) {
            final CheckBoxPreference pref = (CheckBoxPreference) findPreference("defaultthemacheck");
            pref.setChecked(true);
        }
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings_fragment);

        final Preference pref = (Preference) findPreference("defaultthemacheck");

        pref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
                SharedPreferences.Editor editor = sp.edit();
                if(sp.getString("defaultbackground", "greenback").equals("greenback")) {
                    editor.putString("defaultbackground", "whitepic");
                } else {
                    editor.putString("defaultbackground", "greenback");
                }

                editor.apply();
                return true;
            }
        });
    }
}