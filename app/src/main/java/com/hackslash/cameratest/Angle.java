package com.hackslash.cameratest;

/**
 * Created by snehil on 8/4/17.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Angle extends Activity implements SensorEventListener {

    Float azimut;  // View to draw a compass

    public class CustomDrawableView extends View {
        Paint paint = new Paint();

        public CustomDrawableView(Context context) {
            super(context);
            paint.setColor(0xff00ff00);
            paint.setStyle(Style.STROKE);
            paint.setStrokeWidth(2);
            paint.setAntiAlias(true);
        }

        ;

        protected void onDraw(Canvas canvas) {
            int width = getWidth();
            int height = getHeight();
            int centerx = width / 2;
            int centery = height / 2;
            canvas.drawLine(centerx, 0, centerx, height, paint);
            canvas.drawLine(0, centery, width, centery, paint);
            // Rotate the canvas with the azimut
            if (azimut != null)
                canvas.rotate(-azimut * 360 / (2 * 3.14159f), centerx, centery);
            paint.setColor(0xff0000ff);
            canvas.drawLine(centerx, -1000, centerx, +1000, paint);
            canvas.drawLine(-1000, centery, 1000, centery, paint);
            canvas.drawText("N", centerx + 5, centery - 10, paint);
            canvas.drawText("S", centerx - 10, centery + 15, paint);
            paint.setColor(0xff00ff00);
        }
    }

    CustomDrawableView mCustomDrawableView;
    private SensorManager mSensorManager;
    Sensor orientation;
    //Sensor magnetometer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCustomDrawableView = new CustomDrawableView(this);
        setContentView(mCustomDrawableView);    // Register the sensor listeners
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        orientation = mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        //magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, orientation, SensorManager.SENSOR_DELAY_UI);
        //mSensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    float[] mGravity;
    float[] mGeomagnetic;

    /*
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            mGravity = event.values;
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            mGeomagnetic = event.values;
        if (mGravity != null && mGeomagnetic != null) {
            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                azimut = orientation[0]; // orientation contains: azimut, pitch and roll
            }
        }
        mCustomDrawableView.invalidate();
    }*/


    public void onSensorChanged(SensorEvent event) {

        //if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE)
        //   mGravity = event.values;

        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION)
            mGeomagnetic = event.values;
        if (mGeomagnetic != null) {
            float A[] = new float[9];

            Log.e("x axis ", event.values[0] + " ");
            Log.e("y axis", event.values[1] + " ");
            Log.e("z axis", event.values[2] + " ");
            SensorManager.getRotationMatrixFromVector(A, event.values);
            float orientation[] = new float[3];
            SensorManager.getOrientation(A, orientation);
            azimut = orientation[0]; // orientation contains: azimut, pitch and roll

        }
        mCustomDrawableView.invalidate();

        /*


        float rotationMatrix[];
        switch (event.sensor.getType()) {

            case Sensor.TYPE_ROTATION_VECTOR:
                rotationMatrix = new float[16];
                mSensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);
                determineOrientation(rotationMatrix);
                break;

            case Sensor.TYPE_ORIENTATION:
                //   sensorZValue.setText(""+event.values[0]); //rotation about geographical z axis
                //  sensorXValue.setText(""+event.values[1]); //rotation about geographical x axis
                //  sensorYValue.setText(""+event.values[2]); //rotation about geographical y axis

                Log.e("x axis ", event.values[0] + " ");
                Log.e("y axis", event.values[1] + " ");
                Log.e("z axis", event.values[2] + " ");

        }
        mCustomDrawableView.invalidate();*/

    }


    private void determineOrientation(float[] rotationMatrix)
    {
        float[] orientationValues = new float[3];
        SensorManager.getOrientation(rotationMatrix, orientationValues);
        double azimuth = Math.toDegrees(orientationValues[0]);
        double pitch = Math.toDegrees(orientationValues[1]);
        double roll = Math.toDegrees(orientationValues[2]);

     //   sensorZValue.setText(String.valueOf(azimuth));  //rotation about geographical z axis
      //  sensorXValue.setText(String.valueOf(pitch)); //rotation about geographical x axis
       // sensorYValue.setText(String.valueOf(roll)); //rotation about geographical y axis
        Log.e("azimuth axis ", " "+azimuth);
        Log.e("pitch"," "+pitch);
        Log.e("roll", " "+ roll);



    }
}