package com.hackslash.cameratest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by snehil on 8/4/17.
 */

public class BaseActivity extends AppCompatActivity {

    TextView tv;
    public static double height;
    private int STORAGE_PERMISSION_CODE = 23;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base);

        tv=(TextView)findViewById(R.id.editText_height);

        Button open=(Button)findViewById(R.id.button);
        open.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                height = Double.parseDouble(tv.getText().toString());
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

        if(isPermissionAllowed()){

        }

        else{
            requestPermission();
        }
    }

    public static double getHeight(){
        return height;
    }

    private boolean isPermissionAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED)
            return true;

        //If permission is not granted returning false
        return false;
    }

    private void requestPermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)
                && ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)){
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this,new String[]{
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA},STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Displaying a toast
                Toast.makeText(this, "Permission granted!", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Permission denied! App may not work properly", Toast.LENGTH_LONG).show();
            }
        }
    }
}
