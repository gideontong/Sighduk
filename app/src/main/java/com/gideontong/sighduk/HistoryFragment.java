package com.gideontong.sighduk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.gideontong.sighduk.db.SavedDbHelper;

public class HistoryFragment extends Fragment {

    public static final String TAG = "HistoryFragment";

    private SavedDbHelper kHelper;
    private ListView kShowListView;

    public HistoryFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        kHelper = new SavedDbHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        // getView()
    }
}
