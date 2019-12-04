package com.iitbombay.moodi.moodindigo;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.iitbombay.moodi.moodindigo.Adapters.TypeTabPagerAdapter;
import com.iitbombay.moodi.moodindigo.EventsDatabase.DatabaseHelper;
import com.iitbombay.moodi.moodindigo.EventsDatabase.Event;

import java.util.List;

public class EventsActivity extends AppCompatActivity {

    List<Event> events;
    DatabaseHelper db;
    int day;
    Bundle bundle;
    TabLayout tabLayout;
    ViewPager viewPager;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);


                bundle = getIntent().getExtras();
        day = bundle.getInt("day");

        actionBar = getSupportActionBar();
        actionBar.setTitle("Day " + day);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.datePicker)));
        actionBar.setElevation(0);



                tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setText("Competitions"));
        tabLayout.addTab(tabLayout.newTab().setText("Concerts"));
        tabLayout.addTab(tabLayout.newTab().setText("Proshows"));
        tabLayout.addTab(tabLayout.newTab().setText("Informals"));
        tabLayout.addTab(tabLayout.newTab().setText("Workshops"));
        tabLayout.addTab(tabLayout.newTab().setText("Arts and Ideas"));
        tabLayout.setTabTextColors(ColorStateList.valueOf(Color.parseColor("#ffffff")));

        viewPager = (ViewPager) findViewById(R.id.viewpager);

        viewPager.setAdapter(new TypeTabPagerAdapter(getSupportFragmentManager(), day, EventsActivity.this));
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
}
