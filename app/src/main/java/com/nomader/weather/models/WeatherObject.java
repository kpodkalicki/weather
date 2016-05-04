package com.nomader.weather.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nomader on 18.01.16.
 */
public class WeatherObject implements Parcelable{
    public static String TAG = "WeatherObject";

    private City city;
    private String cod;
    private double message;
    private int cnt;
    private ArrayList<DatedWeather> list;

    public WeatherObject(JSONObject jsonObject){
        try {
            this.city = new City(jsonObject.getJSONObject("city"));
            this.cod = jsonObject.getString("cod");
            this.cnt = jsonObject.getInt("cnt");
            this.message = jsonObject.getDouble("message");
            list = new ArrayList<>();
            JSONArray array = jsonObject.getJSONArray("list");
            for (int i = 0; i < array.length(); i++) {
                list.add(new DatedWeather(array.getJSONObject(i)));
//                if(i == 0)Log.d(TAG, i + ": " + array.getJSONObject(i).getJSONObject("snow"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    protected WeatherObject(Parcel in) {
        city = in.readParcelable(City.class.getClassLoader());
        cod = in.readString();
        message = in.readDouble();
        cnt = in.readInt();
        list = in.createTypedArrayList(DatedWeather.CREATOR);
    }

    public static final Creator<WeatherObject> CREATOR = new Creator<WeatherObject>() {
        @Override
        public WeatherObject createFromParcel(Parcel in) {
            return new WeatherObject(in);
        }

        @Override
        public WeatherObject[] newArray(int size) {
            return new WeatherObject[size];
        }
    };

    @Override
    public String toString() {
        return "WeatherObject{" +
                "city=" + city +
                ", cod='" + cod + '\'' +
                ", message=" + message +
                ", cnt=" + cnt +
                ", list=" + list +
                '}';
    }

    public City getCity() {
        return city;
    }

    public String getCod() {
        return cod;
    }

    public double getMessage() {
        return message;
    }

    public int getCounter() {
        return cnt;
    }

    public ArrayList<DatedWeather> getList() {
        return list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(city, flags);
        dest.writeString(cod);
        dest.writeDouble(message);
        dest.writeInt(cnt);
        dest.writeTypedList(list);
    }
}
