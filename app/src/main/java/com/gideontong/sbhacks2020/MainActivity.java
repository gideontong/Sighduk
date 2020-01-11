package com.gideontong.sbhacks2020;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.gideontong.sbhacks2020.db.ShowContract;
import com.gideontong.sbhacks2020.db.ShowDbHelper;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ShowDbHelper mHelper;
    private ListView mShowListView;
    private ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHelper = new ShowDbHelper(this);
        mShowListView = (ListView) findViewById(R.id.list_show);
        updateUI();
    }

    // A function that updates the UI with new database updates
    private void updateUI() {
        ArrayList<String> showList = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();

        Cursor cursor = db.query(ShowContract.ShowEntry.TABLE,
                new String[]{ShowContract.ShowEntry._ID, ShowContract.ShowEntry.COL_SHOW_TITLE},
                null, null, null, null, null);
        while(cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(ShowContract.ShowEntry.COL_SHOW_TITLE);
            showList.add(cursor.getString(idx));
            Log.d(TAG, "Task was added with name " + cursor.getString(idx));
        }

        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this,
                    R.layout.item_show,
                    R.id.show_title,
                    showList);
            mShowListView.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(showList);
            mAdapter.notifyDataSetChanged();
        }

        cursor.close();
        db.close();
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
                                Log.d(TAG, "Will add task with name " + show);

                                SQLiteDatabase db = mHelper.getWritableDatabase();
                                ContentValues values = new ContentValues();

                                values.put(ShowContract.ShowEntry.COL_SHOW_TITLE, show);
                                db.insertWithOnConflict(ShowContract.ShowEntry.TABLE,
                                        null,
                                        values,
                                        SQLiteDatabase.CONFLICT_REPLACE);
                                db.close();
                                updateUI();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();


                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void deleteShow(View view) {
        View parent = (View) view.getParent();
    }
}
