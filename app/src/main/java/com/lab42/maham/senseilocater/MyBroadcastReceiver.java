package com.lab42.maham.senseilocater;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by Maham on 5/22/2017.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        /*Bundle b = intent.getExtras();
        if(b != null) {
            if(b.containsKey("value")){*/
                Toast.makeText(context,"Checked",Toast.LENGTH_SHORT).show();
           /* }

        }
        else
            Toast.makeText(context,"Unchecked",Toast.LENGTH_SHORT).show();*/

    }
}
