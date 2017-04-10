package com.example.weatherapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Hari on 4/8/2017.
 */

public class GetCityLocation extends AsyncTask<String, Void, String>{
    private final String API_KEY = "J9vEd6PkGwevteMPCAq0Qi2rN7XRouz2";
    private String encodedURL;
    private String inJSON;

    CityWeatherActivity activity;
    public GetCityLocation(CityWeatherActivity activity){
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            //encodedURL = URLEncoder.encode(params[0],"UTF-8");
            Log.d("encoded Location URL",params[0]);
            URL locationURL = new URL(params[0]);

            HttpURLConnection con = (HttpURLConnection) locationURL.openConnection();
            con.connect();
            int statusCode = con.getResponseCode();
            if(statusCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();
                while (line != null) {
                    sb.append(line);
                    line = br.readLine();
                }
                inJSON = sb.toString();

                JSONArray root= new JSONArray(inJSON);
                JSONObject obj = root.getJSONObject(0);

                return obj.getString("Key");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String s) {
        String cityURL = "http://dataservice.accuweather.com/forecasts/v1/daily/5day/"+s+"?apikey="+API_KEY;
        Log.d("CityURL",cityURL);
        new GetFiveDaysForeCast(activity).execute(cityURL);
        super.onPostExecute(s);
    }

}
