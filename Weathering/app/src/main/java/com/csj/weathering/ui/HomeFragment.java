package com.csj.weathering.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.csj.weathering.MainViewModel;
import com.csj.weathering.data.Weather;
import com.csj.weathering.data.WeatherData;
import com.csj.weathering.databinding.FragmentHomeBinding;
import com.csj.weathering.ui.list.MainAdapter;
import com.csj.weathering.ui.list.WeatherAdapter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements MainAdapter.OnListItemListener{

    private MainViewModel viewModel;
    private NavController controller;
    private FragmentHomeBinding binding;
    private List<Weather> weathers;
    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getWeathers().observe(getViewLifecycleOwner(),weatherData-> {
            if (weatherData.size() != 0) {
                weathers = weatherData;
                List<Weather> days = getDayWeather(weathers);
                MainAdapter adapter = new MainAdapter(this);

                adapter.setWeathers(days);
                binding.recyclerViewDay.setAdapter(adapter);
                LinearLayoutManager manager = new LinearLayoutManager(requireContext()); // 오리엔테이션을 주는 생성자 or 매니저 생성 후 orientation 지정
                manager.setOrientation(RecyclerView.HORIZONTAL);
                binding.recyclerViewDay.setLayoutManager(manager);
            }
        });
//        weathers = WeatherData.getInstance().getWeathers();
    }

    public List<Weather> getDayWeather(List<Weather> weathers){
        List<Weather> days = new ArrayList<>();
        int rainCount = 0;
        for(Weather item : weathers){
            Weather weather = item;
            if(weather.dateTime.isBefore(LocalDateTime.now())) continue;
            if(days.size() == 8) break;
            if(weather.dateTime.getDayOfMonth() == LocalDateTime.now().getDayOfMonth() && weather.main.equals("Rain")) rainCount++;
            days.add(weather);
        }
        if(rainCount > 0){
            binding.textViewRequire.setText("오늘은 우산이 필요해요!");
        }

        return days;
    }

    @Override
    public void onItemClick(Weather weather) {

    }
}
