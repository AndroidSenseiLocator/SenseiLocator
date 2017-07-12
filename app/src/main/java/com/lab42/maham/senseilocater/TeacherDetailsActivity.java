package com.lab42.maham.senseilocater;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import Model.Teacher;

import static com.lab42.maham.senseilocater.R.id.teacher_profile_check_notif;

public class TeacherDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    TextView teacher_name , teacher_post , teacher_education ;
    ImageView teacher_img;
    Teacher t;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_details);
        teacher_name = (TextView) findViewById(R.id.tv_name_teacher_details);
        teacher_post = (TextView) findViewById(R.id.tv_position_teacher_details);
        teacher_education = (TextView) findViewById(R.id.tv_education_teacher_details);
        teacher_img = (ImageView) findViewById(R.id.img_teacher_details);

        Intent i = getIntent();
        t = (Teacher) i.getSerializableExtra("object");
        teacher_name.setText(t.Name);
        teacher_post.setText(t.Post);
        teacher_education.setText("abc");


        String encodedImageString = t.Education;                                                                                                                                              //    "PPMaQRZYHrhGO3lgM8nGQcevTkkms6P4cyws25mPcfu2A6v6nnrjv1A55NefRVacpVasXGLaad11";
        Log.d("edu" , t.Education);
        byte[] bytarray = Base64.decode(encodedImageString, Base64.DEFAULT);
        Bitmap bmimage = BitmapFactory.decodeByteArray(bytarray, 0,
                bytarray.length);
        Log.d("my img" , bytarray.toString());
//       if(bmimage == null)
//           teacher_img.setImageDrawable(R);
        //Toast.makeText(TeacherDetailsActivity.this, "ohooooo", Toast.LENGTH_SHORT).show();
        if(bmimage != null)
            teacher_img.setImageBitmap(bmimage);

        b = (Button)findViewById(teacher_profile_check_notif);
        b.setOnClickListener(this);
        /*b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
//
//                Notification notif = new Notification.Builder(getApplicationContext())
//                        .setSmallIcon(R.drawable.logo1_web)
//                        .setContentTitle("Notification")
//                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.logo1_web))
//                        .setContentText("You have new Notification")
//                        .build();

               // new myAsynctAsk(t.Id).execute();
                //notificationManager.notify(0,notif);


            }
        });*/


    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.teacher_profile_check_notif)
        {
            Log.i("view id..............", String.valueOf(v.getId()));
            new myAsynctAsk(t.Id).execute();
        }
        else
        {

        }
    }

    private class myAsynctAsk extends AsyncTask<Void,Void,Integer>
    {
        String teacherId;
        myAsynctAsk( String tId)
        {
            teacherId = tId;
        }



        @Override
    protected Integer doInBackground(Void... params) {

            NotificationBO b=new NotificationBO();
            SharedPreferences s;
            s=getSharedPreferences("Preferences",MODE_PRIVATE);

            b.senderId=s.getString("userRollNo","");
            Log.i("Sender",b.senderId);
            b.seenStatus=" ";
            b.receiverId=teacherId;
         //   b.senderId="78";
            Log.i("receiver-------------",b.receiverId);

            Log.i("Sender-----",b.senderId);
            //long date = System.currentTimeMillis();
            //Date d=new Date(date);
            //String d1=String.valueOf(d.getDate());
            //String t1=String.valueOf(d.getTime());
            //b.dateTime=d1+" | "+t1;
            //java.text.DateFormat df = java.text.DateFormat.getDateInstance();
            //b.dateTime=df.getCalendar().getTime().toString();

            java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            b.dateTime= df.format(java.util.Calendar.getInstance().getTime());

            PostNotificationData data=new PostNotificationData();
            int res=data.postNotificationData(b);
            Log.i("res",String.valueOf(res));
            return res;
        }


        @Override
        protected void onPostExecute(Integer response)
        {
            Log.e("reponsee",response.toString());
            if(response==200 ||  response==201)
                Toast.makeText(getApplicationContext(),"Successfully send notification",Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(getApplication(),"Could not send notification",Toast.LENGTH_SHORT).show();
        }
    }
}
