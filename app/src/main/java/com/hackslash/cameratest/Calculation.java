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
    public static double coord[][]=new double[4][2];
    public static double inter[]=new double[4];

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


        for(int k=0;i<4;i++){
            coord[k]=new double[2];

        }


        Toast.makeText(this, angles[0]+ " " + angles[1]+ " "+ angles[2] , Toast.LENGTH_SHORT).show();
        entry=new dbhandler(Calculation.this);

        double vertical_angle,horizontal_angle;
//        vertical_angle = 90.0 + angles[1];
//        vertical_angle = 90.0 - vertical_angle;
        vertical_angle=angles[1]*(-1);

        //assuming height is h
        double h= BaseActivity.getHeight();

        Toast.makeText(Calculation.this," "+ h,Toast.LENGTH_SHORT).show();
        double a1,a2,a3,a4;
        //main activity.getheight
        a1 = Math.tan(Math.toRadians(vertical_angle));
        double distance = a1*h;
        horizontal_angle = angles[0];
       // double constant = distance/(Math.cos(Math.toRadians(horizontal_angle)));
//        constant  = distance;

        // horizontal_angle = 90 + horizontal_angle;
        a2 = (-1.0)/Math.tan(Math.toRadians(horizontal_angle));
        //equation of a line
        double arr[] = new double[2];

        arr[0] = a2;//slope
        arr[1] = distance;



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



                /*
                for(i=1;i<5;i++) {
                    a = entry.querydata(i);
                    if (i + 1 == 5)
                        i = 0;
                    b = entry.querydata((i + 1));
                    Toast.makeText(getApplicationContext(), "slope: " + a[0] + " intercept:" + a[1], Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "slope: " + b[0] + " intercept:" + b[1], Toast.LENGTH_SHORT).show();

                }
                */



                double a[]=new double[2];
                double b[]=new double[2];

                //double angles[]=new double[4];

                /*
                angles=entry.queryAngles(1);
                Toast.makeText(Calculation.this, angles[0]+ " " + angles[1]+ " "+ angles[2] , Toast.LENGTH_SHORT).show();
                angles=entry.queryAngles(2);
                Toast.makeText(Calculation.this, angles[0]+ " " + angles[1]+ " "+ angles[2] , Toast.LENGTH_SHORT).show();
                angles=entry.queryAngles(3);
                Toast.makeText(Calculation.this, angles[0]+ " " + angles[1]+ " "+ angles[2] , Toast.LENGTH_SHORT).show();
                angles=entry.queryAngles(4);
                Toast.makeText(Calculation.this, angles[0]+ " " + angles[1]+ " "+ angles[2] , Toast.LENGTH_SHORT).show();
                */


                a=entry.querydata(1);
                b=entry.querydata(2);

          //      Toast.makeText(Calculation.this, a[0]+ " " + a[1]+ " " , Toast.LENGTH_SHORT).show();
          //      Toast.makeText(Calculation.this, b[0]+ " " + b[1]+ " " , Toast.LENGTH_SHORT).show();
                coord[0]=equation_solver(a[0],a[1],b[0],b[1]);
                inter[0]=a[1];

                //Toast.makeText(getApplicationContext(),"slope: "+a[0]+" intercept:"+a[1]   +   "   slope: "+b[0]+" intercept:"+b[1]+ "                 "+"X: "+coord[0][0]+" Y: "+coord[0][1],Toast.LENGTH_SHORT).show();
                a=entry.querydata(2);
                b=entry.querydata(3);
                inter[1]=a[1];
                //Toast.makeText(Calculation.this, a[0]+ " " + a[1]+ " " , Toast.LENGTH_SHORT).show();
                //Toast.makeText(Calculation.this, b[0]+ " " + b[1]+ " " , Toast.LENGTH_SHORT).show();
                coord[1]=equation_solver(a[0],(-1*a[1]),b[0],b[1]);
                //Toast.makeText(getApplicationContext(),"slope: "+a[0]+" intercept:"+(-1*a[1])   +   "   slope: "+b[0]+" intercept:"+b[1]+ "                 "+"X: "+coord[1][0]+" Y: "+coord[1][1],Toast.LENGTH_SHORT).show();
                a=entry.querydata(3);
                b=entry.querydata(4);
                inter[2]=a[1];
                //Toast.makeText(Calculation.this, a[0]+ " " + a[1]+ " " , Toast.LENGTH_SHORT).show();
                //Toast.makeText(Calculation.this, b[0]+ " " + b[1]+ " " , Toast.LENGTH_SHORT).show();
                coord[2]=equation_solver(a[0],(-1*a[1]),b[0],(-1*b[1]));
                //Toast.makeText(getApplicationContext(),"slope: "+a[0]+" intercept:"+(-1*a[1])   +   "   slope: "+b[0]+" intercept:"+(-1*b[1])+ "                 "+"X: "+coord[2][0]+" Y: "+coord[2][1],Toast.LENGTH_SHORT).show();
                a=entry.querydata(4);
                b=entry.querydata(1);
                inter[3]=a[1];
                //Toast.makeText(Calculation.this, a[0]+ " " + a[1]+ " " , Toast.LENGTH_SHORT).show();
                //Toast.makeText(Calculation.this, b[0]+ " " + b[1]+ " " , Toast.LENGTH_SHORT).show();
                coord[3]=equation_solver(a[0],a[1],b[0],(-1*b[1]));
                //Toast.makeText(getApplicationContext(),"slope: "+a[0]+" intercept:"+a[1]   +   "   slope: "+b[0]+" intercept:"+(-1*b[1])+ "                 "+"X: "+coord[3][0]+" Y: "+coord[3][1],Toast.LENGTH_SHORT).show();

                /*
                for(i=1;i<5;i++){
                    a=entry.querydata(i);
                    if((i+1)==5)
                      {
                        i=0;
                       }

                    b=entry.querydata((i+1));

                    coord=equation_solver(a[0],a[1],b[0],b[1]);
                    Toast.makeText(getApplicationContext(),"slope: "+a[0]+" intercept:"+a[1]   +   "   slope: "+b[0]+" intercept:"+b[1]+ "                 "+"X: "+coord[0]+" Y: "+coord[1],Toast.LENGTH_SHORT).show();


                }*/
                showRes();

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
      //  x_coord = (c2-c1)/(m1-m2);
       // y_coord = (m1*c2 - m2*c1)/(m1-m2);
        double x[]=new double[2];
        x[0]=c1;
        x[1]=c2;
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

        Intent i=new Intent(Calculation.this,ShowResults.class);
        startActivity(i);
        //Toast.makeText(Calculation.this,coord[0][0]+ " "+ coord[1][0]+" "+ coord[2][0]+" "+ coord[3][0],Toast.LENGTH_SHORT).show();


    }



    public static double[][] getCoords(){
        return coord;
    }
    public static double[] getInter(){
        return inter;
    }





}