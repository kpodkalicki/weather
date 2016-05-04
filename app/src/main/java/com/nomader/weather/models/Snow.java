package com.nomader.weather.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by nomader on 18.01.16.
 */
public class Snow  implements Parcelable {
    public static String TAG = "Snow";

    private double threeHour;

    public Snow(JSONObject jsonObject){
        try {
            this.threeHour = jsonObject.getDouble("3h");
        } catch (JSONException e) {
            this.threeHour = 0.0;
        }
    }

    protected Snow(Parcel in) {
        threeHour = in.readDouble();
    }

    public static final Creator<Snow> CREATOR = new Creator<Snow>() {
        @Override
        public Snow createFromParcel(Parcel in) {
            return new Snow(in);
        }

        @Override
        public Snow[] newArray(int size) {
            return new Snow[size];
        }
    };

    @Override
    public String toString() {
        return "Snow{" +
                "threeHour=" + threeHour +
                '}';
    }

    public double getThreeHour() {
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
