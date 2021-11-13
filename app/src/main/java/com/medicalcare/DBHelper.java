package com.medicalcare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private Context context;

    private final static String DB_NAME = "Database.db";
    private final static int DB_VERSION = 4;

    private final static String USER_TABLE_NAME = "USER";
    private final static String STATUS_TABLE_NAME = "STATUS";

    private final String table1_query = "CREATE TABLE USER ( " +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "NAME TEXT, " +
            "PASSWORD TEXT, " +
            "PHONE_NUMBER TEXT" +
            ");";
    private final String table2_query = "CREATE TABLE STATUS (" +
            "USER_ID INTEGER," +
            "TIME TEXT DEFAULT CURRENT_TIMESTAMP," +
            "HBPM TEXT," +
            "TEMPRATURE TEXT," +
            "OXYGEN TEXT" +
            ");";

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(table1_query);
        db.execSQL(table2_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS USER");
        db.execSQL("DROP TABLE IF EXISTS STATUS");
        onCreate(db);
    }
    public void execute(String query){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
    }
    public void flush(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM USER");
        db.execSQL("DELETE FROM STATUS");
    }
    public boolean isUserExist(String name){
        SQLiteDatabase db = this.getReadableDatabase();

        if(db != null) {
            Cursor c = db.rawQuery("SELECT NAME FROM USER WHERE LOWER(NAME) == LOWER('" + name.trim() + "')", null);
            if(c != null && c.getCount() > 0){
                return true;
            }
        }
        return false;
    }
    public Cursor query(String queryStr){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(queryStr,null);
    }
    public String getPhoneNumber(){
        Cursor c = query("SELECT PHONE_NUMBER FROM USER WHERE ID = " + MainActivity.USER_ID);
        if(c != null && c.getCount() > 0){
            c.moveToFirst();
            return c.getString(0);
        }
        return null;
    }
    public int isPasswordCorrect(String name, String password){
        SQLiteDatabase db = this.getReadableDatabase();

        if(db != null) {
            String str = "SELECT ID FROM USER WHERE NAME == lower('" + name.trim() + "') and password == '" + password.trim() + "'";
            Cursor c = db.rawQuery(str, null);
            if(c != null && c.getCount() > 0){
                c.moveToFirst();
                return c.getInt(0);
            }
        }
        return -1;
    }
    public boolean insert_user(String name, String password, String phoneNumber){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("NAME", name.trim().toLowerCase());
        cv.put("PASSWORD", password.trim());
        cv.put("PHONE_NUMBER", phoneNumber.trim());

        return db.insert("USER", null, cv) > 0;
    }
    public boolean update_number(String phoneNumber){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("PHONE_NUMBER", phoneNumber);

        return db.update("USER",cv,"id == " + MainActivity.USER_ID,null) > 0;
    }
    public ArrayList<PersonalStatus> fetchStatus(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<PersonalStatus> ps = null;

        if(db != null){
            Cursor c = db.rawQuery("SELECT replace(substr(Time,0,11),'-','') as day,avg(hbpm),avg(oxygen),avg(temprature)  from STATUS WHERE USER_ID == "+MainActivity.USER_ID+" GROUP by day",null);
            if(c != null && c.getCount() > 0){
                ps = new ArrayList<>();

                c.moveToFirst();
                do{
                    PersonalStatus p = new PersonalStatus();
                    p.date = c.getString(0);
                    p.hbpm = c.getString(1);
                    p.oxygen = c.getString(2);
                    p.tempreture = c.getString(3);

                    ps.add(p);
                }while(c.moveToNext());

            }
        }

        return ps;
    }
    public boolean insertStatus(PersonalStatus p){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("USER_ID", MainActivity.USER_ID + "");
        cv.put("HBPM", p.hbpm);
        cv.put("OXYGEN", p.oxygen);
        cv.put("TEMPRATURE", p.tempreture);

        return db.insert("STATUS", null, cv) > 0;
    }
}
