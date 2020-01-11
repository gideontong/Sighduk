package com.gideontong.sbhacks2020.API;

import java.io.IOException;
import java.util.HashMap;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class myAnimeListAPI {

    private static HashMap<String, Integer> AnimeGenres = new HashMap<>();
    private static HashMap<String, Integer> MangaGenres = new HashMap<>();

    public static String searchAnime(String animeName, int limit, int page) throws IOException{

        if (animeName == null || animeName == "") return null;
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.jikan.moe/v3/search/anime?q="+animeName+"&limit=30")
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println(response.body().string());
            return response.body().string();
        }
    }

    public static String searchGenre(String genre, String type, int limit, int page) throws IOException{

        if (type != "anime" && type != "manga") return null;
        if (!AnimeGenres.containsKey(genre) && !MangaGenres.containsKey(genre)) return null;

        int id = (AnimeGenres.containsKey(genre)) ? AnimeGenres.get(genre) : MangaGenres.get(genre);

        OkHttpClient client = new OkHttpClient();


        Request request = new Request.Builder()
                .url("https://api.jikan.moe/v3/search/genre?q="+id+"&limit=30")
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println(response.body().string());
            return response.body().string();
        }
    }

    public static String searchPeople(String person, int limit, int page) throws IOException{
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.jikan.moe/v3/search/people?q="+person+"&limit=10")
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println(response.body().string());
            return response.body().string();
        }
    }

    public static String topItems(String type, int page) throws IOException{
        OkHttpClient client = new OkHttpClient();
        page = 1;

        Request request = new Request.Builder()
                .url("https://api.jikan.moe/v3/top/"+type+"/"+page)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println(response.body().string());
            return response.body().string();
        }
    }
    public static void main(String args[]) throws IOException {

        AnimeGenres.put("Action", 1); AnimeGenres.put("Martial Arts", 17); AnimeGenres.put("Yaoi", 33);
        AnimeGenres.put("Adventure", 2); AnimeGenres.put("Mecha", 18); AnimeGenres.put("Yuri", 34);
        AnimeGenres.put("Cars", 3); AnimeGenres.put("Music", 19); AnimeGenres.put("Harem", 35);
        AnimeGenres.put("Comedy", 4); AnimeGenres.put("Parody", 20); AnimeGenres.put("Slice of Life", 36);
        AnimeGenres.put("Dementia", 5); AnimeGenres.put("Samurai", 21); AnimeGenres.put("Supernatural", 37);
        AnimeGenres.put("Demons", 6); AnimeGenres.put("Romance", 22); AnimeGenres.put("Military", 38);
        AnimeGenres.put("Mystery", 7); AnimeGenres.put("School", 23); AnimeGenres.put("Police", 39);
        AnimeGenres.put("Drama", 8); AnimeGenres.put("Sci Fi", 24); AnimeGenres.put("Psychological", 40);
        AnimeGenres.put("Ecchi", 9); AnimeGenres.put("Shoujo", 25); AnimeGenres.put("Thriller", 41);
        AnimeGenres.put("Fantasy", 10); AnimeGenres.put("Shoujo Ai", 26); AnimeGenres.put("Seinen", 42);
        AnimeGenres.put("Game", 11); AnimeGenres.put("Shounen", 27); AnimeGenres.put("Josei", 43);
        AnimeGenres.put("Hentai", 12); AnimeGenres.put("Shounen Ai", 28);
        AnimeGenres.put("Historical", 13); AnimeGenres.put("Space", 29);
        AnimeGenres.put("Horror", 14); AnimeGenres.put("Sports", 30);
        AnimeGenres.put("Kids", 15); AnimeGenres.put("Super Power", 31);
        AnimeGenres.put("Magic", 16); AnimeGenres.put("Vampire", 32);

        MangaGenres.put("Action", 1); MangaGenres.put("Martial Arts", 17); MangaGenres.put("Yaoi", 33);
        MangaGenres.put("Adventure", 2); MangaGenres.put("Mecha", 18); MangaGenres.put("Yuri", 34);
        MangaGenres.put("Cars", 3); MangaGenres.put("Music", 19); MangaGenres.put("Harem", 35);
        MangaGenres.put("Comedy", 4); MangaGenres.put("Parody", 20); MangaGenres.put("Slice of Life", 36);
        MangaGenres.put("Dementia", 5); MangaGenres.put("Samurai", 21); MangaGenres.put("Supernatural", 37);
        MangaGenres.put("Demons", 6); MangaGenres.put("Romance", 22); MangaGenres.put("Military", 38);
        MangaGenres.put("Mystery", 7); MangaGenres.put("School", 23); MangaGenres.put("Police", 39);
        MangaGenres.put("Drama", 8); MangaGenres.put("Sci Fi", 24); MangaGenres.put("Psychological", 40);
        MangaGenres.put("Ecchi", 9); MangaGenres.put("Shoujo", 25); MangaGenres.put("Seinen", 41);
        MangaGenres.put("Fantasy", 10); MangaGenres.put("Shoujo Ai", 26); MangaGenres.put("Josei", 42);
        MangaGenres.put("Game", 11); MangaGenres.put("Shounen", 27); MangaGenres.put("Doujinshi", 43);
        MangaGenres.put("Hentai", 12); MangaGenres.put("Shounen Ai", 28); MangaGenres.put("Gender Bender", 44);
        MangaGenres.put("Historical", 13); MangaGenres.put("Space", 29); MangaGenres.put("Thriller", 45);
        MangaGenres.put("Horror", 14); MangaGenres.put("Sports", 30);
        MangaGenres.put("Kids", 15); MangaGenres.put("Super Power", 31);
        MangaGenres.put("Magic", 16); MangaGenres.put("Vampire", 32);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.jikan.moe/v3/top/"+"anime")
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println(response.body().string());
            //return response.body().string();
        }



    }
}
