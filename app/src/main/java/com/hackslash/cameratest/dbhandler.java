package com.hackslash.cameratest;

/**
 * Created by snehil on 8/4/17.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;


public class dbhandler {


    public static final String rowid = "id";
    public static final String anglex = "anglex";
    public static final String angley = "angley";
    public static final String anglez = "anglez";


    public static final String rowid2 = "rowid2";       //original medicine table attrbutes
    public static final String slope = "slope";
    public static final String constant = "constant";



    private static final String database = "scaleit";
    private static final String angle_table = "angle_table";
    private static final String const_table = "const_table";

    private static final int version = 1;

    private DBhelper helper;
    private final Context our_context;
    private SQLiteDatabase our_database;


    private static class DBhelper extends SQLiteOpenHelper {
        public DBhelper(Context context) {
            super(context, database, null, version);
        }




        @Override
        public void onCreate(SQLiteDatabase db) {
            String table1 = "CREATE TABLE " + angle_table + " (" +
                    rowid + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    anglex + " DOUBLE NOT NULL, " +
                    angley + " DOUBLE NOT NULL, " +
                    anglez + " DOUBLE NOT NULL);";


            String table2 = "CREATE TABLE " + const_table + " (" +         //EDIT THIS
                    rowid2 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    slope + " DOUBLE NOT NULL, " +
                    constant + " DOUBLE NOT NULL);";





            db.execSQL(table1);
            // Toast.makeText(dbhandler.this, "table created ", Toast.LENGTH_LONG).show();
            db.execSQL(table2);






        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + angle_table);
            db.execSQL("DROP TABLE IF EXISTS " + const_table);
            onCreate(db);
        }
    }


    public dbhandler(Context context) {
        our_context = context;
    }


    public dbhandler open() throws SQLException {
        helper = new DBhelper(our_context);
        our_database = helper.getWritableDatabase();
        return this;
    }

    public void close() {
        helper.close();
    }


    public long createAngles(double anglex, double angley, double anglez) {
        ContentValues cv = new ContentValues();
        cv.put(this.anglex,anglex);
        cv.put(this.angley,angley);
        cv.put(this.anglez,anglez);
        return our_database.insert(angle_table, null, cv);

    }


    public long createConstant(double slope,double constant) {
        ContentValues cv = new ContentValues();
        cv.put(this.slope, slope);
        cv.put(this.constant, constant);
        return our_database.insert(const_table, null, cv);

    }               //create store method.



    public double[] getAngles() {
        String[] columns = new String[]{rowid, anglex,angley,anglez};
        Cursor c1 = our_database.query(angle_table, columns, null, null, null, null, null);
        double result[]=new double[4];

        int iRow = c1.getColumnIndex(rowid);
        int ianglex = c1.getColumnIndex(anglex);
        int iangley = c1.getColumnIndex(angley);
        int ianglez = c1.getColumnIndex(anglez);

        for (c1.moveToFirst(); !c1.isAfterLast(); c1.moveToNext()) {
            result[0] = c1.getDouble(iRow);
            result[1] = c1.getDouble(ianglex);
            result[2] = c1.getDouble(iangley);
            result[3] = c1.getDouble(ianglez);



        }
        return result;

    }


    public double[] getConstants() {
        String[] columns = new String[]{rowid2, slope,constant};
        Cursor c1 = our_database.query(const_table, columns, null, null, null, null, null);
        double result[]=new double[3];

        int iRow = c1.getColumnIndex(rowid2);
        int islope = c1.getColumnIndex(slope);
        int iconstant = c1.getColumnIndex(constant);


        for (c1.moveToFirst(); !c1.isAfterLast(); c1.moveToNext()) {
            result[0] = c1.getDouble(iRow);
            result[1] = c1.getDouble(islope);
            result[2] = c1.getDouble(iconstant);




        }
        return result;

    }

    /*
    public String searchPass(String user) {

        String[] columns = new String[]{name, password};
        Cursor c2 = our_database.query(login_table, columns, null, null, null, null, null);

        //String query=" select name,password from " + login_table;
        //Cursor c2=our_database.rawQuery(query,null);
        int iname = c2.getColumnIndex(name);
        int ipass = c2.getColumnIndex(password);
        String name;
        String password = "";
        if (c2.moveToFirst()) {
            do {
                name = c2.getString(iname);

                if (name.equals(user)) {
                    password = c2.getString(ipass);
                    break;
                }

            }
            while (c2.moveToNext());

        }
        return password;

    }


    public String[] search(String med) {

        String query = "SELECT " + med_name + "," + med_cost + "," + gen_med + "," + gen_cost + " FROM " + gen_table + " CROSS JOIN " + med_table + " WHERE " + gen_med + "=" + gen_name + " AND " + med_name + "='" + med + "'";
        Cursor c3 = our_database.rawQuery(query, null);
        String result[] = new String[4];
        int imed = c3.getColumnIndex(med_name);
        int imcost = c3.getColumnIndex(med_cost);
        int igen = c3.getColumnIndex(gen_med);
        int igcost = c3.getColumnIndex(gen_cost);

        for (c3.moveToFirst(); !c3.isAfterLast(); c3.moveToNext()) {
            result[0] = c3.getString(imed);
            result[1] = c3.getString(imcost);
            result[2] = c3.getString(igen);
            result[3] = c3.getString(igcost);


        }
        return result;

    }


    */

    ///////////shit//////////////////

    /*


    public ArrayList<String> searchStore(String med){
        ArrayList<String> result=new ArrayList<>();
        String s1;
        String s2;
        String s3;

        //String query="SELECT * FROM "+store_table+ " WHERE "+gen_name2+"='"+ med +"'";

        String query = "SELECT " + shop + "," + location + "," + availability  + " FROM " + store_table + " CROSS JOIN " + gen_table + " WHERE " + gen_name2 + "=" + gen_name + " AND " + gen_name2 + "='" + med+"'";
        Cursor c3 = our_database.rawQuery(query, null);

        int iShop = c3.getColumnIndex(shop);
        int iLoc = c3.getColumnIndex(location);
        int iavail = c3.getColumnIndex(availability);

        //int iprof = c3.getColumnIndex(profile);

        for (c3.moveToFirst(); !c3.isAfterLast(); c3.moveToNext()) {

            s1=c3.getString(iShop) + "     ";
            s2=c3.getString(iLoc) + "     ";
            s3=c3.getString(iavail) + "\n  ";
            result.add(s1+s2+s3);


        }
        return result;

    }


    public String[] searchComp(String med) {

        String query = "SELECT " + c_name + "," + country + "," + c_location + "," + net_worth + " FROM " + med_comp_table + " CROSS JOIN " + comp_table + " WHERE " + company + "=" + c_name + " AND " + gen_name3 + "='" + med + "'";
        Cursor c3 = our_database.rawQuery(query, null);
        String result[] = new String[4];
        int iname = c3.getColumnIndex(c_name);
        int icount = c3.getColumnIndex(country);
        int iloc = c3.getColumnIndex(c_location);
        int iworth = c3.getColumnIndex(net_worth);

        for (c3.moveToFirst(); !c3.isAfterLast(); c3.moveToNext()) {
            result[0] = c3.getString(iname);
            result[1] = c3.getString(icount);
            result[2] = c3.getString(iloc);
            result[3] = c3.getString(iworth);


        }
        return result;

    }
    */


}
















