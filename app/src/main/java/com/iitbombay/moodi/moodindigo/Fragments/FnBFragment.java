package com.iitbombay.moodi.moodindigo.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iitbombay.moodi.moodindigo.R;


public class FnBFragment extends Fragment {

    View view;
    public FnBFragment() {
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
        container.removeAllViews();
        view=inflater.inflate(R.layout.fragment_fn_b, container, false);
        view.findViewById(R.id.faasos_logo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getContext().getPackageManager().getLaunchIntentForPackage("com.done.faasos");
                if (intent != null) {
                    // We found the activity now start the activity
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    // Bring user to the market or let them choose an app?
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setData(Uri.parse("market://details?id=" + "com.done.faasos"));
                    startActivity(intent);
                }
            }
        });
        view.findViewById(R.id.box8_logo).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getContext().getPackageManager().getLaunchIntentForPackage("com.poncho");
                if (intent != null) {
                    // We found the activity now start the activity
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    // Bring user to the market or let them choose an app?
                    intent = new Intent(Intent.ACTION_VIEW);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setData(Uri.parse("market://details?id=" + "com.poncho"));
                    startActivity(intent);
                }
            }
        });
        view.findViewById(R.id.foodOfMumbai_logo).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri webpage = Uri.parse("https://foodofmumbai.wordpress.com/");
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webIntent);
            }
        });
        view.findViewById(R.id.thefoodpunch_logo).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri webpage2 = Uri.parse("https://thefoodpunched.wordpress.com/");
                Intent webIntent2 = new Intent(Intent.ACTION_VIEW, webpage2);
                startActivity(webIntent2);
            }
        });


        return view;
    }

}
