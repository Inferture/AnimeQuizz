package com.example.q.animequizz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.hardware.SensorEventListener;
import android.provider.BaseColumns;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AndroidException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    /*Activity opened at the start, shows the various options*/
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("ANIME_QUIZZ_PREF", Context.MODE_PRIVATE);
        int theme =sharedPref.getInt(getString(R.string.theme), R.style.AppTheme_LightTheme);
        setTheme(theme);

        setContentView(R.layout.activity_menu);


        Button play = findViewById(R.id.btn_play);
        Button playcustom = findViewById(R.id.btn_playcustom);
        Button make = findViewById(R.id.btn_make);
        Button options = findViewById(R.id.btn_options);
        Button credits = findViewById(R.id.btn_credits);


        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent party = new Intent(getApplicationContext(), ChoixModeActivity.class);
                startActivity(party);
            }
        });


        playcustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent party = new Intent(getApplicationContext(), ChoixModeActivity.class);
                party.putExtra("custom", true);

                SQLiteDatabase db = new QuestionDbHelper(getApplicationContext()).getWritableDatabase();

                String[] projection = {
                        BaseColumns._COUNT,
                };
                int num=0;
                Cursor cursor=null;
                try
                {
                    cursor=db.rawQuery("SELECT Count(*) from " + AnimeContract.QuestionEntry.TABLE_NAME +";",null);
                    cursor.moveToFirst();
                    num = cursor.getInt(cursor.getColumnIndexOrThrow("Count(*)"));
                    Log.i("AnimeQuizz", "AnimeStuff: made a query, Count(*)=" + num);
                }
                catch(Exception e)
                {
                    Log.i("AnimeQuizz", "AnimeStuff: error when counting questions:" + e.toString());
                }

                if(num>0)
                {
                    startActivity(party);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Create custom questions first !",Toast.LENGTH_SHORT).show();
                }

            }
        });



        make.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent party = new Intent(getApplicationContext(), CustomListActivity.class);
                startActivity(party);
            }
        });

        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent party = new Intent(getApplicationContext(), OptionsActivity.class);
                startActivity(party);
            }
        });

        credits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent party = new Intent(getApplicationContext(), CreditsActivity.class);
                startActivity(party);
            }
        });

    }


}
