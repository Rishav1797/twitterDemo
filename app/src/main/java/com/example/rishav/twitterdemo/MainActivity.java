package com.example.rishav.twitterdemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rishav.twitterdemo.database.MyDatabse;
import com.twitter.sdk.android.core.Twitter;

public class MainActivity extends AppCompatActivity {
    Button click;
    static  MyDatabse mdb;
    Cursor cursor;
    SimpleCursorAdapter sca;
    public  static TextView data,url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Twitter.initialize(this);
        setContentView(R.layout.activity_main);
        String screen_name=getIntent().getStringExtra("screen_name");

        new MyDatabse(MainActivity.this);
        fetchData process = new fetchData();
        process.execute();





    }
    public static void create(String time,String text,String screen_name)
    {
        ContentValues cv = new ContentValues();

        cv.put("_time",time );
        cv.put("name", screen_name);
        cv.put("url", text);
        mdb.insertData(cv);

    }

}

