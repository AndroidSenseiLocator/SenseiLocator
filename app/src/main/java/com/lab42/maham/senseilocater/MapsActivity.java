package com.lab42.maham.senseilocater;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import Model.Teacher;
import Model.TeacherResponseBO;
import Utils.TeacherConstants;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String abc;
    GPSTracker gpss;
    int time = 30;
    Timer t;
    TimerTask task;
    GetServerData data = new GetServerData();
    ArrayList<Double> lati = new ArrayList<>();
    ArrayList<Double> longi  = new ArrayList<>();
    ArrayList<String> markertxt = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        /*mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/

        mMap = googleMap;
        mMap.setBuildingsEnabled(true);
        mMap.setIndoorEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        LatLng gps = new LatLng(31.5704, 74.3096);
        mMap.addMarker(new MarkerOptions()
                .position(gps)
                .title("PUCIT"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(gps, 18));
        Log.e("Data not found", "Error0 ");
        Log.i("Data not found", "Error0 ");
        t = new Timer();
        task = new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    int i = 0;
                    @Override
                    public void run() {
                        if (time > 0) {
                            time -= 1;
                            i++;
                        }
                        else {

                            //data.execute();
                            lati.clear();
                            longi.clear();
                            markertxt.clear();
                            new GetServerData().execute();


                            gpss = new GPSTracker(getApplicationContext());
                            double latitude = 0;
                            double longitude = 0;
                            // check if GPS enabled
                            if(gpss.canGetLocation()) {

                                latitude = gpss.getLatitude();
                                longitude = gpss.getLongitude();
                            }
                            //Toast.makeText(getApplicationContext(), "  "+longitude+" ", Toast.LENGTH_SHORT).show();
                            LatLng gps = null;
                            MarkerOptions marker = null;
                            /*mMap.addMarker(new MarkerOptions()
                                    .position(gps)
                                    .title("Current Location"));*/

                            mMap.clear();
                            for(int i = 0;i < lati.size();i++){
                                gps = new LatLng(lati.get(i),longi.get(i));
                                marker = new MarkerOptions().position(gps).title(markertxt.get(i));
                                mMap.addMarker(marker);
                            }

                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude), 18));
                        }
                    }
                });
            }
        };
        t.scheduleAtFixedRate(task, 0, 1000);
    }
    /*public void startTimer(){
        t = new Timer();
        task = new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        if (time > 0)
                            time -= 1;
                        else {


                            LatLng gps = new LatLng(31.5704, 74.3096);
                            mMap.addMarker(new MarkerOptions()
                                    .position(gps)
                                    .title("PUCIT"));
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(gps, 19));
                        }
                    }
                });
            }
        };
        t.scheduleAtFixedRate(task, 0, 1000);
    }*/
    private class GetServerData extends AsyncTask<Void, Void, TeacherResponseBO> {

        private ProgressDialog pDialog;



        @Override
        protected TeacherResponseBO doInBackground(Void... params) {
            try {


                TeacherResponseBO ress = getDataFromApi();
                return ress;

            }catch (Exception e){

            }
            return null;
        }

        @Override
        protected void onPostExecute(TeacherResponseBO responseBO) {
            super.onPostExecute(responseBO);


            if(responseBO != null && responseBO.arrayList != null && responseBO.arrayList.size() > 0){
                //Toast.makeText(getApplicationContext(), "The record has been successfully added", Toast.LENGTH_LONG).show();

                ArrayList<Teacher> ll = responseBO.arrayList;


                int i = 0;
                String st = null,st1 = null;
                for(Teacher o : ll) {
                    st = o.Location;
                    st1 = o.Name;
                    if(st.contains(":")) {
                        String[] arr = st.split(":");
                        Log.i("splitted1", arr[0]);
                        lati.add(Double.parseDouble(arr[0]));
                        longi.add(Double.parseDouble(arr[1]));
                        markertxt.add(st1);
                        Log.i("splitted2", arr[1]);
                        //Toast.makeText(getApplicationContext(),st,Toast.LENGTH_SHORT).show();
                    }
                }


            }else{
                //Toast.makeText(getApplicationContext(), "Alert: Something went wrong."+abc, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
    public TeacherResponseBO getDataFromApi()
    {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String JsonStr = null;


        TeacherResponseBO res = new TeacherResponseBO();
        try {

            URL url = new URL("http://senseilocatorwebservices.apphb.com/api/Teacher");
            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");

            urlConnection.connect();


            StringBuffer buffer = new StringBuffer();

            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");

            }
            if (buffer.length() == 0) {

            }
            else {

                JsonStr = buffer.toString();
                res.arrayList = getDataFromJson(JsonStr);
            }




            return res;
        } catch (IOException e) {
            Log.e("Data not found", "Error ", e);
            //Toast.makeText(getApplicationContext(),"Data not found",Toast.LENGTH_SHORT).show();

        } finally{

            if (urlConnection != null) {
                urlConnection.disconnect();

            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("Placeholder", "Error closing stream", e);
                    //Toast.makeText(getApplicationContext(),"it is working",Toast.LENGTH_SHORT).show();

                }
            }
        }
        return null;
    }
    ArrayList<Teacher> getDataFromJson(String JsonStr)
    {
        ArrayList<Teacher> lst = new ArrayList<>();
        Teacher obj = new Teacher();
        //weather obj = new weather();
        if(JsonStr != null){
            try {
                JSONArray res = new JSONArray(JsonStr);
                for(int i = 0;i < res.length();i++){
                    obj = new Teacher();
                    JSONObject o=res.getJSONObject(i);
                    obj.Location = o.getString(TeacherConstants.TAG_LOCATION);
                    obj.Available = o.getString(TeacherConstants.TAG_AVAILABLE);
                    obj.Name = o.getString(TeacherConstants.TAG_NAME);
                    lst.add(obj);
                }

                return lst;


            }catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        return lst;
    }
}