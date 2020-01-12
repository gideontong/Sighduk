package com.gideontong.sighduk;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import com.gideontong.sighduk.db.ShowDbHelper;

public class MainFragment extends Fragment {
    public static final String TAG = "MainFragment";

    private ShowDbHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);

        mHelper = new ShowDbHelper(this);
    }
}
