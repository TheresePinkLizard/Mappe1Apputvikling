package com.s333329.mappe1;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if ("velgspraak".equals(key)) {
                    String language = sharedPreferences.getString(key, "");
                    setLocale(language);
                    recreate();
                }
            }
        });
    }

    private void setLocale(String language) {
        Locale locale;
        switch (language) {
            case "Norsk":
                locale = new Locale("no");
                break;
            case "Tysk":
                locale = new Locale("de");
                break;
            default:
                locale = new Locale("no");
                break;

        }

        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }
}

