package com.example.zach.laststand;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zach.menu.Levels;
import com.example.zach.menu.Menu;


public class OpenGLES20Activity extends Activity {

    private GLSurfaceView mGLView;

    public int levelnum;
    public int worldnum;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        levelnum = intent.getExtras().getInt("level");
        worldnum = intent.getExtras().getInt("world");
        Log.d("World", "accessing world: " + worldnum);
        Log.d("Level", "loading level: " + levelnum);
        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity
        mGLView = new MyGLSurfaceView(this,worldnum, levelnum);
        addContentView(mGLView, new FrameLayout.LayoutParams(

                FrameLayout.LayoutParams.WRAP_CONTENT,

                FrameLayout.LayoutParams.WRAP_CONTENT));

        View mView = getLayoutInflater().inflate(R.layout.gamehud, null);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.WRAP_CONTENT);

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

        RelativeLayout dankness = (RelativeLayout) findViewById(R.id.dankEndScreen);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.WRAP_CONTENT);

        params.rightMargin = 0;
        dankness.setLayoutParams(params);
        double time = ((MyGLSurfaceView)mGLView).mRenderer.getTime();

        TextView dankTime = (TextView) findViewById(R.id.timeSlot);
        dankTime.setText("You completed the level in "+time+" seconds");
        dankTime.setTypeface(font);

        Button dankHomeButton = (Button) findViewById(R.id.home);
        dankHomeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Menu.class);
                startActivity(intent);
            }
        });


        Button dankReplayButton = (Button) findViewById(R.id.replay);
        dankReplayButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), OpenGLES20Activity.class);
                intent.putExtra("level", levelnum);
                intent.putExtra("world", worldnum);
                startActivity(intent);
            }
        });
        Button dankNextButton = (Button) findViewById(R.id.next);
        dankNextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), OpenGLES20Activity.class);
                intent.putExtra("level", levelnum+1);
                intent.putExtra("world", worldnum);
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
