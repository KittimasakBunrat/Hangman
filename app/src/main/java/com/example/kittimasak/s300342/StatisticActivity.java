package com.example.kittimasak.s300342;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
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
import android.widget.TextView;

import com.example.kittimasak.hangman.R;

import java.util.Locale;

/**
 * Created by Kittimasak on 8/29/2017.
 */

public class StatisticActivity extends AppCompatActivity {

    TextView text;
    TextView text1;
    TextView text2;
    TextView text3;

    int win;
    int loss;

    SharedPreferences sp;
    SharedPreferences.Editor spEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        sp = getSharedPreferences("Hangman", MODE_PRIVATE);
        spEdit = sp.edit();

        win = 0;
        loss = 0;

        if(sp.contains("win")) {
            win = sp.getInt("win", win);
        }
        if(sp.contains("loss")) {
            loss = sp.getInt("loss", loss);
        }

        text = (TextView) findViewById(R.id.tekst);
        text1 = (TextView) findViewById(R.id.tekst1);
        text2 = (TextView) findViewById(R.id.tekst2);
        text3 = (TextView) findViewById(R.id.tekst3);

        text.setText(R.string.win);
        text1.setText(String.valueOf(win));
        text2.setText(R.string.lose);
        text3.setText(String.valueOf(loss));


    }


}
