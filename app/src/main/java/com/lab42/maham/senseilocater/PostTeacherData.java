package com.lab42.maham.senseilocater;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Maham on 7/6/2017.
 */

public class PostTeacherData {
    public int postDatateacher(TeacherLogInBO t){
        int responceCode = 0;
        HttpURLConnection urlConnection = null;
        URL url = null;


        try {
            url = new URL("http://senseilocatorwebservices.apphb.com/api/Teacher");// + id);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");


            JSONObject obj = new JSONObject();
            obj.put("Id", t.id);
            obj.put("Name", t.name);
            obj.put("Email",t.email);
            obj.put("Password",t.password);
            obj.put("Post", t.post);
            obj.put("Education", t.education);
            obj.put("Location",t.location);
            obj.put("Available",t.available);


            String temp = obj.toString();
            urlConnection.connect();

            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.writeBytes(temp);


            responceCode = urlConnection.getResponseCode();


        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            urlConnection.disconnect();
        }
        return responceCode;
    }

}

