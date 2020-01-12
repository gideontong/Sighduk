package com.gideontong.sighduk;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.gideontong.sighduk.db.ShowContract;
import com.gideontong.sighduk.db.ShowDbHelper;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivty";

    private ShowDbHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);

        mHelper = new ShowDbHelper(this);
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
}
