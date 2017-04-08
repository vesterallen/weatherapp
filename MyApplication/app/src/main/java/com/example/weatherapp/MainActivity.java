package com.example.weatherapp;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements LoadCityDetails.LoadingCityDetails, LoadWeatherDetails.LoadingWeatherDetails{
    City city;
    public static String CITY_DETAILS_URL="http://dataservice.accuweather.com/locations/v1/US/search?apikey=HMdbWP43lBiXRFh96hJ8ljfSymkWjltu&q=";
    public static String ICON_URL="http://developer.accuweather.com/sites/default/files/0";
    public static String CITY_WEATHER_DETAILS="http://dataservice.accuweather.com/currentconditions/v1/";
    public static String API_KEY="?apikey=HMdbWP43lBiXRFh96hJ8ljfSymkWjltu";
    TextView currentCity;
    Button setCurrentCity;
    Button searchCity;
    EditText cityName;
    EditText countryName;
    RecyclerView recyclerView;
    RelativeLayout relativeLayout;
    TextView noCities;
    AlertDialog.Builder alertDialog;
    EditText alertCity;
    EditText alertCountry;
    String fromMethod;
    ImageView weatherIcon;
    TextView weatherText;
    TextView temperature;
    TextView updatedTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        weatherIcon= (ImageView) findViewById(R.id.weatherIcon);
        weatherText= (TextView) findViewById(R.id.weatherText);
        temperature= (TextView) findViewById(R.id.temperature);
        updatedTime= (TextView) findViewById(R.id.updatedTime);
        currentCity= (TextView) findViewById(R.id.currentCity);
        setCurrentCity= (Button) findViewById(R.id.setCurrentCity);
        searchCity= (Button) findViewById(R.id.searchCity);
        cityName= (EditText) findViewById(R.id.cityName);
        countryName= (EditText) findViewById(R.id.countryName);
        recyclerView= (RecyclerView) findViewById(R.id.recyclerView);
        relativeLayout= (RelativeLayout) findViewById(R.id.relLayoutForSavedCities);
        noCities=new TextView(this);
        alertDialog=new AlertDialog.Builder(this);
        setCurrentCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.setTitle("Enter City Details");
                alertCity=new EditText(MainActivity.this);
                alertCountry=new EditText(MainActivity.this);
                LinearLayout ll=new LinearLayout(MainActivity.this);
                ll.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                ll.setLayoutParams(layoutParams);
                alertCity.setLayoutParams(layoutParams);
                alertCountry.setLayoutParams(layoutParams);
                ll.addView(alertCity);
                ll.addView(alertCountry);
                alertDialog.setView(ll);
                alertDialog.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CITY_DETAILS_URL+=alertCity.getText().toString().trim();
                        new LoadCityDetails(MainActivity.this, MainActivity.this).execute(CITY_DETAILS_URL);
                    }
                });
                alertDialog.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                alertDialog.show();
            }
        });

    }


    @Override
    public void getSelectedCityWeather(City city) {
        this.city=new City();
        this.city = city;
        ICON_URL+=this.city.getCityWeatherIcon()+"-s.png";
        Picasso.with(this).load(ICON_URL).into(weatherIcon);
        currentCity.setText(city.getCityName()+"\n"+city.getCityCountry());
        temperature.setText("Temperature : "+city.getCityTempInC());
        updatedTime.setText(city.getCityTempTime());
        weatherText.setText(city.getCityWeatherText());
        Log.d("In weather completion",ICON_URL);
    }

    @Override
    public void getSelectedCity(City city) {
        setCurrentCity.setVisibility(View.GONE);
        CITY_WEATHER_DETAILS+=city.getCityKey()+API_KEY;
        Log.d("In weather1 completion",CITY_WEATHER_DETAILS);
        new LoadWeatherDetails(this, this, city).execute(CITY_WEATHER_DETAILS);

    }
}
