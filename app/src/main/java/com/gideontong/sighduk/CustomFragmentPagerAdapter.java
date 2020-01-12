package com.gideontong.sighduk;

import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.gideontong.sighduk.db.ShowContract;

public class CustomFragmentPagerAdapter extends FragmentPagerAdapter {
    // private ShowDbHelper mHelper;

    public CustomFragmentPagerAdapter(FragmentManager fm) {
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