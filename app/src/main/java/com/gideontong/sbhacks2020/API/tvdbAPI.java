package com.gideontong.sbhacks2020.API;

import com.gideontong.sbhacks2020.BuildConfig;
import com.uwetrottmann.thetvdb.TheTvdb;
import com.uwetrottmann.thetvdb.entities.Series;
import com.uwetrottmann.thetvdb.entities.SeriesResponse;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.io.InputStream;

import retrofit2.Response;

public class tvdbAPI {


    public static void main(String args[]) throws IOException {

        TheTvdb theTvdb = new TheTvdb(BuildConfig.myAPI_KEY);
        try {
            Response<SeriesResponse> response = theTvdb.series()
                    .series(83462, "en")
                    .execute();
            if (response.isSuccessful()) {
                Series series = response.body().data;
                System.out.println(series.seriesName + " is awesome!");
            }
        } catch (Exception e) {
            // see execute() javadoc
        }

    }
}
