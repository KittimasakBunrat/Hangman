package com.example.kittimasak.hangman;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView text;
    TextView text1;
    TextView text2;
    ArrayList<String> wordlist;
    String word;
    int amount;
    boolean loop;
    char[] wordArray;
    char[] wordArrayTemp;
    AlertDialog.Builder builder;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null) {

            wordArray = savedInstanceState.getCharArray("ordArray");
            wordArrayTemp = savedInstanceState.getCharArray("tempArray");
            amount = savedInstanceState.getInt("antall");
            wordlist = savedInstanceState.getStringArrayList("wordlist");

        } else {

            wordlist = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.ord)));
            int random = new Random().nextInt(wordlist.size());
            word = wordlist.get(random);
            wordArray = new char[word.length()];
            wordArrayTemp = new char[word.length()];
            amount = 6;
            loop = true;

            setArray(wordArray, word);


            for (int i = 0; i< wordArray.length; i++) {
                Character t = '_';
                wordArrayTemp[i] = t;
            }

        }

        builder = new AlertDialog.Builder(this);
        text = (TextView) findViewById(R.id.tekst);
        text1 = (TextView) findViewById(R.id.tekst1);
        text2 = (TextView) findViewById(R.id.tekst2);
        text.setText(Arrays.toString(wordArray));
        text1.setText(Arrays.toString(wordArrayTemp));
        text2.setText(String.valueOf(amount));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putCharArray("ordArray", wordArray);
        outState.putCharArray("tempArray", wordArrayTemp);
        outState.putInt("antall", amount);
        outState.putStringArrayList("wordlist", wordlist);
    }

    public void counter(boolean b) {
        if(b) {
            amount--;
        }
    }

    public void setArray(char[] ordArraytemp,String s) {
        String k = s.toUpperCase();
        for(int i=0;i<k.length(); i++) {
            ordArraytemp[i] = k.charAt(i);
        }
    }

    public void gameLogic(char[] array1, char[] array2, Character c, Button b) {
        if(amount >=1 && !Arrays.equals(wordArray, wordArrayTemp)) {
            b.setBackgroundColor(Color.RED);
            for (int i = 0; i < array1.length; i++) {
                if (c.equals(array1[i])) {
                    loop = false;
                    b.setBackgroundColor(Color.GREEN);
                    array2[i] = c;
                }
            }
            counter(loop);
            text1.setText(Arrays.toString(wordArrayTemp));
            text2.setText(String.valueOf(amount));
            b.setEnabled(false);
        }
    }

    public void alertDialog(String s, String s1) {
        builder.setMessage(s + s1);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.ret, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                finish();

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
        gameLogic(wordArray, wordArrayTemp,Character.valueOf(stringTemp.charAt(0)), b);
        if(amount == 0) {
            alertDialog(getResources().getString(R.string.lost), " " + Arrays.toString(wordArray));
        } else if(Arrays.equals(wordArray, wordArrayTemp)) {
            alertDialog(getResources().getString(R.string.won), "");
        }
    }

}
