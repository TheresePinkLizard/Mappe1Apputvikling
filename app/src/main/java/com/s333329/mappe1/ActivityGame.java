package com.s333329.mappe1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityGame extends AppCompatActivity {

    // Global variabel
    private Global global;
    private EditText et;

    private EditText tall;
    private TextView oppgavetekst;
    private int antall = 0;
    private int randomIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // initierer global verdi
        global = (Global) getApplicationContext();
        // endrer til global verdi
        et = findViewById(R.id.skrivinn);
        et.setText(global.getminvar());

        //knappene
        Button tallet0 = (Button) findViewById(R.id.tall0);
        Button tallet1 = (Button) findViewById(R.id.tall1);
        Button tallet2 = (Button) findViewById(R.id.tall2);
        Button tallet3 = (Button) findViewById(R.id.tall3);
        Button tallet4 = (Button) findViewById(R.id.tall4);
        Button tallet5 = (Button) findViewById(R.id.tall5);
        Button tallet6 = (Button) findViewById(R.id.tall6);
        Button tallet7 = (Button) findViewById(R.id.tall7);
        Button tallet8 = (Button) findViewById(R.id.tall8);
        Button tallet9 = (Button) findViewById(R.id.tall9);

       //Avslutt spill knapp
            // åpner dialogboks som spør om det er dette du vil gjøre, ok fører til første side


        //knapp som sjekket svar og går til neste oppgave
        Button sjekksvaret = (Button) findViewById(R.id.sjekkSvar);
        sjekksvaret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (henterRiktigSvar()){
                    henteOppgave();
                    antall ++;

                    if(antall == ){
                        // hvis antall er samme som sharedpreference antallmattestykker
                        // så skal man få opp en dialogboks som sier spillet er ferdig, spill igjen?
                        //og lede tilbake til startsiden
                    }
                }else{
                    // veiledning viser at svaret er feil men er oppmuntrende
                    //tømmer feltet som er skrevet inn
                }
            }
        });


    }
    // kode som henter valgt antall oppgaver i sharedpreferences

    //henter oppgaver
    public void henteOppgave(){
        //henter array
        String[] questions = getResources().getStringArray(R.array.questions);
        // random spørsmål
        Random rand = new Random();
        randomIndex = rand.nextInt(questions.length);
        //velger random posisjon
        String randomQuestion = questions[randomIndex];

        // skriver ut random spørsmål
        oppgavetekst = findViewById(R.id.oppgavetekst);
        oppgavetekst.setText(String.valueOf(randomQuestion));

    }
    
    //hente riktig svar
    public boolean henterRiktigSvar(){
        String[] answers = getResources().getStringArray(R.array.answers);
        //tall = (EditText) findViewById(R.id.skrivinn);
        int riktigSvar = answers[randomIndex];
        if (et == riktigSvar){
            return true;
        }else{
            return false;
        }
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

        // lagre til global variabel
        String globalText = et.getText().toString();
        global.setminvar(globalText);

        editor.apply();
    }
    @Override
    protected void onSaveInstanceState(Bundle outstate) {
        super.onSaveInstanceState(outstate);
        EditText textView = findViewById(R.id.skrivinn);
        outstate.putString("antall", textView.getText().toString());
    }
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        EditText tw = findViewById(R.id.skrivinn);
        tw.setText(savedInstanceState.getString("antall"));
    }
}

