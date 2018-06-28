package com.example.rishav.twitterdemo.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.rishav.twitterdemo.helper.MyHelper;

public class MyDatabse {

    public static MyDatabse mydb;
    public static final String My_DB="TwitterDB";

    Context myCon;
    SQLiteDatabase sdb;
    MyHelper myHelper;

    public MyDatabse(Context myContext) {
        myCon = myContext;


        myHelper = new MyHelper(myCon, My_DB, null, 1);
    }
        //method to open database
        public void open(){
            sdb=myHelper.getWritableDatabase();
        }
        //method to insert database
        public void insertData(ContentValues cv)
        {

            sdb.insert("feeds",null,cv);// employee is the table name which we have created in MyHelper class
            // nullColumnHack????
        }

    public Cursor getData() {
        sdb = myHelper.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + "feeds";
        Cursor cursor = sdb.rawQuery(selectQuery, null);

        return cursor;
    }

    }

