package com.nomader.weather.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by nomader on 18.01.16.
 */
public class Wind implements Parcelable {
    public static String TAG = "Wind";

    private double speed, deg;

    public Wind(JSONObject jsonObject) {
        try {
            this.speed = jsonObject.getDouble("speed");
        } catch (JSONException e) {
            this.speed = 0.0;
        }
        try {
            this.deg = jsonObject.getDouble("deg");
        } catch (JSONException e) {
            this.deg = 0.0;
        }
    }

    protected Wind(Parcel in) {
        speed = in.readDouble();
        deg = in.readDouble();
    }

    public static final Creator<Wind> CREATOR = new Creator<Wind>() {
        @Override
        public Wind createFromParcel(Parcel in) {
            return new Wind(in);
        }

        @Override
        public Wind[] newArray(int size) {
            return new Wind[size];
        }
    };

    @Override
    public String toString() {
        return "Wind{" +
                "speed=" + speed +
                ", deg=" + deg +
                '}';
    }

    public double getSpeed() {
        return speed;
    }

    public double getDirection() {
        return deg;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(speed);
        dest.writeDouble(deg);
    }
}
