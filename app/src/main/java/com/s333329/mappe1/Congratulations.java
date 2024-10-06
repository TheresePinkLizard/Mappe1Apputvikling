package com.s333329.mappe1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Congratulations extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.congratulations);


        Button startNyttSpill = (Button) findViewById(R.id.nyttSpill);
        Intent i = new Intent(this, ActivityGame.class);

        //kode for å gjøre at data fra forrige spill fjernes
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startNyttSpill.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(i);
            }
        });
        Button sluttSpill = (Button) findViewById(R.id.spillSlutt);
        Intent j = new Intent(this, MainActivity.class);
        sluttSpill.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(j);
            }
        });
    }
}
