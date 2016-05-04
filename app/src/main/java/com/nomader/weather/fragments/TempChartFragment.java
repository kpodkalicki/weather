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
 * Created by nomader on 10.02.16.
 */
public class TempChartFragment extends Fragment {
    public static String TAG = "TempChartFragment";
    private LineChartView chartView;
    private WeatherObject currentWeather;
//    private ArrayList<String> dates;
    private ArrayList<AxisValue> dates;
    private ArrayList<Integer> temps;
    private ArrayList<Integer> pressures;
    private double  minPressure, maxPressure;
    private int minTemp, maxTemp;
    private TextView error;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = layoutInflater.inflate(R.layout.fragment_temp_chart, viewGroup, false);

        chartView = (LineChartView) view.findViewById(R.id.chart);
        chartView.setInteractive(true);
        chartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        error = (TextView)view.findViewById(R.id.noTempTextView);

        Bundle bundle = getArguments();
        if(bundle == null)
            Log.d(TAG, "bundle null");

        if (savedInstanceState != null) {
            Log.d(TAG, "state not null");
            currentWeather = savedInstanceState.getParcelable("WEATHER");
        } else if (bundle != null) {
            Log.d(TAG, "bundle not null");
            currentWeather = bundle.getParcelable("WEATHER");
        }


        if (currentWeather != null) {
            init();
            setData();
        }else {
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
        minPressure = 2000.f;
        maxTemp = -274;
        maxPressure = 0.f;
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
        int tempRange = maxTemp - minTemp;


        ArrayList<Line> lines = new ArrayList<>();

        ArrayList<PointValue> tempPoints = new ArrayList<>();
        for(int i = 0; i < temps.size(); i++){
            tempPoints.add(i, new PointValue(i, temps.get(i)));
        }
        Line tempLine = new Line(tempPoints);
        tempLine.setColor(ChartUtils.COLOR_RED);
        tempLine.setHasPoints(true);
        tempLine.setPointRadius(3);
        tempLine.setStrokeWidth(3);
        lines.add(tempLine);

        LineChartData data = new LineChartData(lines);

        Axis dateAxis = new Axis();
        dateAxis.setName("Data");
        dateAxis.setTextColor(ChartUtils.COLOR_ORANGE);
        dateAxis.setMaxLabelChars(3);
        dateAxis.setHasLines(true);
        dateAxis.setHasTiltedLabels(true);
        dateAxis.setValues(dates);
        data.setAxisXBottom(dateAxis);



        double temp = minTemp - 1;
        ArrayList<AxisValue> tempAxisValues = new ArrayList<>();
        for(int i = 0; temp < maxTemp + 1 || i < 10; i++){
            tempAxisValues.add(i, new AxisValue((float)temp));
            temp += 1;
        }
        Axis tempAxis = new Axis();
        tempAxis.setName("Temperatura");
        tempAxis.setHasLines(true);
        tempAxis.setMaxLabelChars(4);
        tempAxis.setTextColor(ChartUtils.COLOR_RED);
        data.setAxisYLeft(tempAxis);
        chartView.setLineChartData(data);
        chartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        Viewport v = chartView.getMaximumViewport();
        Log.d(TAG, "viewport left: " + v.left + " right: " + v.right + " top " + v.top + " bottom: " + v.bottom);
        v.set(v.left, v.top + 15, v.right, v.bottom - 15);
        chartView.setMaximumViewport(v);
        chartView.setCurrentViewport(v);
        chartView.invalidate();
        Log.d(TAG, "załadowano dane");

    }
}
