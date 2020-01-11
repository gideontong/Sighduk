package com.gideontong.sbhacks2020;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.gideontong.sbhacks2020.db.ShowContract;
import com.gideontong.sbhacks2020.db.ShowDbHelper;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    public static final String TAG = "SearchActivity";

    private ArrayAdapter<String> rAdapter;
    private ListView rShowListView;
    private ShowDbHelper mHelper;

    String export = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        rShowListView = (ListView) findViewById(R.id.list_results);
        mHelper = new ShowDbHelper(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void searchOnline(View view) {
        Log.d(TAG, "Search button was pressed!");
        View parent = (View) view.getParent();
        EditText searchBox = (EditText) parent.findViewById(R.id.searchText);
        String query = String.valueOf(searchBox.getText());
        try {
            Log.d(TAG, "Searching " + query);
            // Networking test = new Networking();
            // String result = test.search(query);
            new BackgroundSearch().execute(query);
            Log.d(TAG, "Result was " + export);
        } catch(Exception e) {
            Log.d(TAG, "We tried but we got " + e);
            String result = null;
        }
        Log.d(TAG, "EOL");
    }

    public void searchCallback(String string) {
        // Object obj;
        // JSONObject jo;

        ArrayList<String> resultsList = new ArrayList<>();

        try {
            Object obj = new JSONParser().parse(string);
            JSONObject jo = (JSONObject) obj;
            JSONArray data = (JSONArray) jo.get("data");

            // resultsList = new ArrayList<>();

            for(Object nextNext: data) {
                JSONObject nextObject = (JSONObject) nextNext;
                String name = (String) nextObject.get("seriesName");
                Log.d(TAG, "Found a name " + name);
                resultsList.add(name);
            }
        } catch (Exception e) {
            Log.d(TAG, "Failed to draw table " + e);
            //do nothing
        }

        if (rAdapter == null) {
            rAdapter = new ArrayAdapter<>(this,
                    R.layout.item_search_entry,
                    R.id.show_title,
                    resultsList);
            rShowListView.setAdapter(rAdapter);
        } else {
            rAdapter.clear();
            rAdapter.addAll(resultsList);
            rAdapter.notifyDataSetChanged();
        }
    }

    public void addToDatabase(View view) {
        TextView nameBox = findViewById(R.id.show_title);
        String name = String.valueOf(nameBox.getText());
        // new MainActivity().addShowInBackground(name);

        SQLiteDatabase db = mHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ShowContract.ShowEntry.COL_SHOW_TITLE, name);

        db.insertWithOnConflict(ShowContract.ShowEntry.TABLE,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    private class BackgroundSearch extends AsyncTask<String, Void, String> {
        private static final String TAG = "BackgroundSearch";
        private static final String DOMAIN = "https://api.thetvdb.com";
        private static final String TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NzkzNDE1NTgsImlkIjoiUmlkZ2xpbmdDYXNpbm8iLCJvcmlnX2lhdCI6MTU3ODczNjc1OH0.mMGjnkr8b052bOqk0x0hZy8O1HlRhqCUbMVaVV05OqjsLqW44p3FvufU8yROaX2AlItnT-kVXQrupNmv26ybSw2gUJGDLLfp3W6tXliZKFZbzrcG5yCeqeyClM3o1RguyMVFqjmO7iOt44r7oiJNb0Ul3vtEIR1GpFWrjEefvkkGCdLHMRL7_VD0L5R2gnkNi3fsbsK4Bp4HGYRXAiy5cU4hru6iDADZVv1bL1pJFUbbi2cBqI1ZmKrPCdviniYqIOSoHUHlGMNhVgBxYS_LCX7_eh_3aEREX4nn7RPBBVf-nmt8d6iKs8Dmc47hpnnOPtXaekkeKbbzPqtfuL3YEQ";

        @Override
        protected String doInBackground(String... queries) {
            Log.d(TAG, "Started background download of search query " + queries[0]);
            URL route;
            try {
                route = new URL(DOMAIN + "/search/series" + "?name=" + queries[0]);
            } catch (Exception e) {
                route = null;
            }
            String readLine = null;
            HttpURLConnection connection;
            try {
                connection = (HttpURLConnection) route.openConnection();
            } catch (Exception e) {
                connection = null;
            }
            Log.d(TAG, "Hello a connection was opened");

            connection.setConnectTimeout(10 * 1000);
            connection.setReadTimeout(10 * 1000);
            try {
                connection.setRequestMethod("GET");
            } catch (Exception e) {
                Log.d(TAG, "Exception occurred with request method " + e);
                // do nothing
            }
            connection.setRequestProperty("Authorization", "Bearer " + TOKEN);
            // connection.setRequestProperty("name", queries[0]); // set userId its a sample here
            Log.d(TAG, "I set some cool stuff");

            int responseCode;
            try {
                responseCode = connection.getResponseCode();
            } catch (Exception e) {
                Log.d(TAG, "A exception was generated with exception " + e);
                responseCode = -1;
            }
            Log.d(TAG, "Response code " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in;
                try {
                    in = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                } catch (Exception e) {
                    in = null;
                }
                StringBuffer response = new StringBuffer();
                try {
                    while ((readLine = in.readLine()) != null) {
                        response.append(readLine);
                    }
                } catch (Exception e) {
                    // do nothing
                }
                try {
                    in.close();
                } catch (Exception e) {
                    // do nothing
                }

                Log.d(TAG, "Result: " + response.toString());
                return response.toString();
            } else {
                Log.d(TAG, "GET request failed with response code " + responseCode);
                return null;
            }
        }

        // This is called when doInBackground() is finished
        @Override
        protected void onPostExecute(String result) {
            export = result;
            searchCallback(result);
            // new SearchActivity().searchCallback(result);
        }
    }
}