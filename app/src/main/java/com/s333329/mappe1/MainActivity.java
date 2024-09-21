package com.s333329.mappe1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements MinDialog.MittInterface {


    @Override
    public void onYesClick() {
        finish();
    }

    @Override
    public void onNoClick() {
        return;
    }

    public void visDialog(View v) {
        DialogFragment dialog = new MinDialog();
        dialog.show(getSupportFragmentManager(), "Dialog");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // avslutte app
        Button avsluttknapp = findViewById(R.id.dialog);
        avsluttknapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                visDialog(view);
            }
        });
        // starte spill
        Button startSpill = (Button) findViewById(R.id.startspill);
        Intent i = new Intent(this, ActivityGame.class);
        startSpill.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(i);
            }
        });

        // gå til om spill side
        Button omSpill = (Button) findViewById(R.id.omSpillet);
        Intent j = new Intent(this, ActivityAboutGame.class);
        omSpill.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(j);
            }
        });
        // gå til preferanseside
        Button preferanseknapp = findViewById(R.id.preferanser);
        Intent intent = new Intent(this,SettingsActivity.class);
        preferanseknapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
    }





}

