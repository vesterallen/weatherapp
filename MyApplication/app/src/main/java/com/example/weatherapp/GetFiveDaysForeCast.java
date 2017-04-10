package com.example.weatherapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Hari on 4/8/2017.
 */

public class GetFiveDaysForeCast extends AsyncTask<String,Void,List<WeatherForecasts>> {
    private String encodedURL;

    WFDisplay activity;

    public GetFiveDaysForeCast(WFDisplay activity){
        this.activity =activity;
    }


    @Override
    protected List<WeatherForecasts> doInBackground(String... params) {
        
        try {
//            encodedURL = URLEncoder.encode(,"UTF-8");
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
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
            return WeatherForeCastUtil.getWeatherForecasts.getForecastJSONParser(sb.toString());
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
    protected void onPostExecute(List<WeatherForecasts> weatherForecastses) {
        activity.showWf(weatherForecastses);


        if(weatherForecastses != null)
            Log.d("5daysForecast",weatherForecastses.toString());
        else
            Log.d("5daysForecast","NULL");
        super.onPostExecute(weatherForecastses);
    }
    interface WFDisplay{
        void showWf(List<WeatherForecasts> weatherForecastses);
    }
}
