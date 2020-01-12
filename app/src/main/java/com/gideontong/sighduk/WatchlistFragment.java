package com.gideontong.sighduk;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gideontong.sighduk.db.ShowContract;
import com.gideontong.sighduk.db.ShowDbHelper;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class WatchlistFragment extends Fragment {

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
        // updateUI();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View viewer = inflater.inflate(R.layout.fragment_watchlist, container, false);
        // mShowListView = (ListView) viewer.findViewById(R.id.list_show);
        // mShowListView.addView();
        // mShowListView = viewer.findViewById(R.id.list_show);
        Log.d(TAG, "Setting created view, list view is at " + mShowListView);
        return viewer;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        // mShowListView = getView().findViewById(R.id.list_show);
        // Log.d(TAG, "Setting created view, list view is at " + mShowListView);
        // updateUI();
    }

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

    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            }catch (Exception e){
                Log.d(TAG,e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // ImageView imageView = findViewById(R.id.show_image);
            // imageView.setImageBitmap(result);
        }
    }

    // A function that updates the UI with new database updates
    private void updateUI() {
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

        HomeAdapter listAdapter = new HomeAdapter(new MainActivity(), showList);
        Log.d(TAG, "Location of mShowListView is " + mShowListView);
        mShowListView.setAdapter(listAdapter);

        cursor.close();
        db.close();
    }
}