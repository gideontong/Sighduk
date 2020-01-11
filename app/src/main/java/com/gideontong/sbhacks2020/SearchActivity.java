package com.gideontong.sbhacks2020;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.gideontong.sbhacks2020.search.Networking;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.Iterator;

public class SearchActivity extends AppCompatActivity {
    public static final String TAG = "SearchActivity";

    private ArrayAdapter<String> rAdapter;
    private ListView rShowListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        rShowListView = (ListView) findViewById(R.id.list_results);
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
            Networking test = new Networking();
            String result = test.search(query);
            Log.d(TAG, "Result was " + result);
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

            for(int i = 0; i < data.length(); i++) {
                JSONObject nextObject = data.getJSONObject(i);
                String name = (String) nextObject.get("seriesName");
                Log.d(TAG, "Found a name " + name);
                resultsList.add(name);
            }
        } catch (Exception e) {
            //do nothing
        }

        if (rAdapter == null) {
            rAdapter = new ArrayAdapter<>(this,
                    R.layout.item_show,
                    R.id.show_title,
                    resultsList);
            rShowListView.setAdapter(rAdapter);
        } else {
            rAdapter.clear();
            rAdapter.addAll(resultsList);
            rAdapter.notifyDataSetChanged();
        }
    }
}