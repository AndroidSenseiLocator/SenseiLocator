package Model;

import android.util.Log;

import com.lab42.maham.senseilocater.NotificationBO;
import Utils.RequestConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by User on 7/6/2017.
 */
public class RequestJSONParser {

    public ArrayList<NotificationBO> parseServerData(String response){
        Log.d("help" , response);
        if(response != null){
            try {
                int i;
                ArrayList<NotificationBO> savedData = new ArrayList<>();
                // JSONObject jsonObj = new JSONObject(response);

                // Getting JSON Array node
                JSONArray requests = new JSONArray(response);
                Log.d("c" , String.valueOf(requests.length()));
                // looping through All Contacts
                for (i = 0; i < requests.length(); i++) {
                    JSONObject c = requests.getJSONObject(i);

                    String id = c.getString(RequestConstants.TAG_ID);
                    String receiverId = c.getString(RequestConstants.TAG_RECEIVER_ID);
                    String senderId = c.getString(RequestConstants.TAG_SENDER_ID);
                    String dateTime = c.getString(RequestConstants.TAG_DATE_TIME);
                    String status = c.getString(RequestConstants.TAG_SEEN_STATUS);
                    String type = c.getString(RequestConstants.TAG_TYPE);



                    // Save Data


                  NotificationBO notificationBO = new NotificationBO();
                    notificationBO.id = id;
                    notificationBO.receiverId=receiverId;
                    notificationBO.senderId=senderId;
                    notificationBO.dateTime = dateTime;
                    notificationBO.type=type;
                    notificationBO.seenStatus=status;
//
//                    Log.d("name" , myTeacher.Name);
//                    Log.d("Email" , myTeacher.Email);

                    savedData.add(notificationBO);

                    // return savedData;

                }
                return savedData;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        return null;

    }
}
