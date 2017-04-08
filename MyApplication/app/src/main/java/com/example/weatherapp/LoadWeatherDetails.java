package com.example.weatherapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Allen on 4/8/17.
 */

public class LoadWeatherDetails extends AsyncTask<String, Void, City> {
    City city;
    LoadingWeatherDetails loadingWeather;
    Context context;
    ProgressDialog progressDialog;
    public LoadWeatherDetails(LoadingWeatherDetails loadingWeather, Context context, City city) {
        this.loadingWeather= loadingWeather;
        this.context=context;
        this.city=city;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(context);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(City city) {
        super.onPostExecute(city);
        loadingWeather.getSelectedCityWeather(city);
        progressDialog.hide();
    }

    @Override
    protected City doInBackground(String... params) {
        StringBuilder sb = null;
        try {
            URL url=new URL(params[0]);
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            connection.connect();
            int statusCode=connection.getResponseCode();
            if(statusCode==HttpURLConnection.HTTP_OK){
                BufferedReader br=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                sb=new StringBuilder();
                String line=br.readLine();
                while(line!=null){
                    sb.append(line);
                    line=br.readLine();
                }
            }
            Log.d("Input",sb.toString());
            city=WeatherUtils.weatherJSONParser(sb.toString(), city);
            connection.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return city;
    }
    public interface LoadingWeatherDetails{
        public void getSelectedCityWeather(City city);
    }
}
