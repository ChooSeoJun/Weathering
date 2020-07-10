package com.csj.weathering.data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Weather {
    public double temp;
    public double minTemp;
    public double maxTemp;
    public String main; // weather
    public String desc; // weather description
    public LocalDateTime dateTime; // datetime division by 24hours
    public String icon;
    public Weather() { }
    public Weather(double temp, double minTemp, double maxTemp, String main, String desc, LocalDateTime dateTime, String icon) {
        this.temp = temp;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.main = main;
        this.desc = desc;
        this.dateTime = dateTime;
        this.icon = icon;
    }
    public String toString(){
        return "Weather: "+ this.main + " / Temperature: " + this.temp + " / Time: " + this.dateTime.getHour();
    }
}
