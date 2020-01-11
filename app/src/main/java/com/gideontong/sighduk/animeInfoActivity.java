package com.gideontong.sighduk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.gideontong.sighduk.db.ShowDbHelper;

import org.w3c.dom.Text;

public class animeInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anime_show_info);

        TextView animeTitle = findViewById(R.id.animeTitle);
        TextView animeScore = findViewById(R.id.scoreValue);
        TextView animeRank = findViewById(R.id.rankValue);
        TextView synopsisText = findViewById(R.id.synopsisText);
        ImageView animeImage = findViewById(R.id.animeImage);
        Button addWatchList = findViewById(R.id.addWatchList);

        Intent intent = new Intent(animeInfoActivity.this, search_item.class);
        startActivity(intent);
    }
}
