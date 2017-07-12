package com.lab42.maham.senseilocater;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import java.util.ArrayList;
import java.util.List;
import Model.LiveDataProvider;

import Model.Teacher;
import Model.TeacherResponseBO;


/**
 * A simple {@link Fragment} subclass.
 */
public class TeacherListFragment extends Fragment {

    LayoutInflater sv;
    ViewGroup vg;
    View v;
    ListView listView = null;
    myAdapter adapter = null;
    //String [] dummy = {"a","b","c","Sania Ashraf" , "Kashif Murtaza" , "Waqar ul Qonain" , "Ahmad Ghazali" , "Aneeze Babar" , "Abdullah" , "Aisha Khan" , "Anum Tariq" , "Fareed ul Hassan" , "Ahmad Raza"} ;
    ArrayList<Teacher> dummy;
    public TeacherListFragment() {

    }
    @Override
    public  void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        new GetServerData().execute();
    }
 @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*View view = inflater.inflate(R.layout.fragment_teacher_list2, container, false);
        listView = (ListView)   view.findViewById(R.id.list_view_teachers_list);
        if(listView != null) {
            adapter = new myAdapter(getActivity(), R.layout.list_item_teachers_list, dummy);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapter, View arg1, int position, long arg3) {

                    Intent i = new Intent( getActivity() , TeacherDetailsActivity.class );
                    i.putExtra("name" , dummy[position]);
                    getActivity().startActivity(i);
                }

            });
        }
     return view;*/
         View view = inflater.inflate(R.layout.fragment_teacher_list2, container, false);
         v=view;
         sv=inflater;
         vg=container;
         return view;
    }


private class GetServerData extends AsyncTask<Void, Void, TeacherResponseBO> {

    private ProgressDialog pDialog;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        // Showing progress dialog
//        pDialog = new ProgressDialog( getActivity().getApplicationContext() );
//        pDialog.setMessage("Please wait...");
//        pDialog.setCancelable(false);
//        pDialog.show();

    }

    @Override
    protected TeacherResponseBO doInBackground(Void... params) {
        try {

            LiveDataProvider liveDataProvider = new LiveDataProvider();
            return liveDataProvider.getServerData();

        }catch (Exception e){

        }
        return null;
    }

    @Override
    protected void onPostExecute(TeacherResponseBO responseBO) {
        super.onPostExecute(responseBO);

        // Dismiss the progress dialog
//        if (pDialog.isShowing())
//            pDialog.dismiss();

        if(responseBO != null && responseBO.arrayList != null &&responseBO.arrayList .size() > 0){
            {
                Toast.makeText(getActivity().getApplicationContext(), "Successfull", Toast.LENGTH_LONG).show();
                dummy = responseBO.arrayList;
                //View view = sv.inflate(R.layout.fragment_teacher_list2, vg, false);
                listView = (ListView)   v.findViewById(R.id.list_view_teachers_list);
                if(listView != null) {
                    adapter = new myAdapter(getActivity(), R.layout.list_item_teachers_list, dummy);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> adapter, View arg1, int position, long arg3) {

                            Intent i = new Intent( getActivity() , TeacherDetailsActivity.class );
                            Bundle b = new Bundle();

                            i.putExtra("object" , dummy.get(position)); //
                            getActivity().startActivity(i);
                        }

                    });
                }


            }

        }else{
            Toast.makeText( getActivity().getApplicationContext(), "Response is empty, please try again.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }


}
}
