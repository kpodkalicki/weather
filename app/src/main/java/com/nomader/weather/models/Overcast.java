package com.nomader.weather.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by nomader on 18.01.16.
 */
public class Overcast implements Parcelable {
    public static String TAG = "Overcast";

    private int all;

    public Overcast(JSONObject jsonObject){
        try {
            this.all = jsonObject.getInt("all");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected Overcast(Parcel in) {
        all = in.readInt();
    }

    public static final Creator<Overcast> CREATOR = new Creator<Overcast>() {
        @Override
        public Overcast createFromParcel(Parcel in) {
            return new Overcast(in);
        }

        @Override
        public Overcast[] newArray(int size) {
            return new Overcast[size];
        }
    };

    @Override
    public String toString() {
        return "Overcast{" +
                "all=" + all +
                '}';
    }

    public int getAll() {
        return all;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(all);
    }
}
