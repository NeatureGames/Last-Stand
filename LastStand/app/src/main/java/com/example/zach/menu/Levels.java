package com.example.zach.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.ViewFlipper;

import com.example.zach.laststand.OpenGLES20Activity;
import com.example.zach.laststand.R;

public class Levels extends FragmentActivity {




    private int rows = 3;
    private int coloums = 3;
    private int worlds = 2;
    private int buttonMarginsHoriz = 10;
    private int buttonMarginsVert = 15;
    private int buttonPaddingTop = 10;
    private int buttonPaddingRight = 21;
    private int buttonWidth = 70;
    private int buttonHeight = 90;
    private ViewFlipper viewFlipper;
    private float lastX;
    //Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levels);

        viewFlipper = (ViewFlipper) findViewById(R.id.flipDatView);

        createTables();
    }
    public boolean onTouchEvent(MotionEvent touchevent)
    {
        switch (touchevent.getAction())
        {
            // when user first touches the screen to swap
            case MotionEvent.ACTION_DOWN:
            {
                lastX = touchevent.getX();
                break;
            }
            case MotionEvent.ACTION_UP:
            {
                float currentX = touchevent.getX();

                // if left to right swipe on screen
                if (lastX < currentX)
                {
                    // If no more View/Child to flip
                    if (viewFlipper.getDisplayedChild() == 0)
                        break;

                    // set the required Animation type to ViewFlipper
                    // The Next screen will come in form Left and current Screen will go OUT from Right
                    viewFlipper.setInAnimation(this, R.transition.in_from_left);
                    viewFlipper.setOutAnimation(this, R.transition.out_to_right);
                    // Show the next Screen
                    viewFlipper.showNext();
                }

                // if right to left swipe on screen
                if (lastX > currentX)
                {
                    if (viewFlipper.getDisplayedChild() == 1)
                        break;
                    // set the required Animation type to ViewFlipper
                    // The Next screen will come in form Right and current Screen will go OUT from Left
                    viewFlipper.setInAnimation(this, R.transition.in_from_right);
                    viewFlipper.setOutAnimation(this, R.transition.out_to_left);
                    // Show The Previous Screen
                    viewFlipper.showPrevious();
                }
                break;
            }
        }
        return false;
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

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/manteka.ttf");


        // Button testBut = (Button) findViewById(R.id.level2);


        for(int j = 0; j<worlds; j++){
            TableLayout table = new TableLayout(this);
            ViewFlipper.LayoutParams tb = new ViewFlipper.LayoutParams(ViewFlipper.LayoutParams.WRAP_CONTENT, ViewFlipper.LayoutParams.WRAP_CONTENT);
            tb.gravity = Gravity.CENTER;

            final int worldNum = j;

            for(int i = 0; i<rows; i++){
                TableRow row = new TableRow(this);
                TableLayout.LayoutParams tl = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
                tl.setMargins(dpToPix(buttonMarginsVert),dpToPix(buttonMarginsVert),dpToPix(buttonMarginsVert),dpToPix(buttonMarginsVert));

                for(int x = 0; x<coloums; x++){
                    final int index = i*coloums+x;

                    TableRow.LayoutParams tr = new TableRow.LayoutParams(dpToPix(buttonWidth), dpToPix(buttonHeight));
                    tr.gravity = Gravity.CENTER;
                    tr.setMargins(dpToPix(buttonMarginsHoriz),dpToPix(buttonMarginsHoriz),dpToPix(buttonMarginsHoriz),dpToPix(buttonMarginsHoriz));


                    Button button = new Button(this);


                    //Drawable buttonImage = getResources().getDrawable(R.drawable.ic_launcher);

                    button.setBackgroundResource(R.drawable.levelbanner);
                    button.setText(Integer.toString(index + 1));
                    //button.setWidth(dpToPix(buttonWidth));
                  //  button.setHeight(dpToPix(buttonHeight));
                    button.setPadding( 0,dpToPix(buttonPaddingTop),dpToPix(buttonPaddingRight),0);
                   // button.setP
                   // button.setPa
                   // button.setM(dpToPix(buttonWidth));
                   // button.setWidth(dpToPix(buttonWidth));
                   // button.setLayoutGravity


                    button.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            Intent intent = new Intent(view.getContext(), OpenGLES20Activity.class);
                            intent.putExtra("level", index);
                            intent.putExtra("world", worldNum);
                            startActivity(intent);
                        }
                    });
                    button.setGravity(Gravity.TOP | Gravity.RIGHT);
                    button.setTypeface(font);
                    button.setTextColor(Color.parseColor("#FFFFFF"));
                    button.setTextSize(22);

                    row.addView(button,tr);
                }
                table.addView(row,tl);
            }
            viewFlipper.addView(table,tb);
        }
    }
    public int dpToPix(int dps){
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }

}
