package com.iitbombay.moodi.moodindigo.Notifications;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by akash on 20-Dec-17.
 */

public class AutoStart extends BroadcastReceiver {

    @Override
    public void onReceive (Context context, Intent intent) {
        Log.d("Autostart",  "Boot complete broadcast recieved. Executing starter service");

        Intent intent1 = new Intent(context, StarterService.class);
        context.startService(intent1);
    }
}