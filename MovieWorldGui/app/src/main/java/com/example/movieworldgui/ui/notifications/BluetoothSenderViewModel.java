package com.example.movieworldgui.ui.notifications;

import android.bluetooth.BluetoothDevice;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BluetoothSenderViewModel extends ViewModel {

    private MutableLiveData<String> senderName;
    private MutableLiveData<BluetoothDevice> senderDevice;

    public BluetoothSenderViewModel() {
        senderName = new MutableLiveData<>();
        senderDevice = new MutableLiveData<>();
        senderName.setValue("");
        senderDevice.setValue(null);
    }

    public MutableLiveData<String> getName() {
        return senderName;
    }
    public MutableLiveData<BluetoothDevice> getDevice() {return senderDevice;}
}