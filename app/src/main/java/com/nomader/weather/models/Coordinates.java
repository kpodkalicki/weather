package com.nomader.weather.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by nomader on 18.01.16.
 */
public class Coordinates implements Parcelable {
    public static String TAG = "Coordinates";

    private double lon, lat;

    public Coordinates(JSONObject jsonObject){
        try {
            this.lon = jsonObject.getDouble("lon");
            this.lat = jsonObject.getDouble("lat");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    protected Coordinates(Parcel in) {
        lon = in.readDouble();
        lat = in.readDouble();
    }

    public static final Creator<Coordinates> CREATOR = new Creator<Coordinates>() {
        @Override
        public Coordinates createFromParcel(Parcel in) {
            return new Coordinates(in);
        }

        @Override
        public Coordinates[] newArray(int size) {
            return new Coordinates[size];
        }
    };

    @Override
    public String toString() {
        return "Coordinates{" +
                "lon=" + lon +
                ", lat=" + lat +
                '}';
    }

    public double getLongitude() {
        return lon;
    }

    public double getLatitude() {
        return lat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(lon);
        dest.writeDouble(lat);
    }
}
