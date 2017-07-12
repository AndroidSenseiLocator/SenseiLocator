package com.lab42.maham.senseilocater;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import Model.Teacher;

/**
 * Created by User on 4/24/2017.
 */
public class myAdapter extends ArrayAdapter {
    Context context;
   // List<Object> list;
    //Object [] list;
   ArrayList<Teacher> list;
    int i;
    int j;
    int k;

    public myAdapter(Context _context, int resource, ArrayList<Teacher> objects) {

        super(_context, resource, objects);
        context = _context;
        list = objects;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v=null;
        viewHolder view_holder= new viewHolder();
        if(convertView==null) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            v = layoutInflater.inflate(R.layout.list_item_teachers_list, null, true);
            TextView tv = (TextView) v.findViewById(R.id.tv_cell_teacher_list);
            ImageView img = (ImageView) v.findViewById(R.id.img_teacher_teacher_list);
            ImageView status = (ImageView) v.findViewById(R.id.img_status_teacher_list);
            view_holder.teacherImage= img;
            view_holder.teacherName= tv;
            view_holder.teacherStatus = status;

            v.setTag(view_holder);
        }
        else
        {
            v = convertView;
            view_holder = (viewHolder) v.getTag();
        }
       //view_holder.teacherName.setText(list[position].toString());
        Teacher t = (Teacher) list.get(position);
        view_holder.teacherName.setText(t.Name);
        String encodedImageString = t.Education;
        Log.d("edu" , t.Education);
//        byte[] bytarray = Base64.decode(encodedImageString, Base64.DEFAULT);
//        Bitmap bmimage = BitmapFactory.decodeByteArray(bytarray, 0,
//                bytarray.length);
//        Log.d("my img" , bytarray.toString());
//        if(bmimage!=null)
//            view_holder.teacherImage.setImageBitmap(getRoundedShape(bmimage));
//        else
//        {
//            Bitmap bmp = BitmapFactory.decodeResource(view_holder.teacherImage.getResources(), R.mipmap.detailpic);
//
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//            byte[] b = baos.toByteArray();
//            //encodedImageString =
//            encodedImageString = Base64.encodeToString(b, Base64.DEFAULT);
//            bytarray = Base64.decode(encodedImageString, Base64.DEFAULT);
//            bmimage = BitmapFactory.decodeByteArray(bytarray, 0,
//                    bytarray.length);
//
//            Log.d("img" , encodedImageString);
//
//            view_holder.teacherImage.setImageBitmap(getRoundedShape(bmimage));
//        }

        return  v;
 }
    public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
        int targetWidth = 50;
        int targetHeight = 50;
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight,Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth),
                        ((float) targetHeight)) / 2),
                Path.Direction.CCW);

        canvas.clipPath(path);
        Bitmap sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap(sourceBitmap,
                new Rect(0, 0, sourceBitmap.getWidth(),
                        sourceBitmap.getHeight()),
                new Rect(0, 0, targetWidth, targetHeight), null);
        return targetBitmap;
    }
}
 class viewHolder
{
    ImageView teacherImage , teacherStatus;
    TextView teacherName;

}