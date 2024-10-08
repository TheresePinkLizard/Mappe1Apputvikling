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

public class ActivityGame extends AppCompatActivity implements MinDialog.MittInterface{

    // Global variabel
    private Global antallvalgtspill;


    private TextView oppgaveteksten;
    private int currentQuestionIndex = 0;
    private int animalcounter = 0;
    private int samletcounter = 0;
    private int oppgcounter = 1;


    ArrayList<Integer> shuffledArray = new ArrayList<>();
    String[] originalArray;
    String[] riktigSvarArray;
    private int[] samletBildeArray = new int[15];

    //Dyrebilder
    int[] animalArray = {R.drawable.mouse, R.drawable.cat, R.drawable.dog,  R.drawable.monkeynew,
            R.drawable.bear, R.drawable.bunny, R.drawable.chicken,  R.drawable.fox,  R.drawable.bull,
            R.drawable.lion,  R.drawable.pig,  R.drawable.panda,  R.drawable.racoon,
            R.drawable.tiger, R.drawable.unicorn};

    //knappene
    int [] buttonIds = { R.id.tall0, R.id.tall1, R.id.tall2, R.id.tall3,R.id.tall4, R.id.tall5,
            R.id.tall6, R.id.tall7, R.id.tall8, R.id.tall9};

    // dialog for å avslutte spill
    @Override
    public void onYesClick(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    @Override
    public void onNoClick(){
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // sørger for at alt blir lagret selv om mobilen roteres
        if(savedInstanceState != null){
            animalcounter = savedInstanceState.getInt("animalcounter");
            samletcounter = savedInstanceState.getInt("samletcounter");
            oppgcounter = savedInstanceState.getInt("oppgcounter");
            currentQuestionIndex = savedInstanceState.getInt("currentQuestionIndex");
            shuffledArray = savedInstanceState.getIntegerArrayList("shuffledArray");
            samletBildeArray = savedInstanceState.getIntArray("samletImagesArray");
            // Set the images on the ImageViews
            for (int i = 0; i < samletcounter; i++) {
                String imageViewId = "samlet" + (i + 1);
                int resId = getResources().getIdentifier(imageViewId, "id", getPackageName());
                ImageView samletDyr = findViewById(resId);
                samletDyr.setImageResource(samletBildeArray[i]);
            }
        }

        // initierer global verdi
        antallvalgtspill = (Global) getApplicationContext();

        if (antallvalgtspill.getAntallSpill() == null || antallvalgtspill.getAntallSpill().isEmpty()) {
            antallvalgtspill.setAntallSpill("5");
        }
        // henter oppgaver og lager et array med shuffles spørsmål med global verdi som input
        int numberOfQuestions = Integer.parseInt(antallvalgtspill.getAntallSpill());
        if (shuffledArray.isEmpty()){
            henteOppgave(numberOfQuestions);
        }

        //starter oppgave ved å sende til display
        oppgaveteksten = findViewById(R.id.oppgavetekst);
        startOppgave();

        Intent congratulations = new Intent(this, Congratulations.class);

        // gjør at språk blir oppdatert når første oppgave loades
        EditText tittel = findViewById(R.id.oppgavetittel);
        tittel.setText(getString(R.string.oppgavetittel, oppgcounter));

        // sjekker om svaret er riktig når man trykker på knappen
            Button sjekksvaret = (Button) findViewById(R.id.sjekkSvar);
            sjekksvaret.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // henter svar
                    EditText skrivInnSvar = findViewById(R.id.skrivinn);
                    String svar = skrivInnSvar.getText().toString();

                    boolean isAnswerCorrect = sjekkSvar(svar);
                    TextView veiledning = findViewById(R.id.veiledning);

                    if(isAnswerCorrect) {
                        // hvis man er ferdig med spillet
                        if(currentQuestionIndex == numberOfQuestions-1){
                            currentQuestionIndex = 0;
                            shuffledArray.clear();
                            startActivity(congratulations);
                        } else{
                            // hopper til neste oppgave, setter tekst og velger nytt bilde fra animalArray
                            currentQuestionIndex ++;
                            veiledning.setText(getString(R.string.riktig1));
                            oppgaveteksten = findViewById(R.id.oppgavetekst);
                            startOppgave();
                            animalcounter++;
                            // setter bilde ved sidenav oppgavetekst
                            ImageView imageView = findViewById(R.id.animals);
                            imageView.setImageResource(animalArray[animalcounter]);

                            // viser samlet dyr
                            for (int i = 0; i <= samletcounter; i++) {
                                String imageViewId = "samlet" + (i + 1);  // generate the ImageView IDs dynamically
                                int resId = getResources().getIdentifier(imageViewId, "id", getPackageName());  // get the resource ID
                                ImageView samletDyr = findViewById(resId);  // find the ImageView by the resource ID
                                samletDyr.setImageResource(animalArray[i]);  // set the image resource
                                samletBildeArray[i] = animalArray[i]; // lagrer til array
                            }



                            // resetter tekstfelt på veiledning og tittel
                            EditText skrivinnfelt = findViewById(R.id.skrivinn);
                            skrivinnfelt.setText("");
                            EditText tittel = findViewById(R.id.oppgavetittel);
                            oppgcounter++;
                            tittel.setText(getString(R.string.oppgavetittel, oppgcounter));
                            samletcounter++;
                        }
                    }
                    // melding om at man er på siste oppgave
                    if (currentQuestionIndex == numberOfQuestions-1){
                        veiledning.setText(R.string.riktig2);
                    }

                    //hvis svaret er feil
                    if (!isAnswerCorrect){
                        veiledning = findViewById(R.id.veiledning);
                        veiledning.setText(R.string.feil1);
                        skrivInnSvar.setText("");
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
        Button tilbakeTilStart = (Button) findViewById(R.id.avsluttSpillKnapp);
        tilbakeTilStart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                MinDialog dialog = new MinDialog();
                dialog.show(getSupportFragmentManager(), "ExitGameDialog");
            }
        });

