package com.example.weatherapp;

import android.app.ProgressDialog;
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity implements LoadCityDetails.LoadingCityDetails, LoadWeatherDetails.LoadingWeatherDetails {
    City city;
    Boolean search;

    public static final String CITY_DETAILS_URL = "http://dataservice.accuweather.com/locations/v1/";
    public static final String ICON_URL = "http://developer.accuweather.com/sites/default/files/0";
    public static final String CITY_WEATHER_DETAILS = "http://dataservice.accuweather.com/currentconditions/v1/";
    public static final String API_KEY = "?apikey=HMdbWP43lBiXRFh96hJ8ljfSymkWjltu";

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
    ImageView weatherIcon;
    TextView weatherText;
    TextView temperature;
    TextView updatedTime;
    ProgressDialog progressDialog;

    private DatabaseReference mDatabase;
    private DatabaseReference cities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        weatherIcon = (ImageView) findViewById(R.id.weatherIcon);
        weatherText = (TextView) findViewById(R.id.weatherText);
        temperature = (TextView) findViewById(R.id.temperature);
        updatedTime = (TextView) findViewById(R.id.updatedTime);
        currentCity = (TextView) findViewById(R.id.currentCity);
        setCurrentCity = (Button) findViewById(R.id.setCurrentCity);
        searchCity = (Button) findViewById(R.id.searchCity);
        cityName = (EditText) findViewById(R.id.cityName);
        countryName = (EditText) findViewById(R.id.countryName);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        relativeLayout = (RelativeLayout) findViewById(R.id.relLayoutForSavedCities);
        noCities = new TextView(this);
        alertDialog = new AlertDialog.Builder(this);
        searchCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search = true;
                String url = null;
                url = CITY_DETAILS_URL + countryName.getText().toString().trim() + "/search" + API_KEY + "&q=" + cityName.getText().toString().trim();
                Log.d("City", url.trim());
                new LoadCityDetails(MainActivity.this, MainActivity.this).execute(url.trim());
            }
        });
        setCurrentCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.setTitle("Enter City Details");
                alertCity = new EditText(MainActivity.this);
                alertCountry = new EditText(MainActivity.this);
                LinearLayout ll = new LinearLayout(MainActivity.this);
                ll.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
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
                        search = false;
                        String url = null;
                        url = CITY_DETAILS_URL + alertCountry.getText().toString().trim() + "/search" + API_KEY + "&q=" + alertCity.getText().toString().trim();
                        new LoadCityDetails(MainActivity.this, MainActivity.this).execute(url.trim());
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
        if (search != true) {
            this.city = new City();
            this.city = city;
            String url = null;
            url = ICON_URL + this.city.getCityWeatherIcon() + "-s.png";
            Picasso.with(this).load(url.trim()).into(weatherIcon);
            currentCity.setText(city.getCityName() + ", " + city.getCityCountry());
            temperature.setText("Temperature : " + city.getCityTempInC());
            updatedTime.setText(city.getCityTempTime());
            weatherText.setText(city.getCityWeatherText());
            Log.d("In weather completion", ICON_URL);
        } else {
            submitCity(city);
        }
    }

    @Override
    public void getSelectedCity(City city) {

        setCurrentCity.setVisibility(View.GONE);
        String url = null;
        url = CITY_WEATHER_DETAILS + city.getCityKey() + API_KEY;
        Log.d("In weather1 completion", CITY_WEATHER_DETAILS);
        new LoadWeatherDetails(this, this, city).execute(url.trim());

    }

    public void submitCity(City city) {
        DatabaseReference ref = mDatabase.child("cities").push();
        String s = ref.getKey();
        city.setCityId(s);
        ref.setValue(city);
        mDatabase.addChildEventListener()
    }
}
