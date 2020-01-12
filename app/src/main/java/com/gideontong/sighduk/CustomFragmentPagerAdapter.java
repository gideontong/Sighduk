package com.gideontong.sighduk;

import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.gideontong.sighduk.db.ShowContract;
import com.gideontong.sighduk.db.ShowDbHelper;

public class CustomFragmentPagerAdapter extends FragmentPagerAdapter {
    private ShowDbHelper mHelper;

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

    /*
    public void deleteShow(View view) {
        View parent = (View) view.getParent();
        TextView showTextView = (TextView) parent.findViewById(R.id.show_title);
        String show = String.valueOf(showTextView.getText());
        SQLiteDatabase db = mHelper.getWritableDatabase();

        db.delete(ShowContract.ShowEntry.TABLE,
                ShowContract.ShowEntry.COL_SHOW_TITLE + " = ?",
                new String[]{show});
        db.close();

        // updateUI();
    }
     */
}