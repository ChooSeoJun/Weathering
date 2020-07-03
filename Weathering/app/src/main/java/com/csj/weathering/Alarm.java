package com.csj.weathering;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.csj.weathering.data.Weather;
import com.csj.weathering.data.WeatherData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Alarm extends BroadcastReceiver {

    private JSONObject data = null;
    private SharedPreferences pref;

    @Override
    public void onReceive(Context context, Intent intent) {
        updateWeatherData(context);
        setLocalWeather(context);
    }

    private void updateWeatherData(Context context) {
        pref = context.getSharedPreferences("weather",Context.MODE_PRIVATE); // please add MODE_PRIVATE value what you want limit calling by another app
        SharedPreferences.Editor editor = pref.edit();

        if(pref != null) {
            editor.clear();
            editor.commit();
        }

        getJSON("Daegu");
        if(data!=null) editor.putString("data",data.toString());
        editor.commit();
    }
    
    public void getJSON(final String city) {

        try {
            URL url = new URL("https://api.openweathermap.org/data/2.5/forecast?q=" + city + "&units=metric&APPID=a9430abd21c6ab31869ce13cd2966b6f"); //weather -> forecast 5days / 3hours
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer json = new StringBuffer(1024);
            String tmp = "";
            while ((tmp = reader.readLine()) != null)
                json.append(tmp).append("\n");
            reader.close();
            JSONObject data = new JSONObject(json.toString());
            if (data.getInt("cod") != 200) { // data.getInt, getDouble로 쪼개서 사용
                System.out.println("Cancelled");
            }
        } catch (Exception e) {
            System.out.println("Weather Exception " + e.getMessage());
        }
    }

    public void setLocalWeather(Context context) {
        pref = context.getSharedPreferences("weather",Context.MODE_PRIVATE); // please add MODE_PRIVATE value what you want limit calling by another app
        SharedPreferences.Editor editor = pref.edit();
        if(pref != null){
            List<Weather> weathers = new ArrayList<>();
            try {
                JSONObject json = null;
                String strJson = pref.getString("data",null);

                if(strJson != null) json = new JSONObject(strJson);
                JSONArray array = json.getJSONArray("list");

                for(int i=0;i<array.length();i++){
                    Weather weather = new Weather();
                    weather.temp = array.getJSONObject(i).getJSONObject("main").getDouble("temp");
                    weather.minTemp = array.getJSONObject(i).getJSONObject("main").getDouble("temp_min");
                    weather.maxTemp = array.getJSONObject(i).getJSONObject("main").getDouble("temp_max");
                    weather.main = array.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main");
                    weather.desc = array.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description");
                    weather.icon = array.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("icon");
                    try
                    {
                        String dt = array.getJSONObject(i).getString("dt_txt");
                        weather.dateTime = LocalDateTime.parse(dt, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));;
                    } catch(Exception e) {
                        System.out.println("Date Exception"+ e.getMessage());
                    }
                    weathers.add(weather);
//                            Log.i("[Weather]","Temp: " + weather.temp + " Main: " + weather.main + " Desc: " + weather.desc + " DateTime: " + weather.dateTime);
                }
            } catch (JSONException e) {
                System.out.println("Weather Exception "+ e.getMessage());
            }
            WeatherData.getInstance().setWeathers(weathers);
//                    Log.i("[WeatherData]",WeatherData.getInstance().getWeathers().toString());
        }
    }
}

//    @SuppressLint("StaticFieldLeak")
//    public void getJSON(final String city) {
//        new AsyncTask<Void, Void, Void>() {
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//            }
//
//            @Override
//            protected Void doInBackground(Void... params) {
//                try {
//                    URL url = new URL("https://api.openweathermap.org/data/2.5/forecast?q="+city+"&units=metric&APPID=a9430abd21c6ab31869ce13cd2966b6f"); //weather -> forecast 5days / 3hours
//                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                    BufferedReader reader =
//                            new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                    StringBuffer json = new StringBuffer(1024);
//                    String tmp = "";
//                    while((tmp = reader.readLine()) != null)
//                        json.append(tmp).append("\n");
//                    reader.close();
//                    data = new JSONObject(json.toString());
//                    if(data.getInt("cod") != 200) { // data.getInt, getDouble로 쪼개서 사용
//                        System.out.println("Cancelled");
//                        return null;
//                    }
//                } catch (Exception e) {
//                    System.out.println("Weather Exception "+ e.getMessage());
//                    return null;
//                }
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void Void) {
//                if(){
//                    List<Weather> weathers = new ArrayList<>();
//                    try {
//                        JSONArray array = data.getJSONArray("list");
//
//                        for(int i=0;i<array.length();i++){
//                            Weather weather = new Weather();
//                            weather.temp = array.getJSONObject(i).getJSONObject("main").getDouble("temp");
//                            weather.minTemp = array.getJSONObject(i).getJSONObject("main").getDouble("temp_min");
//                            weather.maxTemp = array.getJSONObject(i).getJSONObject("main").getDouble("temp_max");
//                            weather.main = array.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main");
//                            weather.desc = array.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description");
//                            weather.icon = array.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("icon");
//                            try
//                            {
//                                String dt = array.getJSONObject(i).getString("dt_txt");
//                                weather.dateTime = LocalDateTime.parse(dt, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));;
//                            } catch(Exception e) {
//                                System.out.println("Date Exception"+ e.getMessage());
//                            }
//                            weathers.add(weather);
////                            Log.i("[Weather]","Temp: " + weather.temp + " Main: " + weather.main + " Desc: " + weather.desc + " DateTime: " + weather.dateTime);
//                        }
//                    } catch (JSONException e) {
//                        System.out.println("Weather Exception "+ e.getMessage());
//                    }
//                    WeatherData.getInstance().setWeathers(weathers);
////                    Log.i("[WeatherData]",WeatherData.getInstance().getWeathers().toString());
//                }
//            }
//        }.execute();
//    }

