package com.example.weatherapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CityWeatherActivity extends AppCompatActivity implements GetFiveDaysForeCast.WFDisplay{
    List<WeatherForecasts> weatherForecastses;
    private final String API_KEY = "J9vEd6PkGwevteMPCAq0Qi2rN7XRouz2";
    private String locationURL;
    private String param1;
    private String param2;
    private String country;
    private String city;
    private String encodedLocationURL;
    private TextView tvForeceastCity;
    private TextView tvHeading;
    private TextView tvDate;
    private TextView tvTemperature;
    private ImageView imDay;
    private ImageView imNight;
    private TextView tvDayPhrase;
    private TextView tvNightPhrase;
    private TextView tvSingleDayDetails;
    private TextView tvExtendedForeCasts;
    private RecyclerView recyclerView;
    private Date displayDate;
    SharedPreferences mSettings;
    WeatherForecasts wf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSettings = this.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        setContentView(R.layout.activity_city_weather);
        tvForeceastCity = (TextView) findViewById(R.id.textViewDailyForecastCity);
        tvHeading = (TextView) findViewById(R.id.textViewHeadline);
        tvDate = (TextView) findViewById(R.id.textViewForecastLabel);
        tvTemperature = (TextView) findViewById(R.id.textViewTemperature);
        imDay = (ImageView) findViewById(R.id.imageViewDay);
        imNight = (ImageView) findViewById(R.id.imageViewNight);
        tvDayPhrase = (TextView) findViewById(R.id.textViewDayPhrase);
        tvNightPhrase = (TextView) findViewById(R.id.textViewNightPhrase);
        tvSingleDayDetails = (TextView) findViewById(R.id.textViewSingledayDetails);
        tvExtendedForeCasts = (TextView) findViewById(R.id.textViewExtendedForeCast);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewWF);
        recyclerView.setHasFixedSize(true);
        tvSingleDayDetails = (TextView) findViewById(R.id.textViewSingledayDetails);
        tvExtendedForeCasts = (TextView) findViewById(R.id.textViewExtendedForeCast);






        Intent weatherIntent = getIntent();
        country = weatherIntent.getStringExtra("country_code");
        city = weatherIntent.getStringExtra("city");

        param1 = "apikey"+ "="+API_KEY;
        param2 = "q"+ "=" + "charlotte" ;
        locationURL = "http://dataservice.accuweather.com/locations/v1/"+"US"+"/search?"+param1+"&"+param2;
        new GetCityLocation(this).execute(locationURL);
    }

    @Override
    public void showWf(List<WeatherForecasts> weatherForecastses) {
        this.weatherForecastses = weatherForecastses;
        tvHeading.setText(WeatherForecasts.getHeadLine());
        wf = weatherForecastses.get(0);
        tvTemperature.setText("Tempearature: "+ wf.getMaxF()+ Character.toString((char)0167) + "/ " + wf.getMinF()+ Character.toString((char)0167) );
        Picasso.with(CityWeatherActivity.this).load(wf.getDayImage()).into(imDay);
        Picasso.with(CityWeatherActivity.this).load(wf.getNightImage()).into(imNight);
        tvDayPhrase.setText(wf.getDayPhrase());
        tvNightPhrase.setText(wf.getNightPhrase());
        myDayAdapter mAdapter = new myDayAdapter(weatherForecastses,this);
        mAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3, GridLayoutManager.VERTICAL,false));
        Format f = new SimpleDateFormat("MMMM dd, yyyy");
        try {
            Date d1 = new SimpleDateFormat("yyyy-mm-dd").parse(wf.getDate());
            tvDate.setText("Forecast on"+ f.format(d1));
            //tvForeceastCity.setText(f.format(d1));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        tvSingleDayDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(wf.getDayLink()));
                Intent browserChooserIntent = Intent.createChooser(browserIntent , "Choose browser of your choice");
                startActivity(browserChooserIntent );
            }
        });

        tvExtendedForeCasts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(WeatherForecasts.getExtendedLink()));
                Intent browserChooserIntent = Intent.createChooser(browserIntent , "Choose browser of your choice");
                startActivity(browserChooserIntent );
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.city_weather_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menuIDSaveCity:

                break;
            case R.id.menuOptionsSetCurrentCity:
                SharedPreferences.Editor editor = mSettings.edit();
                //editor.putString("cur_city",);

                break;
            case R.id.menuOptionsSettings:

                break;
        }


        return super.onOptionsItemSelected(item);
    }
}
