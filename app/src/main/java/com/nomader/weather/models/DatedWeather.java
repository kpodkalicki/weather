package com.nomader.weather.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by nomader on 18.01.16.
 */
public class DatedWeather implements Parcelable {
    public static String TAG = "DatedWeather";

    private long dt;
    private GregorianCalendar time;
    private MainWeather main;
    private ArrayList<Weather> weather;
    private Overcast clouds;
    private Wind wind;
    private Snow snow;
    private Rain rain;
    private DatedWeatherSys sys;
    private String dtTxt;

    public DatedWeather(JSONObject jsonObject){
        try {
            this.dt = jsonObject.getLong("dt");
            this.time = new GregorianCalendar();
            this.time.setTimeInMillis(this.dt*1000);
            this.main = new MainWeather(jsonObject.getJSONObject("main"));
            this.clouds = new Overcast(jsonObject.getJSONObject("clouds"));
            this.dtTxt = jsonObject.getString("dt_txt");

            JSONArray array = jsonObject.getJSONArray("weather");
            weather = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                weather.add(new Weather(array.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            this.wind = new Wind(jsonObject.getJSONObject("wind"));
        } catch (JSONException e) {
            this.wind = null;
        }
        try {
            this.snow = new Snow(jsonObject.getJSONObject("snow"));
        } catch (JSONException e) {
            this.snow = null;
        }
        try {
            this.rain = new Rain(jsonObject.getJSONObject("rain"));
        } catch (JSONException e) {
            this.rain = null;
        }
        try {
            this.sys = new DatedWeatherSys(jsonObject.getJSONObject("sys"));
        } catch (JSONException e) {
            this.sys = null;
        }

    }

    protected DatedWeather(Parcel in) {
        dt = in.readLong();
        main = in.readParcelable(MainWeather.class.getClassLoader());
        weather = in.createTypedArrayList(Weather.CREATOR);
        clouds = in.readParcelable(Overcast.class.getClassLoader());
        wind = in.readParcelable(Wind.class.getClassLoader());
        snow = in.readParcelable(Snow.class.getClassLoader());
        rain = in.readParcelable(Rain.class.getClassLoader());
        sys = in.readParcelable(DatedWeatherSys.class.getClassLoader());
        dtTxt = in.readString();

        this.time = new GregorianCalendar();
        this.time.setTimeInMillis(this.dt*1000);
    }

    public static final Creator<DatedWeather> CREATOR = new Creator<DatedWeather>() {
        @Override
        public DatedWeather createFromParcel(Parcel in) {
            return new DatedWeather(in);
        }

        @Override
        public DatedWeather[] newArray(int size) {
            return new DatedWeather[size];
        }
    };

    @Override
    public String toString() {
        return "DatedWeather{" +
                "dt=" + dt +
                ", time=" + time.getTime().toString() +
                ", main=" + main +
                ", weather=" + weather +
                ", clouds=" + clouds +
                ", wind=" + wind +
                ", snow=" + snow +
                ", rain=" + rain +
                ", sys=" + sys +
                ", dtTxt='" + dtTxt + '\'' +
                '}';
    }

    public long getUnixDate() {
        return dt;
    }

    public String getTime() {
//        return time;
        int day = time.get(Calendar.DAY_OF_MONTH);
        int month = time.get(Calendar.MONTH) + 1;
        int hour = time.get(Calendar.HOUR_OF_DAY);
        int minute = time.get(Calendar.MINUTE);
        return "" + (day < 10 ? "0" + day : day) + "-" + (month < 10 ? "0" + month : month) + " "
                + (hour < 10 ? "0" + hour : hour) + ":" + (minute < 10 ? "0" + minute : minute);
    }

    public MainWeather getMainWeather() {
        return main;
    }

    public ArrayList<Weather> getWeatherList() {
        return weather;
    }

    public Overcast getClouds() {
        return clouds;
    }

    public Wind getWind() {
        return wind;
    }

    public Snow getSnow() {
        return snow;
    }

    public Rain getRain() {
        return rain;
    }

    public DatedWeatherSys getSys() {
        return sys;
    }

    public String getTextDate() {
        return dtTxt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(dt);
        dest.writeParcelable(main, flags);
        dest.writeTypedList(weather);
        dest.writeParcelable(clouds, flags);
        dest.writeParcelable(wind, flags);
        dest.writeParcelable(snow, flags);
        dest.writeParcelable(rain, flags);
        dest.writeParcelable(sys, flags);
        dest.writeString(dtTxt);
    }
}
