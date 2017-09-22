package com.example.kittimasak.hangman;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView text;
    TextView text1;
    TextView text2;
    ArrayList<String> wordlist;
    Set<String> wordset;
    String word;
    int random;
    int amount;
    boolean loop;
    char[] wordArray;
    char[] wordArrayTemp;
    AlertDialog.Builder builder;
    AlertDialog dialog;
    SharedPreferences sp;
    SharedPreferences.Editor spEdit;

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

            sp = PreferenceManager.getDefaultSharedPreferences(this);
            spEdit = sp.edit();
            wordlist = new ArrayList<String>();

            if (sp.contains("wordset")) {
                wordset = sp.getStringSet("wordset", wordset);
                    wordlist.addAll(wordset);
                    sp.edit().clear().commit();
            } else {
                wordlist.addAll(Arrays.asList(getResources().getStringArray(R.array.ord)));
            }

            random = new Random().nextInt(wordlist.size());

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

    @Override
    protected void onStop() {
        super.onStop();
        if(!wordlist.isEmpty()) {
            wordset = new HashSet<String>(wordlist);
            spEdit.putStringSet("wordset", wordset);
            spEdit.commit();
        }
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

    public void alertBox(String s) {
        builder.setMessage(s);
        builder.setCancelable(false);

        builder.setNeutralButton(R.string.ret, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    finish();

                    }
                });

        dialog = builder.create();
        dialog.show();
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
        removeWord();
        System.out.println(String.valueOf("LISTSIZE" + wordlist.size()));
    }

    private void removeWord() {

        String stringList;

        for(int i=0; i<wordlist.size(); i++) {
            stringList = wordlist.get(i);
            if(stringList.equals(word)) {
                wordlist.remove(i);
                System.out.println(Arrays.toString(wordlist.toArray()));
            }
        }

    }


    @Override
    public void onClick(View view) {
        Button b = (Button) view;
        String stringTemp = (String) b.getText();
        loop = true;
        gameLogic(wordArray, wordArrayTemp,Character.valueOf(stringTemp.charAt(0)), b);
        if(amount == 0) {
            alertDialog(getResources().getString(R.string.lost), " " + Arrays.toString(wordArray));
        } else if (Arrays.equals(wordArray, wordArrayTemp)) {
            alertDialog(getResources().getString(R.string.won), "");
        }
    }

}
