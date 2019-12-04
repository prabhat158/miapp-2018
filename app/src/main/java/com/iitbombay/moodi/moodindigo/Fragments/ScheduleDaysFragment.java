package com.iitbombay.moodi.moodindigo.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.iitbombay.moodi.moodindigo.EventsActivity;
import com.iitbombay.moodi.moodindigo.R;

import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ScheduleDaysFragment extends Fragment {
    int x = 0;
    FrameLayout day1;
    FrameLayout day2;
    FrameLayout day3;
    FrameLayout  day4;
    CircleImageView profimage;
    View view;

    public ScheduleDaysFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_schedule_days, container, false);


        ButterKnife.bind(view);

        day1 = (FrameLayout) view.findViewById(R.id.day1);
        day2 = (FrameLayout) view.findViewById(R.id.day2);
        day3 = (FrameLayout) view.findViewById(R.id.day3);
        day4 = (FrameLayout) view.findViewById(R.id.day4);

        day1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EventsActivity.class);
                intent.putExtra("day", 1);
                startActivity(intent);
            }
        });
        day2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EventsActivity.class);
                intent.putExtra("day", 2);
                startActivity(intent);
            }
        });
        day3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EventsActivity.class);
                intent.putExtra("day", 3);
                startActivity(intent);
            }
        });
        day4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), EventsActivity.class);
                intent.putExtra("day", 4);
                startActivity(intent);
            }
        });

        //music float button
//        final FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.schedule_music_button);
//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                x = (x + 1) % 2;
//                if (x == 1) {
//                    fab.setImageResource(R.drawable.ic_pause);
//                    //fab.setBackgroundResource(R.drawable.play);
//                }
////                    mediaPlayer.start();}
//                if (x == 0) {   //fab.setBackgroundResource(R.drawable.ic_launcher_background);
//                    fab.setImageResource((R.drawable.ic_action_name));
////                    mediaPlayer.pause();
//                }
//            }
//
//        });

        return view;
    }
}
