package com.amicus.myapplication;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherResponse {

    @SerializedName("main")
    Main main;

    @SerializedName("coord")
    Coord coord;

    @SerializedName("weather")
    List<Weather> weather;

    public class Main {
        @SerializedName("temp")
        float temp;
        @SerializedName("humidity")
        float humidity;
    }

    public class Coord
    {
        @SerializedName("lon")
        float lon;
        @SerializedName("lat")
        float lat;
    }

    public class Weather {
        @SerializedName("description")
        String description;
    }
}
