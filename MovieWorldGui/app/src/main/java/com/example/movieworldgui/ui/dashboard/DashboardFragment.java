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
import com.example.movieworldgui.api.TicketApiModel;
import com.example.movieworldgui.databinding.FragmentDashboardBinding;
import com.example.movieworldgui.ui.ownedticket.OwnedTicketViewModel;
import com.example.movieworldgui.ui.ownedticket.placeholder.PlaceholderTickets;

import static android.app.Activity.RESULT_CANCELED;

public class DashboardFragment extends Fragment {

    private SelectedTicketViewModel selectedTicketViewModel;
    private FragmentDashboardBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        selectedTicketViewModel = new ViewModelProvider(requireActivity()).get(SelectedTicketViewModel.class);
        OwnedTicketViewModel ownedTickets = new ViewModelProvider(requireActivity()).get(OwnedTicketViewModel.class);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        MainActivity host = (MainActivity) getActivity();

        final TextView selectedTicket = binding.selectedTicketOne;
        selectedTicketViewModel.getItem().observe(getViewLifecycleOwner(), value -> {

            if (value != null) {
                selectedTicket.setText(value.getMoviename());
            } else {
                selectedTicket.setText("");
            }
        });

        binding.shareTicket.setOnClickListener(l -> {

            TicketApiModel sharedTicket = selectedTicketViewModel.getItem().getValue();
            if (sharedTicket == null || sharedTicket.getID().length() <= 0) return;

            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if (bluetoothAdapter == null) {
                host.sendToastMessage("Your device has no bluetooth support");
                return; // no bluetooth support
            }

            if (bluetoothAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
                Intent serveBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                serveBluetooth.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                startActivityForResult(serveBluetooth, 1);
            } else {
                shareTicket(sharedTicket);
            }
        });

        binding.useTicket.setOnClickListener(l -> {

            TicketApiModel usedTicket = selectedTicketViewModel.getItem().getValue();
            if (usedTicket == null || usedTicket.getID().length() <= 0) return;

//TODO - delete in api
            selectedTicketViewModel.getItem().setValue(null);

            host.sendToastMessage("Ticket used successfully.");
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {

        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getActivity().getApplicationContext(), "Please accept the request to use the app", Toast.LENGTH_LONG).show();
        } else {

            TicketApiModel sharedTicket = selectedTicketViewModel.getItem().getValue();
            if (sharedTicket == null || sharedTicket.getID().length() <= 0) return;
            
            shareTicket(sharedTicket);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void shareTicket(TicketApiModel sharedTicket) {
        MainActivity host = (MainActivity) getActivity();
        host.shareTicket(sharedTicket);
    }
}