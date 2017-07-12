package com.lab42.maham.senseilocater;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * Created by lenovo on 4/24/2017.
 */
public class NotificationAdapter extends ArrayAdapter{

    Context con;
    List<NotificationBO> list;
    // String [] list;
    TextView tv1r;
    TextView tv2n;
    TextView tv3d;
    TextView tv4t;
    RelativeLayout relativeLayout;
    ImageView icon;
    public NotificationAdapter(Context context, int resource, List<NotificationBO> objects) {
        super(context, resource, objects);
        this.con=context;
        list=objects;
    }


    public View getView(int position,View convertView, ViewGroup parent){

        View v=null;
        viewHolder1 vh=new viewHolder1();
        if(convertView==null) {
            LayoutInflater layoutInflater = (LayoutInflater) con.getSystemService(con.LAYOUT_INFLATER_SERVICE);
            v = layoutInflater.inflate(R.layout.notification_cell, null, true);
            this.tv1r=(TextView) v.findViewById(R.id.tv_rollno);
            vh.tvrollno=this.tv1r;
            this.tv2n=(TextView) v.findViewById(R.id.tv_name);
            vh.tvname=this.tv2n;
            this.tv3d=(TextView) v.findViewById(R.id.tv_date);
            vh.tvdate=this.tv3d;
            this.tv4t=(TextView) v.findViewById(R.id.tv_time);
            vh.tvtime=this.tv4t;
            this.relativeLayout = (RelativeLayout) v.findViewById(R.id.rel_layout_notification_cell);
            vh.parentLayout=this.relativeLayout;
            this.icon = (ImageView) v.findViewById(R.id.im1);
            vh.icon=this.icon;
            v.setTag(vh);
        }
        else {
            v=convertView;
            vh=(viewHolder1) v.getTag();
        }
        //  String n=list[position];
        NotificationBO notificationBO = list.get(position);
        Log.d("id" , notificationBO.senderId );
        Log.d("date" , notificationBO.dateTime );
        vh.tvrollno.setText(notificationBO.senderId);
        String [] arr = notificationBO.dateTime.split("T");


        vh.tvdate.setText(arr[0]);
        vh.tvtime.setText(arr[1]);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String date = df.format(Calendar.getInstance().getTime());
        Log.d("date" , date);

        if(notificationBO.seenStatus!="true")
        {
//           NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(getContext())
//                   .setSmallIcon(R.drawable.logo1_web)
//                   .setLargeIcon(BitmapFactory.decodeResource(getContext().getResources() , R.drawable.logo1_web))
//                   .setContentTitle("Requested Teacher Status")
//                   .setContentText("Ahmad Ghazali came online");
//           notificationBuilder.setDefaults(
//                   Notification.DEFAULT_LIGHTS |   Notification.DEFAULT_VIBRATE  );
//
//
//
//           Uri alarmSound = Uri.parse("android.resource://"
//                   + getContext().getPackageName() + "/" + R.raw.cave);
//
//           notificationBuilder.setSound(alarmSound);
//           NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
//           notificationManager.notify(1 , notificationBuilder.build());



//
//           GradientDrawable border = new GradientDrawable();
//          // border.setColor(0xFFFFFFFF); //white background
//           border.setStroke(5, 0xFF000000); //black border with full opacity
//           if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
//                vh.parentLayout.setBackgroundDrawable(border);
//           } else {
//               vh.parentLayout.setBackground(border);
//           }

            vh.icon.setBackgroundResource(R.drawable.ic_menu_gallery);



        }

        return v;
    }

}
class viewHolder1
{
    public TextView tvrollno;
    public TextView tvname;
    public TextView tvdate;
    public TextView tvtime;
    public RelativeLayout parentLayout;
    public ImageView icon;
}
