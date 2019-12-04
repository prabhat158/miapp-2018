package com.iitbombay.moodi.moodindigo.Adapters;

import android.support.v7.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iitbombay.moodi.moodindigo.EventsDatabase.DatabaseHelper;
import com.iitbombay.moodi.moodindigo.EventsDatabase.Event;
import com.iitbombay.moodi.moodindigo.Fragments.MapFragment;
import com.iitbombay.moodi.moodindigo.Notifications.MyFirebaseMessagingService;
import com.iitbombay.moodi.moodindigo.R;
import com.iitbombay.moodi.moodindigo.SessionManager;
import com.iitbombay.moodi.moodindigo.Fragments.DetailsDialogFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by mrunz on 8/12/17.
 */

public class UpcomingEventsAdapter extends RecyclerView.Adapter<UpcomingEventsAdapter.myViewHolder> {
    DatabaseHelper db;
    List<Event> events;
    Context context;
    List<Event> raw_events;
    Toast toast;
    SessionManager sessionManager;
    MyFirebaseMessagingService notif;

    public UpcomingEventsAdapter(int day, Context context) {
        db = new DatabaseHelper(context);
        events = new ArrayList<>();
        sessionManager= new SessionManager(context);
        this.context=context;
        notif=new MyFirebaseMessagingService(context);
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        int current_time_string, time_after_hour;
        if (min < 10) {
            current_time_string = Integer.parseInt(String.valueOf(hour) + "0" + String.valueOf(min));
            if (hour != 23) {
                time_after_hour = Integer.parseInt(String.valueOf(hour + 1) + "0" + String.valueOf(min));
            } else {
                time_after_hour = Integer.parseInt("2400");
            }
        } else {
            current_time_string = Integer.parseInt(String.valueOf(hour) + String.valueOf(min));
            if (hour != 23) {
                time_after_hour = Integer.parseInt(String.valueOf(hour + 1) + String.valueOf(min));
            } else {
                time_after_hour = Integer.parseInt("2400");
            }
        }

        raw_events = db.getOrderedEventsByDay(day);
        for (Event event : raw_events) {
            String time = event.getTime_start();
            Log.i("current", String.valueOf(current_time_string));
            Log.i("+1", String.valueOf(time_after_hour));
            Log.i("time", time);
            if (current_time_string <= Integer.parseInt(time) && Integer.parseInt(time) <= time_after_hour) {
                events.add(event);
            }
        }
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.eventitem, parent, false);
        myViewHolder vh = new myViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(final myViewHolder holder, final int position) {
        if ((position == 0)) {
            holder.layout.setVisibility(View.INVISIBLE);
        } else {
               holder.name.setText(events.get(position - 1).getName());
            holder.description.setText(events.get(position - 1).getDescription());
//        holder.venue.setText("Venue: "+events.get(position).getVenue());
            holder.venue.setText(events.get(position - 1).getVenue());
            final String time = events.get(position - 1).getTime_start();
            String time_end = events.get(position - 1).getTime_end();
            holder.time.setText(returnTime(time) + " - ");
            holder.end.setText(returnTime(time_end));
            holder.subtitle.setText(events.get(position - 1).getSubtitle());

            final int ifGoing = events.get(position-1).getGoing();
            if (sessionManager.getBoolean("isLoggedIn")) {

                if (ifGoing == 1) {
                    holder.going.setColorFilter(context.getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
                } else {
                    holder.going.setColorFilter(null);
                }
                holder.going.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String event_time;
                        int event_day, event_id;
                        event_id = events.get(position-1).getId();
                        if (events.get(position - 1).getGoing() == 1) {
                            events.get(position - 1).setGoing(0);
                            notif.cancelNotification(context,event_id);
                            db.updateEvent(events.get(position - 1));

                            holder.going.setColorFilter(null);
                        } else {
                            events.get(position - 1).setGoing(1);
                            holder.going.setColorFilter(context.getResources().getColor(R.color.colorPrimaryDark), PorterDuff.Mode.SRC_ATOP);
                            db.updateEvent(events.get(position - 1));
                            event_time =events.get(position-1).getTime_start();
                            int time = Integer.parseInt(event_time);
                            String finaltime=" ";
                            if (event_time.length()==4)
                            {finaltime = event_time.substring(0,2)+":"+event_time.substring(2);}

                            if (event_time.length()==3)
                            {finaltime = event_time.substring(0,1)+":"+event_time.substring(1);}

                            event_day = events.get(position-1).getDay();
                            String message =events.get(position-1).getName()+ ";"+ events.get(position-1).getVenue() +"   |  ;"+finaltime+" hrs";
                            long delay = notif.delay(event_time,event_day)-1000*60*30;
                            if (delay>0){
                                Log.e("scheduled","scheduled");
                                notif.scheduleNotification(context, delay, event_id, message );}
                        }
                    }
                });
            } else {
                holder.going.setColorFilter(null);
                holder.going.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (toast != null) {
                            toast.cancel();
                        }
                        toast = Toast.makeText(context, "Please Login to access this feature", Toast.LENGTH_LONG);
                        toast.show();
                    }
                });


            }
            holder.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DetailsDialogFragment detailsDialogFragment = new DetailsDialogFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("title", holder.name.getText().toString());
                    bundle.putString("description", holder.description.getText().toString());
                    detailsDialogFragment.setArguments(bundle);
                    detailsDialogFragment.show(((Activity) context).getFragmentManager(), "dialog");
                }
            });
            holder.venueRelativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(context, "Location: "+ events.get(position-1).getVenue(), Toast.LENGTH_LONG).show();
                    Bundle bundle = new Bundle();
                    bundle.putString("venueString", events.get(position-1).getVenue());
                    MapFragment mapFragment = new MapFragment();
                    mapFragment.setArguments(bundle);
                    openFragment(mapFragment, events.get(position-1).getVenue());
                }
            });
        }
    }

        @Override
        public int getItemCount() {
            try {
                Log.i("size", Integer.toString(events.size()));
                return events.size();
            } catch (Exception e) {
                Log.i("size", Integer.toString(0));
                return 0;
            }
        }

        public static class myViewHolder extends RecyclerView.ViewHolder {

            TextView name;
            TextView description;
            TextView subtitle;
            TextView end;
            TextView time;
            TextView venue;
            ImageView going;
            LinearLayout layout;
            RelativeLayout venueRelativeLayout;

            public myViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(itemView);
                going = (ImageView) itemView.findViewById(R.id.going);
                subtitle=(TextView) itemView.findViewById(R.id.subtitle);
                name = (TextView) itemView.findViewById(R.id.name);
                description = (TextView) itemView.findViewById(R.id.description);
                venue = (TextView) itemView.findViewById(R.id.venue);
                time = (TextView) itemView.findViewById(R.id.time);
                end=(TextView) itemView.findViewById(R.id.time_end);
                layout = (LinearLayout) itemView.findViewById(R.id.layout);
                venueRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.venue_button);
            }
        }
    public String returnTime(String time) {
        if (time.length() == 4) {
            //holder.time.setText("Start: "+time.substring(0, 2) + ":" + time.substring(2));
            return (time.substring(0, 2) + ":" + time.substring(2));
        } else {
            //holder.time.setText("Start: "+time.charAt(0) + ":" + time.substring(1));
            return (time.charAt(0) + ":" + time.substring(1));

        }
    }
    public void openFragment(Fragment fragment, String tag){
        FragmentTransaction ft = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
        //ft.setCustomAnimations(R.anim.slide_out_from_left,R.anim.slide_in_from_right);
        ft.replace(R.id.relative_layout_for_main_fragment, fragment, fragment.getTag());
        ft.addToBackStack(tag);
        ft.commit();
    }


}


