package com.csj.weathering.data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Weather {
    private double temp;
    private String main; // weather
    private String desc; // weather description
    private LocalDateTime dateTime; // datetime division by 24hours

    public Weather(double temp, String main, String desc, String dateTime) {
        this.temp = temp;
        this.main = main;
        this.desc = desc;
        this.dateTime = LocalDateTime.parse(dateTime,DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}
