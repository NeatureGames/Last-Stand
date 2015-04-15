package com.example.zach.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.zach.laststand.OpenGLES20Activity;
import com.example.zach.laststand.R;

public class Levels extends Activity  {

    Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levels);
        addListenerOnButton();
    }

    public void addListenerOnButton() {
        final Context context = this;

        button = (Button) findViewById(R.id.level);

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent intent = new Intent(context, OpenGLES20Activity.class);
                startActivity(intent);

            }

        });

    }

}
