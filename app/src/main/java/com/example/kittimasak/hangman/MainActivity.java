package com.example.kittimasak.hangman;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    Button[] button;
    int aob = 25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = new Button[aob];

        for(int i = 0; i<button.length; i++) {
            //int id = getResources().getIdentifier("button"+i, "id", getPackageName());
           button[i] = (Button) findViewById(getResources().getIdentifier("button"+(i+1), "id", getPackageName());
        }


    }


/*
    private void borderlessButtons() {
        for(int i=0; i<button.length; i++) {
            button[i].setBackgroundResource(R.attr.borderlessButtonStyle);
        }
    } */
}
