package com.nomader.weather.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by nomader on 18.01.16.
 */
public class CitySys implements Parcelable{
    public static String TAG = "CitySys";

    private int population;

    public CitySys(JSONObject jsonObject){
        try {
            this.population = jsonObject.getInt("population");
        } catch (JSONException e) {
//            e.printStackTrace();
            this.population = 0;
        }
    }

    protected CitySys(Parcel in) {
        population = in.readInt();
    }

    public static final Creator<CitySys> CREATOR = new Creator<CitySys>() {
        @Override
        public CitySys createFromParcel(Parcel in) {
            return new CitySys(in);
        }

        @Override
        public CitySys[] newArray(int size) {
            return new CitySys[size];
        }
    };

    public int getPopulation() {
        return population;
    }

    @Override
    public String toString() {
        return "CitySys{" +
                "population=" + population +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(population);
    }
}
