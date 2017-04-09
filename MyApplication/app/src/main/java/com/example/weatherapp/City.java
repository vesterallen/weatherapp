package com.example.weatherapp;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Allen on 4/8/17.
 */

public class City {
    public String cityId;
    public String cityKey;
    public String cityName;
    public String cityCountry;
    public String cityTemp;
    public String cityFav;
    public String cityWeatherIcon;
    public String cityWeatherText;
    public String cityTempInF;
    public String cityTempInC;
    public String cityTempTime;

    public String getCityTempTime() {
        return cityTempTime;
    }

    public void setCityTempTime(String cityTempTime) {
        this.cityTempTime = cityTempTime;
    }

    public String getCityWeatherIcon() {
        return cityWeatherIcon;
    }

    public void setCityWeatherIcon(String cityWeatherIcon) {
        this.cityWeatherIcon = cityWeatherIcon;
    }

    public String getCityWeatherText() {
        return cityWeatherText;
    }

    public void setCityWeatherText(String cityWeatherText) {
        this.cityWeatherText = cityWeatherText;
    }

    public String getCityTempInF() {
        return cityTempInF;
    }

    public void setCityTempInF(String cityTempInF) {
        this.cityTempInF = cityTempInF;
    }

    public String getCityTempInC() {
        return cityTempInC;
    }

    public void setCityTempInC(String cityTempInC) {
        this.cityTempInC = cityTempInC;
    }

    public String getCityKey() {
        return cityKey;
    }

    public void setCityKey(String cityKey) {
        this.cityKey = cityKey;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCountry() {
        return cityCountry;
    }

    public void setCityCountry(String cityCountry) {
        this.cityCountry = cityCountry;
    }

    public String getCityTemp() {
        return cityTemp;
    }

    public void setCityTemp(String cityTemp) {
        this.cityTemp = cityTemp;
    }

    public String getCityFav() {
        return cityFav;
    }

    public void setCityFav(String cityFav) {
        this.cityFav = cityFav;
    }

    @Override
    public String toString() {
        return "City{" +
                "cityKey='" + cityKey + '\'' +
                ", cityName='" + cityName + '\'' +
                ", cityCountry='" + cityCountry + '\'' +
                ", cityTemp='" + cityTemp + '\'' +
                ", cityFav='" + cityFav + '\'' +
                ", cityWeatherIcon='" + cityWeatherIcon + '\'' +
                ", cityWeatherText='" + cityWeatherText + '\'' +
                ", cityTempInF='" + cityTempInF + '\'' +
                ", cityTempInC='" + cityTempInC + '\'' +
                '}';
    }

    public City() {
    }

    public City(String cityKey, String cityName, String cityCountry, String cityFav) {

        this.cityKey = cityKey;
        this.cityName = cityName;
        this.cityCountry = cityCountry;
        this.cityFav = cityFav;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("cityKey",  cityKey );
        result.put("cityCountry", cityCountry);
        result.put("cityName", cityName);
        result.put("cityFav", cityFav);


        return result;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
}
