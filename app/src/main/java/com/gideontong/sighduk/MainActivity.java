package com.gideontong.sighduk;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import com.gideontong.sighduk.API.myAnimeListAPI;
import com.gideontong.sighduk.API.pulledData;
import com.gideontong.sighduk.db.ShowContract;
import com.gideontong.sighduk.db.ShowDbHelper;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private ShowDbHelper mHelper;
    private ListView mShowListView;
    private Button testSearchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//---------------------------------------------------------------------------------------------
            new myAnimeListAPI().backgroundSearchAnime("Naruto");
            /*Intent intent = new Intent(MainActivity.this, animeInfoActivity.class);
            intent.putExtra("pulledData", animeData);
            startActivity(intent);*/

//---------------------------------------------------------------------------------------------


        mHelper = new ShowDbHelper(this);

        testSearchButton = findViewById(R.id.searchBtn);
        testSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, search_item.class);
                startActivity(intent);
            }
        });

        mShowListView = findViewById(R.id.list_show);
        updateUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
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

        HomeAdapter listAdapter = new HomeAdapter(this, showList);
        mShowListView.setAdapter(listAdapter);

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

            case R.id.action_search:
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);

            default:
                return super.onOptionsItemSelected(item);
        }
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

        updateUI();
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
            ImageView imageView = findViewById(R.id.show_image);
            imageView.setImageBitmap(result);
        }
    }
}
