package com.iitbombay.moodi.moodindigo.Fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iitbombay.moodi.moodindigo.R;


public class DetailsDialogFragment extends DialogFragment {
    View view;
    Bundle bundle;
    String title;
    String description;
    TextView tv_description;
    TextView tv_name;

    public DetailsDialogFragment() {
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
        view = inflater.inflate(R.layout.fragment_details_dialog, container, false);
        bundle = getArguments();
        title = bundle.getString("title");
        description = bundle.getString("description");
        tv_description = (TextView) view.findViewById(R.id.description);
        tv_name = (TextView) view.findViewById(R.id.name);
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.register_button);
        tv_name.setText(title);
        tv_description.setText("\n" + description + "\n");
        return view;
    }
}
