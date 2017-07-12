package Model;

import android.util.Log;

import Utils.TeacherConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONParsor {

    public ArrayList<Teacher> parseServerData(String response){
        Log.d("help" , response);
        if(response != null){
            try {
                int i;
                ArrayList<Teacher> savedData = new ArrayList<>();
               // JSONObject jsonObj = new JSONObject(response);

                // Getting JSON Array node
                JSONArray teachers = new JSONArray(response);
                Log.d("c" , String.valueOf(teachers.length()));
                // looping through All Contacts
                for (i = 0; i < teachers.length(); i++) {
                    JSONObject c = teachers.getJSONObject(i);

                    String id = c.getString(TeacherConstants.TAG_ID);
                    String name = c.getString(TeacherConstants.TAG_NAME);
                    String email = c.getString(TeacherConstants.TAG_EMAIL);
                    String location = c.getString(TeacherConstants.TAG_LOCATION);
                    String education = c.getString(TeacherConstants.TAG_EDUCATION);
                    String password = c.getString(TeacherConstants.TAG_PASSWORD);
                    String post = c.getString(TeacherConstants.TAG_POST);
                    String available = c.getString(TeacherConstants.TAG_AVAILABLE);


                    // Save Data


                    Teacher myTeacher = new Teacher();
                    myTeacher.Id = id;
                    myTeacher.Name = name;
                    myTeacher.Email= email;
                    myTeacher.Location= location;
                    myTeacher.Education=education;
                    myTeacher.Password=password;
                    myTeacher.Post=post;
                    myTeacher.Available=available;

                    Log.d("name" , myTeacher.Name);
                    Log.d("Email" , myTeacher.Email);

                    savedData.add(myTeacher);

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
