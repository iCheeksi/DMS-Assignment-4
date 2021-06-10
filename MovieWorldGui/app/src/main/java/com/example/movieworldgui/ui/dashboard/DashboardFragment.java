package com.example.movieworldgui.ui.dashboard;

import androidx.lifecycle.ViewModelProvider;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.movieworldgui.BlueToothService;
import com.example.movieworldgui.SelectedTicketViewModel;
import com.example.movieworldgui.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private SelectedTicketViewModel selectedTicketViewModel;
    private FragmentDashboardBinding binding;
    private BlueToothService blueToothService;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        blueToothService = new BlueToothService();
        selectedTicketViewModel = new ViewModelProvider(this).get(SelectedTicketViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView selectedTicket = binding.selectedTicketOne;
        selectedTicketViewModel.getItem().observe(getViewLifecycleOwner(), value -> selectedTicket.setText(value.content));

        binding.shareTicket.setOnClickListener(l -> {

            Intent serveBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            serveBluetooth.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivityForResult(serveBluetooth, 1);

            blueToothService.shareTicket(selectedTicketViewModel.getItem().getValue().content);
        } );

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}