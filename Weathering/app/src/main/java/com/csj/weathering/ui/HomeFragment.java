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

public class HomeFragment extends Fragment{

    private MainViewModel viewModel;
    private NavController controller;
    private FragmentHomeBinding binding;

    private final int SNOW = 0;
    private final int RAIN = 1;
    private final int CLOUD = 2;
    private final int CLEAR = 3;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Weather> weathers = WeatherData.getInstance().getWeathers();
        List<Weather> days = getDaysWeather(weathers);
        MainAdapter adapter = new MainAdapter(this);

        adapter.setWeathers(days);
        binding.recyclerViewDay.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(requireContext()); // 오리엔테이션을 주는 생성자 or 매니저 생성 후 orientation 지정
        manager.setOrientation(RecyclerView.HORIZONTAL);
        binding.recyclerViewDay.setLayoutManager(manager);
        /*binding.buttonLogin.setOnClickListener(v->{
            String id = binding.editTextUserId.getText().toString();
            if(!id.isEmpty()){
                viewModel.setUserId(id);
            }
        });*/
    }

    public List<Weather> getDaysWeather(List<Weather> weathers){

        List<Weather> days = new ArrayList<>();
//            int originDay = weathers.get(0).dateTime.getDayOfMonth();
        LocalDateTime date = weathers.get(0).dateTime;

        for(int i = 0; i < 8; i++){

            String icon = getIconName(weathers.get(i).icon);
            Weather weather = weathers.get(i);
            Weather weatherTemp = new Weather("");
            days.add(weatherTemp);
        }
        return days;
    }

    public String getIconName(String iconName){

        switch(iconName){
            case SNOW:
                iconName = "13";
                break;
            case RAIN:
                iconName = "10";
                break;
            case CLOUD:
                iconName = "03";
                break;
            case CLEAR:
                iconName = "01";
                break;
            default:
                iconName = "50";
        }
        return iconName+"d";
    }
}
