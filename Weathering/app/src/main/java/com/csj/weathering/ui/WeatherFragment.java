package com.csj.weathering.ui;

import android.os.Bundle;
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

import com.csj.weathering.MainViewModel;
import com.csj.weathering.R;
import com.csj.weathering.data.Weather;
import com.csj.weathering.data.WeatherData;
import com.csj.weathering.databinding.FragmentMenuBinding;
import com.csj.weathering.ui.list.WeatherAdapter;

import java.util.List;

public class WeatherFragment extends Fragment implements WeatherAdapter.OnListItemListener {

    private MainViewModel viewModel;
    private FragmentMenuBinding binding;
    private NavController controller;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        binding = FragmentMenuBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        controller=Navigation.findNavController(view);
        List<Weather> weathers = WeatherData.getInstance().getWeathers();
        WeatherAdapter adapter = new WeatherAdapter(this);
        adapter.setMenus(weathers);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
//        binding.buttonTest.setOnClickListener(v->{
//            NavController controller = Navigation.findNavController(view); // NavController로 작업
//            controller.navigate(R.id.action_navigation_menu_to_navigation_menu_detail);//화살표 이름
//        });
    }

    @Override
    public void onItemClick(Weather menu) {

    }
}
