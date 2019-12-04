package com.iitbombay.moodi.moodindigo.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.iitbombay.moodi.moodindigo.Adapters.BottomNavPagerAdapter;
import com.iitbombay.moodi.moodindigo.R;
import com.iitbombay.moodi.moodindigo.data.NewsResponse;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by owais on 05/07/17.
 */


public class MainFragment extends Fragment {
    View view;
    ViewPager viewPager;
    //Bottom Navigation Bar
    BottomNavigationView bottomNavigationView;
    com.iitbombay.moodi.moodindigo.Fragments.UpcomingEventsFragment upcomingEventsFragment, upcomingEventsFragment2;
    MenuItem prevMenuItem;
    MapFragment mapFragment;
    DrawerLayout mDrawerLayout;
    int size;
    com.iitbombay.moodi.moodindigo.Fragments.ScheduleDaysFragment scheduleDaysFragment;
    List<NewsResponse> responses = new ArrayList<>();
    private FragmentActivity myContext;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        container.removeAllViews();

        view = inflater.inflate(R.layout.fragment_main, container, false);

        // Bottom Navigation Bar

        //Initializing viewPager
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);

        //Initializing the bottomNavigationView
        bottomNavigationView = (BottomNavigationView) view.findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_map:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.action_events:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.action_schedule:
                                viewPager.setCurrentItem(2);
                                break;
                        }
                        return false;
                    }
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {

//                if (prevMenuItem != null) {
//                    prevMenuItem.setChecked(false);
//                    if(position ==2) {
////                        FloatingActionButton fab1 = (FloatingActionButton) view.findViewById(R.id.schedule_music_button);
////                        fab1.show();
//                    }
//                    if(position == 1) {
//                        FloatingActionButton fab2 = (FloatingActionButton) view.findViewById(R.id.upcoming_music_button);
//                        fab2.show();
//                    }
//                    if(position == 0) {
////                        FloatingActionButton fab3 = (FloatingActionButton) view.findViewById(R.id.music_map_button);
////                        fab3.show();
//                    }
//                } else {

                    bottomNavigationView.getMenu().getItem(1).setChecked(false);
//                }
                Log.d("page", "onPageSelected: " + position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

       /*  //Disable ViewPager Swipe
       viewPager.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return true;
            }
        });
        */


        BottomNavPagerAdapter adapter = new BottomNavPagerAdapter(getChildFragmentManager());

        mapFragment = new MapFragment();
        upcomingEventsFragment = new com.iitbombay.moodi.moodindigo.Fragments.UpcomingEventsFragment();
        scheduleDaysFragment = new com.iitbombay.moodi.moodindigo.Fragments.ScheduleDaysFragment();

        adapter.addFragment(mapFragment);
        adapter.addFragment(upcomingEventsFragment);
        adapter.addFragment(scheduleDaysFragment);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }


        return view;
    }

    @Override
    public void onAttach(Context context) {
        myContext = (FragmentActivity) context;
        super.onAttach(context);

    }

}
