package com.hackslash.cameratest;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by snehil on 8/4/17.
 */



public class Calculation extends AppCompatActivity {

    Bundle userinput;
    String[] angle=new String[3];
    float angles[]=new float[3];
    int i=0;
    dbhandler entry;

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
        entry=new dbhandler(Calculation.this);

        double vertical_angle,horizontal_angle;
        vertical_angle = 90 + angles[1];
        vertical_angle = 90 - vertical_angle;
        //assuming height is h
        double h= 1.0 ;
        double a1,a2,a3,a4;
        //main activity.getheight
        a1 = Math.tan(Math.toRadians(vertical_angle));
        double distance = a1*h;
        horizontal_angle = angles[0];
        horizontal_angle = 90 + horizontal_angle;
        a2 = Math.tan(Math.toRadians(horizontal_angle));
        //equation of a line
        double arr[] = new double[2];

        arr[0] = a2;//slope
        arr[1] = distance;//constant



        try{

            entry.open();
            entry.createAngles(angles[0],angles[1], angles[2]);
            entry.createConstant(arr[0],arr[1]);

        }
        catch (Exception e){
            e.printStackTrace();
        }


        Button goback=(Button)findViewById(R.id.button3);
        goback.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button calculate=(Button)findViewById(R.id.button4);
        calculate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                //showRes();
                /*
                double result[]=new double[3];
                result=entry.getConstants();

                Toast.makeText(getApplicationContext()," "+result[0]+" "+result[1]+" "+ result[2],Toast.LENGTH_SHORT).show();*/
                double a[]=new double[2];
                double b[]=new double[2];
                double coord[]=new double[2];


                for(i=0;i<4;i++){
                    a=entry.querydata(i);
                    b=entry.querydata((i+1)%4);

                    coord=equation_solver(a[0],a[1],b[0],b[1]);
                    Toast.makeText(getApplicationContext(),"X: "+coord[0]+" Y:"+coord[1],Toast.LENGTH_SHORT).show();


                }

            }
        });
        //saveArray(arr,"lineinfo",getApplicationContext(),i);
        /*
        String retarr[]=loadArray("lineinfo",getApplicationContext(),i);
        i++;
        Toast.makeText(getApplicationContext()," "+retarr[0]+" "+ retarr[1],Toast.LENGTH_SHORT).show();
        if(retarr!=null){
            saveArray(arr,"lineinfo",getApplicationContext(),i);
            String retarr2[]=loadArray("lineinfo",getApplicationContext(),i);

        }
        saveArray(arr,"lineinfo",getApplicationContext(),i);
        */
        //entry.close();

    }

    public double[] equation_solver(double m1,double c1,double m2,double c2)
    {
        double x_coord,y_coord;
        x_coord = (c2-c1)/(m1-m2);
        y_coord = (m1*c2 - m2*c1)/(m1-m2);
        double x[]=new double[2];
        x[0]=x_coord;
        x[1]=y_coord;
        return x;
    }


    public boolean saveArray(String[] arr,String aname,Context context,int i){
        SharedPreferences prefs=context.getSharedPreferences("scaleit",i);
        SharedPreferences.Editor editor=prefs.edit();
        editor.putInt(aname+"_size",arr.length);
        for(int j=0;j<arr.length;j++){
            editor.putString(aname+ "_"+ j,arr[j]);
        }
        return editor.commit();

    }

    public String[] loadArray(String arrayName, Context mContext,int i) {
        SharedPreferences prefs = mContext.getSharedPreferences("scaleit", i);
        int size = prefs.getInt(arrayName + "_size", 0);
        String array[] = new String[size];
        for(int j=0;j<size;j++)
            array[j] = prefs.getString(arrayName + "_" + j, null);
        return array;
    }

    public void showRes(){

        Intent i=new Intent(Calculation.this,ShowDetails.class);
        startActivity(i);

    }



}