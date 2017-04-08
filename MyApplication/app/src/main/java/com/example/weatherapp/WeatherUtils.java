package com.example.weatherapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Allen on 4/8/17.
 */

public class WeatherUtils {
    static City weatherJSONParser(String input, City city) throws JSONException {
        Log.d("In Parser", "In pARSER");
        City cityInUtils = new City();
        cityInUtils = city;
        JSONArray weatherJSONArray = new JSONArray(input);
        JSONObject weatherJSONObject = weatherJSONArray.getJSONObject(0);
        JSONObject tempJSONObject = weatherJSONObject.getJSONObject("Temperature");
        JSONObject tempFJSONObject = tempJSONObject.getJSONObject("Imperial");
        JSONObject tempCJSONObject = tempJSONObject.getJSONObject("Metric");
        cityInUtils.setCityWeatherIcon(weatherJSONObject.getString("WeatherIcon"));
        cityInUtils.setCityWeatherText(weatherJSONObject.getString("WeatherText"));
        cityInUtils.setCityTempInF(tempFJSONObject.getString("UnitType") + tempFJSONObject.getString("Value") + " F");
        cityInUtils.setCityTempInC(tempCJSONObject.getString("UnitType") + tempCJSONObject.getString("Value") + " 'C");
        cityInUtils.setCityTempTime(weatherJSONObject.getString("EpochTime"));
        return cityInUtils;
    }
}
