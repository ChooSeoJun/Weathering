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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.csj.weathering.MainViewModel;
import com.csj.weathering.data.Information;
import com.csj.weathering.databinding.FragmentNotificationBinding;
import com.csj.weathering.ui.list.MainAdapter;
import com.csj.weathering.ui.list.NotificationAdapter;

import java.util.List;
import java.util.Locale;

public class NotificationFragment extends Fragment implements NotificationAdapter.OnListItemListener{

    private MainViewModel viewModel;
    private FragmentNotificationBinding binding;
    private List<Information> notifications;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        binding = FragmentNotificationBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getInformations().observe(getViewLifecycleOwner(),notificationData-> {
            if (notificationData.size() != 0) {
                notifications = notificationData;
                Log.i("[Check data]",notifications.get(0).title);
                NotificationAdapter adapter = new NotificationAdapter(this);

                adapter.setInformations(notifications);
                binding.recyclerViewNotification.setAdapter(adapter);

                LinearLayoutManager manager = new LinearLayoutManager(requireContext()); // 오리엔테이션을 주는 생성자 or 매니저 생성 후 orientation 지정
                manager.setOrientation(RecyclerView.VERTICAL);
                binding.recyclerViewNotification.setLayoutManager(manager);
            }
        });
    }

    @Override
    public void onItemClick(Information notification) {

    }
}
