package com.gideontong.sighduk;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gideontong.sighduk.db.ShowContract;
import com.gideontong.sighduk.db.ShowDbHelper;

import java.util.ArrayList;

public class WatchlistFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "WatchlistFragment";

    private ShowDbHelper mHelper;
    private ListView mShowListView;

    public WatchlistFragment() {
        // mHelper = new ShowDbHelper(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // RelativeLayout content = (RelativeLayout) rootView
        // setContentView(R.layout.activity_watchlist);

        mHelper = new ShowDbHelper(getContext());

        // mShowListView = getView().findViewById(R.id.list_show);
        // Log.d(TAG, "Attempting to find view, found " + mShowListView);
        // updateUI();
    }

    @Override
    public void onResume() {
        super.onResume();
        getView().findViewById(R.id.list_show).invalidate();
        // updateUI();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View viewer = inflater.inflate(R.layout.fragment_watchlist, container, false);
        Log.d(TAG, "Setting created view, list view is at " + mShowListView);

        ArrayList<EntryData> showList = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();

        Cursor cursor = db.query(ShowContract.ShowEntry.TABLE,
                new String[]{ShowContract.ShowEntry._ID, ShowContract.ShowEntry.COL_SHOW_TITLE},
                null, null, null, null, null);
        Cursor urlCursor = db.query(ShowContract.ShowEntry.TABLE,
                new String[]{ShowContract.ShowEntry._ID, ShowContract.ShowEntry.COL_SHOW_IMAGE_URL},
                null, null, null, null, null);
        while(cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(ShowContract.ShowEntry.COL_SHOW_TITLE);
            String nextName = cursor.getString(idx);

            urlCursor.moveToNext();
            int uriIdx = urlCursor.getColumnIndex(ShowContract.ShowEntry.COL_SHOW_IMAGE_URL);
            String grabUrl = "https://www.thetvdb.com" + urlCursor.getString(uriIdx);

            showList.add(new EntryData(nextName, grabUrl));

            Log.d(TAG, "Task was added with name " + cursor.getString(idx));
        }

        db.close();

        HomeAdapter adapter = new HomeAdapter(
                getActivity().getApplicationContext(),
                showList
        );

        ListView listInfo = (ListView) viewer.findViewById(R.id.list_show);
        listInfo.setAdapter(adapter);

        return viewer;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {    }

    @Override
    public void onClick(View view) {
        /*
        Log.d(TAG, "A button press was detected!");
        switch(view.getId()) {
            case R.id.show_delete:
                Log.d(TAG, "Delete action was triggered!");
                deleteShow(view);
                break;
        }
         */
    }
}