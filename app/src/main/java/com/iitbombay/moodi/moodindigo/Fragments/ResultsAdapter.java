package com.iitbombay.moodi.moodindigo.Fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iitbombay.moodi.moodindigo.R;
import com.iitbombay.moodi.moodindigo.data.ResultResponse;

import java.util.List;

/**
 * Created by mrunz on 20/12/17.
 */

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ViewHolder>{

Context context;
    List<ResultResponse> resultResponses;
    public ResultsAdapter(List<ResultResponse> resultResponses,Context context){
        this.resultResponses=resultResponses;
        this.context=context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.results_item, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        return  vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.name.setText(resultResponses.get(position).getName());
        holder.round.setText(resultResponses.get(position).getRound());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.iitbombay.moodi.moodindigo.Fragments.ResultsDialogFragment resultsDialogFragment = new com.iitbombay.moodi.moodindigo.Fragments.ResultsDialogFragment();
                Bundle bundle=new Bundle();
                bundle.putString("results", resultResponses.get(position).getResults());
                bundle.putString("round", resultResponses.get(position).getRound());
                resultsDialogFragment.setArguments(bundle);
                resultsDialogFragment.show(((Activity) context).getFragmentManager(), "dialog");

            }
        });



    }

    @Override
    public int getItemCount() {
        return resultResponses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,round;
        LinearLayout layout;

        public ViewHolder(View v){
            super(v);
            name=v.findViewById(R.id.name);
            round=v.findViewById(R.id.round);
            layout=v.findViewById(R.id.layout);

        }
    }
}
