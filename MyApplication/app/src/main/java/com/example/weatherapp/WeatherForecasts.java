package com.example.weatherapp;

/**
 * Created by Hari on 4/8/2017.
 */

public class WeatherForecasts {
    private static String headLine, extendedLink;
    private String dayImage,nightImage,dayPhrase,nightPhrase, dayLink, Date;
    private double maxF, minF, maxC, minC;

    public static String getHeadLine() {
        return headLine;
    }

    public static void setHeadLine(String headLine) {
        WeatherForecasts.headLine = headLine;
    }

    public static String getExtendedLink() {
        return extendedLink;
    }

    public static void setExtendedLink(String extendedLink) {
        WeatherForecasts.extendedLink = extendedLink;
    }

    public String getDayImage() {
        return dayImage;
    }

    public void setDayImage(String dayImage) {
        this.dayImage = dayImage;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getNightImage() {
        return nightImage;
    }

    public void setNightImage(String nightImage) {
        this.nightImage = nightImage;
    }

    public String getDayPhrase() {
        return dayPhrase;
    }

    public void setDayPhrase(String dayPhrase) {
        this.dayPhrase = dayPhrase;
    }

    public String getNightPhrase() {
        return nightPhrase;
    }

    public void setNightPhrase(String nightPhrase) {
        this.nightPhrase = nightPhrase;
    }

    public String getDayLink() {
        return dayLink;
    }

    public void setDayLink(String dayLink) {
        this.dayLink = dayLink;
    }

    public double getMaxF() {
        return maxF;
    }

    public void setMaxF(double maxF) {
        this.maxF = maxF;
        this.maxC = Math.round(fToC(maxF));
    }

    public double getMinF() {
        return minF;
    }

    public void setMinF(double minF) {
        this.minF = minF;
        this.minC = Math.round(fToC(minF));
    }

    @Override
    public String toString() {
        return "WeatherForecasts{" +
                "dayImage='" + dayImage + '\'' +
                ", nightImage='" + nightImage + '\'' +
                ", dayPhrase='" + dayPhrase + '\'' +
                ", nightPhrase='" + nightPhrase + '\'' +
                ", dayLink='" + dayLink + '\'' +
                ", Date='" + Date + '\'' +
                ", maxF=" + maxF +
                ", minF=" + minF +
                ", maxC=" + maxC +
                ", minC=" + minC +
                '}';
    }

    private double fToC(double f){
        return (double)((f-32)*5)/9;
    }

}
