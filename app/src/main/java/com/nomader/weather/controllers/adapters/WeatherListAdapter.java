package com.nomader.weather.controllers.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nomader.weather.R;
import com.nomader.weather.models.DatedWeather;
import com.nomader.weather.models.MainWeather;
import com.nomader.weather.models.Weather;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by nomader on 22.01.16.
 */
public class WeatherListAdapter extends ArrayAdapter<DatedWeather>{
    public static String TAG = "WeatherListAdapter";
    final String DEGREE  = "\u00b0";
    private int itemResource;
    private Context context;
    private ArrayList<DatedWeather> list;

    public WeatherListAdapter(Context context, int resource, ArrayList<DatedWeather> objects) {
        super(context, resource, objects);
        this.itemResource = resource;
        this.context = context;
        this.list = objects;
    }



    public DatedWeather getItem(int position){
        return this.list.get(position);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent){
        View view = getWorkingView(convertView);
        ViewHolder viewHolder = getViewHolder(view);
        DatedWeather datedWeather = getItem(position);
//        DatedWeather datedWeather = list.get(position);
        Weather details = datedWeather.getWeatherList().get(0);
        MainWeather mainWeather = datedWeather.getMainWeather();

        //Setting icon
//        int id = context.getResources().getIdentifier("w00d", "drawable", context.getPackageName());
        int id = 0;
        if(details.getId() >= 900 && details.getId() <= 906) {
            id = context.getResources().getIdentifier("w99d", "drawable", context.getPackageName());
        }else if(details.getId() >= 951 && details.getId() <= 962) {
            id = context.getResources().getIdentifier("w00d", "drawable", context.getPackageName());
        }else {
            String filename = "w" + details.getIcon();
            id = context.getResources().getIdentifier(filename, "drawable", context.getPackageName());
        }
        viewHolder.weatherIcon.setImageDrawable(context.getDrawable(id));

        viewHolder.tempTextView.setText(mainWeather.getTemp() + " " + DEGREE + "C");
        viewHolder.descTextView.setText(details.getDescription());
        viewHolder.timeTextView.setText(datedWeather.getTime());
        viewHolder.windTextView.setText(datedWeather.getWind().getSpeed() + " km/h");
        if(datedWeather.getRain() != null){
//            Log.d(TAG, "rain");
            viewHolder.dropTextView.setText(datedWeather.getRain().getThreeHourRain() + " mm");
        }else if(datedWeather.getSnow() != null){
//            Log.d(TAG, "snow");
            viewHolder.dropTextView.setText(datedWeather.getSnow().getThreeHour() + " mm");
        }else {
            viewHolder.dropTextView.setText("0 mm");
        }
        viewHolder.pressureTextView.setText(mainWeather.getPressure() + " hPa");


        return view;
    }


    /**
     * @param convertView
     * @return
     */
    private View getWorkingView(final View convertView){
        View workingView = null;

        if(convertView == null){

            final LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            workingView = layoutInflater.inflate(itemResource, null);

        }else {
            workingView = convertView;
        }

        return workingView;
    }

    private ViewHolder getViewHolder(final View workingView){
        final Object tag = workingView.getTag();
        ViewHolder viewHolder = null;

        if(tag == null || !(tag instanceof ViewHolder)){
            viewHolder = new ViewHolder();

            viewHolder.tempTextView = (TextView)workingView.findViewById(R.id.tempTextView);
            viewHolder.descTextView = (TextView)workingView.findViewById(R.id.descTextView);
            viewHolder.timeTextView = (TextView)workingView.findViewById(R.id.timeTextView);
            viewHolder.windTextView = (TextView)workingView.findViewById(R.id.windTextView);
            viewHolder.dropTextView = (TextView)workingView.findViewById(R.id.dropTextView);
            viewHolder.pressureTextView = (TextView)workingView.findViewById(R.id.pressureTextView);
            viewHolder.weatherIcon = (ImageView)workingView.findViewById(R.id.weatherIcon);

            workingView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)tag;
        }

        return viewHolder;
    }

    private static class ViewHolder {
        public TextView tempTextView, descTextView, timeTextView, windTextView, dropTextView, pressureTextView;
        public ImageView weatherIcon;
    }
}
