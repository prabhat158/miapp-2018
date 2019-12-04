package com.iitbombay.moodi.moodindigo.Fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iitbombay.moodi.moodindigo.Adapters.GoingEventsAdapter;
import com.iitbombay.moodi.moodindigo.EventsDatabase.Event;
import com.iitbombay.moodi.moodindigo.R;

public class GoingEventsFragment extends Fragment {

    RecyclerView recyclerView;
    GoingEventsAdapter goingEventsAdapter;
    Bundle bundle;
    String type;
    TextView noEvents;
    int day;
    View view;

    public GoingEventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_going_events, container, false);



        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_events);
        noEvents=(TextView) view.findViewById(R.id.no_events);
        bundle = getArguments();
        day = bundle.getInt("day");


        goingEventsAdapter = new GoingEventsAdapter(day, getContext());
        recyclerView.setAdapter(goingEventsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Log.i("reached", "here");

        goingEventsAdapter.notifyDataSetChanged();

        //swipe for delete item
        ItemTouchHelper touchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                String name=goingEventsAdapter.events.get(position).getName();
                final Event event=goingEventsAdapter.events.get(position);
                goingEventsAdapter.removeItem(position);

                Snackbar snackbar=Snackbar.make(viewHolder.itemView, name + " was removed", Snackbar.LENGTH_LONG).setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e("undoed","undoes");
                        goingEventsAdapter.restoreItem(event,position);
                        goingEventsAdapter.notifyDataSetChanged();
                    }
                });
                snackbar.getView().setBackgroundColor(getContext().getResources().getColor(R.color.datePicker));
                snackbar.setDuration(1000);
                snackbar.show();

            }
        });
        touchHelper.attachToRecyclerView(recyclerView);
        if(goingEventsAdapter.getItemCount()==0){
        recyclerView.setVisibility(View.GONE);
        noEvents.setVisibility(View.VISIBLE);
        }
        else{
            recyclerView.setVisibility(View.VISIBLE);
            noEvents.setVisibility(View.GONE);
        }


        return view;
    }

}
