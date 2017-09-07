package com.example.kittimasak.hangman;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    Button[] button;
    Button en;
    TextView tekst;
    TextView tekst1;
    TextView tekst2;
    int aob = 26;
    ArrayList<String> ordliste;
    ArrayList<Character> ordArray;
    ArrayList<Character> temp;
    String ord;
    final int maxforsok = 6;
    int antallforsok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialiserer

        ordliste = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.ord)));
        ordArray = new ArrayList<Character>();
        temp = new ArrayList<Character>();
        int random = new Random().nextInt(ordliste.size());
        ord = ordliste.get(random);
        antallforsok = 0;

        setArray(ordArray, ord);

        for (int i=0; i<ordArray.size(); i++) {
            Character t = null;
            temp.add(t);
        }

        tekst = (TextView) findViewById(R.id.tekst);
        tekst1 = (TextView) findViewById(R.id.tekst1);
        tekst2 = (TextView) findViewById(R.id.tekst2);
        tekst.setText(Arrays.toString(ordArray.toArray()));



        button = new Button[aob];
        for(int i = 0; i<button.length; i++) {
           button[i] = (Button) findViewById(getResources().getIdentifier("button"+(i+1), "id", getPackageName()));
           button[i].setOnClickListener(this);

        }
    }


    public void setArray(ArrayList<Character> ord,String s) {
        String k = s.toUpperCase();
        for(int i=0;i<k.length(); i++) {
            ord.add(k.charAt(i));
        }
    }

    public void gameLogic(ArrayList<Character> ord, ArrayList<Character> temp, Character c, Button b) {
        b.setBackgroundColor(Color.RED);
        for (int i=0; i<ord.size(); i++) {
            if(c.equals(ord.get(i))) {
                b.setBackgroundColor(Color.GREEN);
                temp.set(i,c);
            }
        }
        tekst1.setText(Arrays.toString(temp.toArray()));
        tekst2.setText(String.valueOf(antallforsok));
        b.setEnabled(false);
    }



    @Override
    public void onClick(View view) {

        Button b = (Button) view;
        String stringTemp = (String) b.getText();
        gameLogic(ordArray, temp,Character.valueOf(stringTemp.charAt(0)), b);

    }
}
