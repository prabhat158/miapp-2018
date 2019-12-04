package com.iitbombay.moodi.moodindigo.Fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iitbombay.moodi.moodindigo.R;

import java.util.Arrays;
import java.util.List;


public class ResultsDialogFragment extends DialogFragment {
    List<String> results;
    View view;
    Bundle bundle;
    TextView description;
    String round;
    TextView note;
    public ResultsDialogFragment() {
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
        view = inflater.inflate(R.layout.fragment_results_dialog, container, false);
        note=view.findViewById(R.id.note);
        bundle=getArguments();
        String result;
        round=bundle.getString("round");
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.register_button);
        if(round.equals("Finals" +
                "")){
            note.setText("Congratulations to all the winners!");
            results = Arrays.asList(bundle.getString("results").split(","));
            Log.e("1", results.get(0));
            int i=1;
            description = view.findViewById(R.id.description);
            result = "\n";
            for (String string : results) {
                result = result +i+". "+ string + "\n\n";
                i++;
            }
        }
        else {
            note.setText("The following names are in no particular order" );
            results = Arrays.asList(bundle.getString("results").split(","));
            Log.e("1", results.get(0));
            description = view.findViewById(R.id.description);
            result = "\n";
            for (String string : results) {
                result = result + string + "\n\n";
            }
        }
                description.setText(result);


        return view;
    }
}
