package com.example.movieworldgui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieworldgui.ui.ownedticket.placeholder.PlaceholderContent;

public class SelectedTicketViewModel extends ViewModel {
    private MutableLiveData<PlaceholderContent.PlaceholderItem> item;

    public SelectedTicketViewModel() {
        item = new MutableLiveData<>();
        item.postValue(new PlaceholderContent.PlaceholderItem("","",""));
    }

    public MutableLiveData<PlaceholderContent.PlaceholderItem> getItem() {
        return item;
    }
}