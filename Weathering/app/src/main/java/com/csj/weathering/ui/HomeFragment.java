package com.csj.weathering.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.csj.weathering.MainViewModel;
import com.csj.weathering.data.Weather;
import com.csj.weathering.data.WeatherData;
import com.csj.weathering.databinding.FragmentHomeBinding;
import com.csj.weathering.ui.list.WeatherAdapter;

import java.util.List;

public class HomeFragment extends Fragment{

    private MainViewModel viewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*binding.buttonLogin.setOnClickListener(v->{
            String id = binding.editTextUserId.getText().toString();
            if(!id.isEmpty()){
                viewModel.setUserId(id);
            }
        });*/
    }
}
