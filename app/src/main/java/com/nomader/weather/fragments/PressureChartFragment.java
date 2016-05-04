package com.nomader.weather.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nomader.weather.R;
import com.nomader.weather.models.DatedWeather;
import com.nomader.weather.models.MainWeather;
import com.nomader.weather.models.WeatherObject;

import java.util.ArrayList;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by nomader on 12.02.16.
 */
public class PressureChartFragment extends Fragment {
    public static String TAG = "PressureChartFragment";
    private LineChartView chartView;
    private WeatherObject currentWeather;
    private ArrayList<AxisValue> dates;
    private ArrayList<Integer> temps;
    private ArrayList<Integer> pressures;
    private int  minPressure, maxPressure;
    private int minTemp, maxTemp;
    private TextView error;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.fragment_pressure_chart, viewGroup, false);

        chartView = (LineChartView)view.findViewById(R.id.pressureChart);
        chartView.setInteractive(true);
        chartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        error = (TextView)view.findViewById(R.id.noPressureTextView);

        Bundle bundle = getArguments();

        if (savedInstanceState != null) {
            currentWeather = savedInstanceState.getParcelable("WEATHER");
        } else if (bundle != null) {
            currentWeather = bundle.getParcelable("WEATHER");
        }


        if (currentWeather != null) {
            init();
            setData();
        }
        else {
            chartView.setVisibility(View.GONE);
            error.setVisibility(View.VISIBLE);
        }

        return view;
    }

    public void onSaveInstanceState(Bundle state){
        super.onSaveInstanceState(state);
        state.putParcelable("WEATHER", currentWeather);
    }

    private void init(){
        minTemp = 100;
        minPressure = 2000;
        maxTemp = -274;
        maxPressure = 0;
        temps = new ArrayList<>();
        pressures = new ArrayList<>();
        ArrayList<DatedWeather> list = currentWeather.getList();
        dates = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){
            DatedWeather datedWeather = list.get(i);
            MainWeather mainWeather = datedWeather.getMainWeather();
//            dates.add(i, datedWeather.getTextDate());
            dates.add(i, new AxisValue(i).setLabel(datedWeather.getTime()));
            int temp = mainWeather.getTemp();
            int pressure = mainWeather.getPressure();
            temps.add(i, temp);
            pressures.add(i, pressure);

            if(temp < minTemp)
                minTemp = temp;
            if(temp > maxTemp)
                maxTemp = temp;
            if(pressure < minPressure)
                minPressure = pressure;
            if(pressure > maxPressure)
                maxPressure = pressure;
        }
    }

    private void setData(){
        int tempRange = maxPressure - minPressure;


        ArrayList<Line> lines = new ArrayList<>();

        ArrayList<PointValue> pressurePoints = new ArrayList<>();
        for(int i = 0; i < pressures.size(); i++){
            pressurePoints.add(i, new PointValue(i, pressures.get(i)));
        }
        Line pressureLine = new Line(pressurePoints);
        pressureLine.setColor(ChartUtils.COLOR_RED);
        pressureLine.setHasPoints(true);
        pressureLine.setPointRadius(3);
        pressureLine.setStrokeWidth(3);
        lines.add(pressureLine);

        LineChartData data = new LineChartData(lines);

        Axis dateAxis = new Axis();
        dateAxis.setName("Data");
        dateAxis.setTextColor(ChartUtils.COLOR_ORANGE);
        dateAxis.setMaxLabelChars(3);
        dateAxis.setHasLines(true);
        dateAxis.setHasTiltedLabels(true);
        dateAxis.setValues(dates);
        data.setAxisXBottom(dateAxis);



        double press = minPressure - 10;
        ArrayList<AxisValue> pressureAxisValues = new ArrayList<>();
        for(int i = 0; press < maxPressure + 10 || i < 10; i++){
            pressureAxisValues.add(i, new AxisValue((float) press));
            press += 1;
        }
        Axis pressureAxis = new Axis();
        pressureAxis.setName("Ciśnienie");
        pressureAxis.setHasLines(true);
        pressureAxis.setMaxLabelChars(4);
        pressureAxis.setTextColor(ChartUtils.COLOR_RED);
        data.setAxisYLeft(pressureAxis);
        chartView.setLineChartData(data);
        chartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        Viewport v = chartView.getMaximumViewport();
        Log.d(TAG, "viewport left: " + v.left + " right: " + v.right + " top " + v.top + " bottom: " + v.bottom);
        v.set(v.left, v.top + 30, v.right, v.bottom - 30);
        chartView.setMaximumViewport(v);
        chartView.setCurrentViewport(v);

    }
}
