package com.hackslash.cameratest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class ShowResults extends AppCompatActivity{

    dbhandler entry=new dbhandler(ShowResults.this);
    TextView textViewW1L1;
    TextView textViewW1L2;
    TextView textViewW2L2;
    TextView textViewW2L1;
    TextView textViewW3L1;
    TextView textViewW3L2;
    TextView textViewW4L1;
    TextView textViewW4L2;

    TextView textViewWL1;
    TextView textViewWL2;
    TextView textViewWL3;
    TextView textViewWL4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);
        textViewW1L1=(TextView)findViewById(R.id.textViewW1A1);
        textViewW1L2=(TextView)findViewById(R.id.textViewW1A2);
        textViewW2L1=(TextView)findViewById(R.id.textViewW2A1);
        textViewW2L2=(TextView)findViewById(R.id.textViewW2A2);
        textViewW3L1=(TextView)findViewById(R.id.textViewW3A1);
        textViewW3L2=(TextView)findViewById(R.id.textViewW3A2);
        textViewW4L1=(TextView)findViewById(R.id.textViewW4A1);
        textViewW4L2=(TextView)findViewById(R.id.textViewW4A2);

        textViewWL1=(TextView)findViewById(R.id.textViewWS);
        textViewWL2=(TextView)findViewById(R.id.textViewW2S);
        textViewWL3=(TextView)findViewById(R.id.textViewW3S);
        textViewWL4=(TextView)findViewById(R.id.textViewW4S);


        //textViewW1A1.setText(entry.querydata(1).toString());
        //textViewW1A2.setText(entry.querydata(1).toString());

        double res[][]=Calculation.getCoords();

        textViewW1L1.setText(String.valueOf(res[0][0]));
        textViewW1L2.setText(String.valueOf(res[0][1]));
        textViewW2L1.setText(String.valueOf(res[1][0]));
        textViewW2L2.setText(String.valueOf(res[1][1]));
        textViewW3L1.setText(String.valueOf(res[2][0]));
        textViewW3L2.setText(String.valueOf(res[2][1]));
        textViewW4L1.setText(String.valueOf(res[3][0]));
        textViewW4L2.setText(String.valueOf(res[3][1]));

        double res2[]=Calculation.getInter();

        textViewWL1.setText(String.valueOf(res2[0]));
        textViewWL2.setText(String.valueOf(res2[1]));
        textViewWL3.setText(String.valueOf(res2[2]));
        textViewWL4.setText(String.valueOf(res2[3]));





    }
}
