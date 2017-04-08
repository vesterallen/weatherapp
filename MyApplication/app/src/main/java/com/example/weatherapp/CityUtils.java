package com.example.weatherapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Allen on 4/8/17.
 */

public class CityUtils {
    static City cityJSONParser(String input) throws JSONException {
        City city=new City();
        Log.d("In Parser", "In pARSER");
        JSONArray citiesJSONArray = new JSONArray(input);
        JSONObject cityJSONObject = citiesJSONArray.getJSONObject(0);
        JSONObject countryJsonObject = cityJSONObject.getJSONObject("Country");
        city.setCityCountry(countryJsonObject.getString("EnglishName"));
        city.setCityName(cityJSONObject.getString("EnglishName"));
        city.setCityKey(cityJSONObject.getString("Key"));
        return city;
    }
}
