package com.hackslash.cameratest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;


public class ShowResults extends AppCompatActivity{

    dbhandler entry=new dbhandler(ShowResults.this);
    TextView textViewW1A1;
    TextView textViewW1A2;
    TextView textViewW2A2;
    TextView textViewW2A1;
    TextView textViewW3A1;
    TextView textViewW3A2;
    TextView textViewW4A1;
    TextView textViewW4A2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);
        textViewW1A1=(TextView)findViewById(R.id.textViewW1A1);
        textViewW1A2=(TextView)findViewById(R.id.textViewW1A2);
        textViewW2A1=(TextView)findViewById(R.id.textViewW2A1);
        textViewW2A2=(TextView)findViewById(R.id.textViewW2A2);
        textViewW3A1=(TextView)findViewById(R.id.textViewW3A1);
        textViewW3A2=(TextView)findViewById(R.id.textViewW3A2);
        textViewW4A1=(TextView)findViewById(R.id.textViewW4A1);
        textViewW4A2=(TextView)findViewById(R.id.textViewW4A2);

        textViewW1A1.setText(entry.querydata(1).toString());
        textViewW1A2.setText(entry.querydata(1).toString());



    }
}
