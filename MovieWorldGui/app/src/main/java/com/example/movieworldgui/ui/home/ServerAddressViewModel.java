package com.example.movieworldgui.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ServerAddressViewModel extends ViewModel {
    private MutableLiveData<String> address;

    public ServerAddressViewModel(){}

    public MutableLiveData<String> getAddress(){
        if (address == null){
            address = new MutableLiveData<>();
            address.setValue("");
        }

        return address;
    }
}
