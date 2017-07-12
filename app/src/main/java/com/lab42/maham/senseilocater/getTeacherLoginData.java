package com.lab42.maham.senseilocater;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by User on 7/5/2017.
 */
public class getTeacherLoginData {
    public ArrayList<TeacherLogInBO> getDatafromApiTeacher(){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        ArrayList<TeacherLogInBO> w=new ArrayList<TeacherLogInBO>();
        String JsonStr = null;
        final String mode = "json";
        final String unit = "metric";
        try {
            URL url = new URL("http://senseilocatorwebservices.apphb.com/api/Teacher");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                return null;
            }
            JsonStr = buffer.toString();
            try {

                w=getDataParse(JsonStr);
                //  Toast.makeText(getApplicationContext(),"FF",Toast.LENGTH_LONG).show();
                Log.i("DD","SS");
            } catch (Exception e){//JSONException e) {
                e.printStackTrace();
            }

            //Log.v(LOG_TAG, forecastJsonStr);
        } catch (Exception e){//IOException e) {
            Log.e("Data hi nahi mila", "Error ", e);
            return null;
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("Placeholder", "Error closing stream", e);
                }
            }
        }

        return w;
    }

    private Context getApplicationContext() {
        return getApplicationContext();
    }

    public ArrayList<TeacherLogInBO> getDataParse(String forecastJsonStr ) {

        ArrayList<TeacherLogInBO> data=new ArrayList<TeacherLogInBO>() ;
        if(forecastJsonStr!=null){
            try {
                //WeatherBO w=new WeatherBO();
                JSONArray res= new JSONArray(forecastJsonStr);

                for(int i=0;i<res.length();i++){
                    JSONObject o=res.getJSONObject(i);
                    String r=o.getString("Id");
                    String n=o.getString("Name");
                    String email=o.getString("Email");
                    String Pass=o.getString("Password");

                    TeacherLogInBO w=new TeacherLogInBO();
                    w.id=r;
                    w.name=n;
                    w.email=email;
                    w.password=Pass;
                    w.location=o.getString("Location");
                    w.education=o.getString("Education");
                    w.post=o.getString("Post");
                    w.available=o.getString("Available");

                    data.add(w);
                }
                return data;

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return data;
    }
}
