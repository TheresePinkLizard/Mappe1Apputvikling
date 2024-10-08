package com.s333329.mappe1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;
import android.content.SharedPreferences;

import java.util.Arrays;
import java.util.List;

public class Preferences extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preferences);

        Global global = (Global) getApplicationContext();

        Button tilbakeTilStart = (Button) findViewById(R.id.tilbakeStart);

        Button femSpill = findViewById(R.id.femSpill);
        Button tiSpill = findViewById(R.id.tiSpill);
        Button femtenSpill = findViewById(R.id.femtenSpill);

        Button tysk = findViewById(R.id.tilTysk);
        Button norsk = findViewById(R.id.tilNorsk);


        // tilbake til start
        Intent i = new Intent(this, MainActivity.class);
        tilbakeTilStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(i);
            }
        });

        femSpill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Store button text in global variable
                String newValue = femSpill.getText().toString();
                global.setAntallSpill(newValue);


                // Save to SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("MinePreferanser", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("antallmattestykker", newValue);
                editor.apply();
            }
        });
        tiSpill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Store button text in global variable
                String newValue = tiSpill.getText().toString();
                global.setAntallSpill(newValue);


                // Save to SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("MinePreferanser", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("antallmattestykker", newValue);
                editor.apply();
            }
        });
        femtenSpill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Store button text in global variable
                String newValue = femtenSpill.getText().toString();
                global.setAntallSpill(newValue);

                // Save to SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("MinePreferanser", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("antallmattestykker", newValue);
                editor.apply();
            }
        });


        tysk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocaleListCompat appLocale = LocaleListCompat.forLanguageTags("de-DE");
                AppCompatDelegate.setApplicationLocales(appLocale);
            }
        });


        norsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocaleListCompat appLocale = LocaleListCompat.forLanguageTags("no-NO");
                AppCompatDelegate.setApplicationLocales(appLocale);
            }
        });

    }

}
