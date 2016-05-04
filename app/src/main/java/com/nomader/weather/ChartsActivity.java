package com.nomader.weather;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.nomader.weather.fragments.PressureChartFragment;
import com.nomader.weather.fragments.TempChartFragment;
import com.nomader.weather.models.WeatherObject;

/**
 * Created by nomader on 12.02.16.
 */
public class ChartsActivity extends Activity {
    public static String TAG = "ChartsActivity";
    private FragmentManager fragmentManager;
    private int container;
    private String currentFragment;
    private WeatherObject currentWeather;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);
        fragmentManager = getFragmentManager();
        container = R.id.chartsFrame;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            currentWeather = extras.getParcelable("WEATHER");
            Log.d(TAG, "extras not null");
        }
        if(currentWeather != null)
            Log.d(TAG, "not null w activity");

        if (savedInstanceState == null) {
            Fragment fragment = new TempChartFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable("WEATHER", currentWeather);
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction().replace(container, fragment, TempChartFragment.TAG).commit();
            currentFragment = TempChartFragment.TAG;
        } else {
            boolean reused = true;
            currentFragment = savedInstanceState.getString("FRAGMENT");
            Fragment fragment = null;
            if (currentFragment.equals(TempChartFragment.TAG)) {
                fragment = fragmentManager.findFragmentByTag(TempChartFragment.TAG);
                if (fragment == null) {
                    fragment = new TempChartFragment();
                    reused = false;
                }
            } else if (currentFragment.equals(PressureChartFragment.TAG)) {
                fragment = fragmentManager.findFragmentByTag(PressureChartFragment.TAG);
                if (fragment == null) {
                    fragment = new PressureChartFragment();
                    reused = false;
                }
            }
            Bundle bundle = new Bundle();
            if (currentWeather != null) {
                bundle.putParcelable("WEATHER", currentWeather);
            }
            if (!reused) {
                fragment.setArguments(bundle);
            }

            fragmentManager.beginTransaction().replace(container, fragment, currentFragment).commit();
        }
    }

    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putString("FRAGMENT", currentFragment);
    }

    public void toggleCharts(View view) {
        Fragment fragment = null;
        if (currentFragment.equals(TempChartFragment.TAG)) {
            fragment = fragmentManager.findFragmentByTag(PressureChartFragment.TAG);
            if (fragment == null) {
                fragment = new PressureChartFragment();
            }
            currentFragment = PressureChartFragment.TAG;
        } else if (currentFragment.equals(PressureChartFragment.TAG)) {
            fragment = fragmentManager.findFragmentByTag(TempChartFragment.TAG);
            if (fragment == null) {
                fragment = new TempChartFragment();
            }
            currentFragment = TempChartFragment.TAG;
        }
        Bundle bundle = new Bundle();
        if (currentWeather != null) {
            bundle.putParcelable("WEATHER", currentWeather);
        }
        fragment.setArguments(bundle);

        fragmentManager.beginTransaction().replace(container, fragment, currentFragment).commit();
    }
}
