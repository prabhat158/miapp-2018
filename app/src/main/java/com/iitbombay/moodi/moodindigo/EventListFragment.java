package com.iitbombay.moodi.moodindigo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iitbombay.moodi.moodindigo.Adapters.ScheduleEventsAdapter;

public class EventListFragment extends Fragment {

    RecyclerView recyclerView;
    ScheduleEventsAdapter scheduleEventsAdapter;
    Bundle bundle;
    String type;
    int day;
    View view;

    public EventListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_event_list, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_events);
        bundle = getArguments();
        type = bundle.getString("type");
        Log.i("type", type);
        day = bundle.getInt("day");

        scheduleEventsAdapter = new ScheduleEventsAdapter(type, day, getContext());
        recyclerView.setAdapter(scheduleEventsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Log.i("reached", "here");


        return view;
    }

}
