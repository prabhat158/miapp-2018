package com.iitbombay.moodi.moodindigo.Notifications;

/**
 * Created by akash on 20-Dec-17.
 */
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.iitbombay.moodi.moodindigo.EventsDatabase.DatabaseHelper;
import com.iitbombay.moodi.moodindigo.EventsDatabase.Event;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StarterService extends Service {
    private static final String TAG = "MyService";
    private int position=0;
    DatabaseHelper db;
    List<Event> events;
    Toast toast;
    Context context;
    MyFirebaseMessagingService notif;
    int day;

    @Override
    public void onCreate() {

        Date currentTime = Calendar.getInstance().getTime();
        String time = currentTime.toString();
        String array1[] = time.split(" ");
        day = Integer.parseInt(array1[2])-21;
        context = StarterService.this;
        db = new DatabaseHelper(context);
        if(day>=1&&day<=4)
            events = db.getOrderedGoingEventsByDay(day);
        notif=new MyFirebaseMessagingService(context);

        super.onCreate();
        Log.d(TAG, "onCreate()");


    }

    /**
     * The started service starts the AlarmManager.
     */

    @Override
    public int onStartCommand(Intent intent,int flags, int startid) {

        Log.d("in StartService", "in onStartCommand threadId:"+Thread.currentThread().getId());
        if(day>=1&&day<=4){
            new Thread(new Runnable() {
                @Override
                public void run() {

                    Looper.prepare();
                    while (position <events.size())
                    {
                        if (events.get(position).getGoing()==1)
                        {
                            String event_time;
                            int event_day, event_id;
                            event_id = events.get(position).getId();
                            event_time =events.get(position).getTime_start();
                            int time = Integer.parseInt(event_time);
                            String finaltime=" ";
                            if (event_time.length()==4)
                            {finaltime = event_time.substring(0,2)+":"+event_time.substring(2);}

                            if (event_time.length()==3)
                            {finaltime = event_time.substring(0,1)+":"+event_time.substring(1);}

                            event_day = events.get(position).getDay();
                            String message =events.get(position ).getName()+ ";"+ events.get(position).getVenue() +"   |  ;"+finaltime+" hrs";
                            long delay = notif.delay(event_time,event_day)-1000*60*30;
                            if (delay>0){
                                Log.e("scheduled","scheduled");
                                notif.scheduleNotification(context, delay, event_id, message );}
                        }
                        position++;
                    }

                    Log.d(TAG, "onStartCommand()");
                }
            }).start();}
//        Toast.makeText(StarterService.this, "My Service started", Toast.LENGTH_LONG).show();

        return START_STICKY;

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent intent = new Intent(this,AutoStart.class );
        sendBroadcast(intent);
        super.onTaskRemoved(rootIntent);
        Log.d(TAG, "onTaskRemoved");

    }

    @Override
    public void onDestroy() {
        //       Toast.makeText(this, "My Service stopped", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onDestroy");
    }
}