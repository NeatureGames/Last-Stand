package com.example.zach.laststand;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.util.DisplayMetrics;
import android.util.Log;
import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Map;

/**
 * Provides drawing instructions for a GLSurfaceView object. This class
 * must override the OpenGL ES drawing lifecycle methods:
 * <ul>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onSurfaceCreated}</li>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onDrawFrame}</li>
 *   <li>{@link android.opengl.GLSurfaceView.Renderer#onSurfaceChanged}</li>
 * </ul>
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {

    private static final String TAG = "MyGLRenderer";

    protected final Context mActivityContext;

    public int backgroundTex;
    public int coinTex;
    public int stoneTex;
    public int grassTex;
    public int trampTex;
    public int supaTrampTex;
    public int defaultTex;
    public int playerTex;
    public int goldtrophyTex;
    public int silvertrophyTex;
    public int bronzetrophyTex;


    //private Map mTriangle;
    public Player   mPlayer;

    //private Map mMap = new Map(0);

    public Obstacle background;
    public Obstacle trophy;

    ArrayList<Obstacle> ground = new ArrayList<>();
    ArrayList<Obstacle> trampoline = new ArrayList<>();
    ArrayList<Obstacle> coins = new ArrayList<>();
    ArrayList<Obstacle> BABlocks = new ArrayList<>();
    ArrayList<Obstacle> supaTramp = new ArrayList<>();

    ArrayList<Integer> EnlessModeLevels = new ArrayList<>();
    ArrayList<Integer> EnlessModeWorlds = new ArrayList<>();


    int mapNum = 0;
    int worldNum = 0;
    int coinAmount = 0;
    int endlessLevelAmount = 0;
    int starAmount = 0;

    float pastTropyX;

    long startTimeL;
    boolean firstSwip = true;

    boolean completed = false;
    boolean endless = false;
    boolean easyEndless = false;
    boolean spawnNextLevel = true;
    boolean deleteLastLevel = true;

    int[][] blacklist = {
            {1,2}
    };

    float[][][][][] levels =
    {//World
            {//levels
                    {{{2.5f,-17.5f},{1.5f,-17.5f},{0.5f,-17.5f},{4.5f,-17.5f},{6.5f,-17.5f},{7.5f,-17.5f},{8.5f,-17.5f},{9.5f,-17.5f},{11.5f,-17.5f},{12.5f,-17.5f},{13.5f,-17.5f},},{},{},{},{{13.5f,-14.5f}},{}
                    ,{{6.15f},{5.125f},{4.1f}}},

                    {{{0.5f,-17.5f},{1.5f,-17.5f},{2.5f,-17.5f},{4.5f,-17.5f},{6.5f,-17.5f},{7.5f,-17.5f},{8.5f,-17.5f},{9.5f,-17.5f},{10.5f,-17.5f},{11.5f,-17.5f},{13.5f,-17.5f},{14.5f,-17.5f},{15.5f,-17.5f},{17.5f,-17.5f},},{},{},{},{{17.5f,-14.5f}},{}
                    ,{{7.65f},{6.375f},{5.1f}}},

                    {{{0.5f,-17.5f},{1.5f,-17.5f},{2.5f,-17.5f},{3.5f,-17.5f},{4.5f,-17.5f},{5.5f,-17.5f},{7.5f,-17.5f},{9.5f,-17.5f},{11.5f,-17.5f},{12.5f,-17.5f},{13.5f,-17.5f},{14.5f,-17.5f},{16.5f,-17.5f},{17.5f,-17.5f},{18.5f,-17.5f},{19.5f,-17.5f},{21.5f,-17.5f},},{},{},{},{{21.5f,-14.5f}},{}
                    ,{{24.97f},{20.81f},{16.65f}}},

                    {{{0.5f,-17.5f},{1.5f,-17.5f},{2.5f,-17.5f},{4.5f,-17.5f},{5.5f,-17.5f},{6.5f,-17.5f},{7.5f,-17.5f},{9.5f,-17.5f},{10.5f,-17.5f},{11.5f,-17.5f},{12.5f,-17.5f},{14.5f,-17.5f},{15.5f,-17.5f},{16.5f,-17.5f},},{},{{15.5f,-14.5f},{1.5f,-14.5f},{11.5f,-14.5f},},{},{{16.5f,-14.5f}},{}
                    ,{{11.55f},{9.62f},{7.7f}}},

                    {{{0.5f,-17.5f},{1.5f,-17.5f},{2.5f,-17.5f},{3.5f,-17.5f},{4.5f,-17.5f},{6.5f,-17.5f},{8.5f,-17.5f},{9.5f,-17.5f},{10.5f,-17.5f},{11.5f,-17.5f},{12.5f,-17.5f},{13.5f,-17.5f},{14.5f,-17.5f},{16.5f,-17.5f},{17.5f,-17.5f},{18.5f,-17.5f},{20.5f,-17.5f},},{},{{1.5f,-14.5f},{9.5f,-14.5f},{12.5f,-14.5f},{17.5f,-14.5f},},{},{{20.5f,-14.5f}},{}
                    ,{{13.5f},{11.25f},{9.0f}}},

                    {{{0.5f,-17.5f},{1.5f,-17.5f},{2.5f,-17.5f},{3.5f,-17.5f},{4.5f,-17.5f},{6.5f,-17.5f},{7.5f,-17.5f},{8.5f,-17.5f},{10.5f,-17.5f},{11.5f,-17.5f},{12.5f,-17.5f},{13.5f,-17.5f},{14.5f,-17.5f},{15.5f,-17.5f},{16.5f,-17.5f},{17.5f,-17.5f},{18.5f,-17.5f},{20.5f,-17.5f},{21.5f,-17.5f},{22.5f,-17.5f},{23.5f,-17.5f},},{},{{18.5f,-14.5f},{12.5f,-14.5f},{13.5f,-14.5f},{7.5f,-14.5f},{3.5f,-14.5f}},{},{{23.5f,-14.5f},},{}
                    ,{{15.6f},{13f},{10.4f}}},

                    {{{0.5f,-17.5f},{1.5f,-17.5f},{2.5f,-17.5f},{3.5f,-17.5f},{5.5f,-17.5f},{6.5f,-17.5f},{7.5f,-17.5f},{9.5f,-17.5f},{11.5f,-17.5f},{12.5f,-17.5f},{13.5f,-17.5f},{14.5f,-17.5f},{15.5f,-17.5f},{17.5f,-17.5f},{18.5f,-17.5f},{19.5f,-17.5f},{20.5f,-17.5f},{21.5f,-17.5f},{23.5f,-17.5f},},{},{{20.5f,-14.5f},{14.5f,-14.5f},{12.5f,-14.5f},{6.5f,-14.5f},{3.5f,-14.5f},{2.5f,-14.5f}},{},{{23.5f,-14.5f},},{}
                    ,{{15.9f},{13.25f},{10.6f}}},

                    {{{0.5f,-17.5f},{1.5f,-17.5f},{2.5f,-17.5f},{5.5f,-17.5f},{6.5f,-17.5f},{7.5f,-17.5f},{9.5f,-17.5f},{11.5f,-17.5f},{12.5f,-17.5f},{13.5f,-17.5f},{14.5f,-17.5f},{15.5f,-17.5f},{17.5f,-17.5f},},{{3.5f,-17.5f},},{{14.5f,-14.5f},{12.5f,-14.5f},{5.5f,-14.5f},{1.5f,-14.5f}},{},{{17.5f,-14.5f},},{}
                    ,{{9.75f},{8.125f},{6.5f}}},

                    {{{0.5f,-17.5f},{1.5f,-17.5f},{2.5f,-17.5f},{5.5f,-17.5f},{6.5f,-17.5f},{7.5f,-17.5f},{11.5f,-17.5f},},{{4.5f,-17.5f},{8.5f,-17.5f},},{{5.5f,-14.5f},{1.5f,-14.5f},},{},{{11.5f,-14.5f}},{}
                    ,{{8.1f},{6.75f},{5.4f}}},

            },
            {//levels
                    {{{0.5f,-17.5f},{1.5f,-17.5f},{5.5f,-17.5f},{6.5f,-17.5f},{7.5f,-17.5f},{8.5f,-17.5f},{10.5f,-17.5f},{11.5f,-17.5f},{12.5f,-17.5f},{14.5f,-17.5f},{15.5f,-17.5f},{16.5f,-17.5f},{20.5f,-17.5f},},{{2.5f,-17.5f},{4.5f,-17.5f},{17.5f,-17.5f},},{{11.5f,-14.5f},{16.5f,-14.5f},{6.5f,-14.5f},{1.5f,-14.5f}},{},{{20.5f,-14.5f},},{}
                    ,{{15f},{12.5f},{10f}}},

                    {{{0.5f,-17.5f},{1.5f,-17.5f},{4.5f,-17.5f},{5.5f,-17.5f},{7.5f,-17.5f},{8.5f,-17.5f},{9.5f,-17.5f},{11.5f,-17.5f},{12.5f,-17.5f},{13.5f,-17.5f},{14.5f,-17.5f},{18.5f,-17.5f},},{{2.5f,-17.5f},{6.5f,-17.5f},{15.5f,-17.5f},},{{6.5f,-14.5f},{8.5f,-14.5f},{12.5f,-14.5f}},{},{{18.5f,-14.5f},},{}
                    ,{{11.925f},{9.93f},{7.95f}}},

                    {{{0.5f,-17.5f},{1.5f,-17.5f},{2.5f,-17.5f},{6.5f,-17.5f},{7.5f,-17.5f},{9.5f,-17.5f},{11.5f,-17.5f},{13.5f,-17.5f},{15.5f,-17.5f},{17.5f,-17.5f},},{{4.5f,-17.5f},{8.5f,-17.5f},{12.5f,-17.5f},{16.5f,-17.5f},},{{15.5f,-14.5f},{8.5f,-14.5f},{9.5f,-14.5f},{1.5f,-14.5f}},{},{{13.5f,-14.5f},},{}
                    ,{{15.45f},{12.88f},{10.3f}}},

                    {{{0.5f,-17.5f},{1.5f,-17.5f},{2.5f,-17.5f},{8.5f,-17.5f},{9.5f,-17.5f},{10.5f,-17.5f},{12.5f,-17.5f},{13.5f,-17.5f},{14.5f,-17.5f},{15.5f,-17.5f},{16.5f,-17.5f},{18.5f,-17.5f},},{},{{9.5f,-14.5f},{13.6f,-14.5f},{15.5f,-14.5f},},{},{{18.5f,-14.5f}},{{3.5f,-17.5f},}
                    ,{{10.98f},{9.15f},{7.32f}}},

                    {{{0.5f,-17.5f},{1.5f,-17.5f},{4.5f,-17.5f},{5.5f,-17.5f},{6.5f,-17.5f},{7.5f,-17.5f},{9.5f,-17.5f},{10.5f,-17.5f},{11.5f,-17.5f},{12.5f,-17.5f},{15.5f,-17.5f},{17.5f,-17.5f},{19.5f,-17.5f},{21.5f,-17.5f},},{{14.5f,-17.5f},{18.5f,-17.5f},},{{15.5f,-14.5f},{4.5f,-14.5f},{10.5f,-14.5f},},{},{{21.5f,-14.5f}},{{2.5f,-17.5f},{8.5f,-17.5f},}
                    ,{{15.25f},{12.71f},{10.17f}}},

                    {{{0.5f,-17.5f},{1.5f,-17.5f},{5.5f,-17.5f},{6.5f,-17.5f},{10.5f,-17.5f},{11.5f,-17.5f},{12.5f,-17.5f},{14.5f,-17.5f},},{{4.5f,-17.5f},{9.5f,-17.5f},},{{1.5f,-14.5f}},{},{{14.5f,-14.5f},},{{2.5f,-17.5f},{7.5f,-17.5f},}
                    ,{{12.15f},{10.12f},{8.1f}}},

                    {{{0.5f,-17.5f},{1.5f,-17.5f},{2.5f,-17.5f},{3.5f,-17.5f},{7.5f,-17.5f},{9.5f,-17.5f},{11.5f,-17.5f},{15.5f,-17.5f},},{{4.5f,-17.5f},},{{7.5f,-14.5f},{1.5f,-14.5f}},{},{{15.5f,-14.5f},},{{5.5f,-17.5f},{10.5f,-17.5f},}
                    ,{{14.02f},{11.68f},{9.35f}}},

                    {{{0.5f,-17.5f},{1.5f,-17.5f},{2.5f,-17.5f},{5.5f,-17.5f},{6.5f,-17.5f},{11.5f,-17.5f},{13.5f,-17.5f},{15.5f,-17.5f},{17.5f,-17.5f},{19.5f,-17.5f},},{{4.5f,-17.5f},{7.5f,-17.5f},{9.5f,-17.5f},{10.5f,-17.5f},},{{11.5f,-14.5f},{5.5f,-14.5f},{1.5f,-14.5f}},{},{{19.5f,-14.5f},},{{14.5f,-17.5f},{16.5f,-17.5f},}
                    ,{{21f},{17.5f},{14.00f}}},

                    {{{0.5f,-17.5f},{1.5f,-17.5f},{6.5f,-17.5f},{10.5f,-17.5f},{11.5f,-17.5f},{13.5f,-17.5f},{15.5f,-17.5f},},{{2.5f,-17.5f},{4.5f,-17.5f},{7.5f,-17.5f},{8.5f,-17.5f},{9.5f,-17.5f},},{{6.5f,-14.5f},{1.5f,-14.5f}},{},{{15.5f,-14.5f},},{{5.5f,-17.5f},{12.5f,-17.5f},}
                    ,{{19.08f},{15.9f},{12.72f}}},
            },
            {
                    {{{0.5f,-17.5f},{1.5f,-17.5f},{2.5f,-17.5f},{4.5f,-17.5f},{6.5f,-17.5f},{8.5f,-17.5f},{11.5f,-17.5f},{13.5f,-17.5f},{14.5f,-17.5f},{15.5f,-17.5f},{17.5f,-17.5f},{21.5f,-17.5f},},{{5.5f,-17.5f},{9.5f,-17.5f},{10.5f,-17.5f},{12.5f,-17.5f},},{{14.5f,-14.5f},{11.5f,-14.5f},{4.5f,-14.5f},{1.5f,-14.5f},},{},{{21.5f,-14.5f}},{{3.5f,-17.5f},{16.5f,-17.5f},}
                    ,{{19.08f},{15.9f},{12.72f}}},

                    {{{0.5f,-17.5f},{1.5f,-17.5f},{2.5f,-17.5f},{10.5f,-17.5f},{14.5f,-17.5f},{16.5f,-17.5f},{17.5f,-17.5f},{18.5f,-17.5f},{23.5f,-17.5f},{25.5f,-17.5f},},{{4.5f,-17.5f},{12.5f,-17.5f},{13.5f,-17.5f},{20.5f,-17.5f},{22.5f,-17.5f},},{{17.5f,-14.5f},{14.5f,-14.5f},{12.5f,-14.5f},{4.5f,-14.5f},{2.5f,-14.5f}},{},{{25.5f,-14.5f},},{{3.5f,-17.5f},{8.5f,-17.5f},{9.5f,-17.5f},{15.5f,-17.5f},{19.5f,-17.5f},}
                    ,{{19.08f},{15.9f},{12.72f}}},

                    {{{0.5f,-17.5f},{1.5f,-17.5f},{2.5f,-17.5f},{9.5f,-17.5f},{13.5f,-17.5f},{15.5f,-17.5f},},{{4.5f,-17.5f},{12.5f,-17.5f},},{{9.5f,-14.5f},{1.5f,-14.5f},{3.5f,-14.5f}},{{3.5f,-17.5f},},{{15.5f,-14.5f},},{{7.5f,-17.5f},{8.5f,-17.5f},}
                    ,{{19.08f},{15.9f},{12.72f}}},

                    {
                            {{8.5f,-17.5f},{9.5f,-17.5f},},
                            {},
                            {{6.5f,-14.5f},{7.5f,-14.5f},{1.5f,-14.5f},},
                            {{0.5f,-17.5f},{1.5f,-17.5f},{2.5f,-17.5f},{3.5f,-17.5f},{4.5f,-17.5f},{5.5f,-17.5f},{6.5f,-17.5f},{7.5f,-17.5f},},
                            {{9.5f,-14.5f}},
                            {}
                    ,{{19.08f},{15.9f},{12.72f}}},

                    {
                            {{10.5f,-17.5f},{19.5f,-17.5f},{21.5f,-17.5f},{23.5f,-17.5f},},
                            {{12.5f,-17.5f},{18.5f,-17.5f},{20.5f,-17.5f},},
                            {{17.5f,-14.5f},{7.5f,-14.5f},{4.5f,-14.5f},{1.5f,-14.5f}},
                            {{0.5f,-17.5f},{1.5f,-17.5f},{2.5f,-17.5f},{3.5f,-17.5f},{4.5f,-17.5f},{6.5f,-17.5f},{7.5f,-17.5f},{8.5f,-17.5f},{17.5f,-17.5f},},
                            {{23.5f,-14.5f},},
                            {{5.5f,-17.5f},{9.5f,-17.5f},{14.5f,-17.5f},{15.5f,-17.5f},}
                    ,{{19.08f},{15.9f},{12.72f}}},


            },

            /*{
                    {{3.5f,-17.5f},{2.5f,-17.5f},{0.5f,-17.5f},{4.5f,-17.5f},{4.5f,-17.5f},{6.5f,-17.5f},{7.5f,-17.5f},{8.5f,-17.5f},{9.5f,-17.5f},{10.5f,-17.5f},{11.5f,-17.5f},{13.5f,-17.5f},{14.5f,-17.5f},{15.5f,-17.5f},},{},{{3.5f,-14.5f},{7.5f,-14.5f},{10.5f,-14.5f},{14.5f,-14.5f}}
                    /*{ //ground
                            {0, -2.25f},
                            {2, -2.25f},
                            {3, -2.25f},
                            {4, -2.25f},
                            {5, -2.25f},
                            {7, -2.25f},
                            {9, -2.25f},
                            {10, -2.25f},
                    },
                    { //trampolines

                    },
                    { //coins

                    }
            }*/
    };
    /* world /*levels  /*level  /*obstacles  /*x,y*/



    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private final float[] mRotationMatrix = new float[16];
    private float[] mTempMatrix = new float[16];
    private final float[] mModelMatrix = new float[16];



    public static float gravity =  -0.02f;
    public static float cameraDist = -4;
    public static float cameraCenter = -14;
    public float halfScreenWidth;
    public float ratio;

    private boolean createObjectQueue = false;
    private int queueMapNum = 0;
    private int queueWorldNum = 0;
    private int queueObjectIndex = 0;
    private int queueTypeIndex = 0;
    private int queueSpawnAmount = 2;
    private float queueTrophyX = 0;

    long startTime = System.nanoTime();
    private float frames = 0;
    public float fps = 60;

    public double getTime(){
        //Log.d("Level", "level error: Level " + (System.currentTimeMillis() - startTimeL) + " does not exist");
        return  (double)(System.currentTimeMillis() - startTimeL)/1000;

    }
    public void changeSeg(){
        endlessLevelAmount++;

        int levelDiffculty = (int) Math.floor(Math.random()*(endlessLevelAmount)*2);

        if((int) Math.floor(levelDiffculty/levels[0].length) < levels.length) {
            worldNum = (int) Math.floor(levelDiffculty / levels[0].length);
        }
        else{
            worldNum = levels.length-1;
        }
        mapNum = levelDiffculty%levels[0].length;
        for (int i = 0; i < blacklist.length; i++){
            if(worldNum == blacklist[i][0] && mapNum == blacklist[i][1]){
                changeSeg();
                return;
            }
        }
        EnlessModeWorlds.add(worldNum);
        EnlessModeLevels.add(mapNum);
    }
    public void deleteLastLevel(){
        for (int i = 0; i < levels[EnlessModeWorlds.get(endlessLevelAmount-2)][EnlessModeLevels.get(endlessLevelAmount-2)][0].length; i++) {
            ground.remove(0);
        }
        for (int i = 0; i < levels[EnlessModeWorlds.get(endlessLevelAmount-2)][EnlessModeLevels.get(endlessLevelAmount-2)][1].length; i++) {
            trampoline.remove(0);
        }
        for (int i = 0; i < levels[EnlessModeWorlds.get(endlessLevelAmount-2)][EnlessModeLevels.get(endlessLevelAmount-2)][3].length; i++) {
            BABlocks.remove(0);
        }
        for (int i = 0; i < levels[EnlessModeWorlds.get(endlessLevelAmount-2)][EnlessModeLevels.get(endlessLevelAmount-2)][5].length; i++) {
            supaTramp.remove(0);
        }
    }
    public void createEndlessMap(){
        if(!createObjectQueue){ // first time running function in queue, start building process
            Log.d("Queue", "Started Queue");
            changeSeg();
            if(worldNum > levels.length-1){
                worldNum = levels.length-1;
            }

            //create specific queue map and world nums so they won't change
            queueMapNum = mapNum;
            queueWorldNum = worldNum;
            queueTrophyX = trophy.x;
            createObjectQueue = true;
        }
        else{
            for(int i = 0; i<queueSpawnAmount; i++){
                if(queueObjectIndex >= levels[queueWorldNum][queueMapNum][queueTypeIndex].length){
                    queueTypeIndex++;
                    queueObjectIndex = 0;
                }
                if(queueTypeIndex<levels[queueWorldNum][queueMapNum].length) {

                    if (queueTypeIndex == 0 && levels[queueWorldNum][queueMapNum][0].length > 0)
                        ground.add(new Obstacle(levels[queueWorldNum][queueMapNum][0][queueObjectIndex][0] + queueTrophyX + 1.5f, levels[worldNum][mapNum][0][queueObjectIndex][1], 1, 5, "ground", grassTex, this));
                    else if (queueTypeIndex == 1 && levels[queueWorldNum][queueMapNum][1].length > 0)
                        trampoline.add(new Obstacle(levels[queueWorldNum][queueMapNum][1][queueObjectIndex][0] + queueTrophyX + 1.5f, levels[worldNum][mapNum][1][queueObjectIndex][1], 1, 5, "tramp", trampTex, this));
                    else if (queueTypeIndex == 3 && levels[queueWorldNum][queueMapNum][3].length > 0)
                        BABlocks.add(new Obstacle(levels[queueWorldNum][queueMapNum][3][queueObjectIndex][0] + queueTrophyX + 1.5f, levels[worldNum][mapNum][3][queueObjectIndex][1], 1, 5, "BABlock", stoneTex, this));
                    else if (queueTypeIndex == 5 && levels[queueWorldNum][queueMapNum][5].length > 0)
                        supaTramp.add(new Obstacle(levels[queueWorldNum][queueMapNum][5][queueObjectIndex][0] + queueTrophyX + 1.5f, levels[worldNum][mapNum][5][queueObjectIndex][1], 1, 5, "supaTramp", supaTrampTex, this));

                    queueObjectIndex++;
                }
                else{
                    Log.d("Queue", "Finished Queue");
                    createObjectQueue = false;
                    queueObjectIndex = 0;
                    queueTypeIndex = 0;
                    break;
                }
            }
        }
       /* changeSeg();
        if(worldNum > levels.length-1){
            worldNum = levels.length-1;
        }
        for (int i = 0; i < levels[worldNum][mapNum][0].length; i++) {
            ground.add(new Obstacle(levels[worldNum][mapNum][0][i][0] + trophy.x + 1.5f, levels[worldNum][mapNum][0][i][1], 1, 5, "ground", grassTex, this));
        }
        for (int i = 0; i < levels[worldNum][mapNum][1].length; i++) {
            trampoline.add(new Obstacle(levels[worldNum][mapNum][1][i][0] + trophy.x + 1.5f, levels[worldNum][mapNum][1][i][1], 1, 5, "tramp", trampTex, this));
        }

        for (int i = 0; i < levels[worldNum][mapNum][3].length; i++) {
            BABlocks.add(new Obstacle(levels[worldNum][mapNum][3][i][0] + trophy.x + 1.5f, levels[worldNum][mapNum][3][i][1], 1, 5, "BABlock", stoneTex, this));
        }
        for (int i = 0; i < levels[worldNum][mapNum][5].length; i++) {
            supaTramp.add(new Obstacle(levels[worldNum][mapNum][5][i][0] + trophy.x + 1.5f, levels[worldNum][mapNum][5][i][1], 1, 5, "supaTramp", supaTrampTex, this));
        }*/
    }
    public MyGLRenderer(Context context,int world, int level){
        mActivityContext = context;



        Log.d("World", "accessed world: " + world);
        Log.d("Level", "loaded level: " + level);
        if(world <levels.length && world >= 0) {
            worldNum = world;
            if (level < levels[worldNum].length) {
                mapNum = level;
            } else {
                mapNum = 0;
                Log.d("Level", "level error: Level " + level + " does not exist");
            }
        }
        else if(world == -1) {
            Log.d("World", "Loading Endless mode");
            mapNum = 0;
            worldNum = -1;
        }else if(world == -2) {
            Log.d("World", "Loading Easy Endless mode");
            mapNum = 0;
            worldNum = -2;
        }
        else{
            Log.d("World", "world error: World " + world + " does not exist");
            mapNum = 0;
            worldNum = 0;
        }
    }
    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {

        GLES20.glClearColor(0.5f, 0.3f, 0.2f, 1.0f);
        backgroundTex  = loadTexture(mActivityContext, R.drawable.test);
        coinTex = loadTexture(mActivityContext, R.drawable.testcoin);
        trampTex = loadTexture(mActivityContext, R.drawable.tramppillar);
        supaTrampTex = loadTexture(mActivityContext, R.drawable.supertram);
        grassTex = loadTexture(mActivityContext, R.drawable.grasspillar);
        stoneTex = loadTexture(mActivityContext, R.drawable.stonepillars);
        defaultTex = loadTexture(mActivityContext, R.drawable.ic_launcher);
        playerTex = loadTexture(mActivityContext, R.drawable.characterred);
        goldtrophyTex = loadTexture(mActivityContext, R.drawable.goldtrophy);
        silvertrophyTex = loadTexture(mActivityContext, R.drawable.silvertrophy);
        bronzetrophyTex = loadTexture(mActivityContext, R.drawable.bronzetrophy);

        mPlayer   = new Player(playerTex, this);

        loadMap();
        //background = new Obstacle(0,0,-cameraDist*ratio*2,-cameraDist*2,"background",this);
    }
    public void loadMap(){

        ground.clear();
        trampoline.clear();
        coins.clear();
        BABlocks.clear();

        if(worldNum < 0){
            if(worldNum == -2){
                easyEndless = true;
            }
            endless = true;
        }

        firstSwip = true;

        if(endless) changeSeg();

        mPlayer   = new Player(playerTex, this);
        for (int i = 0; i < levels[worldNum][mapNum][0].length; i++) {
            ground.add(new Obstacle(levels[worldNum][mapNum][0][i][0], levels[worldNum][mapNum][0][i][1], 1, 5, "ground", grassTex, this));
        }
        for (int i = 0; i < levels[worldNum][mapNum][1].length; i++) {
            trampoline.add(new Obstacle(levels[worldNum][mapNum][1][i][0], levels[worldNum][mapNum][1][i][1], 1, 5, "tramp", trampTex, this));
        }
        for (int i = 0; i < levels[worldNum][mapNum][2].length; i++) {
            coins.add(new Obstacle(levels[worldNum][mapNum][2][i][0], levels[worldNum][mapNum][2][i][1], .4f, .4f, "coin", coinTex, this));
        }
        for (int i = 0; i < levels[worldNum][mapNum][3].length; i++) {
            BABlocks.add(new Obstacle(levels[worldNum][mapNum][3][i][0], levels[worldNum][mapNum][3][i][1], 1, 5, "BABlock", stoneTex, this));
        }
        trophy = new Obstacle(levels[worldNum][mapNum][4][0][0], levels[worldNum][mapNum][4][0][1], .5f, .5f, "trophy", goldtrophyTex, this);

        for (int i = 0; i < levels[worldNum][mapNum][5].length; i++) {
            supaTramp.add(new Obstacle(levels[worldNum][mapNum][5][i][0], levels[worldNum][mapNum][5][i][1], 1, 5, "supaTramp", supaTrampTex, this));
        }

        pastTropyX = trophy.x;
    }
    public void endGame(){
        if(!completed) {

            if (coins.size() == 0) {


                if (endless) {
                    if(getTime() > levels[EnlessModeWorlds.get(EnlessModeWorlds.size()-2)][EnlessModeLevels.get(EnlessModeLevels.size()-2)][levels[worldNum][mapNum].length - 1][0][0] && !easyEndless){
                        ((OpenGLES20Activity) mActivityContext).run();
                    } else {
                        spawnNextLevel = true;
                        deleteLastLevel = true;
                        pastTropyX = trophy.x;
                        for (int i = 0; i < levels[worldNum][mapNum][2].length; i++) {
                            coins.add(new Obstacle(levels[worldNum][mapNum][2][i][0] + trophy.x + 1.5f, levels[worldNum][mapNum][2][i][1], .4f, .4f, "coin", coinTex, this));
                        }
                        trophy = new Obstacle(levels[worldNum][mapNum][4][0][0] + trophy.x + 1.5f, levels[worldNum][mapNum][4][0][1], .5f, .5f, "trophy", goldtrophyTex, this);
                        startTimeL = System.currentTimeMillis();
                    }
                }
                else{
                    for (int i = 0; i < levels[worldNum][mapNum][levels[worldNum][mapNum].length - 1].length; i++) {
                        if ( getTime() < levels[worldNum][mapNum][levels[worldNum][mapNum].length - 1][i][0]){
                            if(i == 2){
                                starAmount = 3;
                            } else if(i == 1){
                                starAmount = 2;
                            } else if(i == 0){
                                starAmount = 1;
                            } else {
                                starAmount = 0;
                            }
                        }
                    }
                    completed = true;
                    Log.d("End Game", "Yay! ...and there was much rejoicing..." + starAmount);

                    ((OpenGLES20Activity) mActivityContext).run();

                }

               // RelativeLayout dankness = (RelativeLayout)((Activity)mActivityContext).getWindow().getDecorView().findViewById(R.id.dankEndScreen);
                //FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.WRAP_CONTENT);

                //params.rightMargin =0;
                //dankness.setLayoutParams(params);
                //viewFlipper = (RelativeLayout) mActivityContext.findViewById(R.id.dankEndScreen);
            } else {
                Log.d("End Game", "not enough coins");
            }
        }
    }
    @Override
    public void onDrawFrame(GL10 unused) {
        //update all objects
        mPlayer.update();

        if(mPlayer.posX > trophy.x - halfScreenWidth && endless && spawnNextLevel){
            createEndlessMap();

            spawnNextLevel = false;
        }

        if(mPlayer.posX - 1 > pastTropyX + halfScreenWidth  && endless && deleteLastLevel){
            deleteLastLevel();
            deleteLastLevel = false;
        }
        if(createObjectQueue){
            createEndlessMap();
        }


        float[] scratch = new float[16];

       // mTempMatrix = mMVPMatrix.clone();
        //Matrix.multiplyMM(scratch, 0, mTempMatrix, 0, mModelMatrix, 0);

        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        //Draw background
        background.draw(mMVPMatrix);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, -mPlayer.posX, cameraCenter, cameraDist, -mPlayer.posX, cameraCenter, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);

        /** Draw Background **/
        //mModelMatrix = mBaseMatrix.clone();
        Matrix.setIdentityM(mModelMatrix, 0); // initialize to identity matrix
        Matrix.translateM(mModelMatrix, 0, -mPlayer.posX, cameraCenter, 0); // translation to the player position

        Matrix.setRotateM(mRotationMatrix, 0, 0, 0, 0, 1.0f);

        mTempMatrix = mModelMatrix.clone();
        Matrix.multiplyMM(mModelMatrix, 0, mTempMatrix, 0, mRotationMatrix, 0);

        mTempMatrix = mMVPMatrix.clone();
        Matrix.multiplyMM(scratch, 0, mTempMatrix, 0, mModelMatrix, 0);
        // Draw square
        background.draw(scratch);



        /** Draw Player **/
        //mModelMatrix = mBaseMatrix.clone();
        Matrix.setIdentityM(mModelMatrix, 0); // initialize to identity matrix
        Matrix.translateM(mModelMatrix, 0, -mPlayer.posX, mPlayer.posY, 0); // translation to the player position

        Matrix.setRotateM(mRotationMatrix, 0, mPlayer.mAngle, 0, 0, 1.0f);

        mTempMatrix = mModelMatrix.clone();
        Matrix.multiplyMM(mModelMatrix, 0, mTempMatrix, 0, mRotationMatrix, 0);

        mTempMatrix = mMVPMatrix.clone();
        Matrix.multiplyMM(scratch, 0, mTempMatrix, 0, mModelMatrix, 0);
        // Draw square
        mPlayer.draw(scratch);

        /** Draw Trophy **/
        //mModelMatrix = mBaseMatrix.clone();
        Matrix.setIdentityM(mModelMatrix, 0); // initialize to identity matrix
        Matrix.translateM(mModelMatrix, 0, -trophy.x, trophy.y, 0); // translation to the player position

        Matrix.setRotateM(mRotationMatrix, 0, 0, 0, 0, 1.0f);

        mTempMatrix = mModelMatrix.clone();
        Matrix.multiplyMM(mModelMatrix, 0, mTempMatrix, 0, mRotationMatrix, 0);

        mTempMatrix = mMVPMatrix.clone();
        Matrix.multiplyMM(scratch, 0, mTempMatrix, 0, mModelMatrix, 0);
        // Draw square
        trophy.draw(scratch);

       // Log.d("Player", "posy: " + mPlayer.posY);
        for(int i = 0; i < ground.size(); i++) {
           // Log.d("End Game",  "" +( -ground.get(5).x - -mPlayer.posX) +"");

            Matrix.setIdentityM(mModelMatrix, 0); // initialize to identity matrix
            Matrix.translateM(mModelMatrix, 0, -ground.get(i).x, ground.get(i).y, 0); // translation to the player position

            Matrix.setRotateM(mRotationMatrix, 0, 0, 0, 0, 1.0f);

            mTempMatrix = mModelMatrix.clone();
            Matrix.multiplyMM(mModelMatrix, 0, mTempMatrix, 0, mRotationMatrix, 0);

            mTempMatrix = mMVPMatrix.clone();
            Matrix.multiplyMM(scratch, 0, mTempMatrix, 0, mModelMatrix, 0);
            // Draw square
            ground.get(i).draw(scratch);

        }
        for(int i = 0; i < coins.size(); i++) {
            Matrix.setIdentityM(mModelMatrix, 0); // initialize to identity matrix
            Matrix.translateM(mModelMatrix, 0, -coins.get(i).x, coins.get(i).y, 0); // translation to the player position

            Matrix.setRotateM(mRotationMatrix, 0, 0, 0, 0, 1.0f);

            mTempMatrix = mModelMatrix.clone();
            Matrix.multiplyMM(mModelMatrix, 0, mTempMatrix, 0, mRotationMatrix, 0);

            mTempMatrix = mMVPMatrix.clone();
            Matrix.multiplyMM(scratch, 0, mTempMatrix, 0, mModelMatrix, 0);
            // Draw square
            coins.get(i).draw(scratch);
        }
        for(int i = 0; i < trampoline.size(); i++) {
            Matrix.setIdentityM(mModelMatrix, 0); // initialize to identity matrix
            Matrix.translateM(mModelMatrix, 0, -trampoline.get(i).x, trampoline.get(i).y, 0); // translation to the player position

            Matrix.setRotateM(mRotationMatrix, 0, 0, 0, 0, 1.0f);

            mTempMatrix = mModelMatrix.clone();
            Matrix.multiplyMM(mModelMatrix, 0, mTempMatrix, 0, mRotationMatrix, 0);

            mTempMatrix = mMVPMatrix.clone();
            Matrix.multiplyMM(scratch, 0, mTempMatrix, 0, mModelMatrix, 0);
            // Draw square
            trampoline.get(i).draw(scratch);
        }
        for(int i = 0; i < supaTramp.size(); i++) {
            Matrix.setIdentityM(mModelMatrix, 0); // initialize to identity matrix
            Matrix.translateM(mModelMatrix, 0, -supaTramp.get(i).x, supaTramp.get(i).y, 0); // translation to the player position

            Matrix.setRotateM(mRotationMatrix, 0, 0, 0, 0, 1.0f);

            mTempMatrix = mModelMatrix.clone();
            Matrix.multiplyMM(mModelMatrix, 0, mTempMatrix, 0, mRotationMatrix, 0);

            mTempMatrix = mMVPMatrix.clone();
            Matrix.multiplyMM(scratch, 0, mTempMatrix, 0, mModelMatrix, 0);
            // Draw square
            supaTramp.get(i).draw(scratch);
        }
        for(int i = 0; i < BABlocks.size(); i++) {
            if(!BABlocks.get(i).deleted) {
                BABlocks.get(i).update();

                Matrix.setIdentityM(mModelMatrix, 0); // initialize to identity matrix
                Matrix.translateM(mModelMatrix, 0, -BABlocks.get(i).x, BABlocks.get(i).y, 0); // translation to the player position

                Matrix.setRotateM(mRotationMatrix, 0, 0, 0, 0, 1.0f);

                mTempMatrix = mModelMatrix.clone();
                Matrix.multiplyMM(mModelMatrix, 0, mTempMatrix, 0, mRotationMatrix, 0);

                mTempMatrix = mMVPMatrix.clone();
                Matrix.multiplyMM(scratch, 0, mTempMatrix, 0, mModelMatrix, 0);
                // Draw square
                BABlocks.get(i).draw(scratch);
            }
            else {
                BABlocks.remove(i);
            }
        }

        //fps stuff
        frames++;
        if(System.nanoTime() - startTime >= 1000000000) {
            //Log.d("FPSCounter", "fps: " + frames);
            fps = frames;
            frames = 0;

            startTime = System.nanoTime();

            if(!endless){
                checkTrophy();
            }

        }

    }
    public void checkTrophy(){
        for (int i = 0; i < levels[worldNum][mapNum][levels[worldNum][mapNum].length - 1].length; i++) {
            if ( getTime() < levels[worldNum][mapNum][levels[worldNum][mapNum].length - 1][i][0]){
                if(i == 2){
                   trophy.mTextureDataHandle = goldtrophyTex;
                }
                else if(i == 1){
                   trophy.mTextureDataHandle = silvertrophyTex;
                } else if(i == 0){
                    trophy.mTextureDataHandle = bronzetrophyTex;
                } else {
                    trophy.mTextureDataHandle = bronzetrophyTex;
                }
            }
        }
    }
    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        // Adjust the viewport based on geometry changes,
        // such as screen rotation
        GLES20.glViewport(0, 0, width, height);

        ratio = (float) width / (float) height;


        //Create background here because now we have ratio
        background = new Obstacle(0,0,-cameraDist*2,-cameraDist*2,"background",backgroundTex,this);
        halfScreenWidth = -cameraDist*ratio;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 1, 100);

    }

    /**
     * Utility method for compiling a OpenGL shader.
     *
     * <p><strong>Note:</strong> When developing shaders, use the checkGlError()
     * method to debug shader coding errors.</p>
     *
     * @param type - Vertex or fragment shader type.
     * @param shaderCode - String containing the shader code.
     * @return - Returns an id for the shader.
     */
    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    /**
    * Utility method for debugging OpenGL calls. Provide the name of the call
    * just after making it:
    *
    * <pre>
    * mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
    * MyGLRenderer.checkGlError("glGetUniformLocation");</pre>
    *
    * If the operation is not successful, the check throws an error.
    *
    * @param glOperation - Name of the OpenGL call to check.
    */
    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e(TAG, glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }
    public static int loadTexture(final Context context, final int resourceId)
    {
        final int[] textureHandle = new int[1];

        GLES20.glGenTextures(1, textureHandle, 0);

        if (textureHandle[0] != 0)
        {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inScaled = false;   // No pre-scaling

            // Read in the resource
            final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);

            // Bind to the texture in OpenGL
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureHandle[0]);

            // Set filtering
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
            GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);

            // Load the bitmap into the bound texture.
            GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

            // Recycle the bitmap, since its data has been loaded into OpenGL.
            bitmap.recycle();
        }

        if (textureHandle[0] == 0)
        {
            throw new RuntimeException("Error loading texture.");
        }

        return textureHandle[0];
    }



    /**
     * Returns the rotation angle of the triangle shape (mTriangle).
     *
     * @return - A float representing the rotation angle.
     */
   // public float getAngle() {
     //   return mAngle;
   // }

    /**
     * Sets the rotation angle of the triangle shape (mTriangle).
     */
   // public void setAngle(float angle) {
      //  mAngle = angle;
    //}

}