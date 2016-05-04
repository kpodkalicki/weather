package com.nomader.weather.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by nomader on 18.01.16.
 */
public class City implements Parcelable {
    public static String TAG = "City";

    private String id, name, country;
    private long population;
    private CitySys sys;
    private Coordinates coordinates;


    public City(JSONObject jsonObject) {
        try {
            this.id = jsonObject.getString("id");
            this.name = jsonObject.getString("name");
            this.country = jsonObject.getString("country");

            this.population = jsonObject.getLong("population");

            this.sys = new CitySys(jsonObject.getJSONObject("sys"));
            this.coordinates = new Coordinates(jsonObject.getJSONObject("coord"));
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    protected City(Parcel in) {
        id = in.readString();
        name = in.readString();
        country = in.readString();
        population = in.readLong();
        sys = in.readParcelable(CitySys.class.getClassLoader());
        coordinates = in.readParcelable(Coordinates.class.getClassLoader());
    }

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };

    @Override
    public String toString() {
        return "City{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", population=" + population +
                ", sys=" + sys +
                ", coordinates=" + coordinates +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }


    public long getPopulation() {
        return population;
    }


    public CitySys getSys() {
        return sys;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(country);
        dest.writeLong(population);
        dest.writeParcelable(sys, flags);
        dest.writeParcelable(coordinates, flags);
    }
}
