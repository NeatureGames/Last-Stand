package com.example.zach.menu;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zach.laststand.R;

public class Shop extends Activity {
    private SharedPreferences saves;

    private int ids[][] = {
                            {R.id.upgradescount1,R.id.upgradestitle1,R.id.upgradesbutton1,R.id.upgradescoinimg1,R.id.upgradescointex1,R.id.upgradesLayout},
                            {R.id.upgradescount2,R.id.upgradestitle2,R.id.upgradesbutton2,R.id.upgradescoinimg2,R.id.upgradescointex2,R.id.upgradesLayout2}
                          };

    private int costs[] = {50,30};



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop);

        checkOnclick();
    }

    public void checkOnclick() {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/manteka.ttf");
        final SharedPreferences saves = getSharedPreferences("label", 0);
        final SharedPreferences.Editor savesEditor = saves.edit();

        final int save[] = {saves.getInt("double coins amount", 0),saves.getInt("double score amount", 0)};

        for(int i = 0; i < ids.length; i++) {
            final TextView count = (TextView) findViewById(ids[i][0]);
            final TextView title = (TextView) findViewById(ids[i][1]);
            ImageButton button = (ImageButton) findViewById(ids[i][2]);
            ImageView coin = (ImageView) findViewById(ids[i][3]);
            TextView coinAmount = (TextView) findViewById(ids[i][4]);
            RelativeLayout layout = (RelativeLayout) findViewById(ids[i][5]);

            final int totalCoins = saves.getInt("total coins", 0);

            final int index = i;
            final int cost = costs[i];

            title.setTypeface(font);
            coinAmount.setTypeface(font);
            count.setTypeface(font);

            coinAmount.setText("" + cost + "");
            count.setText("" + save[i] + "X");

            if(totalCoins >= cost && Integer.parseInt(count.getText().toString().substring(0,count.getText().toString().length()-1)) < 99) {
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        if(Integer.parseInt(count.getText().toString().substring(0,count.getText().toString().length()-1)) < 99) {
                            savesEditor.putInt("total coins", totalCoins - cost).apply();

                            if (title.getText().equals("2x coins")) {
                                Log.d("d", "" + title.getText());
                                savesEditor.putInt("double coins amount", saves.getInt("double coins amount", 0) + 1);
                            } else if (title.getText().equals("2x score")) {
                                savesEditor.putInt("double score amount", saves.getInt("double score amount", 0) + 1);
                            }
                            count.setText("" + saves.getInt("double coins amount", 0) + "X");
                        }
                    }
                });
            } else {
                button.setAlpha(.5f);
            }
            ImageButton backbutton = (ImageButton) findViewById(R.id.backButton);
            backbutton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    startActivity(new Intent(Shop.this, Menu.class));
                    finish();
                }
            });
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Shop.this, Menu.class));
        finish();
    }
}


