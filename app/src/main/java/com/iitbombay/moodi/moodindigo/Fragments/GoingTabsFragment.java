package com.iitbombay.moodi.moodindigo.Fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iitbombay.moodi.moodindigo.Adapters.GoingTabsPagerAdapter;
import com.iitbombay.moodi.moodindigo.EventsDatabase.Event;
import com.iitbombay.moodi.moodindigo.R;
import com.iitbombay.moodi.moodindigo.SessionManager;

import java.util.List;

public class GoingTabsFragment extends Fragment {

    List<Event> events;
    Toolbar toolbar;
    SessionManager sessionManager;
    View view;
    Bundle bundle;
    TabLayout tabLayout;
    ViewPager viewPager;
    LinearLayout layout;
    TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_going_tabs, container, false);
        sessionManager=new SessionManager(getContext());
        layout=(LinearLayout) view.findViewById(R.id.layout);
        textView=(TextView) view.findViewById(R.id.please_login);
        toolbar=(Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("             My Events");
        toolbar.setTitleTextColor(getContext().getResources().getColor(R.color.colorAccent));

        if(sessionManager.getBoolean("isLoggedIn")){
            layout.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
            tabLayout = (TabLayout) view.findViewById(R.id.tabs);
            tabLayout.addTab(tabLayout.newTab().setText("Day 1"));
            tabLayout.addTab(tabLayout.newTab().setText("Day 2"));
            tabLayout.addTab(tabLayout.newTab().setText("Day 3"));
            tabLayout.addTab(tabLayout.newTab().setText("Day 4"));
            tabLayout.setTabTextColors(ColorStateList.valueOf(Color.parseColor("#ffffff")));

            viewPager = (ViewPager) view.findViewById(R.id.viewpager);


            viewPager.setAdapter(new GoingTabsPagerAdapter(getChildFragmentManager(), getContext()));
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());

                }
            });


        }
        else{
            layout.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }


        return view;
    }
}
