package com.nomader.weather;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.nomader.weather.controllers.http.WeatherHttpRequest;
import com.nomader.weather.fragments.TempChartFragment;
import com.nomader.weather.fragments.MapFragment;
import com.nomader.weather.fragments.WeatherFragment;
import com.nomader.weather.models.WeatherObject;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    private FragmentManager fragmentManager;
    private String currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getFragmentManager();
        Fragment tmp = null;
        if (savedInstanceState == null) {
            tmp = new WeatherFragment();
            currentFragment = WeatherFragment.TAG;
        } else {
            String key = savedInstanceState.getString("FRAGMENT");
            if (key.equals(WeatherFragment.TAG)) {

                tmp = fragmentManager.findFragmentByTag(WeatherFragment.TAG);
                if (tmp == null) {
//                    Log.d(TAG, "new weather fragment");
                    tmp = new WeatherFragment();

                }
                currentFragment = WeatherFragment.TAG;
                fragmentManager.beginTransaction().replace(R.id.mainFrame, tmp, currentFragment).commit();

            } else if (key.equals(MapFragment.TAG)) {

                tmp = fragmentManager.findFragmentByTag(MapFragment.TAG);
                if (tmp == null) {
                    tmp = new MapFragment();
//                    Log.d(MapFragment.TAG, "new map fragment");
                }

                currentFragment = MapFragment.TAG;
                fragmentManager.beginTransaction()
                        .replace(R.id.mainFrame, tmp, currentFragment)
//                        .addToBackStack(null)
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                        .commit();
            }
        }
        fragmentManager.beginTransaction().replace(R.id.mainFrame, tmp, currentFragment).commit();

//        Log.d(TAG, "backStack: " + fragmentManager.getBackStackEntryCount());
//        Log.d(TAG, "current fragment: " + currentFragment);

    }

    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putString("FRAGMENT", currentFragment);
    }

    public void onClick(View view) {
//        Fragment tmp = fragmentManager.findFragmentByTag(MapFragment.TAG);
        Fragment tmp = null;
        String tag = null;
        WeatherObject currentWeather = getCurrentWeather();
        switch (view.getId()) {
            case R.id.floatingMapButtonButton:
                tag = MapFragment.TAG;
                tmp = fragmentManager.findFragmentByTag(tag);
                if (tmp == null)
                    tmp = new MapFragment();
                currentFragment = tag;
//                currentWeather = getCurrentWeather();
                if (currentWeather != null) {
                    Bundle mapBundle = new Bundle();
                    mapBundle.putDouble("lat", currentWeather.getCity().getCoordinates().getLatitude());
                    mapBundle.putDouble("lng", currentWeather.getCity().getCoordinates().getLongitude());
                    tmp.setArguments(mapBundle);
                }
                fragmentManager.beginTransaction().replace(R.id.mainFrame, tmp, tag)
                        .addToBackStack(null)
                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)
                        .commit();
                break;
            case R.id.floatingChartsButton:
                Intent intent = new Intent(this, ChartsActivity.class);
                if (currentWeather != null) {
                    intent.putExtra("WEATHER", currentWeather);
                    Log.d(TAG, "dupa");
                }
                startActivity(intent);

                break;
        }
    }

    public void onBackPressed() {
//        Log.d(TAG, "backStack: " + fragmentManager.getBackStackEntryCount());
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
            currentFragment = WeatherFragment.TAG;
        } else {
            super.onBackPressed();
        }
    }

    public void getWeather(double lat, double lng) {
        WeatherHttpRequest request = new WeatherHttpRequest();
        WeatherObject weatherObject = null;
        try {
            JSONObject object = request.execute(WeatherHttpRequest.TYPE_BY_GEOGRAPHIC_COORDINATES, String.valueOf(lat), String.valueOf(lng)).get();
            weatherObject = new WeatherObject(object);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        if (weatherObject != null) {
            Log.d(TAG, "wczytano pogode");
            WeatherFragment target = (WeatherFragment) fragmentManager.findFragmentByTag(WeatherFragment.TAG);
            if (target != null) {
                target.refreshWeather(weatherObject);
            }
        }
    }

    private WeatherObject getCurrentWeather() {
        return ((WeatherFragment) fragmentManager.findFragmentByTag(WeatherFragment.TAG)).getCurrentWeather();
    }


}
