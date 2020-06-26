package com.csj.weathering.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.csj.weathering.MainViewModel;
import com.csj.weathering.databinding.FragmentNotificationBinding;

import java.util.Locale;

public class NotificationFragment extends Fragment {

    private MainViewModel viewModel;
    private FragmentNotificationBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        binding = FragmentNotificationBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        viewModel.getCity().observe(getViewLifecycleOwner(),city->{ // getValue == 한 번만 받을 때, observe로 하면 여러번 가능
//            if(city!=null)
//                binding.textViewUserId.setText(city);
//        });
    }
}
