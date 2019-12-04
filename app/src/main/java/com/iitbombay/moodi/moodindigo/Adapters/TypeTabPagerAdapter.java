package com.iitbombay.moodi.moodindigo.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.iitbombay.moodi.moodindigo.EventListFragment;

/**
 * Created by mrunz on 9/12/17.
 */

public class TypeTabPagerAdapter extends FragmentPagerAdapter {
    int day;
    Context context;

    public TypeTabPagerAdapter(FragmentManager fm, int day, Context context) {
        super(fm);

        this.day = day;
        this.context = context;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Fragment getItem(int position) {
        EventListFragment eventListFragment;
        Bundle bundle;

        switch (position) {
            case 0:
                eventListFragment = new EventListFragment();
                bundle = new Bundle();
                bundle.putString("type", "Competitions");
                bundle.putInt("day", day);
                eventListFragment.setArguments(bundle);
                return eventListFragment;
            case 1:
                eventListFragment = new EventListFragment();
                bundle = new Bundle();
                bundle.putString("type", "Concerts");
                bundle.putInt("day", day);
                eventListFragment.setArguments(bundle);
                return eventListFragment;
            case 2:
                eventListFragment = new EventListFragment();
                bundle = new Bundle();
                bundle.putString("type", "Proshows");
                bundle.putInt("day", day);
                eventListFragment.setArguments(bundle);
                return eventListFragment;
            case 3:
                eventListFragment = new EventListFragment();
                bundle = new Bundle();
                bundle.putString("type", "Informals");
                bundle.putInt("day", day);
                eventListFragment.setArguments(bundle);
                return eventListFragment;
            case 4:
                eventListFragment = new EventListFragment();
                bundle = new Bundle();
                bundle.putString("type", "Workshops");
                bundle.putInt("day", day);
                eventListFragment.setArguments(bundle);
                return eventListFragment;
            case 5:
                eventListFragment = new EventListFragment();
                bundle = new Bundle();
                bundle.putString("type", "Arts and Ideas");
                bundle.putInt("day", day);
                eventListFragment.setArguments(bundle);
                return eventListFragment;
            default:
                return null;
        }
    }
}