package com.example.kittimasak.hangman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    Button[] button;
    Button en;
    TextView tekst;
    int aob = 25;
    List<String> ordliste;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Initialiserer

        tekst = (TextView) findViewById(R.id.tekst);
        ordliste = Arrays.asList(getResources().getStringArray(R.array.ord));

        button = new Button[aob];
        for(int i = 0; i<button.length; i++) {
           button[i] = (Button) findViewById(getResources().getIdentifier("button"+(i+1), "id", getPackageName()));
           button[i].setOnClickListener(this);

        }
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.button1:
            tekst.setText(ordliste.size());
                break;

            case R.id.button2:
                tekst.setText(ordliste.get(1));
                break;

            case R.id.button3:
                tekst.setText(ordliste.get(2));
                break;

            case R.id.button4:
                tekst.setText("Fire");
                break;

            case R.id.button5:
                tekst.setText("Fem");
                break;

            default:
                break;

        }
    }
}
