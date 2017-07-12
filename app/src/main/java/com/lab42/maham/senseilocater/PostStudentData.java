package com.lab42.maham.senseilocater;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import Model.Student;

/**
 * Created by Maham on 7/6/2017.
 */

public class PostStudentData {
    public int postDatast(LogInBO st){
        int responceCode = 0;
            HttpURLConnection urlConnection = null;
            URL url = null;


            try {
                url = new URL("http://senseilocatorwebservices.apphb.com/api/Student");// + id);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");


                JSONObject obj = new JSONObject();
                obj.put("RollNumber", st.RollNo);
                obj.put("Name", st.name);
                obj.put("Email",st.email);
                obj.put("Password",st.password);



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

