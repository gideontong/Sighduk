package com.gideontong.sighduk;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.gideontong.sighduk.db.ShowDbHelper;

public class MainFragment extends AppCompatActivity {
    public static final String TAG = "MainActivty";

    private ShowDbHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);

        mHelper = new ShowDbHelper(this);
    }
}
