package com.nomader.weather.controllers.http;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by nomader on 18.01.16.
 */
public class WeatherHttpRequest extends AsyncTask<String, Void, JSONObject> {
    public static String TAG = "WeatherHttpRequest";
    public static String TYPE_BY_CITY = "byCity";
    public static String TYPE_BY_ID = "byID";
    public static String TYPE_BY_GEOGRAPHIC_COORDINATES = "byGeo";

    private static String API_KEY = "6d7ec494e9db201a7441dd6b59112004";
    private static String charset = "UTF-8";

    @Override
    protected JSONObject doInBackground(String... params) {
        JSONObject jObject = null;
        String type = params[0];
        String ending = "";

        if (type.equals(TYPE_BY_CITY)) {
            ending = "?q=" + params[1] + "," + params[2];
        } else if (type.equals(TYPE_BY_ID)) {
            ending = "?id=" + params[1];
        } else if (type.equals(TYPE_BY_GEOGRAPHIC_COORDINATES)) {
            ending = "?lat=" + params[1] + "&lon=" + params[2];
        }

        String completeUrl = "http://api.openweathermap.org/data/2.5/forecast"
                + ending + "&mode=json&appid=" + API_KEY;
        try {
            URL url = new URL(completeUrl);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(false);
            connection.setRequestMethod("GET");
//            connection.setRequestProperty("Accept-Charset", charset);
            connection.setConnectTimeout(15000);
            connection.connect();


            InputStream input = new BufferedInputStream(connection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            jObject = new JSONObject(result.toString());
//            Log.d(TAG, "Response from server: " + result.toString());
        } catch (java.io.IOException | JSONException e) {
            e.printStackTrace();
        }

        return jObject;
    }
}
