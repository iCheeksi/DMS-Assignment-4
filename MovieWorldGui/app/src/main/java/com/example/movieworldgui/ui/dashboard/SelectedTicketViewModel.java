package com.example.movieworldgui.ui.dashboard;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieworldgui.api.TicketApiModel;

/***
 * Author - Shelby Mun (19049176) & Angelo Ryndon (18028033)
 */
public class SelectedTicketViewModel extends ViewModel {
    private MutableLiveData<TicketApiModel> item;

    public SelectedTicketViewModel() {

    }

    public MutableLiveData<TicketApiModel> getItem() {
        if (item == null){
            item = new MutableLiveData<>();
            item.postValue(new TicketApiModel("","",""));
        }

        return item;
    }
}