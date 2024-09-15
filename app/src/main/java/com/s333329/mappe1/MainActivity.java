package com.s333329.mappe1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

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




        Button avsluttknapp = findViewById(R.id.dialog);
        avsluttknapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                visDialog(view);
            }
        });

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

