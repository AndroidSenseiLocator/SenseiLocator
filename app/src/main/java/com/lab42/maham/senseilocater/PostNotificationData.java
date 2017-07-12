package com.lab42.maham.senseilocater;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Latif Laptop on 7/7/2017.
 */

public class PostNotificationData {
    public int postNotificationData(NotificationBO b)
    {
        int responceCode = 0;
        HttpURLConnection urlConnection = null;
        URL url = null;


        try {
            url = new URL("http://senseilocatorwebservices.apphb.com/api/Request");// + id);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");


            JSONObject obj = new JSONObject();
            obj.put("SenderId", b.senderId);
            obj.put("ReceiverId",b.receiverId);
            obj.put("DateTime",b.dateTime );
            obj.put("SeenStatus","helo");
            obj.put("Type",65);


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
