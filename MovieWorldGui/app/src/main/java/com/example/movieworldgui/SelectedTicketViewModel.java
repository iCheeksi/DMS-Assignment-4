package com.example.movieworldgui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieworldgui.ui.ownedticket.placeholder.PlaceholderTickets;

public class SelectedTicketViewModel extends ViewModel {
    private MutableLiveData<PlaceholderTickets.TicketItem> item;

    public SelectedTicketViewModel() {
        item = new MutableLiveData<>();
        item.postValue(new PlaceholderTickets.TicketItem("","",""));
    }

    public MutableLiveData<PlaceholderTickets.TicketItem> getItem() {
        return item;
    }
}