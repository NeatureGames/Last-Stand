package com.example.zach.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.ViewFlipper;

import com.example.zach.laststand.OpenGLES20Activity;
import com.example.zach.laststand.R;

public class Levels extends FragmentActivity {




    int rows = 3;
    int coloums = 3;
    int worlds = 2;
    int buttonMarginsHoriz = 10;
    int buttonMarginsVert = 15;
    int buttonPadding = 0;
    int buttonWidth = 50;
    int buttonHeight = 50;
    //Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levels);
        createTables();
    }

    /*public void addListenerOnButton() {
        final Context context = this;

      //  for(int i = 0; i<9; i++) {
            button[0] = (Button) findViewById(R.id.level1);

            button[0].setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    Intent intent = new Intent(context, OpenGLES20Activity.class);
                    startActivity(intent);

                }

            });
       // }

    }*/
    public void createTables(){
        ViewFlipper flipper = (ViewFlipper) findViewById(R.id.viewFlipper);

        for(int j = 0; j<worlds; j++){
            TableLayout table = new TableLayout(this);
            TableLayout.LayoutParams tb = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);


            for(int i = 0; i<rows; i++){
                TableRow row = new TableRow(this);
                TableLayout.LayoutParams tl = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
                tl.setMargins(dpToPix(buttonMarginsVert),dpToPix(buttonMarginsVert),dpToPix(buttonMarginsVert),dpToPix(buttonMarginsVert));
                for(int x = 0; x<coloums; x++){
                    final int index = i*coloums+x;

                    TableRow.LayoutParams tr = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
                    tr.gravity = Gravity.CENTER;
                    tr.setMargins(dpToPix(buttonMarginsHoriz),dpToPix(buttonMarginsHoriz),dpToPix(buttonMarginsHoriz),dpToPix(buttonMarginsHoriz));


                    Button button = new Button(this);

                    //Drawable buttonImage = getResources().getDrawable(R.drawable.ic_launcher);

                    button.setBackgroundResource(R.drawable.ic_launcher);
                    button.setText(Integer.toString(index + 1));
                    button.setWidth(dpToPix(buttonWidth));
                    button.setHeight(dpToPix(buttonHeight));
                    button.setPadding( dpToPix(buttonPadding),dpToPix(buttonPadding),dpToPix(buttonPadding),dpToPix(buttonPadding));
                   // button.setM(dpToPix(buttonWidth));
                   // button.setWidth(dpToPix(buttonWidth));
                   // button.setLayoutGravity


                    button.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            Intent intent = new Intent(view.getContext(), OpenGLES20Activity.class);
                            intent.putExtra("level", index);
                            startActivity(intent);
                        }
                    });


                    row.addView(button,tr);
                }
                table.addView(row,tl);
            }
            flipper.addView(table,tb);
        }
    }
    public int dpToPix(int dps){
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }

}
