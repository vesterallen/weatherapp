package com.example.weatherapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hari on 4/8/2017.
 */

public class WeatherForeCastUtil {
    public static class getWeatherForecasts{
        public static List<WeatherForecasts> getForecastJSONParser(String in) throws JSONException {
            List<WeatherForecasts> weather5days = new ArrayList<WeatherForecasts>();
            WeatherForecasts wf;

            JSONObject root = new JSONObject(in);
            JSONObject headline = root.getJSONObject("Headline");
            WeatherForecasts.setHeadLine(headline.getString("Text"));
            WeatherForecasts.setExtendedLink(headline.getString("MobileLink"));
            JSONArray dailyForecasts = root.getJSONArray("DailyForecasts");

            for(int i=0; i < dailyForecasts.length(); i++){
                wf = new WeatherForecasts();
                JSONObject dailyForecast = dailyForecasts.getJSONObject(i);
                wf.setDate(dailyForecast.getString("Date").substring(0,10));
                wf.setDayLink(dailyForecast.getString("MobileLink"));
                JSONObject day = dailyForecast.getJSONObject("Day");
                wf.setDayPhrase(day.getString("IconPhrase"));
                if(Integer.parseInt(day.getString("Icon"))<10)
                    wf.setDayImage("http://developer.accuweather.com/sites/default/files/0"+day.getString("Icon")+"-s.png");
                else
                    wf.setDayImage("http://developer.accuweather.com/sites/default/files/"+day.getString("Icon")+"-s.png");
                JSONObject night = dailyForecast.getJSONObject("Night");
                wf.setNightPhrase(night.getString("IconPhrase"));
                if(Integer.parseInt(night.getString("Icon"))<10)
                    wf.setNightImage("http://developer.accuweather.com/sites/default/files/0"+night.getString("Icon")+"-s.png");
                else
                    wf.setNightImage("http://developer.accuweather.com/sites/default/files/"+night.getString("Icon")+"-s.png");
                JSONObject temparature = dailyForecast.getJSONObject("Temperature");
                JSONObject Minimum = temparature.getJSONObject("Minimum");
                wf.setMinF(Minimum.getDouble("Value"));
                JSONObject Maximum = temparature.getJSONObject("Maximum");
                wf.setMaxF(Maximum.getDouble("Value"));
                weather5days.add(wf);
            }


            return weather5days;
        }
    }
}
