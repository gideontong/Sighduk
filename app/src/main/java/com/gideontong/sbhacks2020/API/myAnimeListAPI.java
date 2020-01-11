package com.gideontong.sbhacks2020.API;

import org.apache.http.auth.AuthenticationException;


import com.gideontong.sbhacks2020.BuildConfig;

import java.io.IOException;
import java.util.HashMap;
import java.util.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class myAnimeListAPI {

    private static HashMap<String, Integer> AnimeGenres = new HashMap<>();

    public static void searchAnime(String animeName, int limit) throws IOException{
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.jikan.moe/v3/search/anime?q="+animeName+"&limit=16")
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println(response.body().string());
            //return response.body().string();
        }
    }

    public static void searchGenre(String genre, int limit) throws IOException{
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.jikan.moe/v3/search/genre?q="+genre+"&limit=16")
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println(response.body().string());
            //return response.body().string();
        }
    }

    public static void main(String args[]) throws IOException {

        



    }
}
