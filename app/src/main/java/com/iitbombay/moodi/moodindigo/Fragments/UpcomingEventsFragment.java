package com.iitbombay.moodi.moodindigo.Fragments;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iitbombay.moodi.moodindigo.R;
import com.iitbombay.moodi.moodindigo.SessionManager;
import com.iitbombay.moodi.moodindigo.Adapters.UpcomingEventsAdapter;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by owais on 05/07/17.
 */

public class UpcomingEventsFragment extends Fragment {
    CircleImageView profimage;
    RecyclerView events;
    UpcomingEventsAdapter eventsAdapter;

    SessionManager sessionManager;
    String image, name, mi_number;
    RelativeLayout relativeLayout;
    ProgressBar progressBar;
    TextView time;
    int day_set;
    TextView no_events;
    TextView days_to_go_text;

    int x = 0;

    public UpcomingEventsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        container.removeAllViews();
//        return inflater.inflate(R.layout.fragment_upcoming_events, container, false);
//    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //container.removeAllViews();

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_upcoming_events, container, false);
        profimage = (CircleImageView) rootView.findViewById(R.id.imageView);
        sessionManager = new SessionManager(getContext());
        relativeLayout = rootView.findViewById(R.id.relativeLayout2);
        progressBar = (ProgressBar) rootView.findViewById(R.id.countdown);
        events = (RecyclerView) rootView.findViewById(R.id.events);
        days_to_go_text = (TextView) rootView.findViewById(R.id.days_to_go);
        time = (TextView) rootView.findViewById(R.id.timer);
        no_events=(TextView) rootView.findViewById(R.id.no_events);
no_events.setVisibility(View.GONE);
        Calendar c = Calendar.getInstance();
        int current_day = c.get(Calendar.DAY_OF_MONTH);

        if (sessionManager.getBoolean("isLoggedIn")) {
            name = sessionManager.getString("name");
            image = sessionManager.getString("image");
            mi_number = sessionManager.getString("mi number");

        } else {
            name = "User Name";
            image = null;
            mi_number = "MI number";
        }

        TextView profname = (TextView) rootView.findViewById(R.id.name);
        profname.setText(name);

        TextView mi_no = (TextView) rootView.findViewById(R.id.mi_number);
        mi_no.setText(mi_number);




        if (current_day < 22) {
            events.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.VISIBLE);
            days_to_go_text.setVisibility(View.VISIBLE);
//        Animation an=new RotateAnimation(0.0f,-90.0f);
//        an.setFillAfter(true);
//        progressBar.startAnimation(an);
            time.setText(String.valueOf(22 - current_day));
            int progress = 7 * (22 - current_day);
            progressBar.setProgress(progress);

        } else if (current_day <= 25 && current_day >= 22) {
            events.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.GONE);
            days_to_go_text.setVisibility(View.GONE);

            switch (current_day) {
                case 22:
                    day_set=1;
                    eventsAdapter = new UpcomingEventsAdapter(1, getContext());
                    break;
                case 23:
                    day_set=2;
                    eventsAdapter = new UpcomingEventsAdapter(2, getContext());
                    break;
                case 24:
                    day_set=3;
                    eventsAdapter = new UpcomingEventsAdapter(3, getContext());
                    break;
                case 25:
                    day_set=4;
                    eventsAdapter = new UpcomingEventsAdapter(4, getContext());
                    break;
            }
            events.setAdapter(eventsAdapter);
            events.setLayoutManager(new LinearLayoutManager(getContext()));
            events.setOverScrollMode(View.OVER_SCROLL_NEVER);
            no_events.setVisibility(View.GONE);
        } else {
            events.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.GONE);
            days_to_go_text.setVisibility(View.GONE);
            no_events.setVisibility(View.VISIBLE);

        }
        if(eventsAdapter.getItemCount()==0){
            events.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.GONE);
            days_to_go_text.setVisibility(View.GONE);
            no_events.setVisibility(View.VISIBLE);
        }

        //music float button
        final FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.upcoming_music_button);
        final MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.song);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                x = (x + 1) % 2;
                if (x == 1) {
                    fab.setImageResource(R.drawable.ic_pause);
                    mediaPlayer.start();
                    //fab.setBackgroundResource(R.drawable.play);
                }
//                    mediaPlayer.start();}
                if (x == 0) {   //fab.setBackgroundResource(R.drawable.ic_launcher_background);
                    fab.setImageResource((R.drawable.ic_action_name));
                    mediaPlayer.pause();
//                    mediaPlayer.pause();
                }
            }

        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        eventsAdapter=new UpcomingEventsAdapter(day_set,getContext());
        if(eventsAdapter.getItemCount()==0){
            events.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.GONE);
            days_to_go_text.setVisibility(View.GONE);
            no_events.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        eventsAdapter=new UpcomingEventsAdapter(day_set,getContext());
       
    }
}
