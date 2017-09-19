package com.example.kittimasak.hangman;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tekst;
    TextView tekst1;
    TextView tekst2;
    ArrayList<String> ordliste;
    String ord;
    int antallforsok;
    boolean loop;
    char[] ordArraytemp;
    char[] temptemp;
    AlertDialog.Builder builder;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null) {

            ordArraytemp = savedInstanceState.getCharArray("ordArray");
            temptemp = savedInstanceState.getCharArray("tempArray");
            antallforsok = savedInstanceState.getInt("antall");
            ordliste = savedInstanceState.getStringArrayList("ordliste");

        } else {

            ordliste = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.ord)));
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

        builder = new AlertDialog.Builder(this);
        tekst = (TextView) findViewById(R.id.tekst);
        tekst1 = (TextView) findViewById(R.id.tekst1);
        tekst2 = (TextView) findViewById(R.id.tekst2);
        tekst.setText(Arrays.toString(ordArraytemp));
        tekst1.setText(Arrays.toString(temptemp));
        tekst2.setText(String.valueOf(antallforsok));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putCharArray("ordArray", ordArraytemp);
        outState.putCharArray("tempArray", temptemp);
        outState.putInt("antall", antallforsok);
        outState.putStringArrayList("ordliste", ordliste);
    }

    public void counter(boolean b) {
        if(b) {
            antallforsok--;
        }
    }

    public void setArray(char[] ordArraytemp,String s) {
        String k = s.toUpperCase();
        for(int i=0;i<k.length(); i++) {
            ordArraytemp[i] = k.charAt(i);
        }
    }

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

    public void alertDialog(String s, String s1) {
        builder.setMessage(s + s1);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.ret, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        builder.setNegativeButton(R.string.retry, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                finish();
                startActivity(getIntent());

            }
        });
        dialog = builder.create();
        dialog.show();
    }


    @Override
    public void onClick(View view) {
        Button b = (Button) view;
        String stringTemp = (String) b.getText();
        loop = true;
        gameLogic(ordArraytemp, temptemp,Character.valueOf(stringTemp.charAt(0)), b);
        if(antallforsok == 0) {
            alertDialog(getResources().getString(R.string.lost), " " + Arrays.toString(ordArraytemp));
        } else if(Arrays.equals(ordArraytemp, temptemp)) {
            alertDialog(getResources().getString(R.string.won), "");
        }
    }

}
