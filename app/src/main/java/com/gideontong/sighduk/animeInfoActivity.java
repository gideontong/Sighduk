package com.gideontong.sighduk;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.gideontong.sighduk.db.ShowDbHelper;

import org.w3c.dom.Text;

import java.io.Serializable;

public class animeInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anime_show_info);

        getIntent().getSerializableExtra("pulledData");
        
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



        //Intent intent = new Intent(animeInfoActivity.this, search_item.class);
        //startActivity(intent);
    }
}
