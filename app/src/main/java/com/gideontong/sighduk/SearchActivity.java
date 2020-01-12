package com.gideontong.sighduk;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gideontong.sighduk.API.myAnimeListAPI;
import com.gideontong.sighduk.API.pulledData;
import com.gideontong.sighduk.db.ShowContract;
import com.gideontong.sighduk.db.ShowDbHelper;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchActivity extends AppCompatActivity {
    public static final String TAG = "SearchActivity";

    private ArrayAdapter<String> rAdapter;
    private ListView rShowListView;
    private ShowDbHelper mHelper;


    boolean animeSearch = false;
    ArrayList<String> resultsList;
    pulledData data;
    ArrayList<String> uriList;

    String export = "";
    ArrayList<String> items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        rShowListView = (ListView) findViewById(R.id.list_results);

        mHelper = new ShowDbHelper(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return super.onCreateOptionsMenu(menu);
    }



    public void searchAnime(View view) {

        animeSearch = true;
        Log.d(TAG, "Search anime button was pressed!");
        View parent = (View) view.getParent();
        EditText searchBox =  findViewById(R.id.searchText);
        String query = String.valueOf(searchBox.getText());
        try {
            Log.d(TAG, "Searching " + query);
            // Networking test = new Networking();
            // String result = test.search(query);
            //System.out.println(this);
            new searchAnimeAsync().execute(query);
        } catch(Exception e) {
            Log.d(TAG, "We tried but we got " + e);
            String result = null;
        }
        Log.d(TAG, "EOL");
    }

    public void onItemClick(View v){
        System.out.println("CLICK");

        System.out.println("Getting information on: " +((TextView)findViewById(v.getId())).getText());
        new myAnimeListAPI(v.getContext()).backgroundSearchAnime(((TextView)findViewById(v.getId())).getText().toString());

    }

    public void searchOnline(View view) {
        animeSearch = false;
        Log.d(TAG, "Search button was pressed!");
        View parent = (View) view.getParent();
        EditText searchBox = (EditText) findViewById(R.id.searchText);
        String query = String.valueOf(searchBox.getText());
        try {
            Log.d(TAG, "Searching " + query);
            // Networking test = new Networking();
            // String result = test.search(query);
            new BackgroundSearch().execute(query);
            Log.d(TAG, "Result was " + export);
        } catch(Exception e) {
            Log.d(TAG, "We tried but we got " + e);
            String result = null;
        }
        Log.d(TAG, "EOL");
    }

    public void animeCallBack(pulledData data){
        this.data = data;
        if (rAdapter == null) {
            rAdapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1,
                    data.getTitle());

            //Button button = (Button)findViewById(R.id.show_title);


        } else {
            rAdapter.clear();

            rAdapter.addAll(data.getTitle());

            rAdapter.notifyDataSetChanged();
        }
    }

    public void searchCallback(String string) {
        // Object obj;
        // JSONObject jo;

        resultsList = new ArrayList<>();
        uriList = new ArrayList<>();

        try {
            Object obj = new JSONParser().parse(string);
            JSONObject jo = (JSONObject) obj;
            JSONArray data = (JSONArray) jo.get("data");

            // resultsList = new ArrayList<>();

            for(Object nextNext: data) {
                JSONObject nextObject = (JSONObject) nextNext;
                String name = (String) nextObject.get("seriesName");
                String uri = (String) nextObject.get("banner");
                Log.d(TAG, "Found a name " + name + " with uri" + uri);
                resultsList.add(name);
                uriList.add(uri);
            }
        } catch (Exception e) {
            Log.d(TAG, "Failed to draw table " + e);
            //do nothing
        }

        if (rAdapter == null) {
            rAdapter = new ArrayAdapter<>(this,
                    R.layout.item_search_entry,
                    R.id.show_title,
                    resultsList);

            rShowListView.setAdapter(rAdapter);



        } else {
            rAdapter.clear();
            rAdapter.addAll(resultsList);
            rAdapter.notifyDataSetChanged();
        }
    }

    public void addToDatabase(View view) {
        // TextView nameBox = findViewById(R.id.show_title);
        // String name = String.valueOf(nameBox.getText());
        // new WatchlistActivity().addShowInBackground(name);

        RelativeLayout row = (RelativeLayout)view.getParent();
        TextView child = (TextView)row.getChildAt(0);
        String name = child.getText().toString();

        SQLiteDatabase db = mHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ShowContract.ShowEntry.COL_SHOW_TITLE, name);
        if (!animeSearch) {
            int i = resultsList.indexOf(name);
            values.put(ShowContract.ShowEntry.COL_SHOW_IMAGE_URL, uriList.get(i));
            Log.d(TAG, "Attempting to add uri " + uriList.get(i));
        }
        else if (animeSearch){
            int i = data.find(name);
            values.put(ShowContract.ShowEntry.COL_SHOW_IMAGE_URL, data.getImage_url().get(i));
            Log.d(TAG, "Attempting to add image url "+data.getImage_url().get(i));
        }



        db.insertWithOnConflict(ShowContract.ShowEntry.TABLE,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE);
        db.close();

        // go back to WatchlistActivity view
        setContentView(R.layout.activity_search);
        finish();
    }


    static JSONParser parser = new JSONParser();

    private View.OnClickListener buttonListener = new View.OnClickListener() {
        public void onClick(View v) {
            System.out.println("Getting information on: " +((TextView)findViewById(v.getId())).getText());
            new myAnimeListAPI(v.getContext()).backgroundSearchAnime(((TextView)findViewById(v.getId())).getText().toString());
        }
    };



    private class searchAnimeAsync extends AsyncTask<String, Void, pulledData> {

        @Override
        protected pulledData doInBackground(String... queries) {

            String animeName = queries[0];
            System.out.println("Searching for...! "+animeName);
            if (animeName == null || animeName == "") return null;
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://api.jikan.moe/v3/search/anime?q=" + animeName + "&limit=30")
                    .get()
                    .build();

            try (Response response = client.newCall(request).execute()) {
                //System.out.println(response.body().string());
                JSONObject json = (JSONObject) parser.parse(response.body().string());
                JSONArray results = ((JSONArray) json.get("results"));
                ArrayList<String> title = new ArrayList<>();
                ArrayList<String> image_url = new ArrayList<>();
                ArrayList<String> url = new ArrayList<>();
                ArrayList<String> synopsis = new ArrayList<>();
                ArrayList<Long> episodes = new ArrayList<>();
                ArrayList<String> score = new ArrayList<>();
                ArrayList<String> rank = new ArrayList<>();
                ArrayList<String> id = new ArrayList<>();

                for (int i = 0; i < results.size(); i++) {
                    title.add(i, ((JSONObject) results.toArray()[i]).get("title").toString());
                    image_url.add(i, ((JSONObject) results.toArray()[i]).get("image_url").toString());
                    url.add(i, ((JSONObject) results.toArray()[i]).get("url").toString());
                    synopsis.add(i, ((JSONObject) results.toArray()[i]).get("synopsis").toString());
                    episodes.add(i, ((Long) ((JSONObject) results.toArray()[i]).get("episodes")));
                    id.add(i, ((JSONObject)results.toArray()[i]).get("mal_id").toString());
                    if ((((JSONObject) results.toArray()[i]).get("rank")) != null) {
                        rank.add(i, (((JSONObject) results.toArray()[i]).get("rank")).toString());
                    } else {
                        rank.add(i, null);
                    }
                    if ((((JSONObject) results.toArray()[i]).get("score")) != null) {
                        score.add(i, (((JSONObject) results.toArray()[i]).get("score")).toString());
                    } else {
                        score.add(i, null);
                    }
                }
                System.out.println(title);
                System.out.println(image_url);
                System.out.println(url);
                System.out.println(synopsis);
                System.out.println(episodes);
                System.out.println(score);
                System.out.println("-----------------------------------------------------------------------------------------");
                System.out.println(json);

                return new pulledData(title, image_url, url, synopsis, episodes, score, rank, null, id);
            } catch (IOException e) {
                return null;
            } catch (ParseException e) {
                return null;
            }
        }

        // This is called when doInBackground() is finished
        @Override
        protected void onPostExecute(pulledData result) {
            animeCallBack(result);

            // new SearchActivity().searchCallback(result);
        }
    }

    private class BackgroundSearch extends AsyncTask<String, Void, String> {
        private static final String TAG = "BackgroundSearch";
        private static final String DOMAIN = "https://api.thetvdb.com";
        private static final String TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NzkzNDE1NTgsImlkIjoiUmlkZ2xpbmdDYXNpbm8iLCJvcmlnX2lhdCI6MTU3ODczNjc1OH0.mMGjnkr8b052bOqk0x0hZy8O1HlRhqCUbMVaVV05OqjsLqW44p3FvufU8yROaX2AlItnT-kVXQrupNmv26ybSw2gUJGDLLfp3W6tXliZKFZbzrcG5yCeqeyClM3o1RguyMVFqjmO7iOt44r7oiJNb0Ul3vtEIR1GpFWrjEefvkkGCdLHMRL7_VD0L5R2gnkNi3fsbsK4Bp4HGYRXAiy5cU4hru6iDADZVv1bL1pJFUbbi2cBqI1ZmKrPCdviniYqIOSoHUHlGMNhVgBxYS_LCX7_eh_3aEREX4nn7RPBBVf-nmt8d6iKs8Dmc47hpnnOPtXaekkeKbbzPqtfuL3YEQ";

        @Override
        protected String doInBackground(String... queries) {
            Log.d(TAG, "Started background download of search query " + queries[0]);
            URL route;
            try {
                route = new URL(DOMAIN + "/search/series" + "?name=" + queries[0]);
            } catch (Exception e) {
                route = null;
            }
            String readLine = null;
            HttpURLConnection connection;
            try {
                connection = (HttpURLConnection) route.openConnection();
            } catch (Exception e) {
                connection = null;
            }
            Log.d(TAG, "Hello a connection was opened");

            connection.setConnectTimeout(10 * 1000);
            connection.setReadTimeout(10 * 1000);
            try {
                connection.setRequestMethod("GET");
            } catch (Exception e) {
                Log.d(TAG, "Exception occurred with request method " + e);
                // do nothing
            }
            connection.setRequestProperty("Authorization", "Bearer " + TOKEN);
            // connection.setRequestProperty("name", queries[0]); // set userId its a sample here
            Log.d(TAG, "I set some cool stuff");

            int responseCode;
            try {
                responseCode = connection.getResponseCode();
            } catch (Exception e) {
                Log.d(TAG, "A exception was generated with exception " + e);
                responseCode = -1;
            }
            Log.d(TAG, "Response code " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in;
                try {
                    in = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                } catch (Exception e) {
                    in = null;
                }
                StringBuffer response = new StringBuffer();
                try {
                    while ((readLine = in.readLine()) != null) {
                        response.append(readLine);
                    }
                } catch (Exception e) {
                    // do nothing
                }
                try {
                    in.close();
                } catch (Exception e) {
                    // do nothing
                }

                Log.d(TAG, "Result: " + response.toString());
                return response.toString();
            } else {
                Log.d(TAG, "GET request failed with response code " + responseCode);
                return null;
            }
        }

        // This is called when doInBackground() is finished
        @Override
        protected void onPostExecute(String result) {
            export = result;
            searchCallback(result);
            // new SearchActivity().searchCallback(result);
        }
    }
}