package com.gideontong.sighduk;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.gideontong.sighduk.API.pulledData;
import com.gideontong.sighduk.db.ShowContract;
import com.gideontong.sighduk.db.ShowDbHelper;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.Serializable;

public class animeInfoActivity extends AppCompatActivity {

    public static final String TAG = "animeInfoActivity";
    private ShowDbHelper mHelper;


    public void addToDatabase(String animeTitle, String imageURL) {
        // TextView nameBox = findViewById(R.id.show_title);
        // String name = String.valueOf(nameBox.getText());
        // new WatchlistActivity().addShowInBackground(name);

        String name = getParent().findViewById(R.id.animeTitle).toString();
        System.out.println(name+ " is the thing we're adding to sqLITE!");
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ShowContract.ShowEntry.COL_SHOW_TITLE, animeTitle);

        values.put(ShowContract.ShowEntry.COL_SHOW_IMAGE_URL, imageURL);
        Log.d(TAG, "Attempting to add image url "+imageURL);



        db.insertWithOnConflict(ShowContract.ShowEntry.TABLE,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE);
        db.close();

        // go back to WatchlistActivity view
        setContentView(R.layout.activity_search);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anime_show_info);

        mHelper = new ShowDbHelper(this);

        Bundle extras = getIntent().getExtras();
        System.out.println(extras);
        if (extras != null) {
            pulledData data = (pulledData)getIntent().getSerializableExtra("pulledData");

            //pulledData data = (pulledData)getIntent().getSerializableExtra("pulledData");

            final TextView animeTitle = findViewById(R.id.animeTitle);
            final TextView animeScore = findViewById(R.id.scoreValue);
            final TextView animeRank = findViewById(R.id.rankValue);
            final TextView synopsisText = findViewById(R.id.synopsisText);
            final ImageView animeImage = findViewById(R.id.animeImage);
            final Button addWatchList = findViewById(R.id.addWatchList);

            if (data.getTitle() != null) {
                animeTitle.setText(data.getTitle().get(0));
            } else {
                animeTitle.setText("No information was found");
            }

            if (data.getScore() != null) {
                animeScore.setText(data.getScore().get(0));
            } else {
                animeScore.setText("No information was found");
            }

            if (data.getRank() != null) {
                animeRank.setText("#"+data.getRank().get(0));
            } else {
                animeRank.setText("No information was found");
            }

            if (data.getSynopsis() != null) {
                synopsisText.setText(data.getSynopsis().get(0));
            } else {
                synopsisText.setText("No information was found");
            }

            if (data.getImage_url() != null) {
                Picasso.with(this)
                        .load(data.getImage_url().get(0))
                        .into(animeImage);
            } else {
                System.out.println("No image URL found...");
            }
            /*animeScore.setText(data.getScore().get(0));
            animeRank.setText(data.getRank().get(0));
            synopsisText.setText(data.getSynopsis().get(0));*/


            //Intent intent = new Intent(animeInfoActivity.this, search_item.class);
            //startActivity(intent);
        }
    }
}
