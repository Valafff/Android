package com.amicus.myapplication;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by sizik on 25.09.2025.
 */
public interface WeatherApi {
    @GET("weather")
    Call<WeatherResponse> getWeather(@Query("q") String city,@Query("appid")String apiKey,@Query("units")String units);
}
