package com.nomader.weather.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by nomader on 18.01.16.
 */
public class Rain  implements Parcelable {
    public static String TAG = "Rain";

    private double threeHour;

    public Rain(JSONObject jsonObject){
        try {
            this.threeHour = jsonObject.getDouble("3h");
        } catch (JSONException e) {
            this.threeHour = 0.0;
        }
    }

    protected Rain(Parcel in) {
        threeHour = in.readDouble();
    }

    public static final Creator<Rain> CREATOR = new Creator<Rain>() {
        @Override
        public Rain createFromParcel(Parcel in) {
            return new Rain(in);
        }

        @Override
        public Rain[] newArray(int size) {
            return new Rain[size];
        }
    };

    @Override
    public String toString() {
        return "Rain{" +
                "threeHour=" + threeHour +
                '}';
    }

    public double getThreeHourRain() {
        return threeHour;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(threeHour);
    }
}
