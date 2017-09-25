package com.example.kittimasak.s300342;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kittimasak.hangman.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView text1;
    TextView text2;
    ImageView iv;
    ArrayList<String> wordlist;
    Set<String> wordset;
    String word;
    int random;
    int amount;
    int win;
    int loss;
    boolean loop;
    char[] wordArray;
    char[] wordArrayTemp;
    AlertDialog.Builder builder;
    AlertDialog dialog;
    AlertDialog.Builder builder1;
    AlertDialog dialog1;
    SharedPreferences sp;
    SharedPreferences.Editor spEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wordlist = new ArrayList<String>();

        if(savedInstanceState != null) {

            wordArray = savedInstanceState.getCharArray("ordArray");
            wordArrayTemp = savedInstanceState.getCharArray("tempArray");
            amount = savedInstanceState.getInt("antall");
            wordlist = savedInstanceState.getStringArrayList("wordlist");

        } else {

            sp = getSharedPreferences("Hangman", MODE_PRIVATE);
            spEdit = sp.edit();

            if(sp.contains("win")) {
                win = sp.getInt("win", win);
            }
            if(sp.contains("loss")) {
                loss = sp.getInt("loss", loss);
            }

            if(sp.contains("wordset")) {
                wordset = sp.getStringSet("wordset", wordset);
                wordlist.addAll(wordset);
                sp.edit().remove("wordset").commit();

            } else {
                wordlist.addAll(Arrays.asList(getResources().getStringArray(R.array.ord)));
            }

            random = new Random().nextInt(wordlist.size());
            word = wordlist.get(random);
            wordArray = new char[word.length()];
            wordArrayTemp = new char[word.length()];
            amount = 7;
            loop = true;

            setArray(wordArray, word);


            for (int i = 0; i< wordArray.length; i++) {
                Character t = '_';
                wordArrayTemp[i] = t;
            }

        }

        builder = new AlertDialog.Builder(this);
        builder1 = new AlertDialog.Builder(this);
        text1 = (TextView) findViewById(R.id.tekst1);
        text2 = (TextView) findViewById(R.id.tekst2);
        iv = (ImageView) findViewById(R.id.bilde);
        iv.setImageResource(R.drawable.first);
        text1.setText(wordFixed(wordArrayTemp));
        text2.setText(getResources().getString(R.string.amount) + "" + String.valueOf(amount));
    }

    public String wordFixed(char[] array) {
        String wordReplaced = Arrays.toString(array)
                .replace(",", "")  //remove the commas
                .replace("[", "")  //remove the right bracket
                .replace("]", "")  //remove the left bracket
                .trim();
        return wordReplaced;
    }

    public void setImage() {
        if(amount == 6) {
            iv.setImageResource(R.drawable.second);
        } else if(amount == 5) {
            iv.setImageResource(R.drawable.third);
        } else if (amount == 4) {
            iv.setImageResource(R.drawable.fourth);
        } else if (amount == 3) {
            iv.setImageResource(R.drawable.fifth);
        } else if (amount == 2) {
            iv.setImageResource(R.drawable.sixth);
        } else if (amount == 1) {
            iv.setImageResource(R.drawable.seven);
        } else if (amount == 0) {
            iv.setImageResource(R.drawable.eight);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putCharArray("ordArray", wordArray);
        outState.putCharArray("tempArray", wordArrayTemp);
        outState.putInt("antall", amount);
        outState.putStringArrayList("wordlist", wordlist);
    }

     private void save() {
         if(!wordlist.isEmpty()) {
            wordset = new LinkedHashSet<String>(wordlist);
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
            text1.setText(wordFixed(wordArrayTemp));
            text2.setText(getResources().getString(R.string.amount) + "" + String.valueOf(amount));
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
        removeWord();
        save();
        dialog = builder.create();
        dialog.show();
    }

    private void removeWord() {

        for(int i=0; i<wordlist.size(); i++) {
            if(word.equals(wordlist.get(i))) {
                wordlist.remove(i);
            }
        }

    }

    private void win() {
        win = win + 1;
        spEdit.putInt("win", win);
        spEdit.commit();
    }

    private void loss() {
        loss = loss + 1;
        spEdit.putInt("loss", loss);
        spEdit.commit();
    }

    @Override
    public void onClick(View view) {
        Button b = (Button) view;
        String stringTemp = (String) b.getText();
        loop = true;
        gameLogic(wordArray, wordArrayTemp,Character.valueOf(stringTemp.charAt(0)), b);
        if(amount == 0) {
            loss();
            alertDialog(getResources().getString(R.string.lost), " " + wordFixed(wordArray));
        } else if (Arrays.equals(wordArray, wordArrayTemp)) {
            win();
            alertDialog(getResources().getString(R.string.won), "");
        }
        setImage();
    }

}
