package com.example.movieworldgui.ui.dashboard;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieworldgui.ui.ownedticket.placeholder.PlaceholderTickets;

public class SelectedTicketViewModel extends ViewModel {
    private MutableLiveData<PlaceholderTickets.TicketItem> item;

    public SelectedTicketViewModel() {

    }

    public MutableLiveData<PlaceholderTickets.TicketItem> getItem() {
        if (item == null){
            item = new MutableLiveData<>();
            item.postValue(new PlaceholderTickets.TicketItem("","",""));
        }

        return item;
    }
}