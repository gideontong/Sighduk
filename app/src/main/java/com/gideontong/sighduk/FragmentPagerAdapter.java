package com.gideontong.sighduk;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {


    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new WatchlistFragment();
            case 1:
                return new HistoryFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}