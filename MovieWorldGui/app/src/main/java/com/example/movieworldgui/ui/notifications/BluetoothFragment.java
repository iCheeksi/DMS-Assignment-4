package com.example.movieworldgui.ui.notifications;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.movieworldgui.MainActivity;
import com.example.movieworldgui.databinding.FragmentNotificationsBinding;

import org.jetbrains.annotations.NotNull;

import java.util.Set;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class BluetoothFragment extends Fragment {

    private BluetoothSenderViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;
    private BluetoothAdapter bluetoothAdapter;
    private boolean bluetoothRequestAccepted;
    private MainActivity host;

    private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // is there a better way to check a sender, not just checking the device name
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getName().equalsIgnoreCase(notificationsViewModel.getName().getValue())) {
                    notificationsViewModel.getDevice().setValue(device);
                }
            }
        }
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        host = (MainActivity) getActivity();
        notificationsViewModel = new ViewModelProvider(this).get(BluetoothSenderViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        notificationsViewModel.getName().observe(getViewLifecycleOwner(), s -> textView.setText("Get ticket from: " + s));

        binding.getTicket.setOnClickListener(l -> {

            if (bluetoothAdapter == null) {
                Toast.makeText(getActivity().getApplicationContext(), "Your device has no bluetooth support", Toast.LENGTH_LONG).show();
                return; // no bluetooth support
            }

            if (!bluetoothAdapter.isEnabled()) {
                Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBluetooth, 1);
            } else {
                startConnectionToSender();
            }
        });

        binding.senderName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                String value = s.toString();
                if (!value.isEmpty()) {
                    notificationsViewModel.getName().setValue(value);
                } else {
                    notificationsViewModel.getName().setValue("");
                }
            }
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {

        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(getActivity().getApplicationContext(), "Please accept the request to use the app", Toast.LENGTH_LONG).show();
        } else {
            startConnectionToSender();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void startConnectionToSender() {
        String targetDevice = notificationsViewModel.getName().getValue();
        BluetoothDevice pairedDevice = notificationsViewModel.getDevice().getValue();
        if (pairedDevice != null && pairedDevice.getName().equalsIgnoreCase(targetDevice)) {
            //we have an existing connection
            host.getTicket(pairedDevice);
            return;
        }

        //check on paired devices first, before doing a broadcast discovery
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            //reset saved connection
            notificationsViewModel.getDevice().setValue(null);
            pairedDevice = getPairedDevice(targetDevice, pairedDevices);
        }

        //look for the broadcasting device if no paired device found
        //TODO- still need to figure out why it is not discovering unpaired devices
        if (pairedDevice == null) {
            startBluetoothDiscovery();
        }

        // get the ticket from a connected device
        pairedDevice = notificationsViewModel.getDevice().getValue();
        if (pairedDevice != null) {
            host.getTicket(pairedDevice);
        }
    }

    private void startBluetoothDiscovery() {
        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
        }

        requestPermissions();
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        getActivity().registerReceiver(broadcastReceiver, filter);
        bluetoothAdapter.startDiscovery();

        while (bluetoothAdapter.isDiscovering()) {
            Log.d("BLUETOOTH DISCOVERY >>>>> ", "Discovering devices");
        }
        getActivity().unregisterReceiver(broadcastReceiver);
    }

    private BluetoothDevice getPairedDevice(String targetDevice, Set<BluetoothDevice> pairedDevices) {

        BluetoothDevice pairedDevice = null;
        for (BluetoothDevice device : pairedDevices) {
            if (device.getName().equalsIgnoreCase(targetDevice)) {
                notificationsViewModel.getDevice().setValue(device);
                pairedDevice = device;
            }
            break;
        }
        return pairedDevice;
    }

    private void requestPermissions() {

        boolean givenPermissions = true;
        String[] requestedPermissions =
                {
                        Manifest.permission.BLUETOOTH,
                        Manifest.permission.BLUETOOTH_ADMIN,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                };

        for (String permission : requestedPermissions) {
            givenPermissions = givenPermissions
                    && (ContextCompat.checkSelfPermission(getActivity(), permission) == PackageManager.PERMISSION_GRANTED);
        }

        if (!givenPermissions) {
            ActivityCompat.requestPermissions(getActivity(), requestedPermissions, 1);
        }
    }
}