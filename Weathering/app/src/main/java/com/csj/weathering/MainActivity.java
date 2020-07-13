package com.csj.weathering;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.csj.weathering.data.NotificationData;
import com.csj.weathering.data.Weather;
import com.csj.weathering.data.WeatherData;
import com.csj.weathering.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    JSONObject data = null;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        regist();
        getJSON("Daegu"); // set variation : default - korea

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_weather, R.id.navigation_home, R.id.navigation_notification)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class); // ViewModelProvider를 이용해서 viewModel 객체를 만들어야함 - 하나로 사용할 것이기 때문

    }

    /*public void regist(){
        Intent intent = new Intent(this, Alarm.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0,intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 20);
        calendar.set(Calendar.MINUTE, 40);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // 지정한 시간에 매일 알림
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),  AlarmManager.INTERVAL_DAY, pIntent);

    }// regist()..

    public void unregist(View view) {
        Intent intent = new Intent(this, Alarm.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmManager.cancel(pIntent);
    }// unregist()..*/


    @SuppressLint("StaticFieldLeak")
    public void getJSON(final String city) {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    URL url = new URL("https://api.openweathermap.org/data/2.5/forecast?q="+city+"&units=metric&APPID=a9430abd21c6ab31869ce13cd2966b6f"); //weather -> forecast 5days / 3hours
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    BufferedReader reader =
                            new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuffer json = new StringBuffer(1024);
                    String tmp = "";
                    while((tmp = reader.readLine()) != null)
                        json.append(tmp).append("\n");
                    reader.close();
                    data = new JSONObject(json.toString());
                    if(data.getInt("cod") != 200) { // data.getInt, getDouble로 쪼개서 사용
                        System.out.println("Cancelled");
                        return null;                                                                                   
                    }
                } catch (Exception e) {
                    System.out.println("Weather Exception "+ e.getMessage());
                    return null;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void Void) {
                if(data!=null){
                    List<Weather> weathers = new ArrayList<>();
                    try {
                        JSONArray array = data.getJSONArray("list");

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
                                weather.dateTime = LocalDateTime.parse(dt,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
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
                    viewModel.setWeathers(weathers);
//                    Log.i("[Check Null]",NotificationData.getInstance().getInformation().get(0).title);
                    viewModel.setInformations(NotificationData.getInstance().getInformation());
//                    Log.i("[WeatherData]",WeatherData.getInstance().getWeathers().toString());
                }
            }
        }.execute();
    }
}
