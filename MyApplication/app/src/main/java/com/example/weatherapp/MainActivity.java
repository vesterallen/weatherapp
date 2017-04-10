package com.example.weatherapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements LoadCityDetails.LoadingCityDetails, LoadWeatherDetails.LoadingWeatherDetails, CityAdapter.PassFavourite {
    City city;
    Boolean search;
    ArrayList<City> cities;
    int counter = 0;

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
    TextView searchAndSave;
    ProgressDialog progressDialog;
    CityAdapter adaptor;
    SharedPreferences mSettings;

    private DatabaseReference mDatabase;
    private DatabaseReference ref, cityRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSettings = this.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        progressDialog = new ProgressDialog(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ref = mDatabase.child("cities");
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
        noCities = (TextView) findViewById(R.id.noCities);
        searchAndSave = (TextView) findViewById(R.id.searchAndSave);
        alertDialog = new AlertDialog.Builder(this);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                cities = new ArrayList<>();
                counter = (int) dataSnapshot.getChildrenCount();
                for (DataSnapshot dataSnapshotChild : dataSnapshot.getChildren()) {
                    City city = dataSnapshotChild.getValue(City.class);
                    Log.d("CityObj", city.toString());
                    cities.add(city);
                }
                Log.d("couny", "" + counter);
                if (counter != 0) {
                    noCities.setText("");
                    searchAndSave.setText("");
                    adaptor = new CityAdapter(MainActivity.this, cities, MainActivity.this);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(adaptor);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                } else {
                    noCities.setGravity(Gravity.CENTER_HORIZONTAL);
                    searchAndSave.setGravity(Gravity.CENTER_HORIZONTAL);
                    noCities.setText("There are no cities to display");
                    searchAndSave.setText("Search the city from the search box and save");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        searchCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search = true;
                String url = null;
                url = CITY_DETAILS_URL + countryName.getText().toString().trim() + "/search" + API_KEY + "&q=" + cityName.getText().toString().trim();
                Log.d("City", url.trim());
                new LoadCityDetails(MainActivity.this, MainActivity.this).execute(url.trim());
                Intent i = new Intent(MainActivity.this, CityWeatherActivity.class);
                i.putExtra("country_code","US");
                i.putExtra("city","Charlotte");
                startActivity(i);
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
            SharedPreferences.Editor editor = mSettings.edit();
            editor.putString("cur_city", city.getCityName());
            editor.putString("cur_country", city.getCityCountry());
            editor.putString("cur_cityKey", city.getCityKey());
            editor.putString("unit", "C");
            editor.commit();
            submitCity(city);
        }
    }

    @Override
    public void getSelectedCity(City city) {
        if(search != true) {
                setCurrentCity.setVisibility(View.GONE);
            }
            String url = null;
            url = CITY_WEATHER_DETAILS + city.getCityKey() + API_KEY;
            Log.d("In weather1 completion", CITY_WEATHER_DETAILS);
            new LoadWeatherDetails(this, this, city).execute(url.trim());

    }

    public ArrayList<City> submitCity(final City city) {
        cityRef = ref.push();
        String s = cityRef.toString();
        city.setCityFav("notFavourite");
        city.setCityId(s);
        cityRef.setValue(city);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("Count ", "" + dataSnapshot.getChildrenCount());
                cities = null;
                cities = new ArrayList<City>();
                counter = (int) dataSnapshot.getChildrenCount();
                for (DataSnapshot dataSnapshotChild : dataSnapshot.getChildren()) {
                    City cityChild = dataSnapshotChild.getValue(City.class);
                    Log.d("City", counter + city.getCityName());
                    cities.add(cityChild);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return cities;
    }

    @Override
     protected void onStart() {
        super.onStart();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                        cities = new ArrayList<>();
                        counter = (int) dataSnapshot.getChildrenCount();
                        for (DataSnapshot dataSnapshotChild : dataSnapshot.getChildren()) {
                                City city = dataSnapshotChild.getValue(City.class);
                                Log.d("CityObj", city.toString());
                                cities.add(city);
                            }
                        Log.d("couny", "" + counter);
                        if (counter != 0) {
                                noCities.setText("");
                                searchAndSave.setText("");
                                adaptor = new CityAdapter(MainActivity.this, cities, MainActivity.this);
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setAdapter(adaptor);
                                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                            } else {
                                noCities.setGravity(Gravity.CENTER_HORIZONTAL);
                                searchAndSave.setGravity(Gravity.CENTER_HORIZONTAL);
                                noCities.setText("There are no cities to display");
                                searchAndSave.setText("Search the city from the search box and save");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void passFavouriteClickData(City city) {
        if (city.getCityFav().equals("Favourite")) {
            city.setCityFav("notFavourite");
            }else {
                city.setCityFav("Favourite");
        }
        Map<String, Object> map=new HashMap<>();
        map.put(city.getCityId(),city);
        ref.updateChildren(map);
        }
}
