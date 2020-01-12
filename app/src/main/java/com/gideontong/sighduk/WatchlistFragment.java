package com.gideontong.sighduk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.gideontong.sighduk.db.ShowDbHelper;

public class WatchlistFragment extends Fragment {

    private static final String TAG = "WatchlistFragment";

    private ShowDbHelper mHelper;
    private ListView mShowListView;

    public WatchlistFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);

        mHelper = new ShowDbHelper(this);

        mShowListView = findViewById(R.id.list_show);
        updateUI();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_watchlist, container, false);
    }
}