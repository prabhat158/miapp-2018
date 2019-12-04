package com.iitbombay.moodi.moodindigo.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.iitbombay.moodi.moodindigo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by owais on 05/07/17.
 */

public class FAQFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    public View view;
    public Spinner type;
    public LinearLayout compi,general,acco,concerts;

    public FAQFragment() {
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

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_faq, container, false);
        compi=(LinearLayout) view.findViewById(R.id.compi);
        general=(LinearLayout) view.findViewById(R.id.general);
        concerts=(LinearLayout)  view.findViewById(R.id.concerts);
        acco=(LinearLayout) view.findViewById(R.id.acco);

        type= (Spinner) view.findViewById(R.id.type);
        type.setOnItemSelectedListener(this);
        final List<String>types=new ArrayList<>();
        types.add("General");
        types.add("Competitions");
        types.add("Concerts");
        types.add("Accomodation");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item_1, types);
        arrayAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        type.setAdapter(arrayAdapter);
        type.setSelection(0);


        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0: general.setVisibility(View.VISIBLE);
                    compi.setVisibility(View.GONE);
                    concerts.setVisibility(View.GONE);
                    acco.setVisibility(View.GONE);
                    break;
            case 1:general.setVisibility(View.GONE);
            compi.setVisibility(View.VISIBLE);
            concerts.setVisibility(View.GONE);
                acco.setVisibility(View.GONE);
            break;
            case 2:general.setVisibility(View.GONE);
                compi.setVisibility(View.GONE);
                concerts.setVisibility(View.VISIBLE);
                acco.setVisibility(View.GONE);
                break;
            case 3:general.setVisibility(View.GONE);
                compi.setVisibility(View.GONE);
                concerts.setVisibility(View.GONE);
                acco.setVisibility(View.VISIBLE);
                break;
            default:general.setVisibility(View.GONE);
            compi.setVisibility(View.GONE);
            concerts.setVisibility(View.GONE);
                acco.setVisibility(View.GONE);
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
