package com.lab42.maham.senseilocater;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

//import org.apache.http.client.HttpClient;
/**
 * Created by Maham on 7/7/2017.
 */

public class UpdateTeacherData {
    public int postDatateacher(TeacherLogInBO t){
        int responceCode = 0;
        HttpURLConnection urlConnection = null;
        HttpResponse httpResponse = null;

        URL url = null;


        try {



            HttpClient httpclient = new DefaultHttpClient();


            Log.e("id maham--------" , t.id);
            Log.e("m name------" , t.name);
            Log.e(" m educat-----" , t.education);
            Log.e("m loc-------" ,t.location );
            Log.e("m pass ----" , t.password);
            Log.e("m acal+---" , t.available);
            Log.e("m post-----" , t.post);
            Log.e("m ema-------" , t.email);

            Log.i("id maham" , t.id);
            String ab = t.id.trim();
            Log.e("bger trim" , t.id+"ab");
            Log.e(" trim" , ab+"ab");
            HttpPut httpPut = new
                    HttpPut("http://senseilocatorwebservices.apphb.com/api/Teacher/"+ ab);
            String json = "";

        //    JSONObject jsonObject = new JSONObject();


            JSONObject obj = new JSONObject();
            obj.put("Id", t.id);
            obj.put("Name", t.name);
            obj.put("Email",t.email);
            obj.put("Password",t.password);
            obj.put("Post", t.post);
            obj.put("Education", t.education);
            obj.put("Location",t.location);
            obj.put("Available",t.available);


            DataOutputStream wr = null;
//            url = new URL("http://senseilocatorwebservices.apphb.com/api/Teacher/"+t.id);// + id);
//            urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.setRequestMethod("PUT");
//            //urlConnection.setDoInput(true);
//            urlConnection.setDoOutput(true);
//            urlConnection.setRequestProperty("Content-Type", "application/json");
//            urlConnection.setRequestProperty("Accept","application/json");

            json = obj.toString();
            StringEntity se = new StringEntity(json);
            httpPut.setEntity(se);







        //    httpPut.setEntity(se);

            httpPut.addHeader("Accept", "application/json");
            httpPut.addHeader("Content-type", "application/json");

            httpResponse = httpclient.execute(httpPut);
            return  httpResponse.getStatusLine().getStatusCode();

        } catch (Exception e){
            e.printStackTrace();
        }

        return -1;
//        int responceCode = 0;
//        HttpURLConnection urlConnection = null;
//        URL url = null;
//
//
//        try {
//
//
//            HttpClient httpclient = new DefaultHttpClient();
//
//            HttpPut httpPut = new
//                    HttpPut("http://senseilocatorwebservices.apphb.com/api/Teacher/2");
//            String json = "";
//
//            JSONObject jsonObject = new JSONObject();
//
//            jsonObject.put("Id", "2");
//            jsonObject.put("Name", "Fahad");
//            jsonObject.put("Email","fff");
//            jsonObject.put("Location","fati");
//            jsonObject.put("Education","Phd");
//            jsonObject.put("Password","123");
//            jsonObject.put("Post","junior");
//            jsonObject.put("Available","false");
//
//            json = jsonObject.toString();
//            StringEntity se = new StringEntity(json);
//            httpPut.setEntity(se);
//
//            httpPut.addHeader("Accept", "application/json");
//            httpPut.addHeader("Content-type", "application/json");
//
//            httpResponse = httpclient.execute(httpPut);
//
//
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return httpResponse.getStatusLine().getStatusCode();
    }
}
