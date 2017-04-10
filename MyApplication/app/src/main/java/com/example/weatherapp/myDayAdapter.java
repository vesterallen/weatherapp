package com.example.weatherapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Hari on 4/8/2017.
 */

public class myDayAdapter extends RecyclerView.Adapter<myDayAdapter.ViewHolder> {

    private CityWeatherActivity context;
    List<WeatherForecasts> wfs;


    public myDayAdapter(List<WeatherForecasts> wfs,CityWeatherActivity context){
        this.wfs = wfs;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WeatherForecasts wf = wfs.get(position);
        Picasso.with(context).load(wf.getDayImage()).into(holder.ivDay);
        Format f = new SimpleDateFormat("dd, yyyy");
        try {
                Date d1 = new SimpleDateFormat("yyyy-mm-dd").parse(wf.getDate());
                holder.tvDay.setText(f.format(d1));
                //tvForeceastCity.setText(f.format(d1));
                    } catch (ParseException e) {
                e.printStackTrace();
            }
    }

    @Override
    public int getItemCount() {
        return wfs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivDay;
        TextView tvDay;

        public ViewHolder(View itemView) {
            super(itemView);
            ivDay = (ImageView) itemView.findViewById(R.id.imageViewDay);
            tvDay = (TextView) itemView.findViewById(R.id.textViewDay);
        }
    }
}
