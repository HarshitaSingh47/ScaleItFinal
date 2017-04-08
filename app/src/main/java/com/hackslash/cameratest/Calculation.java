package com.hackslash.cameratest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by snehil on 8/4/17.
 */



public class Calculation extends AppCompatActivity {

    Bundle userinput;
    String[] angle=new String[3];
    float angles[]=new float[3];
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calc);


        userinput=getIntent().getExtras();
        angle[0]=userinput.getString("angleX");
        angle[1]=userinput.getString("angleY");
        angle[2]=userinput.getString("angleZ");

        angles[0]=Float.valueOf(angle[0]);
        angles[1]=Float.valueOf(angle[1]);
        angles[2]=Float.valueOf(angle[2]);

        Toast.makeText(this, angles[0]+ " " + angles[1]+ " "+ angles[2] , Toast.LENGTH_SHORT).show();





    }
}
