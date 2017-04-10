package com.example.weatherapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Allen on 4/10/17.
 */

public class CityAdapter extends RecyclerView.Adapter {
    Context context;
    List<City> cities;
    PassFavourite passFavourite;

    public CityAdapter(Context context, List<City> cities, PassFavourite passFavourite) {
        this.context = context;
        this.cities = cities;
        this.passFavourite=passFavourite;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.list_cities, parent, false);
        holder h = new holder(v);
        return h;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final holder h = (holder) holder;
        final City city = cities.get(position);
        Log.d("cit",city.toString());
        h.cityName.setText(city.getCityName());
        h.temp.setText(city.getCityTempInC());
        h.weather.setText(city.getCityWeatherText());
        Drawable myDrawable;
        if(city.getCityFav().equals("notFavourite")){
            myDrawable = context.getResources().getDrawable(R.drawable.stargray);
            h.fav.setImageDrawable(myDrawable);
        }else {
            myDrawable = context.getResources().getDrawable(R.drawable.stargold);
            h.fav.setImageDrawable(myDrawable);
        }
        h.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passFavourite.passFavouriteClickData(cities.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.cities.size();
    }
    class holder extends RecyclerView.ViewHolder {
        View v;
        TextView cityName;
        TextView weather;
        TextView temp;
        ImageView fav;

        public holder(View v) {
            super(v);
            this.v = v;
            cityName = (TextView) v.findViewById(R.id.cityNameText);
            weather = (TextView) v.findViewById(R.id.weatherText);
            temp = (TextView) v.findViewById(R.id.temperature);
            fav = (ImageView) v.findViewById(R.id.favouriteCityIcon);
        }
    }
    public interface PassFavourite{
        public void passFavouriteClickData(City city);
    }

}
