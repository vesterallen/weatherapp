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
import java.util.ArrayList;

/**
 * Created by Allen on 4/8/17.
 */

public class LoadCityDetails extends AsyncTask<String, Void, City> {
    City city;
    LoadingCityDetails loadingCity;
    Context context;
    ProgressDialog progressDialog;
    public LoadCityDetails(LoadingCityDetails loadingCity, Context context) {
        this.loadingCity= loadingCity;
        this.context=context;
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
        loadingCity.getSelectedCity(city);
        progressDialog.hide();
    }

    @Override
    protected City doInBackground(String... params) {
        StringBuilder sb = null;
        city=new City();
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
            city=CityUtils.cityJSONParser(sb.toString());
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
    public interface LoadingCityDetails{
        public void getSelectedCity(City city);
    }
}
