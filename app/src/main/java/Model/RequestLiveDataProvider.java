package Model;

import android.util.Log;

import Utils.RequestConstants;

/**
 * Created by User on 7/6/2017.
 */
public class RequestLiveDataProvider {

String teacherId;
    public RequestLiveDataProvider(String id)
    {
        teacherId = id;
    }
    public RequestResponseBO getServerData ()
    {
        RequestHTTPURLConnectionHelper requestHTTPURLConnectionHelper = new RequestHTTPURLConnectionHelper(teacherId);
        Log.e("inner e" , teacherId);
        String response = requestHTTPURLConnectionHelper.makeServiceCall(RequestConstants.METHOD_GET, null);
       // String response = "[{\"Id\":1,\"SenderId\":\"1\",\"ReceiverId\":\"1\",\"DateTime\":\"2017-06-21T00:00:00\",\"Type\":\"54\",\"SeenStatus\":null}]";

        RequestResponseBO requestResponseBO = new RequestResponseBO();
        RequestJSONParser requestJSONParser = new RequestJSONParser();
        requestResponseBO.arrayList = requestJSONParser.parseServerData(response);
        return requestResponseBO;
    }
}
