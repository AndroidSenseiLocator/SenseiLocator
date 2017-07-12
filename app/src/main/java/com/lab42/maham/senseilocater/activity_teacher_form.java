package com.lab42.maham.senseilocater;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class activity_teacher_form extends AppCompatActivity {

    EditText t1, t2, t3, t4, t5, t6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_form);

        t1 = (EditText)findViewById(R.id.et_teacher_profile_fname);
        t2 = (EditText)findViewById(R.id.et_teacher_profile_lname);
        t3 = (EditText)findViewById(R.id.et_teacher_profile_edu);
        t4 = (EditText)findViewById(R.id.et_teacher_email);
        t5 = (EditText)findViewById(R.id.et_teacher_profile_password);
        t6 = (EditText)findViewById(R.id.et_teacher_profile_password_reEnter);

        Button bu = (Button)findViewById(R.id.btn_teacher_profile_signup);
        bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ff2=true;
                if(ff2)
                {
                    boolean f1=attemptLogin();
                    if(f1==false)
                        ff2=false;
                }
                if(ff2==false)
                {
                    SharedPreferences sp=getSharedPreferences("Preferences",MODE_PRIVATE);
                    SharedPreferences.Editor e=sp.edit();
                    e.putString("user","teacher");
                    e.putString("userName",t1.getText().toString());
                    e.putString("userEmail",t4.getText().toString());
                    e.putString("userPassword",t5.getText().toString());
                    e.putString("userEducation",t3.getText().toString());
                    e.putBoolean("userAvailable",true);
                    TeacherLogInBO t=new TeacherLogInBO();
                    String av="true";

                    t.available=av;
                    t.education=t3.getText().toString();
                    t.name=t1.getText().toString();
                    t.email=t4.getText().toString();
                    t.password=t5.getText().toString();
                    t.location="";
                    t.post="";
                    t.id=t4.getText().toString().substring(0,t4.getText().toString().lastIndexOf("@"));
                    e.putString("userID",t.id);
                    e.commit();
                    Toast.makeText(getApplicationContext(),t.id,Toast.LENGTH_SHORT).show();
                    TeacherTask t1=new TeacherTask(t);
                    t1.execute();
                    //e.putString("userPost",response.get(i).post);
                    //e.putString("userId",response.get(i).id);
                    //e.putString("userlocation",response.get(i).location);

                    Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
                    startActivity(intent);
                }



            }
        });
    }
    private boolean attemptLogin() {
        t1.setError(null);
        t2.setError(null);
        t3.setError(null);
        t4.setError(null);
        t5.setError(null);
        t6.setError(null);

        // Store values at the time of the login attempt.
        String fname=t1.getText().toString();
        String lname=t2.getText().toString();
        String edu=t3.getText().toString();
        String email = t4.getText().toString();
        String password = t5.getText().toString();
        String rePass=t6.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if(TextUtils.isEmpty(fname))
        {
            t1.setError("First Name is required");
            focusView=t1;
            cancel=true;
        }
        else if(TextUtils.isEmpty(lname))
        {
            t2.setError("Last Name is required");
            focusView=t2;
            cancel=true;
        }
        else if(TextUtils.isEmpty(edu))
        {
            t3.setError("Education is required");
            focusView=t3;
            cancel=true;
        }
        else if (TextUtils.isEmpty(password) || !isPasswordValid(password) || !password.equals(rePass))
        {
            t5.setError(getString(R.string.error_invalid_password));
            focusView = t5;
            cancel = true;
        }

        // Check for a valid email address.
        else if (TextUtils.isEmpty(email)) {
            t4.setError(getString(R.string.error_field_required));
            focusView = t4;
            cancel = true;
        } else if (!isEmailValid(email)) {
            t4.setError(getString(R.string.error_invalid_email));
            focusView = t4;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        }
        return cancel;
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        boolean flag=false;
        flag=email.contains("@");
        if(flag==true)
            flag=email.contains(".");
        return  flag;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    class TeacherTask extends AsyncTask<Void, Void ,Boolean> {

        //   private final String mEmail;
        //  private final String mPassword;
        private  TeacherLogInBO t=new TeacherLogInBO();
        TeacherTask( TeacherLogInBO l) {
            t=l;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            int resp = 0;
            try {
                PostTeacherData p = new PostTeacherData();
                resp = p.postDatateacher(t);

            } catch (Exception e) {
                return false;
            }
            if(resp==200)
                return true;
            else
                return false;
        }

        protected void onPostExecute(Boolean response) {
            if(response==true)
            {
                Toast.makeText(getApplicationContext(),"Successfully signed up!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
                startActivity(intent);

            }
            else{
                Toast.makeText(getApplicationContext(),"can't signed up!",Toast.LENGTH_SHORT).show();

            }
        }


    }
}

