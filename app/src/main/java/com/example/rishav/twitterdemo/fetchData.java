package com.example.rishav.twitterdemo;




import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.util.Linkify;
import android.util.Log;
import android.util.Patterns;

import com.example.rishav.twitterdemo.database.MyDatabse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

 class fetchData extends AsyncTask<Void,Void,Void> {
    String data ="";
    String dataParsed = "";
    String dataParsedurl= "";
    String time ="";
    String text="";
    String screen_names="";

    @Override
    protected Void doInBackground(Void... voids) {
        Cursor cursor;
        ContentValues cv = new ContentValues();
        try {


            URL url = new URL("http://31e92214.ngrok.io/twitter/open.php");



            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String line = "";
            while(line != null){
                line = bufferedReader.readLine();
                data = data + line;
            }

            JSONArray JA = new JSONArray(data);
            for(int i =0 ;i <JA.length(); i++){
                JSONObject ja=JA.getJSONObject(i);
                time=ja.getString("created_at")+"\n";
                text=ja.getString("text");
                List<String> extractedUrls = extractUrls(text);

                for (String url2 : extractedUrls)
                {
                    text=url2.toString();
                }

                screen_names=ja.getJSONObject("user").getString("screen_name");


                dataParsed = dataParsed +"\n"+ time +"\n"+"\n"+screen_names+"\n" ;
                dataParsedurl =dataParsedurl +"\n"+text+"\n";

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }



        return null;
    }
    public static List<String> extractUrls(String text1)
    {
        List<String> containedUrls = new ArrayList<String>();
        String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(text1);

        while (urlMatcher.find())
        {
            containedUrls.add(text1.substring(urlMatcher.start(0),
                    urlMatcher.end(0)));
        }

        return containedUrls;
    }



    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        MainActivity.data.setText(this.dataParsed);
        MainActivity.url.setText(this.dataParsedurl);
        Linkify.addLinks(MainActivity.url,Linkify.WEB_URLS);
        MainActivity.url.setLinkTextColor(Color.RED);
        //MainActivity.create(time,text,screen_names);

    }
}