package com.gideontong.sbhacks2020.search;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gideontong.sbhacks2020.db.TokenData;
import com.gideontong.sbhacks2020.db.TokenDbHelper;

public class Networking {
    private static final String TAG = "AppNetworking";
    private static final String DOMAIN = "https://api.tvdb.com";
    private static final String API = "f777969b76262ceb54369c4912ba66d4"; // REVOKE THIS KEY LATER!
    private static final String TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NzkzNDE1NTgsImlkIjoiUmlkZ2xpbmdDYXNpbm8iLCJvcmlnX2lhdCI6MTU3ODczNjc1OH0.mMGjnkr8b052bOqk0x0hZy8O1HlRhqCUbMVaVV05OqjsLqW44p3FvufU8yROaX2AlItnT-kVXQrupNmv26ybSw2gUJGDLLfp3W6tXliZKFZbzrcG5yCeqeyClM3o1RguyMVFqjmO7iOt44r7oiJNb0Ul3vtEIR1GpFWrjEefvkkGCdLHMRL7_VD0L5R2gnkNi3fsbsK4Bp4HGYRXAiy5cU4hru6iDADZVv1bL1pJFUbbi2cBqI1ZmKrPCdviniYqIOSoHUHlGMNhVgBxYS_LCX7_eh_3aEREX4nn7RPBBVf-nmt8d6iKs8Dmc47hpnnOPtXaekkeKbbzPqtfuL3YEQ";

    private TokenDbHelper tHelper;

    // private String token;

    public Networking(TokenDbHelper helperObject) {
        // tHelper = helperObject;
    }

    public void Login() throws IOException {
        URL login = new URL(DOMAIN + "/login");
        URL keepAlive = new URL(DOMAIN + "/refresh_token");


    }

    public static void GetRequest(String Uri) throws IOException {
        URL urlForGetRequest = new URL(Uri);
        String readLine = null;
        HttpURLConnection connection = (HttpURLConnection) urlForGetRequest.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("userId", "a1bcdef"); // set userId its a sample here

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            StringBuffer response = new StringBuffer();
            while ((readLine = in.readLine()) != null) {
                response.append(readLine);
            }
            in.close();

            Log.d(TAG, "Result: " + response.toString());
        } else {
            System.out.println("GET request failed with response code " + responseCode);
        }
    }

    public static String search(String search) throws IOException{

        // Log.d(TAG, "THIS LOG SHOULD NOT EXIST");
        // return null;
    }

    public static void PostRequest(String Uri) throws IOException {
        final String POST_PARAMS = "{\n" + "\"userId\": 101,\r\n" +
                "    \"id\": 101,\r\n" +
                "    \"title\": \"Test Title\",\r\n" +
                "    \"body\": \"Test Body\"" + "\n}";
        System.out.println(POST_PARAMS);
        URL obj = new URL(Uri);
        HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
        postConnection.setRequestMethod("POST");
        postConnection.setRequestProperty("userId", "a1bcdefgh");
        postConnection.setRequestProperty("Content-Type", "application/json");
        postConnection.setDoOutput(true);
        OutputStream os = postConnection.getOutputStream();
        os.write(POST_PARAMS.getBytes());
        os.flush();
        os.close();
        int responseCode = postConnection.getResponseCode();
        System.out.println("POST Response Code :  " + responseCode);
        System.out.println("POST Response Message : " + postConnection.getResponseMessage());
        if (responseCode == HttpURLConnection.HTTP_CREATED) { //success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    postConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response.toString());
        } else {
            System.out.println("POST NOT WORKED");
        }
    }
}

private class BackgroundSearch extends AsyncTask<URL, Integer, Long> {
    // Do the long-running work in here
    protected Long doInBackground(String query) {
        URL route = new URL(DOMAIN + "/search/series");
        String readLine = null;
        HttpURLConnection connection = (HttpURLConnection) route.openConnection();
        Log.d(TAG, "Hello a connection was opened");

        connection.setConnectTimeout(10 * 1000);
        connection.setReadTimeout(10 * 1000);
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + TOKEN);
        connection.setRequestProperty("name", search); // set userId its a sample here
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

    // This is called each time you call publishProgress()
    protected void onProgressUpdate(Integer... progress) {
        setProgressPercent(progress[0]);
    }

    // This is called when doInBackground() is finished
    protected void onPostExecute(Long result) {
        showNotification("Downloaded " + result + " bytes");
    }