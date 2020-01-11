package com.gideontong.sbhacks2020.search;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import android.os.AsyncTask;

public class BackgroundSearch {

}

private class BackgroundSearch extends AsyncTask<URL, Integer, Long> {
    private static final String TAG = "AppNetworking";
    private static final String DOMAIN = "https://api.thetvdb.com";
    private static final String TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NzkzNDE1NTgsImlkIjoiUmlkZ2xpbmdDYXNpbm8iLCJvcmlnX2lhdCI6MTU3ODczNjc1OH0.mMGjnkr8b052bOqk0x0hZy8O1HlRhqCUbMVaVV05OqjsLqW44p3FvufU8yROaX2AlItnT-kVXQrupNmv26ybSw2gUJGDLLfp3W6tXliZKFZbzrcG5yCeqeyClM3o1RguyMVFqjmO7iOt44r7oiJNb0Ul3vtEIR1GpFWrjEefvkkGCdLHMRL7_VD0L5R2gnkNi3fsbsK4Bp4HGYRXAiy5cU4hru6iDADZVv1bL1pJFUbbi2cBqI1ZmKrPCdviniYqIOSoHUHlGMNhVgBxYS_LCX7_eh_3aEREX4nn7RPBBVf-nmt8d6iKs8Dmc47hpnnOPtXaekkeKbbzPqtfuL3YEQ";

    // Do the long-running work in here
    @Override
    public String doInBackground(String query) {
        URL route = new URL(DOMAIN + "/search/series");
        String readLine = null;
        HttpURLConnection connection = (HttpURLConnection) route.openConnection();
        Log.d(TAG, "Hello a connection was opened");

        connection.setConnectTimeout(10 * 1000);
        connection.setReadTimeout(10 * 1000);
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + TOKEN);
        connection.setRequestProperty("name", query); // set userId its a sample here
        Log.d(TAG, "I set some cool stuff");

        int responseCode = connection.getResponseCode();
        Log.d(TAG, "Reasponse code " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((readLine = in.readLine()) != null) {
                response.append(readLine);
            }
            in.close();

            Log.d(TAG, "Result: " + response.toString());
            return response.toString();
        } else {
            Log.d(TAG, "GET request failed with response code " + responseCode);
            return null;
        }
    }

    // This is called when doInBackground() is finished
    protected void onPostExecute(Long result) {
        // do something
    }
}
