package com.s333329.mappe1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityGame extends AppCompatActivity {

    // Added a Global variable
    private EditText et;
    private Global global;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Initialized the Global variable
        global = (Global) getApplicationContext();
        // Set the text of EditText from the global variable
        et.setText(global.getminvar());
        et = findViewById(R.id.skrivinn);
    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("MinePreferanser", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("brukernavn", "");
        int age = sharedPreferences.getInt("alder", 0);
        boolean isLoggedIn = sharedPreferences.getBoolean("innlogget", false);

        // Restored the text of EditText from the global variable
        String globalText = global.getminvar();
        et.setText(globalText);
    }
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences("MinePreferanser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("brukernavn", "JohnDoe");
        editor.putInt("alder", 30);
        editor.putBoolean("innlogget", true);

        // Saved the current text of EditText to the global variable
        String globalText = et.getText().toString();
        global.setminvar(globalText);

        editor.apply();
    }
    @Override
    protected void onSaveInstanceState(Bundle outstate) {
        super.onSaveInstanceState(outstate);
        EditText textView = findViewById(R.id.tekst);
        outstate.putString("antall", textView.getText().toString());
    }
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        EditText tw = findViewById(R.id.tekst);
        tw.setText(savedInstanceState.getString("antall"));
    }
}

