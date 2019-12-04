package com.iitbombay.moodi.moodindigo.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.iitbombay.moodi.moodindigo.R;
import com.iitbombay.moodi.moodindigo.RetrofitClass;
import com.iitbombay.moodi.moodindigo.SearchInterface;
import com.iitbombay.moodi.moodindigo.SessionManager;
import com.iitbombay.moodi.moodindigo.data.ResultResponse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by owais on 05/07/17.
 */

public class ResultsFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    public View view;
    public Spinner type;
    public RecyclerView rv;
    public ResultsAdapter resultsAdapter;
    RetrofitClass rcInitiate;
    SearchInterface client;
    TextView messages,logincheck;
    SessionManager sessionManager;
    Boolean isLoggedIn=false;
    LinearLayout layout;
    List<List<ResultResponse>> resultResponses=new ArrayList<>();
    List<ResultResponse> rawresponseList;

    public ResultsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        container.removeAllViews();
        // Inflate the layout for this fragment
        //container.removeAllViews();
        view = inflater.inflate(R.layout.fragment_results, container, false);
        rv=(RecyclerView) view.findViewById(R.id.rv_events);
        messages=(TextView) view.findViewById(R.id.messages);
logincheck=(TextView) view.findViewById(R.id.logincheck);
layout=(LinearLayout) view.findViewById(R.id.layout);
        sessionManager=new SessionManager(getContext());
        isLoggedIn=sessionManager.getBoolean("isLoggedIn");
        if(isLoggedIn){
            layout.setVisibility(View.VISIBLE);
            logincheck.setVisibility(View.GONE);
        for(int i=0;i<5;i++){
            resultResponses.add(new ArrayList<ResultResponse>());
        }


        // Inflate the layout for this fragment
        rcInitiate=new RetrofitClass(getContext());
        client=rcInitiate.createBuilder().create(SearchInterface.class);
        Call<List<ResultResponse>> call=client.getResults();
        call.enqueue(new Callback<List<ResultResponse>>() {
            @Override
            public void onResponse(Call<List<ResultResponse>> call, Response<List<ResultResponse>> response) {
                rawresponseList=response.body();
                if(response.body().size()>0) {
                    for (ResultResponse resultResponse : rawresponseList) {
                        resultResponses.get(resultResponse.getDay()).add(resultResponse);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ResultResponse>> call, Throwable t) {

            }
        });

        type= (Spinner) view.findViewById(R.id.type);
        type.setOnItemSelectedListener(this);
        final List<String>types=new ArrayList<>();
        types.add("Day 0");
        types.add("Day 1");
        types.add("Day 2");
        types.add("Day 3");
        types.add("Day 4");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item_1, types);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        type.setAdapter(arrayAdapter);
        Calendar c=Calendar.getInstance();
        int day=c.get(Calendar.DAY_OF_MONTH);
        switch (day){
            case 21: type.setSelection(0);break;
            case 22: type.setSelection(1);break;
            case 23: type.setSelection(2);break;
            case 24: type.setSelection(3);break;
            case 25: type.setSelection(4);break;
            default:type.setSelection(0);
        }
        }
        else
        {
            layout.setVisibility(View.GONE);
            logincheck.setVisibility(View.VISIBLE);
        }


        return view;

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(resultResponses.get(position).size()>0) {
            rv.setVisibility(View.VISIBLE);
            messages.setVisibility(View.GONE);
            resultsAdapter = new ResultsAdapter(resultResponses.get(position), getContext());
            rv.setAdapter(resultsAdapter);
            rv.setLayoutManager(new LinearLayoutManager(getContext()));
        }
        else {
            Log.e("position", String.valueOf(position));
            rv.setVisibility(View.GONE);
            messages.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
