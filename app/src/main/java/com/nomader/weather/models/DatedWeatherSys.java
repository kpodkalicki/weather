package com.nomader.weather.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by nomader on 18.01.16.
 */
public class DatedWeatherSys implements Parcelable{
    public static String TAG = "DatedWeatherSys";

    private String pod;

    public DatedWeatherSys(JSONObject jsonObject){
        try {
            this.pod = jsonObject.getString("pod");
        } catch (JSONException e) {
            this.pod = "";
        }
    }

    protected DatedWeatherSys(Parcel in) {
        pod = in.readString();
    }

    public static final Creator<DatedWeatherSys> CREATOR = new Creator<DatedWeatherSys>() {
        @Override
        public DatedWeatherSys createFromParcel(Parcel in) {
            return new DatedWeatherSys(in);
        }

        @Override
        public DatedWeatherSys[] newArray(int size) {
            return new DatedWeatherSys[size];
        }
    };

    @Override
    public String toString() {
        return "DatedWeatherSys{" +
                "pod='" + pod + '\'' +
                '}';
    }

    public String getPod() {
        return pod;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pod);
    }
}
