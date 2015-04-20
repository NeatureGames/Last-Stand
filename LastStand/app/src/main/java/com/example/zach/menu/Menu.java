package com.example.zach.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.zach.laststand.R;

public class Menu extends Activity {

    Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        addListenerOnButton();
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

            }

        });

        button = (Button) findViewById(R.id.endless);
        button.setGravity(Gravity.CENTER);
        button.setTypeface(font);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, Levels.class);
                startActivity(intent);

            }

        });

        button = (Button) findViewById(R.id.buygold);
        button.setGravity(Gravity.CENTER);
        button.setTypeface(font);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, Levels.class);
                startActivity(intent);

            }

        });

        button = (Button) findViewById(R.id.character);
        button.setGravity(Gravity.CENTER);
        button.setTypeface(font);

        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, Levels.class);
                startActivity(intent);

            }

        });

    }

}