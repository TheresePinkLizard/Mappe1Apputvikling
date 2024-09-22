package com.s333329.mappe1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
    private int currentQuestionIndex = 0;
    private String svar;
    private int animalcounter = 0;
    private int samletcounter = 0;
    private int oppgcounter = 1;

    ArrayList<String> shuffledArray = new ArrayList<>();
    String[] originalArray;
    String[] riktigSvarArray;

    //Dyrebilder
    int[] animalArray = {R.drawable.mouse, R.drawable.cat, R.drawable.dog,  R.drawable.monkeynew,
            R.drawable.bear, R.drawable.bunny, R.drawable.chicken,  R.drawable.fox,  R.drawable.bull,
            R.drawable.lion,  R.drawable.pig,  R.drawable.panda,  R.drawable.racoon,
            R.drawable.tiger, R.drawable.unicorn};

    //knappene
    int [] buttonIds = { R.id.tall0, R.id.tall1, R.id.tall2, R.id.tall3,R.id.tall4, R.id.tall5,
            R.id.tall6, R.id.tall7, R.id.tall8, R.id.tall9};


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
        //starter oppgave ved å sende til display
        startOppgave();

        // sjekker om svaret er riktig når man trykker på knappen
            Button sjekksvaret = (Button) findViewById(R.id.sjekkSvar);
            sjekksvaret.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isAnswerCorrect = sjekkSvar();
                    TextView veiledning = findViewById(R.id.veiledning);

                    if(isAnswerCorrect) {
                        // hopper til neste oppgave, setter tekst og velger nytt bilde fra animalArray
                        currentQuestionIndex ++;
                        veiledning.setText(getString(R.string.riktig1));
                        startOppgave();
                        animalcounter++;
                        ImageView imageView = findViewById(R.id.animals);
                        imageView.setImageResource(animalArray[animalcounter]);

                        // kode for å vise hvilke dyr som er samlet
                        String imageViewId = "samlet" + (samletcounter + 1);
                        // henter id til nåværende bilde
                        int resId = getResources().getIdentifier(imageViewId, "id", getPackageName());
                        ImageView samletDyr = findViewById(resId);
                        samletDyr.setImageResource(animalArray[samletcounter]);
                        samletcounter ++;
                        if (samletcounter == animalArray.length){
                            samletcounter = 0;
                        }

                        // resetter tekstfelt på veiledning og tittel
                        EditText skrivinnfelt = findViewById(R.id.skrivinn);
                        skrivinnfelt.setText("");
                        EditText tittel = findViewById(R.id.oppgavetittel);
                        oppgcounter++;
                        tittel.setText("Oppgave " + oppgcounter);
                    }
                    // melding om at man er på siste oppgave
                    if (currentQuestionIndex == shuffledArray.size()){
                        veiledning.setText(R.string.riktig2);
                    }

                    // hvis man er ferdig med spillet
                    if(currentQuestionIndex == shuffledArray.size()+1){
                        // kommer opp dialog som spør om du skal avslutte spillet
                    }
                    //hvis svaret er feil
                    if (!isAnswerCorrect){
                        veiledning = findViewById(R.id.veiledning);
                        veiledning.setText(R.string.feil1);
                    }
                }
            });


        EditText skrivinnfelt = findViewById(R.id.skrivinn);

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
        // Hvert spørsmål har et bilde av et dyr
        ImageView imageView = findViewById(R.id.animals);
        imageView.setImageResource(animalArray[animalcounter]);

        EditText tittel = findViewById(R.id.oppgavetittel);
        tittel.setText("Oppgave " + oppgcounter);
    }
    //henter oppgaver og putter verdier i array
    public void henteOppgave(int antall){
        //henter array med spørsmål
        originalArray = getResources().getStringArray(R.array.questions);
        //henter array med svar
        riktigSvarArray = getResources().getStringArray(R.array.answers);

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

    }
    public boolean sjekkSvar(){
        // loope igjennom shufflearray, finne matchende spørsmål i originalarray
        // ta verdien på indexen til originalarray og sammenligne med verdien på
        // samme index i String[] riktigSvarArray;
/*
        EditText skrivinn = findViewById(R.id.skrivinn);
        String brukersvar = skrivinn.getText().toString();

        String currentQuestion = shuffledArray.get(currentQuestionIndex);
        int index = Arrays.asList(originalArray).indexOf(currentQuestion);

        if (index != -1) {
            svar = riktigSvarArray[index];
            if (brukersvar.equals(svar)) {
                return true;
            }return false;
        } */
        return true;



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

