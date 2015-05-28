package com.example.zach.menu;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zach.laststand.OpenGLES20Activity;
import com.example.zach.laststand.R;

public class Extras extends Activity{
    int endlessUnlockAmount = 25;
    int easyEndlessUnlockAmount = 40;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.extras);
        addListenerOnButton();


    }

    public void addListenerOnButton() {
        SharedPreferences saves = getSharedPreferences("label", 0);
        int starAmount = saves.getInt("total stars", 0);

        final Context context = this;

        RelativeLayout button;
        ImageView background;
        TextView text;
        ImageView stars;
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/manteka.ttf");


        button = (RelativeLayout) findViewById(R.id.endless);
        background = (ImageView) findViewById(R.id.endlessImg);
        stars = (ImageView) findViewById(R.id.starIcon1);
        text = (TextView) findViewById(R.id.unlockAmount1);
        text.setTypeface(font);
        if(starAmount >= endlessUnlockAmount) {
            stars.setAlpha(0f);
            text.setText("");
            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    Intent intent = new Intent(context, OpenGLES20Activity.class);
                    intent.putExtra("level", 1);
                    intent.putExtra("world", -1);
                    startActivity(intent);
                    finish();


                }

            });
        } else {
            text.setText("" + endlessUnlockAmount + "");
            background.setAlpha(.3f);
        }



        button = (RelativeLayout) findViewById(R.id.easyEndless);
        background = (ImageView) findViewById(R.id.easyEndlessImg);
        stars = (ImageView) findViewById(R.id.starIcon2);
        text = (TextView) findViewById(R.id.unlockAmount2);
        text.setTypeface(font);
        if(starAmount >= easyEndlessUnlockAmount) {
            stars.setAlpha(0f);
            text.setText("");
            button.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    Intent intent = new Intent(context, OpenGLES20Activity.class);
                    intent.putExtra("level", 1);
                    intent.putExtra("world", -1);
                    startActivity(intent);
                    finish();


                }

            });
        } else {
            text.setText("" + easyEndlessUnlockAmount + "");
            background.setAlpha(.3f);
        }
        ImageButton backbutton = (ImageButton) findViewById(R.id.backButton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(Extras.this, Menu.class));
                finish();
            }
        });
    }
}
