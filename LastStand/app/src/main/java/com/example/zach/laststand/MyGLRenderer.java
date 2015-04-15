package com.example.zach.laststand;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.util.Log;

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


    //private Map mTriangle;
    public Player   mPlayer;

    //private Map mMap = new Map(0);


    ArrayList<Obstacle> ground = new ArrayList<>();
    ArrayList<Obstacle> trampoline = new ArrayList<>();
    ArrayList<Obstacle> coins = new ArrayList<>();


    int mapNum = 0;

    public MyGLRenderer(Context context){
        mActivityContext = context;
    }

    float[][][][] levels =
    {

            {{{0f,-17.5f},{2f,-17.5f},{3f,-17.5f},{4f,-17.5f},{6f,-17.5f},{7f,-17.5f},{8f,-17.5f},{9f,-17.5f},{10f,-17.5f},{12f,-17.5f},{13f,-17.5f},{14f,-17.5f},{15f,-17.5f},{17f,-17.5f},{19f,-17.5f},{19f,-17.5f},{24f,-17.5f},{24f,-17.5f},{27f,-17.5f},{26f,-17.5f},{20f,-17.5f},{21f,-17.5f},{22f,-17.5f},{23f,-17.5f},{28f,-17.5f},},{},{{3f,-14.5f},{7f,-14.5f},{10f,-14.5f},{13f,-14.5f},{22f,-14f},{27f,-14.5f}}}


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
     /*levels  /*level  /*obstacles  /*x,y*/



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

    long startTime = System.nanoTime();
    private float frames = 0;
    public float fps = 60;
    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {

        // Set the background frame color
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        mPlayer   = new Player(this);

        //Temp colors
        float [] blue = {0.2f, 0.709803922f, 0.898039216f, 1.0f};
        float [] yellow = {1f, 0.92f, 0.23f, 1.0f};

        for(int i = 0; i < levels[mapNum][0].length; i++){
            ground.add(new Obstacle(levels[mapNum][0][i][0], levels[mapNum][0][i][1], 1, 5, blue));
        }
        for(int i = 0; i < levels[mapNum][1].length; i++){
            trampoline.add(new Obstacle(levels[mapNum][1][i][0],levels[mapNum][1][i][1], 1, 5, blue));
        }
        for(int i = 0; i < levels[mapNum][2].length; i++){
            coins.add(new Obstacle(levels[mapNum][2][i][0],levels[mapNum][2][i][1], .25f, .25f, yellow));
        }

    }

    @Override
    public void onDrawFrame(GL10 unused) {
        //update all objects
        mPlayer.update();



        float[] scratch = new float[16];

       // mTempMatrix = mMVPMatrix.clone();
        //Matrix.multiplyMM(scratch, 0, mTempMatrix, 0, mModelMatrix, 0);

        // Draw background color
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, -mPlayer.posX, cameraCenter, cameraDist, -mPlayer.posX, cameraCenter, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);


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
       // Log.d("Player", "posy: " + mPlayer.posY);
        for(int i = 0; i < ground.size(); i++) {
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

      /*  Matrix.setIdentityM(mModelMatrix, 0); // initialize to identity matrix
        Matrix.translateM(mModelMatrix, 0, ground.get(1).x, ground.get(1).y, 0); // translation to the player position

        Matrix.setRotateM(mRotationMatrix, 0, 0, 0, 0, 1.0f);

        mTempMatrix = mModelMatrix.clone();
        Matrix.multiplyMM(mModelMatrix, 0, mTempMatrix, 0, mRotationMatrix, 0);

        mTempMatrix = mMVPMatrix.clone();
        Matrix.multiplyMM(scratch, 0, mTempMatrix, 0, mModelMatrix, 0);
        // Draw square
        ground.get(1).draw(scratch);*/

      // }




        //fps stuff
        frames++;
        if(System.nanoTime() - startTime >= 1000000000) {
            //Log.d("FPSCounter", "fps: " + frames);
            fps = frames;
            frames = 0;

            startTime = System.nanoTime();
        }

        /*
       // mModelMatrix = mBaseMatrix.clone();
        Matrix.setIdentityM(mModelMatrix, 0); // initialize to identity matrix
        Matrix.translateM(mModelMatrix, 0, 0.5f, 0, 0); // translation to the left

        Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0, 0, 1.0f);

        mTempMatrix = mModelMatrix.clone();
        Matrix.multiplyMM(mModelMatrix, 0, mTempMatrix, 0, mRotationMatrix, 0);

        mTempMatrix = mMVPMatrix.clone();
        Matrix.multiplyMM(scratch, 0, mTempMatrix, 0, mModelMatrix, 0);
        // Draw square
        mTriangle.draw(scratch);*/
        // Create a rotation for the triangle

        // Use the following code to generate constant rotation.
        // Leave this code out when using TouchEvents.
        // long time = SystemClock.uptimeMillis() % 4000L;
        // float angle = 0.090f * ((int) time);

       // Matrix.setRotateM(mRotationMatrix, 0, mAngle, 0, 0, 1.0f);
      //  Matrix.translateM(mTempTranslationMatrix, 0, mMVPMatrix, 0, -.5f, 0, 0);

        // Combine the rotation matrix with the projection and camera view
        // Note that the mMVPMatrix factor *must be first* in order
        // for the matrix multiplication product to be correct.
        //Matrix.multiplyMM(scratch, 0, mTempTranslationMatrix, 0, mRotationMatrix, 0);
       // Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, scratch, 0);

        // Draw triangle
      //  mTriangle.draw(scratch);
    }

    @Override
    public void onSurfaceChanged(GL10 unused, int width, int height) {
        // Adjust the viewport based on geometry changes,
        // such as screen rotation
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / (float) height;

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