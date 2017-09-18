package com.example.kittimasak.hangman;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

    TextView tekst;
    TextView tekst1;
    TextView tekst2;
    ArrayList<String> ordliste;
    //ArrayList<Character> ordArray;
    //ArrayList<Character> temp;
    String ord;
    int antallforsok;
    boolean loop;
    char[] ordArraytemp;
    char[] temptemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null) {

            ordArraytemp = savedInstanceState.getCharArray("ordArray");
            temptemp = savedInstanceState.getCharArray("tempArray");
            antallforsok = savedInstanceState.getInt("antall");

        } else {

            ordliste = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.ord)));
            //ordArray = new ArrayList<Character>();
            //temp = new ArrayList<Character>();
            int random = new Random().nextInt(ordliste.size());
            ord = ordliste.get(random);
            ordArraytemp = new char[ord.length()];
            temptemp = new char[ord.length()];
            antallforsok = 6;
            loop = true;

            setArray(ordArraytemp, ord);


            for (int i=0; i<ordArraytemp.length; i++) {
                Character t = '_';
                temptemp[i] = t;
            }

        }

        tekst = (TextView) findViewById(R.id.tekst);
        tekst1 = (TextView) findViewById(R.id.tekst1);
        tekst2 = (TextView) findViewById(R.id.tekst2);
        tekst.setText(Arrays.toString(ordArraytemp));
        tekst1.setText(Arrays.toString(temptemp));
        //tekst1.setText(TextUtils.join(" ",temp));
        tekst2.setText(String.valueOf(antallforsok));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putCharArray("ordArray", ordArraytemp);
        outState.putCharArray("tempArray", temptemp);
        outState.putInt("antall", antallforsok);


        /*
        Sende inn følgende:
        -ordArray
        -temp
        -TextView tekst1, tekst2, tekst3
        -antallforsok
         */

    }

    public void counter(boolean b) {
        if(b) {
            antallforsok--;
        }
    }

    /*
    public void setArray(ArrayList<Character> ord,String s) {
        String k = s.toUpperCase();
        for(int i=0;i<k.length(); i++) {
            ord.add(k.charAt(i));
        }
    }
    */
    public void setArray(char[] ordArraytemp,String s) {
        String k = s.toUpperCase();
        for(int i=0;i<k.length(); i++) {
            ordArraytemp[i] = k.charAt(i);
        }
    }

/*
    public void gameLogic(ArrayList<Character> ord, ArrayList<Character> temp, Character c, Button b) {
        if(antallforsok>=1 && !(temp.equals(ord))) {
        b.setBackgroundColor(Color.RED);
            for (int i = 0; i < ord.size(); i++) {
                if (c.equals(ord.get(i))) {
                    loop = false;
                    b.setBackgroundColor(Color.GREEN);
                    temp.set(i, c);
                }
            }
            counter(loop);
            tekst1.setText(TextUtils.join(" ",temp));
            tekst2.setText(String.valueOf(antallforsok));
            b.setEnabled(false);
        }
    }

*/
    public void gameLogic(char[] array1, char[] array2, Character c, Button b) {
        if(antallforsok>=1 && !Arrays.equals(ordArraytemp, temptemp)) {
            b.setBackgroundColor(Color.RED);
            for (int i = 0; i < array1.length; i++) {
                if (c.equals(array1[i])) {
                    loop = false;
                    b.setBackgroundColor(Color.GREEN);
                    array2[i] = c;
                }
            }
            counter(loop);
            tekst1.setText(Arrays.toString(temptemp));
            tekst2.setText(String.valueOf(antallforsok));
            b.setEnabled(false);
        }
    }

    @Override
    public void onClick(View view) {
        Button b = (Button) view;
        String stringTemp = (String) b.getText();
        loop = true;
        gameLogic(ordArraytemp, temptemp,Character.valueOf(stringTemp.charAt(0)), b);
        if(antallforsok == 0) {
            tekst.setText("TAPTE");
        } else if(Arrays.equals(ordArraytemp, temptemp)) {
            tekst.setText("VANT");
        }
    }
}
