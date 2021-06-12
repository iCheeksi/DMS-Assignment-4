package com.example.movieworldgui.ui.ownedticket;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieworldgui.api.TicketApiModel;
import com.example.movieworldgui.ui.ownedticket.placeholder.PlaceholderTickets;

import java.util.ArrayList;
import java.util.List;

public class OwnedTicketViewModel extends ViewModel {

    private MutableLiveData<List<TicketApiModel>> items;

    public MutableLiveData<List<TicketApiModel>> getItems() {
        if (items == null) {
            items = new MutableLiveData<>();
            items.setValue(new ArrayList<>());
        }

        return items;
    }
}
