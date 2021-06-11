package com.example.movieworldgui.ui.ownedticket;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieworldgui.ui.ownedticket.placeholder.PlaceholderTickets;

import java.util.ArrayList;
import java.util.List;

public class OwnedTicketViewModel extends ViewModel {

    private MutableLiveData<List<PlaceholderTickets.TicketItem>> items;

    public MutableLiveData<List<PlaceholderTickets.TicketItem>> getItems() {
        if (items == null) {
            items = new MutableLiveData<>();
            items.setValue(new ArrayList<>());
        }

        return items;
    }
}
