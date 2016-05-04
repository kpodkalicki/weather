package com.nomader.weather.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nomader.weather.MainActivity;
import com.nomader.weather.R;
import com.nomader.weather.controllers.adapters.WeatherListAdapter;
import com.nomader.weather.controllers.http.WeatherHttpRequest;
import com.nomader.weather.models.DatedWeather;
import com.nomader.weather.models.WeatherObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;



/**
 * Created by nomader on 23.01.16.
 */
public class WeatherFragment extends Fragment {
    public static String TAG = "WeatherFragment";

    private MainActivity mainActivity;
    private ListView weatherListView;
    private WeatherListAdapter adapter;
    private WeatherObject currentWeather;
    private TextView textView;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState){
        Log.d(TAG, this.getTag());
        mainActivity = (MainActivity)getActivity();

        View view = layoutInflater.inflate(R.layout.fragment_weather, viewGroup, false);
        textView = (TextView)view.findViewById(R.id.textView);
        weatherListView = (ListView)view.findViewById(R.id.weatherListView);
        adapter = new WeatherListAdapter(getActivity(), R.layout.weather_list_item, new ArrayList<DatedWeather>());
        weatherListView.setAdapter(adapter);
        if(savedInstanceState != null){
            currentWeather = savedInstanceState.getParcelable("WEATHER");
        }
        refreshWeather(currentWeather);
        return view;
    }

    public void refreshWeather(WeatherObject weather){
        currentWeather = weather;
        if(currentWeather != null){
            Log.d(TAG, currentWeather.getList().get(0).getWeatherList().toString());
            if(currentWeather.getList().isEmpty()){
                Toast.makeText(getActivity(), "Wybierz inną lokalizacje", Toast.LENGTH_SHORT).show();
                weatherListView.setVisibility(View.GONE);
                textView.setText("Wybierz lokalizacje");
            }else {
                weatherListView.setVisibility(View.VISIBLE);
                textView.setText(currentWeather.getCity().getName());
                Log.d(TAG, "wczytano pogode");
                adapter.clear();
                adapter.addAll(currentWeather.getList());
            }
        }
        else{
            weatherListView.setVisibility(View.GONE);
            textView.setText("Wybierz lokalizacje");
        }
    }

    public void onSaveInstanceState(Bundle state){
        super.onSaveInstanceState(state);
        state.putParcelable("WEATHER", currentWeather);
    }

    public WeatherObject getCurrentWeather() {
        return currentWeather;
    }
}
