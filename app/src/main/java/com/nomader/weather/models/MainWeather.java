package com.nomader.weather.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by nomader on 18.01.16.
 */
public class MainWeather implements Parcelable{
    public static String TAG = "MainWeather";

    private double temp, tempMin, tempMax, pressure, seaLevel, grndLevel, tempKf;
    private int humidity;

    public MainWeather(JSONObject jsonObject){
        try {
            this.temp = jsonObject.getDouble("temp");
            this.tempMin = jsonObject.getDouble("temp_min");
            this.tempMax = jsonObject.getDouble("temp_max");
            this.pressure = jsonObject.getDouble("pressure");
            this.seaLevel = jsonObject.getDouble("sea_level");
            this.grndLevel = jsonObject.getDouble("grnd_level");
            this.humidity = jsonObject.getInt("humidity");
            this.tempKf = jsonObject.getDouble("temp_kf");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    protected MainWeather(Parcel in) {
        temp = in.readDouble();
        tempMin = in.readDouble();
        tempMax = in.readDouble();
        pressure = in.readDouble();
        seaLevel = in.readDouble();
        grndLevel = in.readDouble();
        tempKf = in.readDouble();
        humidity = in.readInt();
    }

    public static final Creator<MainWeather> CREATOR = new Creator<MainWeather>() {
        @Override
        public MainWeather createFromParcel(Parcel in) {
            return new MainWeather(in);
        }

        @Override
        public MainWeather[] newArray(int size) {
            return new MainWeather[size];
        }
    };

    @Override
    public String toString() {
        return "MainWeather{" +
                "temp=" + temp +
                ", tempMin=" + tempMin +
                ", tempMax=" + tempMax +
                ", pressure=" + pressure +
                ", seaLevel=" + seaLevel +
                ", grndLevel=" + grndLevel +
                ", tempKf=" + tempKf +
                ", humidity=" + humidity +
                '}';
    }

    public int getTemp() {
        return (int) -(273.15f - temp);
    }

    public double getTempMin() {
        return tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public int getPressure() {
        return (int)Math.round(pressure);
    }

    public double getSeaLevel() {
        return seaLevel;
    }

    public double getGroundLevel() {
        return grndLevel;
    }

    public double getTempKf() {
        return tempKf;
    }

    public int getHumidity() {
        return humidity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(temp);
        dest.writeDouble(tempMin);
        dest.writeDouble(tempMax);
        dest.writeDouble(pressure);
        dest.writeDouble(seaLevel);
        dest.writeDouble(grndLevel);
        dest.writeDouble(tempKf);
        dest.writeInt(humidity);
    }
}
