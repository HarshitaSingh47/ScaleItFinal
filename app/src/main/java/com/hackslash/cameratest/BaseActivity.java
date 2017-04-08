package com.hackslash.cameratest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Created by snehil on 8/4/17.
 */

public class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base);

        Button open=(Button)findViewById(R.id.button);
        open.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(BaseActivity.this, CapturePreview.class);
                startActivity(i);
            }
        });

        Button open2=(Button)findViewById(R.id.button2);
        open2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(BaseActivity.this, Angle.class);
                startActivity(i);
            }
        });


    }
}
