package com.gideontong.sighduk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.gideontong.sighduk.API.pulledData;
import com.gideontong.sighduk.db.ShowDbHelper;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.Serializable;

public class animeInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anime_show_info);

        pulledData data = (pulledData)getIntent().getSerializableExtra("pulledData");

        final TextView animeTitle = findViewById(R.id.animeTitle);
        final TextView animeScore = findViewById(R.id.scoreValue);
        final TextView animeRank = findViewById(R.id.rankValue);
        final TextView synopsisText = findViewById(R.id.synopsisText);
        final ImageView animeImage = findViewById(R.id.animeImage);
        final Button addWatchList = findViewById(R.id.addWatchList);

        addWatchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("YO BRUH I ADDED "+animeTitle.getText());
            }
        });

        animeTitle.setText(data.getName().get(0));
        animeScore.setText(data.getScore().get(0));
        animeRank.setText(data.getRank().get(0));
        synopsisText.setText(data.getSynopsis().get(0));
        Picasso.with(this)
                .load(data.getImage_url().get(0))
                .into(animeImage);

        //Intent intent = new Intent(animeInfoActivity.this, search_item.class);
        //startActivity(intent);
    }
}
