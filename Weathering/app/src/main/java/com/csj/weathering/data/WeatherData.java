package com.csj.weathering.data;

import java.util.ArrayList;
import java.util.List;

public class WeatherData {
    private static WeatherData instance = new WeatherData();
    private List<Weather> weathers;
    private WeatherData(){
        weathers = new ArrayList<>();
    }
    public static WeatherData getInstance() { return instance; }
    public List<Weather> getWeathers() { return weathers; }
    public void setWeathers(List<Weather> weathersData) {
        weathers.clear();
        this.weathers = weathersData;
    }

//    private void setWeathersByJson(){
//
//    }
}