package com.example.movieworldgui.ui.receiveticket;

import android.bluetooth.BluetoothDevice;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BluetoothSenderViewModel extends ViewModel {

    private MutableLiveData<String> senderName;
    private MutableLiveData<BluetoothDevice> senderDevice;

    public BluetoothSenderViewModel() {


    }

    public MutableLiveData<String> getName() {
        if (senderName == null) {
            senderName = new MutableLiveData<>();
            senderName.setValue("");
        }

        return senderName;
    }

    public MutableLiveData<BluetoothDevice> getDevice() {
        if (senderDevice == null) {
            senderDevice = new MutableLiveData<>();
            senderDevice.setValue(null);
        }

        return senderDevice;
    }
}