package com.example.kittimasak.hangman;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Created by Kittimasak on 8/29/2017.
 */

public class FrontActivity extends AppCompatActivity implements View.OnClickListener{

    Button play;
    Button stat;
    Button set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);

        play = (Button) findViewById(R.id.play);
        stat = (Button) findViewById(R.id.stat);
        set = (Button) findViewById(R.id.set);
        play.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.play:
                Intent intent1 = new Intent(this, MainActivity.class);
                startActivity(intent1);
                break;

            default:
                break;

        }
    }

}
