package com.gideontong.sighduk;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.gideontong.sighduk.db.SavedContract;
import com.gideontong.sighduk.db.SavedDbHelper;

import java.util.ArrayList;

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
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        Log.d(TAG, "Added a view located at " + view);

        ArrayList<EntryData> showList = new ArrayList<>();
        SQLiteDatabase db = kHelper.getReadableDatabase();

        Cursor cursor = db.query(SavedContract.SavedEntry.TABLE,
                new String[]{SavedContract.SavedEntry._ID, SavedContract.SavedEntry.COL_SHOW_TITLE},
                null, null, null, null, null);
        Cursor urlCursor = db.query(SavedContract.SavedEntry.TABLE,
                new String[]{SavedContract.SavedEntry._ID, SavedContract.SavedEntry.COL_SHOW_IMAGE_URL},
                null, null, null, null, null);

        while(cursor.moveToNext()) {
            int index = cursor.getColumnIndex(SavedContract.SavedEntry.COL_SHOW_TITLE);
            String nextName = cursor.getString(index);

            urlCursor.moveToNext();
            int urlIndex = urlCursor.getColumnIndex(SavedContract.SavedEntry.COL_SHOW_IMAGE_URL);
            String grabUri = "https://www.thetvdb.com" + urlCursor.getString(urlIndex);
            showList.add(new EntryData(nextName, grabUri));

            Log.d(TAG, "New task was added to the view with name " + nextName + " and uri " + grabUri);
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getView().findViewById(R.id.list_saved).invalidate();
    }
}
