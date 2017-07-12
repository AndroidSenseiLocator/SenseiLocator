package Model;

import Utils.RequestConstants;
import Utils.TeacherConstants;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by User on 7/6/2017.
 */
public class RequestHTTPURLConnectionHelper {

    private final String USER_AGENT = "Mozilla/5.0";
    String teacherId;
    public RequestHTTPURLConnectionHelper(String id)
    {
        teacherId = id;
    }


    public String makeServiceCall(String method, String urlParameters){
        try {

            URL obj = new URL(RequestConstants.BASE_URL+teacherId);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod(method);

            //add request header
//            con.setRequestProperty("User-Agent", USER_AGENT);

            if(method.equalsIgnoreCase(TeacherConstants.METHOD_POST) && urlParameters != null){
                // Send post request
                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(urlParameters);
                wr.flush();
                wr.close();
            }
            int responseCode = con.getResponseCode();
//            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        }catch (Exception e){
            return null;
        }
    }

}
