package com.example.kittimasak.hangman;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    Button[] button;
    Button en;
    TextView tekst;
    int aob = 26;
    ArrayList<String> ordliste;
    char[] ordArray;
    String ord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialiserer

        ordliste = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.ord)));
        int random = new Random().nextInt(ordliste.size());
        ord = ordliste.get(random);

        tekst = (TextView) findViewById(R.id.tekst);
        tekst.setText(String.valueOf(ord.length()));


        button = new Button[aob];
        for(int i = 0; i<button.length; i++) {
           button[i] = (Button) findViewById(getResources().getIdentifier("button"+(i+1), "id", getPackageName()));
           button[i].setOnClickListener(this);

        }
    }


    public void setArray(String s) {

    }

    @Override
    public void onClick(View view) {

        Button b = (Button) view;
        b.setAlpha(.2f);
        tekst.setText(String.valueOf(b.getText()));
        /*
        switch (view.getId()) {

            case R.id.button1:
            tekst.setText(String.valueOf(ordliste.size()));
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

        */
    }
}
