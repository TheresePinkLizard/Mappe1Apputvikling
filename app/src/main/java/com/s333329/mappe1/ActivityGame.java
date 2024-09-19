package com.s333329.mappe1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityGame extends AppCompatActivity {

    // Global variabel
    private Global global;
    private TextView oppgavetekst;
    private TextView veiledning;
    private int currentQuestionIndex;

    ArrayList<String> shuffledArray = new ArrayList<>();
    String[] originalArray;
    int[] riktigSvarArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        // initierer global verdi
        global = (Global) getApplicationContext();

        // putter antall spill preferanse til global verdi
      global = (Global) getApplicationContext();
      SharedPreferences sharedPreferences = getSharedPreferences(getPackageName() + "_preferences", MODE_PRIVATE);
        String antallmattestykker = sharedPreferences.getString("antallmattestykker", "5");
      global.setminvar(antallmattestykker);


        // henter oppgaver og lager et array med shuffles spørsmål med global verdi som input
        int numberOfQuestions = Integer.parseInt(global.getminvar());
        henteOppgave(numberOfQuestions);
        startOppgave();

        // sjekker om svaret er rikitig når man trykker på knappen
            Button sjekksvaret = (Button) findViewById(R.id.sjekkSvar);
            sjekksvaret.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(sjekkSvar()) {
                        currentQuestionIndex ++;
                        veiledning.setText("Riktig! Her er neste oppgave");
                        startOppgave();
                    }
                    // melding om at man er på siste oppgave
                    if (currentQuestionIndex == shuffledArray.size()){
                        veiledning.setText("Riktig! du er på siste spørsmål! Du har nesten klart det!");
                    }

                    // hvis man er ferdig med spillet
                    if(currentQuestionIndex == shuffledArray.size()+1){
                        // kommer opp dialog som spør om du skal avslutte spillet
                    }
                    //hvis svaret er feil
                    if (!sjekkSvar()){
                        veiledning = findViewById(R.id.veiledning);
                        veiledning.setText("Nesten! prøv igjen! Du kan klare det!");
                    }
                }
            });

        //knappene
        int [] buttonIds = { R.id.tall0, R.id.tall1, R.id.tall2, R.id.tall3,R.id.tall4, R.id.tall5,
                R.id.tall6, R.id.tall7, R.id.tall8, R.id.tall9};

        final EditText skrivinnfelt = findViewById(R.id.skrivinn);

        // hvis en knapp trykkes så flyttes verdien inn i tekstfeltet
        for (int id : buttonIds){
            Button button = findViewById(id);
            button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    // henter knappnummer
                    String buttomText = ((Button) v).getText().toString();

                    // flytter nummer til tekstfelt
                    skrivinnfelt.setText(skrivinnfelt.getText().toString() + buttomText);
                }
            });
        }

        //Avslutt spill knapp
        // åpner dialogboks som spør om det er dette du vil gjøre, ok fører til første side
        Button tilbakeTilStart = (Button) findViewById(R.id.avsluttSpillKnapp);
        Intent i = new Intent(this, MainActivity.class);
        tilbakeTilStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(i);
            }
        });


    }

    public void startOppgave(){
        oppgavetekst = findViewById(R.id.oppgavetekst);
        oppgavetekst.setText(String.valueOf(shuffledArray.get(currentQuestionIndex)));
    }
    //henter oppgaver og putter verdier i array
    public void henteOppgave(int antall){
        //henter array med spørsmål
        originalArray = getResources().getStringArray(R.array.questions);
        //henter array med svar
        riktigSvarArray = getResources().getIntArray(R.array.answers);

        // lager nytt array med spørsmål som er shuffled
        List<String> nyttArray = Arrays.asList(originalArray);

        //shuffler array
        Collections.shuffle(nyttArray);

        // hvis antall oppgaver er mindre en arrayet sin størrelse så lages det en sublist
        if (antall < nyttArray.size()){
            shuffledArray = new ArrayList<>(nyttArray.subList(0,antall));
        } else{
            shuffledArray = new ArrayList<>(nyttArray);
        }
        currentQuestionIndex = 0;

    }
    public boolean sjekkSvar(){
        //knapp som sjekket svar og går til neste oppgave
        // loope igjennom shufflearray, finne match til originalarray
        // ta indexsen av det og sammenligne med index av svar array
        
    }


    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("MinePreferanser", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("brukernavn", "");
        int age = sharedPreferences.getInt("alder", 0);
        boolean isLoggedIn = sharedPreferences.getBoolean("innlogget", false);

        // Restored the text of EditText from the global variable
        String antallmattestykker = sharedPreferences.getString("antallmattestykker", "5");
        global.setminvar(antallmattestykker);
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
        String antallmattestykker = sharedPreferences.getString("antallmattestykker", "5");
        global.setminvar(antallmattestykker);

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

