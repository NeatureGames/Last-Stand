package com.example.zach.laststand;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.Typeface;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zach.menu.Levels;
import com.example.zach.menu.Menu;


public class OpenGLES20Activity extends Activity {

    private GLSurfaceView mGLView;

    private Menu menu;

    public int levelnum;
    public int worldnum;
    public int playernum;

    TextView display;

    static String key = "key";
    boolean check = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        SharedPreferences saves = getSharedPreferences("label", 0);
        playernum = saves.getInt("character checked",0);

        Intent intent = getIntent();
        levelnum = intent.getExtras().getInt("level");
        worldnum = intent.getExtras().getInt("world");
        Log.d("World", "accessing world: " + worldnum);
        Log.d("Level", "loading level: " + levelnum);
        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity
        mGLView = new MyGLSurfaceView(this,worldnum, levelnum,playernum);
        addContentView(mGLView, new FrameLayout.LayoutParams(

                FrameLayout.LayoutParams.WRAP_CONTENT,

                FrameLayout.LayoutParams.WRAP_CONTENT));
        View mView;
        FrameLayout.LayoutParams params;
        if(worldnum >= 0) {
            mView = getLayoutInflater().inflate(R.layout.gamehud, null);
            params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        }
        else{
            mView = getLayoutInflater().inflate(R.layout.enlessgamehud, null);
            params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        }

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        //int height = size.y;
        params.rightMargin = width;

        addContentView(mView, params);

    }
    public void run() {
        // Your logic here...

        // When you need to modify a UI element, do so on the UI thread.
        // 'getActivity()' is required as this is being ran from a Fragment.
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // This code will always run on the UI thread, therefore is safe to modify UI elements.
                showDankEndScreen();
            }
        });
    }

    public void showDankEndScreen(){
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/manteka.ttf");
        SharedPreferences saves = getSharedPreferences("label", 0);

        int numstars = ((MyGLSurfaceView) mGLView).mRenderer.starAmount;

        if(worldnum >=0 ) {
            ImageView dankStar1 = (ImageView) findViewById(R.id.star1);
            ImageView dankStar2 = (ImageView) findViewById(R.id.star2);
            ImageView dankStar3 = (ImageView) findViewById(R.id.star3);

            if (numstars == 2) {
                dankStar3.setBackgroundResource(R.drawable.starempty);
            } else if (numstars == 1) {
                dankStar3.setBackgroundResource(R.drawable.starempty);
                dankStar2.setBackgroundResource(R.drawable.starempty);
            } else if (numstars == 0) {
                dankStar3.setBackgroundResource(R.drawable.starempty);
                dankStar2.setBackgroundResource(R.drawable.starempty);
                dankStar1.setBackgroundResource(R.drawable.starempty);
            }

            //saving
            SharedPreferences.Editor savesEditor = saves.edit();
            //int totalStars = saves.getInt("total stars", 0);
            if(levelnum + 1 != 9) {
                savesEditor.putBoolean("level" + (levelnum + 1) + "world" + (worldnum), true).apply();
            } else {
                savesEditor.putBoolean("level" + (0) + "world" + (worldnum + 1), true).apply();
            }
            savesEditor.putInt("level"+levelnum+"world"+worldnum+"stars", numstars).apply();
            //add up stars
            int totalStars = 0;
            for(int i = 0; i<Levels.worlds; i++){
                for(int x = 0; x< Levels.coloums * Levels.rows; x++){
                    int star = saves.getInt("level"+x+"world"+i+"stars",0);
                    totalStars+=star;
                }
            }
            savesEditor.putInt("total stars", totalStars).apply();
        }

        RelativeLayout dankness = (RelativeLayout) findViewById(R.id.dankEndScreen);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);

        params.rightMargin = 0;
        dankness.setLayoutParams(params);

        int coinamount = ((MyGLSurfaceView)mGLView).mRenderer.coinAmount;

        double time = ((MyGLSurfaceView)mGLView).mRenderer.getTime();

        //saveing
        int totalCoins = saves.getInt("total coins", 0);
        SharedPreferences.Editor savesEditor = saves.edit();
        savesEditor.putInt("total coins",totalCoins + coinamount).apply();




        TextView dankCompleted = (TextView) findViewById(R.id.completed);
        if(worldnum < 0){
            dankCompleted.setText("Game Over!");
        }
        dankCompleted.setTypeface(font);

        TextView dankTime = (TextView) findViewById(R.id.timeSlot);
        dankTime.setText("Time: "+time+" s");
        dankTime.setTypeface(font);

        TextView dankBestTime = (TextView) findViewById(R.id.fastestSlot);
        dankBestTime.setText("Best: "+time+" s");
        dankBestTime.setTypeface(font);


        TextView dankCoins = (TextView) findViewById(R.id.coinSlot);
        dankCoins.setText("Coins: "+coinamount);
        dankCoins.setTypeface(font);

        ImageButton dankHomeButton = (ImageButton) findViewById(R.id.home);
        dankHomeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Menu.class);
                startActivity(intent);
            }
        });


        ImageButton dankReplayButton = (ImageButton) findViewById(R.id.restart);
        dankReplayButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), OpenGLES20Activity.class);
                intent.putExtra("level", levelnum);
                intent.putExtra("world", worldnum);
                startActivity(intent);
            }
        });
        ImageButton dankNextButton = (ImageButton) findViewById(R.id.next);
        dankNextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), OpenGLES20Activity.class);
                if(levelnum + 1 != 9) {
                    intent.putExtra("level", levelnum+1);
                    intent.putExtra("world", worldnum);
                } else {
                    intent.putExtra("level", 0);
                    intent.putExtra("world", worldnum+1);
                }
                startActivity(intent);
            }
        });



    }
    @Override
    protected void onPause() {
        super.onPause();
        // The following call pauses the rendering thread.
        // If your OpenGL application is memory intensive,
        // you should consider de-allocating objects that
        // consume significant memory here.
        mGLView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // The following call resumes a paused rendering thread.
        // If you de-allocated graphic objects for onPause()
        // this is a good place to re-allocate them.
        mGLView.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(worldnum > -1) {
            startActivity(new Intent(OpenGLES20Activity.this, Levels.class));
        }
        else{
            startActivity(new Intent(OpenGLES20Activity.this, Menu.class));
        }
        finish();
    }
}
