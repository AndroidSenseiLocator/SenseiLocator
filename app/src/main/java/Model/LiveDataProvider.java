package Model;


import android.util.Log;

import Model.TeacherResponseBO;
import Utils.TeacherConstants;

public class LiveDataProvider {

    public TeacherResponseBO getServerData(){
        try {
            HTTPURLConnectionHelper httpurlConnectionHelper = new HTTPURLConnectionHelper();
            String response = httpurlConnectionHelper.makeServiceCall(TeacherConstants.METHOD_GET, null);
            JSONParsor jsonParsor = new JSONParsor();
            TeacherResponseBO responseBO = new TeacherResponseBO();
            responseBO.arrayList = jsonParsor.parseServerData(response);
            Log.d("ALLAH" , responseBO.toString());
            return responseBO;

        }catch (Exception e){
            return null;
        }
    }
}
