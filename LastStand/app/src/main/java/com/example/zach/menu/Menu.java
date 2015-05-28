package com.example.zach.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.LevelListDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zach.laststand.MyGLRenderer;
import com.example.zach.laststand.OpenGLES20Activity;
import com.example.zach.laststand.R;

public class Menu extends Activity {


    Button button;
    TextView text;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        addListenerOnButton();


    }
    @Override
    public void onResume(){
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/manteka.ttf");

        super.onResume();
    //saving stuff
        SharedPreferences saves = getSharedPreferences("label", 0);
        SharedPreferences.Editor savesEditor = saves.edit();
        int coinAmount = saves.getInt("total coins", 0);

        savesEditor.putBoolean("level0world0",true).apply();

        button = (Button) findViewById(R.id.characterpic);

        button.setBackgroundResource(MyGLRenderer.charactersTexs[saves.getInt("character checked",0)]);

        text = (TextView) findViewById(R.id.totalcoins);
        text.setText("" + coinAmount + "");
        text.setTypeface(font);



        int starAmount = saves.getInt("total stars", 0);
        text = (TextView) findViewById(R.id.totalstars);
        text.setText("" + starAmount + "");
        text.setTypeface(font);
    }
    public void addListenerOnButton() {

        final Context context = this;
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/manteka.ttf");

        button = (Button) findViewById(R.id.levels);
        button.setGravity(Gravity.CENTER);
        button.setTypeface(font);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, Levels.class);
                startActivity(intent);
                finish();

            }

        });

        button = (Button) findViewById(R.id.extras);
        button.setGravity(Gravity.CENTER);
        button.setTypeface(font);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, Extras.class);
                startActivity(intent);
                finish();
            }

        });
        button = (Button) findViewById(R.id.character);
        button.setGravity(Gravity.CENTER);
        button.setTypeface(font);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, Characters.class);
                startActivity(intent);
                finish();
            }

        });

        button = (Button) findViewById(R.id.shop);
        button.setGravity(Gravity.CENTER);
        button.setTypeface(font);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, Shop.class);;
                startActivity(intent);
                finish();
            }

        });



        button = (Button) findViewById(R.id.characterpic);
        button.setGravity(Gravity.CENTER);
        button.setTypeface(font);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, Characters.class);
                startActivity(intent);
                finish();
            }

        });


        button = (Button) findViewById(R.id.clearData);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                clearData();
            }

        });

    }
    public void clearData(){
        SharedPreferences saves = getSharedPreferences("label", 0);
        SharedPreferences.Editor savesEditor = saves.edit();
        savesEditor.clear().apply();

        savesEditor.putBoolean("level0world0",true).apply();
    }

}