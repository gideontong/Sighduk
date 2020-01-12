package com.gideontong.sighduk;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.gideontong.sighduk.db.ShowContract;
import com.gideontong.sighduk.db.ShowDbHelper;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    CustomFragmentPagerAdapter myFragmentPagerAdapter;
    private ShowDbHelper mHelper;
    private ListView mShowListView;

    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        setPagerAdapter();

        // new myAnimeListAPI(this).backgroundSearchAnime("Naruto");

        setTabLayout();
    }

    private void setPagerAdapter(){
        myFragmentPagerAdapter = new CustomFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(myFragmentPagerAdapter);
    }

    private void setTabLayout() {
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("Watchlist");
        tabLayout.getTabAt(1).setText("History");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()) {
            case R.id.action_add_show:
                Log.d(TAG, "Add a new show button was pressed!");

                final EditText taskEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add a new TV show or anime")
                        .setMessage("What's the name?")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String show = String.valueOf(taskEditText.getText());

                                SQLiteDatabase db = mHelper.getWritableDatabase();
                                ContentValues values = new ContentValues();

                                values.put(ShowContract.ShowEntry.COL_SHOW_TITLE, show);
                                db.insertWithOnConflict(ShowContract.ShowEntry.TABLE,
                                        null,
                                        values,
                                        SQLiteDatabase.CONFLICT_REPLACE);
                                db.close();
                                // updateUI();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();

                return true;

            case R.id.action_search:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
                return true;

            case R.id.searchBtn:
                Intent searchIntent = new Intent(this, LocalSearchActivity.class);
                startActivity(searchIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
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
     */
}
