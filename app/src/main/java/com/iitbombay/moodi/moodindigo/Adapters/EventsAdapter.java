package com.iitbombay.moodi.moodindigo.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iitbombay.moodi.moodindigo.R;

/**
 * Created by mrunz on 22/9/17.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.myViewHolder> {


    public EventsAdapter() {

    }

    @Override
    public EventsAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.eventitem, parent, false);
        EventsAdapter.myViewHolder vh = new EventsAdapter.myViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(final EventsAdapter.myViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public static class myViewHolder extends RecyclerView.ViewHolder {

        public myViewHolder(View itemView) {
            super(itemView);
        }
    }
}
