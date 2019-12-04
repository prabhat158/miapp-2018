/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.iitbombay.moodi.moodindigo.Notifications;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.iitbombay.moodi.moodindigo.EventsActivity;
import com.iitbombay.moodi.moodindigo.EventsDatabase.DatabaseHelper;
import com.iitbombay.moodi.moodindigo.MainActivity;
import com.iitbombay.moodi.moodindigo.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Calendar;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private Context context=this;
    private DatabaseHelper db;
    private Toast toast;
    public MyFirebaseMessagingService(Context context){
        this.context = context;
        db=new DatabaseHelper(context);
    }

    public MyFirebaseMessagingService(){}

    //    * Called when message is received.
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

        }
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            sendNotification(this, remoteMessage.getNotification().getBody());
        }
    }

    public void cancelNotification(Context context, int event_id){

        Intent notificationIntent = new Intent(context,MyNotificationPublisher.class );

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                event_id, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        am.cancel(pendingIntent);
        pendingIntent.cancel();

        // Toast.makeText(context.getApplicationContext(),"notification cancelled for event_id : "+event_id, Toast.LENGTH_LONG).show();
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void scheduleNotification(Context context, long delay, int event_id, String message) {

        Intent intent = new Intent(context, EventsActivity.class);
        intent.putExtra("day",db.getEvent(event_id).getDay());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(
                0,PendingIntent.FLAG_UPDATE_CURRENT);

        String channelId = "channel_id_01";
        String message1[] = message.split(";");


        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, channelId)
                        .setSmallIcon(R.drawable.mi_stamp)
                        .setContentTitle(message1[0])
                        .setContentText(message1[1]+" "+message1[2])
//        NotificationCompat.InboxStyle inboxStyle =
//                new NotificationCompat.InboxStyle();
//
//// Sets a title for the Inbox in expanded layout
//        inboxStyle.setBigContentTitle("Mood Indigo");
//
//        for (int i=0; i < message1.length; i++) {
//
//            inboxStyle.addLine(message1[i]);
//        }
//                        notificationBuilder.setStyle(inboxStyle)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        Notification notification = notificationBuilder.build();

        Intent notificationIntent = new Intent(context, MyNotificationPublisher.class);
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION_ID, event_id);
        notificationIntent.putExtra(MyNotificationPublisher.NOTIFICATION, notification);

        PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context, event_id, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent2);


        long days = delay/(60000*24*60);
        long hours = (delay%(60000*24*60))/(60000*60);
        long minutes = ((delay%(60000*24*60))%(60000*60))/(60000);

        toast=Toast.makeText(context.getApplicationContext(),"scheduled for "+days+" days, "+hours+" hours,"+minutes+" minutes", Toast.LENGTH_SHORT);
    toast.show();

    }
    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */


    public void sendNotification(Context context, String messageBody) {

        Intent intent = new Intent(context, EventsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = "channel_id_01";
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, channelId)
                        .setSmallIcon(R.drawable.mi_stamp)
                        .setContentTitle("FCM Message")
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
    public long delay(String time, int day)
    {
        Calendar current_time = Calendar.getInstance();
        Calendar future_time = Calendar.getInstance();
        int date, hr, min;
        if (day==1)
            date=22;
        else if (day==2)
            date = 23;
        else if (day==3)
            date=24;
        else if (day==4)
            date=25;
        else
            date=day;
        int timeint = Integer.parseInt(time);
        hr = timeint/100;
        min = timeint%100;

        future_time.set(Calendar.MONTH, Calendar.DECEMBER);
        future_time.set(Calendar.DATE, date);
        future_time.set(Calendar.HOUR_OF_DAY, hr);
        future_time.set(Calendar.MINUTE, min);
        future_time.set(Calendar.SECOND,0);

        long future = future_time.getTimeInMillis();
        long current = current_time.getTimeInMillis();

        return future-current;
    }
    public void cancelToast(){
        if(toast!=null){
            toast.cancel();
        }
    }
}