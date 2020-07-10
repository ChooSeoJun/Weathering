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
    import androidx.navigation.Navigation;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import com.csj.weathering.MainViewModel;
    import com.csj.weathering.R;
    import com.csj.weathering.data.Weather;
    import com.csj.weathering.data.WeatherData;
    import com.csj.weathering.databinding.FragmentWeatherBinding;
    import com.csj.weathering.ui.list.WeatherAdapter;

    import java.lang.reflect.Array;
    import java.time.LocalDateTime;
    import java.util.ArrayList;
    import java.util.Collection;
    import java.util.Collections;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

    public class WeatherFragment extends Fragment implements WeatherAdapter.OnListItemListener {

        private MainViewModel viewModel;
        private FragmentWeatherBinding binding;
        private NavController controller;
        private List<Weather> weathers;

        private final int SNOW = 0;
        private final int RAIN = 1;
        private final int CLOUD = 2;
        private final int CLEAR = 3;

        public View onCreateView(@NonNull LayoutInflater inflater,
                ViewGroup container, Bundle savedInstanceState) {
            viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
            binding = FragmentWeatherBinding.inflate(inflater);
            return binding.getRoot();
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
    //        controller=Navigation.findNavController(view);

            viewModel.getWeathers().observe(getViewLifecycleOwner(),weatherData-> {
                if (weatherData.size() != 0) {
                    weathers = weatherData;
                    List<Weather> days = getDaysWeather(weathers);
                    WeatherAdapter adapter = new WeatherAdapter(this);

                    adapter.setWeathers(days);
                    binding.recyclerViewDays.setAdapter(adapter);
                    LinearLayoutManager manager = new LinearLayoutManager(requireContext()); // 오리엔테이션을 주는 생성자 or 매니저 생성 후 orientation 지정
                    manager.setOrientation(RecyclerView.HORIZONTAL);
                    binding.recyclerViewDays.setLayoutManager(manager);
                }
            });
//            List<Weather> weathers = WeatherData.getInstance().getWeathers();

    //        binding.buttonTest.setOnClickListener(v->{
    //            NavController controller = Navigation.findNavController(view); // NavController로 작업
    //            controller.navigate(R.id.action_navigation_menu_to_navigation_menu_detail);//화살표 이름
    //        });
        }

        @Override
        public void onItemClick(Weather menu) {

        }

        public List<Weather> getDaysWeather(List<Weather> weathers){

            List<Weather> days = new ArrayList<>();
//            int originDay = weathers.get(0).dateTime.getDayOfMonth();
            LocalDateTime date = weathers.get(0).dateTime;

            for(int i = 0; i < 5; i++){
                List<Weather> weathersByDatetime = new ArrayList<>();
                double minTemp;
                double maxTemp;
                LocalDateTime finalOriginDay = date.plusDays(i);
//                int finalOriginDay = originDay + i;
                for(Weather item : weathers)
                {
//                    Log.i("[Item datetime]",item.dateTime.toString());
                    if(item.dateTime.getDayOfMonth() == finalOriginDay.getDayOfMonth())
                        weathersByDatetime.add(item);
//                    Log.i("[Compare Result]",item.dateTime.getDayOfMonth() == finalOriginDay?item.dateTime.getDayOfMonth() + " is true":"false");
                }
//                weathersByDatetime.stream()
//                        .filter(weather-> weather.dateTime.getDayOfMonth() == finalOriginDay)
//                        .findAny()
//                        .orElse(null);

                minTemp = weathersByDatetime.get(0).minTemp;
                maxTemp = weathersByDatetime.get(0).maxTemp;
                int[] weathersCount = new int[4];

                for(Weather item : weathersByDatetime){
                    if(item.minTemp < minTemp) minTemp = item.minTemp;
                    if(item.maxTemp > maxTemp) maxTemp = item.maxTemp;

                    int index = getIndexByWeather(item);
                    weathersCount[index]+=1;
                    Log.i("[WeatherIndex]", String.valueOf(weathersCount[index]));
                }
                String icon = getIconName(weathersCount);

                Weather weatherTemp = new Weather(0f,minTemp,maxTemp,"","",weathersByDatetime.get(0).dateTime,icon);
                days.add(weatherTemp);
            }
            return days;
        }

        public int getIndexByWeather(Weather weather){

            String desc = weather.desc;
            if(desc.contains("snow"))
                return SNOW;
            else if(desc.contains("rain"))
                return RAIN;
            else if(!desc.equals("few clouds") && desc.contains("clouds"))
                return CLOUD;
            else if(desc.contains("few clouds") || desc.contains("clear"))
                return CLEAR;
            return CLOUD;
        }

        public String getIconName(int[] weathersCount){
            int maxResult = weathersCount[0];
            int maxIndex = 0;
            for(int i=0;i<weathersCount.length;i++){
                if(weathersCount[i] > maxResult){
                    maxResult = weathersCount[i];
                    maxIndex = i;
                }
            }

            String iconName;

            switch(maxIndex){
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
