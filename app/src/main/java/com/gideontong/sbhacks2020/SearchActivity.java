package com.gideontong.sbhacks2020;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.gideontong.sbhacks2020.search.Networking;

public class SearchActivity extends AppCompatActivity {
    public static final String TAG = "SearchActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
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
            String result = Networking.search(query);
        } catch(Exception e) {
            String result = null;
        }
    }
}
