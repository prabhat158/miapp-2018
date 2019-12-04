package com.iitbombay.moodi.moodindigo.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iitbombay.moodi.moodindigo.EasterEggsActivity;
import com.iitbombay.moodi.moodindigo.R;


public class DevelopersFragment extends Fragment implements View.OnClickListener {
    View view;
    Intent intent;
    LinearLayout layout;
    TextView hp, tbbt, hunger, house, sherlock, naruto, got,myhero,friends;
    int count_tap =0;

    public DevelopersFragment() {
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
        view = inflater.inflate(R.layout.fragment_developers, container, false);
        layout = view.findViewById(R.id.layout_developers);
        tbbt = ((TextView) layout.findViewWithTag("tbbt"));
        tbbt.setAnimation(AnimationUtils.loadAnimation(getContext(),
                R.anim.blink));
        hp = (TextView) layout.findViewWithTag("hp");
        hp.setAnimation(AnimationUtils.loadAnimation(getContext(),
                R.anim.blink));
        hunger = (TextView) layout.findViewWithTag("hunger");
        hunger.setAnimation(AnimationUtils.loadAnimation(getContext(),
                R.anim.blink));
        house = (TextView) layout.findViewWithTag("house");
        house.setAnimation(AnimationUtils.loadAnimation(getContext(),
                R.anim.blink));
        naruto = (TextView) layout.findViewWithTag("naruto");
        naruto.setAnimation(AnimationUtils.loadAnimation(getContext(),
                R.anim.blink));
        sherlock = (TextView) layout.findViewWithTag("sherlock");
        sherlock.setAnimation(AnimationUtils.loadAnimation(getContext(),
                R.anim.blink));
        got = (TextView) layout.findViewWithTag("got");
        got.setAnimation(AnimationUtils.loadAnimation(getContext(),
                R.anim.blink));
        myhero = (TextView) layout.findViewWithTag("myhero");
        myhero.setAnimation(AnimationUtils.loadAnimation(getContext(),
                R.anim.blink));
        friends = (TextView) layout.findViewWithTag("friends");
        friends.setAnimation(AnimationUtils.loadAnimation(getContext(),
                R.anim.blink));

        tbbt.setOnClickListener(this);
        hp.setOnClickListener(this);
        sherlock.setOnClickListener(this);
        hunger.setOnClickListener(this);
        got.setOnClickListener(this);
        naruto.setOnClickListener(this);
        house.setOnClickListener(this);
        myhero.setOnClickListener(this);
        friends.setOnClickListener(this);


        return view;
    }


    @Override
    public void onClick(View v) {
        intent = new Intent(getContext(), EasterEggsActivity.class);
        intent.putExtra("series", v.getTag().toString());
        TextView email = (TextView) v;
        intent.putExtra("email", email.getText());
        startActivity(intent);

    }
}

