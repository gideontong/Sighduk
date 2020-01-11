package com.gideontong.sbhacks2020.search;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.gideontong.sbhacks2020.db.TokenDbHelper;

public class Networking {
    private static final String TAG = "AppNetworking";
    private static final String DOMAIN = "https://api.tvdb.com";
    private static final String API = "f777969b76262ceb54369c4912ba66d4"; // REVOKE THIS KEY LATER!

    private TokenDbHelper tHelper;

    public Networking(TokenDbHelper helperObject) {
        tHelper = helperObject;
    }

    public void Login() throws IOException {
        URL login = new URL(DOMAIN + "/login");
        URL keepAlive = new URL(DOMAIN + "/refresh_token");

        SQLiteDatabase db = tHelper.getWritableDatabase();
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
