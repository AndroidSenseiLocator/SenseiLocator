package com.lab42.maham.senseilocater;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import Model.Teacher;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment implements  View.OnClickListener{

    String a;
    Teacher myTeacher;

    ImageView teacher_image;
    EditText education , email , name , password;
    Button editButton;
    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        teacher_image = (ImageView) view.findViewById(R.id.img_edit_profile_activity);
        education = (EditText) view.findViewById(R.id.et_experience);

        name = (EditText) view.findViewById(R.id.et_name);
        email= (EditText) view.findViewById(R.id.et_email);
        password = (EditText) view.findViewById(R.id.et_password);

        Teacher t = getDataFromSP();

        name.setText(t.Name);
        email.setText(t.Email);
        password.setText(t.Password);
        education.setText(t.Education);




        String encodedImageString = t.Pic;                                                                                                                                              //    "PPMaQRZYHrhGO3lgM8nGQcevTkkms6P4cyws25mPcfu2A6v6nnrjv1A55NefRVacpVasXGLaad11";
        //  Log.d("edu" , t.Education);
        byte[] bytarray = Base64.decode(encodedImageString, Base64.DEFAULT);
        Bitmap bmimage = BitmapFactory.decodeByteArray(bytarray, 0,
                bytarray.length);
        Log.d("my img" , bytarray.toString());
//       if(bmimage == null)
//           teacher_img.setImageDrawable(R);
        //Toast.makeText(TeacherDetailsActivity.this, "ohooooo", Toast.LENGTH_SHORT).show();
        if(bmimage != null)
            teacher_image.setImageBitmap(bmimage);







        teacher_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);

            }

        });

        editButton = (Button) view.findViewById(R.id.btn_submit_changes);
        editButton.setOnClickListener(this);
        return view;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error

                //   Toast.makeText(MainActivity.this, "dur fit", Toast.LENGTH_SHORT).show();
                return;
            }
            Uri selectedImage = data.getData();
            InputStream imageStream = null;
            try {
                imageStream = getActivity().getApplicationContext().getContentResolver().openInputStream(selectedImage);
            } catch (Exception e)
            {

            }
            Bitmap bitmap = BitmapFactory.decodeStream(imageStream);

//imp code
            ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOS);


            a = Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);


            byte[] bytarray = Base64.decode(a, Base64.DEFAULT);
            Bitmap bmimage = BitmapFactory.decodeByteArray(bytarray, 0,
                    bytarray.length);

            teacher_image.setImageBitmap(bmimage);

        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() ==  R.id.btn_submit_changes)
        {


            myTeacher = new Teacher();
            Toast.makeText( getActivity().getApplicationContext() , "clicked", Toast.LENGTH_SHORT).show();
            Log.d("btn" , "called");
            Log.i("btn" , "called");
            Log.e("btn" , "called");

            myTeacher.Name =name.getText().toString();
            myTeacher.Password= password.getText().toString();
            myTeacher.Email = email.getText().toString();
            myTeacher.Pic=a;
            myTeacher.Education=education.getText().toString();

            new UpdateTeacher().execute();
        }
    }

    Teacher getDataFromSP ()
    {
        SharedPreferences sp=  getActivity().getSharedPreferences("Preferences", getActivity().MODE_PRIVATE);


        Teacher t = new Teacher();
        t.Id =  sp.getString("userID" , "");
        t.Name = sp.getString("userName" , "");
        t.Email = sp.getString("userEmail" , "");
        t.Password = sp.getString("userPassword" , "");
        t.Education = sp.getString("userEducation" , "");
//        t.Available = sp.getString("userAvailable", "");
//        t.Location = sp.getString("userLocation" , "");
        t.Pic = sp.getString("userPic" , "");


        return t;
    }


    private class UpdateTeacher extends AsyncTask < Void , Void  , Integer> {
        HttpResponse httpResponse;



        @Override
        protected void onPostExecute( Integer s) {
            Log.d("onPost" , s + "");
            Log.e("onPost----------" , s + "");
            if(s==200 || s==201) {
                Toast.makeText(getActivity().getApplicationContext(), " Changes saved succussfully ", Toast.LENGTH_SHORT).show();



                SharedPreferences sp=  getActivity().getSharedPreferences("Preferences", getActivity().MODE_PRIVATE);
                SharedPreferences.Editor e=sp.edit();

                e.putString("userName", name.getText().toString());
                e.putString("userEmail",email.getText().toString());
                e.putString("userPassword", password.getText().toString());
                e.putString("userEducation", education.getText().toString());
                e.putString("userPic" , a);
                e.commit();
            }
            else
                Toast.makeText( getActivity().getApplicationContext() , "Your changes not saved" , Toast.LENGTH_SHORT).show();
            super.onPostExecute(s);

        }

        @Override
        protected Integer doInBackground(Void... params) {

            Log.d("doIn" , "called");
            int responceCode = 0;
            HttpURLConnection urlConnection = null;
            URL url = null;


            SharedPreferences sp=  getActivity().getSharedPreferences("Preferences", getActivity().MODE_PRIVATE);

            try {

                String u = "http://senseilocatorwebservices.apphb.com/api/Teacher/"+sp.getString("userId" , "").toString() ;


                HttpClient httpclient = new DefaultHttpClient();

                HttpPut httpPut = new
                        HttpPut(u);
                String json = "";

                JSONObject jsonObject = new JSONObject();

                jsonObject.put("Id", sp.getString("userId" , "bsef14m0").toString());
                jsonObject.put("Name", myTeacher.Name);
                jsonObject.put("Email",myTeacher.Email);
                jsonObject.put("Location", sp.getString("userLocation" , "0:0").toString());
                jsonObject.put("Education", myTeacher.Education);
                jsonObject.put("Password", myTeacher.Password);
                jsonObject.put("Post","junior");
                jsonObject.put("Available", sp.getBoolean("userAvailable" , false));

               Log.e("ID---------------------" , sp.getString("userId" , "m").toString());

                Log.e("Name------" , myTeacher.Name);



                Log.d("ID---------------------" , sp.getString("userId" , "m").toString());

                Log.d("Name------" , myTeacher.Name);



                Log.i("ID---------------------" , sp.getString("userId" , "m").toString());

                Log.i("Name------" , myTeacher.Name);


                json = jsonObject.toString();
                StringEntity se = new StringEntity(json);
                httpPut.setEntity(se);

                httpPut.addHeader("Accept", "application/json");
                httpPut.addHeader("Content-type", "application/json");

                httpResponse = httpclient.execute(httpPut);

                Log.e("responseee-------" , httpResponse.toString());
              //  Log.e("responseee-------" , httpResponse.getStatusCode().to);

            } catch (Exception e){
                e.printStackTrace();
            }

            return httpResponse.getStatusLine().getStatusCode();
        }
    }




}