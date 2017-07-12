package com.lab42.maham.senseilocater;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import Model.RequestLiveDataProvider;
import Model.RequestResponseBO;


public class MainNotificationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    ListView listView;
    List<NotificationBO> ln=new ArrayList<NotificationBO>();
    // String [] In = {"a","b","c","d","e","f","g","h","i","j","k","l"};
    NotificationAdapter adapter;
    View saved_view;
    String id;


    int time = 20;
    Timer t;
    TimerTask task;

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    private GetServerData data = new GetServerData();
    //  private OnFragmentInteractionListener mListener;

    public MainNotificationFragment() {
        // Required empty public constructor

    }

    @Override
    public  void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //new GetServerData().execute();
        SharedPreferences sp= getActivity().getSharedPreferences("Preferences", getActivity().MODE_PRIVATE);

        // change to de done here;
        // String id = sp.getString("userEmail" , "");
        id="1";
        t = new Timer();
        task = new TimerTask() {

            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        if (time > 0) {
                            time -= 1;
                            Log.d("timer" , "called");

                        } else {
                            try {
                                //if (data.getStatus() != AsyncTask.Status.RUNNING) {

                                //data.cancel(true);
                                Log.d("call" , "yes");
                                new GetServerData().execute();
                                //}
                            }catch(Exception e){
                                Log.e("exception",e.toString());
                            }

                        }
                    }
                });
            }
        };
        t.scheduleAtFixedRate(task, 0, 1000);





    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_main_notification,container,false);
        saved_view = view;
        return view;
    }
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment MainNotificationFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static MainNotificationFragment newInstance(String param1, String param2) {
//        MainNotificationFragment fragment = new MainNotificationFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }


//
//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        //Thread.currentThread().interrupt();
        super.onDestroy();

    }
    @Override
    public void onDetach() {
        super.onDetach();
        Thread.currentThread().interrupt();
        data.cancel(true);
        t.cancel();
    }

    private class GetServerData extends AsyncTask<Void, Void, RequestResponseBO> {

        private ProgressDialog pDialog;

//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            // Showing progress dialog
//        pDialog = new ProgressDialog( getActivity().getApplicationContext() );
//        pDialog.setMessage("Please wait...");
//         pDialog.setCancelable(false);
//       pDialog.show();
//
//        }

        @Override
        protected RequestResponseBO doInBackground(Void... params) {
            try {
                //if(!data.isCancelled()) {
                SharedPreferences sp = getActivity().getSharedPreferences("Preferences",getActivity().MODE_PRIVATE);
                id = sp.getString("userId" , "");
                RequestLiveDataProvider liveDataProvider = new RequestLiveDataProvider(id);
                Log.d("eeeeee-------" , id.toString());
                return liveDataProvider.getServerData();
                //}


            }catch (Exception e){

            }
            return null;
        }

        @Override
        protected void onPostExecute(RequestResponseBO responseBO) {
            super.onPostExecute(responseBO);

            // Dismiss the progress dialog
            //     if (pDialog.isShowing())
            //       pDialog.dismiss();


            Toast.makeText( getActivity().getApplicationContext(), "executed", Toast.LENGTH_SHORT).show();
            //if(!data.isCancelled()) {
            if (responseBO != null && responseBO.arrayList != null && responseBO.arrayList.size() > 0) {
                {


                    if(getActivity() !=null){
                        //  Toast.makeText(getActivity().getApplicationContext(), "Successfull yeh wala", Toast.LENGTH_LONG).show();
                        ln = responseBO.arrayList;
                        //View view = sv.inflate(R.layout.fragment_teacher_list2, vg, false);
                        listView = (ListView) saved_view.findViewById(R.id.notificationList);
                        if (listView != null) {
                            adapter = new NotificationAdapter(getActivity(), R.layout.fragment_main_notification, ln);
                            listView.setAdapter(adapter);
//                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//                            @Override
//                            public void onItemClick(AdapterView<?> adapter, View arg1, int position, long arg3) {
//
//                                Intent i = new Intent( getActivity() , TeacherDetailsActivity.class );
//                                Bundle b = new Bundle();
//
//                                i.putExtra("object" , ln.get(position)); //
//                                getActivity().startActivity(i);
//                            }
//
//                        });
                        }
                    }


                }

            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Response is empty, please try again.", Toast.LENGTH_LONG).show();
            }
//                for(int i = 0;i < 1000;i++){
//                    for(int j = 0;j < 1000;j++)
//                    {
//                        for(int k = 0;k < 1000;k++){
//
//                        }
//                    }
//                }
//
//                boolean flag = false;
//                while(flag == false)
//                {
//                    if(data.getStatus() != Status.RUNNING) {
//
//                        data.execute();
//                        flag = true;
//                    }
//                }

//                Context context = getActivity().getApplicationContext();
//                alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//                Intent intent = new Intent(context, activity_go.class);
//                alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
//
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTimeInMillis(System.currentTimeMillis());
////                alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
////                        1000 * 5 * 1, alarmIntent);
//                alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                        SystemClock.elapsedRealtime() +
//                                5 * 1000, alarmIntent);




//                Intent intent = new Intent( getActivity().getApplicationContext() , activity_go.class);
//                PendingIntent sender = PendingIntent.getBroadcast( getActivity().getApplicationContext()  ,
//                        0, intent, 0);
//
//                // We want the alarm to go off 10 seconds from now.
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTimeInMillis(System.currentTimeMillis());
//                calendar.add(Calendar.SECOND, 10);
//
//                // Schedule the alarm!
//                AlarmManager am = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
//                am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
//
//                // Tell the user about what we did.
//                if (mToast != null) {
//                    mToast.cancel();
//                }
//                mToast = Toast.makeText(AlarmDemo.this,"Alarm Started",
//                        Toast.LENGTH_LONG);
//                mToast.show();

            //}
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }


    }
}
