package com.iitbombay.moodi.moodindigo.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.iitbombay.moodi.moodindigo.Fragments.GoingEventsFragment;

/**
 * Created by mrunz on 9/12/17.
 */

public class GoingTabsPagerAdapter extends FragmentPagerAdapter {

    Context context;

    public GoingTabsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);

        this.context = context;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Fragment getItem(int position) {
        GoingEventsFragment goingEventsFragment;
        Bundle bundle;

        switch (position) {
            case 0:
                goingEventsFragment = new GoingEventsFragment();
                bundle = new Bundle();
                
                bundle.putInt("day", 1);
                goingEventsFragment.setArguments(bundle);
                return goingEventsFragment;
            case 1:
                goingEventsFragment = new GoingEventsFragment();
                bundle = new Bundle();
            
                bundle.putInt("day", 2);
                goingEventsFragment.setArguments(bundle);
                return goingEventsFragment;
            case 2:
                goingEventsFragment = new GoingEventsFragment();
                bundle = new Bundle();
                
                bundle.putInt("day", 3);
                goingEventsFragment.setArguments(bundle);
                return goingEventsFragment;
            case 3:
                goingEventsFragment = new GoingEventsFragment();
                bundle = new Bundle();
                
                bundle.putInt("day", 4);
                goingEventsFragment.setArguments(bundle);
                return goingEventsFragment;
            
            default:
                return null;
        }
    }
}