package com.s333329.mappe1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityAboutGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutgame);

        Button tilbakeTilStart = (Button) findViewById(R.id.tilbakeStart);

        Intent i = new Intent(this, MainActivity.class);

        tilbakeTilStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(i);
            }
        });
    }
}