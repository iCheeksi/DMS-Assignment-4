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
import com.example.movieworldgui.api.ApiMethods;
import com.example.movieworldgui.api.MovieApiModel;
import com.example.movieworldgui.api.TicketApiModel;
import com.example.movieworldgui.databinding.FragmentDashboardBinding;
import com.example.movieworldgui.ui.Helpers;
import com.example.movieworldgui.ui.home.ServerConnectionViewModel;
import com.example.movieworldgui.ui.ownedticket.OwnedTicketViewModel;

import static android.app.Activity.RESULT_CANCELED;

/***
 * Author - Shelby Mun (19049176) & Angelo Ryndon (18028033)
 */
public class DashboardFragment extends Fragment {

    private SelectedTicketViewModel selectedTicketViewModel;
    private ServerConnectionViewModel serverConnectionViewModel;
    private FragmentDashboardBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        selectedTicketViewModel = new ViewModelProvider(requireActivity()).get(SelectedTicketViewModel.class);
        serverConnectionViewModel = new ViewModelProvider(requireActivity()).get(ServerConnectionViewModel.class);
        OwnedTicketViewModel ownedTickets = new ViewModelProvider(requireActivity()).get(OwnedTicketViewModel.class);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        MainActivity host = (MainActivity) getActivity();

        final TextView selectedTicket = binding.selectedTicketOne;
        selectedTicketViewModel.getItem().observe(getViewLifecycleOwner(), value -> {

            if (value != null) {
                selectedTicket.setText(value.getMovieName());
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
                // send request to make the phone discoverable
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

            ApiMethods api = Helpers.api(serverConnectionViewModel.getAddress().getValue());
            Helpers.deleteTicketAsync(api.requestDeleteTicket(usedTicket.getID()), (MainActivity) getActivity(), ownedTickets, usedTicket);
            selectedTicketViewModel.getItem().setValue(null);
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