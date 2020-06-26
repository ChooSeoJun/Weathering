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

    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    public class WeatherFragment extends Fragment implements WeatherAdapter.OnListItemListener {

        private MainViewModel viewModel;
        private FragmentWeatherBinding binding;
        private NavController controller;
        private HashMap<String,Integer> weatherMap;

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
            List<Weather> weathers = WeatherData.getInstance().getWeathers();
            List<Weather> days = getDaysWeather(weathers);
            WeatherAdapter adapter = new WeatherAdapter(this);

            adapter.setWeathers(days);
            binding.recyclerViewDays.setAdapter(adapter);
            LinearLayoutManager manager = new LinearLayoutManager(requireContext()); // 오리엔테이션을 주는 생성자 or 매니저 생성 후 orientation 지정
            manager.setOrientation(RecyclerView.HORIZONTAL);
            binding.recyclerViewDays.setLayoutManager(manager);
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
            int originDay = weathers.get(0).dateTime.getDayOfMonth();

            weatherMap= new HashMap<>();

            for(int i = 0; i < 5; i++){
                List<Weather> weathersByDatetime = new ArrayList<>();
                double minTemp;
                double maxTemp;
                int finalOriginDay = originDay + i;

                for(Weather item : weathers)
                {
//                    Log.i("[Item datetime]",item.dateTime.toString());
                    if(item.dateTime.getDayOfMonth() == finalOriginDay)
                        weathersByDatetime.add(item);
//                    Log.i("[Compare Result]",item.dateTime.getDayOfMonth() == finalOriginDay?item.dateTime.getDayOfMonth() + " is true":"false");
                }
//                weathersByDatetime.stream()
//                        .filter(weather-> weather.dateTime.getDayOfMonth() == finalOriginDay)
//                        .findAny()
//                        .orElse(null);

                minTemp = weathersByDatetime.get(0).minTemp;
                maxTemp = weathersByDatetime.get(0).maxTemp;

                for(Weather item : weathersByDatetime){
                    if(item.minTemp < minTemp) minTemp = item.minTemp;
                    if(item.maxTemp > maxTemp) maxTemp = item.maxTemp;
                }

                Weather weatherTemp = new Weather(0f,minTemp,maxTemp,"","",weathersByDatetime.get(0).dateTime);
                days.add(weatherTemp);
            }
            return days;
        }

        public int getIndexByWeather(Weather weather){

            weather.

            return 0;
        }
    }
