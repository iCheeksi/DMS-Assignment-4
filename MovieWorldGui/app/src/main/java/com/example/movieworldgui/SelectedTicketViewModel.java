package com.example.movieworldgui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SelectedTicketViewModel extends ViewModel {
    private MutableLiveData<String> text;

    public SelectedTicketViewModel() {
        text = new MutableLiveData<>();
        text.setValue(" ");
    }

    public MutableLiveData<String> getText() {
        return text;
    }
}