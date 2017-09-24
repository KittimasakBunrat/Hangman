package com.example.kittimasak.hangman;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.Locale;

/**
 * Created by Kittimasak on 8/29/2017.
 */

public class FrontActivity extends AppCompatActivity implements View.OnClickListener{

    Button play;
    Button rules;
    Button stat;
    ImageButton norsk;
    ImageButton english;
    SharedPreferences sp;
    SharedPreferences.Editor spEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);

        sp = getSharedPreferences("Hangman", MODE_PRIVATE);
        spEdit = sp.edit();

        play = (Button) findViewById(R.id.play);
        rules = (Button) findViewById(R.id.rules);
        stat = (Button) findViewById(R.id.stat);
        norsk = (ImageButton) findViewById(R.id.norsk);
        english = (ImageButton) findViewById(R.id.english);

        play.setOnClickListener(this);
        rules.setOnClickListener(this);
        stat.setOnClickListener(this);
        norsk.setOnClickListener(this);
        english.setOnClickListener(this);

        if(Locale.getDefault().getDisplayLanguage().equals("English")) {
            norsk.setAlpha(0.25f);
        } else {
            english.setAlpha(0.25f);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        spEdit.clear().apply();
    }

    public void changeLanguage(String s) {
        Locale locale = new Locale(s);
        Locale.setDefault(locale);
        Configuration conf = new Configuration();
        conf.locale = locale;
        getBaseContext().getResources().updateConfiguration(conf,
                getBaseContext().getResources().getDisplayMetrics());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.play:
                Intent intent1 = new Intent(this, MainActivity.class);
                startActivity(intent1);
                break;

            case R.id.rules:
                Intent intent2 = new Intent(this, RulesActivity.class);
                startActivity(intent2);
                break;

            case R.id.stat:
                Intent intent3 = new Intent(this, StatisticActivity.class);
                startActivity(intent3);
                break;

            case R.id.norsk:
                changeLanguage("no");
                finish();
                startActivity(getIntent());
                break;

            case R.id.english:
                changeLanguage("en");
                finish();
                startActivity(getIntent());
                break;

            default:
                break;

        }
    }



}