        // tømme skriv inn felt ved å trykke på knappen tøm
        Button empthyfield = (Button) findViewById(R.id.clear);
        empthyfield.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText skrivinnfelt = findViewById(R.id.skrivinn);
                skrivinnfelt.setText("");
            }
        });


    }
// kode som starter hver oppgave
    public void startOppgave(){
        originalArray = getResources().getStringArray(R.array.questions);

        if (!shuffledArray.isEmpty()){
            oppgaveteksten.setText((originalArray[shuffledArray.get(currentQuestionIndex)]));
        }
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
        // lager nytt array med spørsmål som er shuffled
        for (int i = 0; i < originalArray.length; i++){
            shuffledArray.add(i);
        }
        //shuffler array
        Collections.shuffle(shuffledArray);

        // hvis antall oppgaver er mindre en arrayet sin størrelse så lages det en sublist
        if (antall < shuffledArray.size()){
            shuffledArray = new ArrayList<>(shuffledArray.subList(0,antall));
        } else{
            shuffledArray = new ArrayList<>(shuffledArray);
        }

    }
    public boolean sjekkSvar(String svar){
        //henter array med svar
        riktigSvarArray = getResources().getStringArray(R.array.answers);
        int shuffledIndex = shuffledArray.get(currentQuestionIndex);

        String currentQuestion = originalArray[shuffledIndex];

        String correctAnswer = riktigSvarArray[shuffledIndex];

        if (svar.equals(correctAnswer)){
            return true;
        }
            // hvis svaret ikke stemmer
        return false;
    }


    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("MinePreferanser", Context.MODE_PRIVATE);

       // lagrer antall spill til global verdi
        String antallmattestykker = sharedPreferences.getString("antallmattestykker", "5");
        antallvalgtspill.setAntallSpill(antallmattestykker);

        // lagrer antall dyr til global verdi
        String antallDyr = sharedPreferences.getString("antallDyr", "0");
        antallvalgtspill.setAntallSpill(antallmattestykker);
    }
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences("MinePreferanser", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // lagre til global variabel
        String antallmattestykker = sharedPreferences.getString("antallmattestykker", "5");
        antallvalgtspill.setAntallSpill(antallmattestykker);

        editor.apply();
    }
    @Override
    protected void onSaveInstanceState(Bundle outstate) {
        super.onSaveInstanceState(outstate);
        EditText textView = findViewById(R.id.skrivinn);
        outstate.putString("antall", textView.getText().toString());
        outstate.putInt("animalcounter", animalcounter);
        outstate.putInt("samletcounter", samletcounter);
        outstate.putInt("oppgcounter", oppgcounter);
        outstate.putInt("currentQuestionIndex", currentQuestionIndex);
        outstate.putIntegerArrayList("shuffledArray", shuffledArray);
        outstate.putIntArray("samletImagesArray", samletBildeArray);

    }
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        EditText tw = findViewById(R.id.skrivinn);
        tw.setText(savedInstanceState.getString("antall"));
    }
}

