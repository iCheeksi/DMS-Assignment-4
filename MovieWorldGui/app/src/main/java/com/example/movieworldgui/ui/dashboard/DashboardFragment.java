package com.example.movieworldgui.ui.dashboard;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.movieworldgui.MainActivity;
import com.example.movieworldgui.databinding.FragmentDashboardBinding;

import static android.app.Activity.RESULT_CANCELED;

public class DashboardFragment extends Fragment {

    private SelectedTicketViewModel selectedTicketViewModel;
    private FragmentDashboardBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        selectedTicketViewModel = new ViewModelProvider(requireActivity()).get(SelectedTicketViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView selectedTicket = binding.selectedTicketOne;
        selectedTicketViewModel.getItem().observe(getViewLifecycleOwner(), value -> selectedTicket.setText(value.content));

        binding.shareTicket.setOnClickListener(l -> {

            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if (bluetoothAdapter == null) {
                Toast.makeText(requireActivity().getApplicationContext(), "Your device has no bluetooth support", Toast.LENGTH_LONG).show();
                return; // no bluetooth support
            }

            if (bluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
                Intent serveBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                serveBluetooth.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                startActivityForResult(serveBluetooth, 1);
            } else {
                shareTicket();
            }
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {

        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getActivity().getApplicationContext(), "Please accept the request to use the app", Toast.LENGTH_LONG).show();
        }else {
            shareTicket();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void shareTicket() {
        MainActivity host = (MainActivity) getActivity();
        host.shareTicket(selectedTicketViewModel.getItem().getValue());
    }
}