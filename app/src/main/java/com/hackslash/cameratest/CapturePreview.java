package com.hackslash.cameratest;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


public class CapturePreview extends Activity implements SurfaceHolder.Callback,SensorEventListener{


    int k=0;

    Camera camera;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    boolean previewing = false;
    LayoutInflater controlInflater = null;
    Float azimut;  // View to draw a compass
    private SensorManager mSensorManager;
    Sensor orientation;
    public float xaxis[]=new float[50];
    public float yaxis[]=new float[50];
    public float zaxis[]=new float[50];


    public int count=1;
    public float avg=0;
    public boolean flag=false;
    public float resx=0;
    public float resy=0;
    public float resz=0;
    public Handler handler;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


        getWindow().setFormat(PixelFormat.UNKNOWN);
        surfaceView = (SurfaceView)findViewById(R.id.camerapreview);

        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);


        controlInflater = LayoutInflater.from(getBaseContext());
        View viewControl = controlInflater.inflate(R.layout.control, null);

        LayoutParams layoutParamsControl
                = new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT);
        this.addContentView(viewControl, layoutParamsControl);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        orientation = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);


        Button capture=(Button)viewControl.findViewById(R.id.takepicture);
        capture.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(CapturePreview.this,"HEEE HAWWWWW",Toast.LENGTH_SHORT).show();
                mSensorManager.registerListener(CapturePreview.this, orientation, SensorManager.SENSOR_DELAY_UI);


                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {

                        mSensorManager.unregisterListener(CapturePreview.this);

                        int i;
                        float sumx=0;
                        float sumy=0;
                        float sumz=0;
                        for(i=0;i<50;i++){
                            sumx+=xaxis[i];
                            sumy+=yaxis[i];
                            sumz+=zaxis[i];
                        }
                        resx=sumx/count;
                        resy=sumy/count;
                        resz=sumz/count;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Toast.makeText(getApplicationContext(),resx+ " "+resy+" "+resz,Toast.LENGTH_SHORT).show();

                                Intent i=new Intent(getApplicationContext(),Calculation.class);

                                Bundle extras = new Bundle();
                                extras.putString("angleX", Float.toString(resx));
                                extras.putString("angleY", Float.toString(resy));
                                extras.putString("angleZ", Float.toString(resz));
                                i.putExtras(extras);
                                startActivity(i);



                            }
                        });


                    }
                }, 3000);








            }
        });

        //Toast.makeText(getApplicationContext(),res+ " ",Toast.LENGTH_SHORT).show();




        }





    public void runtest(){
        /*
        int i;
        float sum=0;
        for(i=0;i<50;i++){
            sum=xaxis[i]/count;
        }
        res=sum/count;
        //Toast.makeText(getApplicationContext(),res+ " ",Toast.LENGTH_SHORT).show();
        */
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
// TODO Auto-generated method stub
        if(previewing){
            //camera.setDisplayOrientation(180);
            camera.stopPreview();
            previewing = false;
        }

        if (camera != null){
            try {
                camera.setPreviewDisplay(surfaceHolder);
                camera.startPreview();
                previewing = true;
            } catch (IOException e) {
// TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {

            camera.setDisplayOrientation(90);

        } else {

            camera.setDisplayOrientation(0);

        }
        // start preview with new settings
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();

        } catch (Exception e) {
            //Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }



    protected void onResume() {
        super.onResume();
        //mSensorManager.registerListener(this, orientation, SensorManager.SENSOR_DELAY_UI);
        //mSensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
    }

    protected void onPause() {
        super.onPause();
        //mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    float[] mGravity;
    float[] mGeomagnetic;


    public void onSensorChanged(SensorEvent event) {

        //if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE)
        //   mGravity = event.values;

        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION)
            mGeomagnetic = event.values;
        if (mGeomagnetic != null) {
            float A[] = new float[9];

            xaxis[k]=event.values[0];
            yaxis[k]=event.values[1];
            zaxis[k]=event.values[2];
            //sumf=(float)(sumf+xaxis[k])/count;
            count++;

            Log.e("x axis ", event.values[0] + " ");
            Log.e("y axis ", event.values[1] + " ");

            //Log.e("x axis ", event.values[0] + " ");

            // xaxis[k++]=event.values[0];
            // Log.e("y axis", event.values[1] + " ");
            //Log.e("
            // z axis", event.values[2] + " ");
            SensorManager.getRotationMatrixFromVector(A, event.values);
            float orientation[] = new float[3];


            k++;

            SensorManager.getOrientation(A, orientation);
            azimut = orientation[0]; // orientation contains: azimut, pitch and roll

        }
        //mCustomDrawableView.invalidate();

    }



    @Override
    public void surfaceCreated(SurfaceHolder holder) {
// TODO Auto-generated method stub
        camera = Camera.open();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
// TODO Auto-generated method stub
        camera.stopPreview();
        camera.release();
        camera = null;
        previewing = false;
    }
}
