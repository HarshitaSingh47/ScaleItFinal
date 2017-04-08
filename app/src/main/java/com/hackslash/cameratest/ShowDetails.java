package com.hackslash.cameratest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ShowDetails extends AppCompatActivity {

    TextView textViewW1A1, textViewW1A2, textViewW2A1, textViewW2A2;
    TextView textViewW3A1, textViewW3A2, textViewW4A1, textViewW4A2;
    TextView textViewW1S, textViewW1C, textViewW2S, textViewW2C;
    TextView textViewW3S, textViewW3C, textViewW4S, textViewW4C;
    dbhandler entry=new dbhandler(ShowDetails.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_details);

        try{
            entry.open();


        }catch (Exception e){


        }


        textViewW1A1 = (TextView)findViewById(R.id.textViewW1A1);
        textViewW1A2 = (TextView)findViewById(R.id.textViewW1A2);
        textViewW2A1 = (TextView)findViewById(R.id.textViewW2A1);
        textViewW2A2 = (TextView)findViewById(R.id.textViewW2A2);
        textViewW3A1 = (TextView)findViewById(R.id.textViewW3A1);
        textViewW3A2 = (TextView)findViewById(R.id.textViewW3A2);
        textViewW4A1 = (TextView)findViewById(R.id.textViewW4A1);
        textViewW4A2 = (TextView)findViewById(R.id.textViewW4A2);
        textViewW1S = (TextView)findViewById(R.id.textViewW1S);
        textViewW1C = (TextView)findViewById(R.id.textViewW1C);
        textViewW2S = (TextView)findViewById(R.id.textViewW2S);
        textViewW2C = (TextView)findViewById(R.id.textViewW2C);
        textViewW3S = (TextView)findViewById(R.id.textViewW3S);
        textViewW3C = (TextView)findViewById(R.id.textViewW3C);
        textViewW4S = (TextView)findViewById(R.id.textViewW4S);
        textViewW4C = (TextView)findViewById(R.id.textViewW4C);
    }
}
