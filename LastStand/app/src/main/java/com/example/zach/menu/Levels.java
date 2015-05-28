package com.example.zach.menu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.ViewFlipper;

import com.example.zach.laststand.OpenGLES20Activity;
import com.example.zach.laststand.R;

import java.util.ResourceBundle;

public class Levels extends FragmentActivity {


    public static int rows = 3;
    public static int coloums = 3;
    public static int worlds = 3;

    private int buttonMarginsHoriz = 8;
    private int buttonMarginsVert = 5;
    private int buttonPaddingTop = 10;
    private int buttonPaddingRight = 21;
    private int buttonWidth = 80;
    private int buttonHeight = 80;
    private int starSize = 24;
    private int starHangOff = starSize + 3;
    private int starSpacingTop = buttonHeight - starSize + starHangOff;
    private int starSpacingHoriz = starSize + 5;
    private ViewFlipper viewFlipper;
    private float lastX;

    private int worldWidth = 180;
    private int worldHeight = 70;

    private int worldTexs[] = {R.drawable.world1,R.drawable.world2,R.drawable.world3};
    private int worldNum = 0;
    private ImageView world;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levels);

        world = (ImageView) findViewById(R.id.world);
        viewFlipper = (ViewFlipper) findViewById(R.id.flipDatView);

        createTables();

    }
    float pressDownPoint = 0;
    int viewFlipperViewCount = 0;

    @Override
    public boolean onTouchEvent(MotionEvent touchevent) {




        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        float screenDensity = metrics.density;

        switch (touchevent.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                //Gets the startpoint for you finger

                pressDownPoint = touchevent.getRawX();
                Log.d("touch", pressDownPoint+"");
                //Gets how many view there is in the viewviewFlipper
                viewFlipperViewCount = viewFlipper.getChildCount();

                //Checks if there is a view to the left of the current view
//if there is, it positions it to the left of the current view
                if (viewFlipper.getDisplayedChild() > 0) {
                    View leftChild = viewFlipper.getChildAt(viewFlipper.getDisplayedChild() - 1);
                    //You must set the left view to invisible or visible 
//or it will not move to the position you tell it
                    leftChild.setVisibility(View.INVISIBLE);
                    leftChild.layout(-screenWidth,
                            leftChild.getTop(), 0,
                            leftChild.getBottom());
                }

                //Same as above but for the view to the right
                if (viewFlipper.getDisplayedChild() < viewFlipperViewCount - 1) {
                    View rightChild = viewFlipper.getChildAt(viewFlipper.getDisplayedChild() + 1);
                    rightChild.setVisibility(View.INVISIBLE);
                    rightChild.layout(screenWidth,
                            rightChild.getTop(), screenWidth * 2,
                            rightChild.getBottom());
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                //Gets the absolute position on the screen
                float releasePoint = touchevent.getRawX();

                //Calculates if the fling is to the right or left
//The screenDensity variable is simply the density of the device
//Have in mind that this will not flipp the viewviewFlipper if you drag
//your finger less than about 0.5cm (depeding on the device)
//In that case you need to make an animation that takes the view back
//to its original position. Else it will just get stuck where you
//let go with your finger.
                if (Math.abs(pressDownPoint - releasePoint) / screenDensity > 30) {
                    if (pressDownPoint > releasePoint) {
                        //myAnimLeft(); //Method with your animation
                       // viewFlipper.getDisplayedChild().SetInAnimation(this, R.transition.in_from_left);
                        //viewFlipper.getDisplayedChild().SetOutAnimation(this, R.transition.out_to_left);
                        viewFlipper.setInAnimation(this, R.transition.in_from_right);
                        viewFlipper.setOutAnimation(this, R.transition.out_to_left);

                        viewFlipper.showNext();

                        worldNum++;
                        if(worldNum > worlds - 1){
                            worldNum = 0;
                        }
                        world.setBackgroundResource(worldTexs[worldNum]);
                    } else {
                        //myAnimRight();
                        viewFlipper.setInAnimation(this, R.transition.in_from_left);
                        viewFlipper.setOutAnimation(this, R.transition.out_to_right);
                        //viewFlipper.getDisplayedChild().SetInAnimation(this, R.transition.in_from_right);
                        //viewFlipper.getDisplayedChild().SetOutAnimation(this, R.transition.out_to_right);

                        viewFlipper.showPrevious();

                        worldNum--;

                        if(worldNum == -1){
                            worldNum = worlds - 1;
                        }
                        world.setBackgroundResource(worldTexs[worldNum]);
                    }
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                View currentView = viewFlipper.getCurrentView();

                //Moves the current view
//screenWidth is based on the current devices screen width
                Log.d("touch", (touchevent.getRawX() - pressDownPoint)+"");
                currentView.layout((int) (touchevent.getRawX() - pressDownPoint),
                        currentView.getTop(), (int) (touchevent.getRawX() - pressDownPoint) + screenWidth,
                        currentView.getBottom());

                //Moves the view to the left if there is one
               /* if (viewFlipper.getDisplayedChild() > 0) {
                    View leftChild = viewFlipper.getChildAt(viewFlipper.getDisplayedChild() - 1);
                    leftChild.layout((int) (touchevent.getRawX() - pressDownPoint - screenWidth),
                            leftChild.getTop(), (int) (touchevent.getRawX() - pressDownPoint),
                            leftChild.getBottom());

                    //Sets the left view to visible so it shows
                    if (leftChild.getVisibility() == View.INVISIBLE) {
                        leftChild.setVisibility(View.VISIBLE);
                    }
                }

                //Same as above but for the view to the right
                if (viewFlipper.getDisplayedChild() < viewFlipperViewCount - 1) {
                    View rightChild = viewFlipper.getChildAt(viewFlipper.getDisplayedChild() + 1);
                    rightChild.layout((int) (touchevent.getRawX() - pressDownPoint + screenWidth),
                            rightChild.getTop(), (int) (touchevent.getRawX() - pressDownPoint + (screenWidth * 2)),
                            rightChild.getBottom());

                    if (rightChild.getVisibility() == View.INVISIBLE) {
                        rightChild.setVisibility(View.VISIBLE);
                    }
                }*/
            }

        }
        return true;
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
            ViewFlipper.LayoutParams tb = new ViewFlipper.LayoutParams(ViewFlipper.LayoutParams.WRAP_CONTENT, ViewFlipper.LayoutParams.MATCH_PARENT);
            tb.gravity = Gravity.CENTER_HORIZONTAL;
            tb.setMargins(0,dpToPix(120),0,0);

            final int worldNum = j;
            for(int i = 0; i<rows; i++){

                TableRow row = new TableRow(this);
                TableLayout.LayoutParams tl = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
                tl.setMargins(dpToPix(buttonMarginsVert),dpToPix(buttonMarginsVert),dpToPix(buttonMarginsVert),dpToPix(buttonMarginsVert));

                for(int x = 0; x<coloums; x++){
                    final int index = i*coloums+x;

                    TableRow.LayoutParams tr = new TableRow.LayoutParams(dpToPix(buttonWidth), dpToPix(buttonHeight+starHangOff));
                    tr.gravity = Gravity.TOP|Gravity.CENTER;
                    tr.setMargins(dpToPix(buttonMarginsHoriz),dpToPix(buttonMarginsHoriz),dpToPix(buttonMarginsHoriz),dpToPix(buttonMarginsHoriz));

                    RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, dpToPix(buttonHeight));
                    RelativeLayout container = new RelativeLayout(this);

                    ImageView button = new ImageView(this);

                    SharedPreferences saves = getSharedPreferences("label", 0);
                    final boolean accessLevel = saves.getBoolean("level" + index + "world" + worldNum, false);

                    if(index == 0){
                        button.setBackgroundResource(R.drawable.level1);
                    } else if(index == 1){
                        button.setBackgroundResource(R.drawable.level2);
                    } else if(index == 2){
                        button.setBackgroundResource(R.drawable.level3);
                    } else if(index == 3){
                        button.setBackgroundResource(R.drawable.level4);
                    } else if(index == 4){
                        button.setBackgroundResource(R.drawable.level5);
                    } else if(index == 5){
                        button.setBackgroundResource(R.drawable.level6);
                    } else if(index == 6){
                        button.setBackgroundResource(R.drawable.level7);
                    } else if(index == 7){
                        button.setBackgroundResource(R.drawable.level8);
                    } else if(index == 8) {
                        button.setBackgroundResource(R.drawable.level9);
                    }
                    if(!accessLevel){
                        button.setAlpha(.5f);
                        //transparency
                    }


                    button.setPadding(0, dpToPix(buttonPaddingTop), dpToPix(buttonPaddingRight), 0);


                    button.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View view) {
                            if(accessLevel) {
                                Intent intent = new Intent(view.getContext(), OpenGLES20Activity.class);
                                intent.putExtra("level", index);
                                intent.putExtra("world", worldNum);
                                startActivity(intent);
                            }
                        }
                    });
                   // button.setGravity(Gravity.TOP | Gravity.RIGHT);
                   // relativeParams.gravity = ""
                    container.addView(button, relativeParams);


                    final int starAmnt = saves.getInt("level" + index + "world" + worldNum + "stars", 0);

                    RelativeLayout.LayoutParams starParams1 = new RelativeLayout.LayoutParams(dpToPix(starSize), dpToPix(starSize));

                    ImageView star1 = new ImageView(this);
                    starParams1.setMargins(dpToPix(buttonWidth/2-starSpacingHoriz-starSize/2),dpToPix(starSpacingTop),0,0);
                    star1.setBackgroundResource(R.drawable.starempty);
                    container.addView(star1, starParams1);

                    RelativeLayout.LayoutParams starParams2 = new RelativeLayout.LayoutParams(dpToPix(starSize), dpToPix(starSize));

                    ImageView star2 = new ImageView(this);
                    starParams2.setMargins(dpToPix(buttonWidth/2-starSize/2),dpToPix(starSpacingTop),0,0);
                    star2.setBackgroundResource(R.drawable.starempty);
                    container.addView(star2, starParams2);

                    RelativeLayout.LayoutParams starParams3 = new RelativeLayout.LayoutParams(dpToPix(starSize), dpToPix(starSize));

                    ImageView star3 = new ImageView(this);
                    starParams3.setMargins(dpToPix(buttonWidth/2+starSpacingHoriz-starSize/2),dpToPix(starSpacingTop),0,0);
                    star3.setBackgroundResource(R.drawable.starempty);
                    container.addView(star3, starParams3);

                    if(starAmnt >= 1)star1.setBackgroundResource(R.drawable.starfull);
                    if(starAmnt >= 2)star2.setBackgroundResource(R.drawable.starfull);
                    if(starAmnt >= 3)star3.setBackgroundResource(R.drawable.starfull);

                    row.addView(container,tr);
                }
                table.addView(row,tl);
            }

            viewFlipper.addView(table,tb);
        }
        ImageButton backbutton = (ImageButton) findViewById(R.id.backButton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(Levels.this, Menu.class));
                finish();
            }
        });

    }
    public int dpToPix(int dps){
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Levels.this, Menu.class));
        finish();
    }
}
