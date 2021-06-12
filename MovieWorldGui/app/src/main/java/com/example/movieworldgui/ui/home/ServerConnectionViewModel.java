package com.example.movieworldgui.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieworldgui.api.MovieApiModel;

import java.util.ArrayList;
import java.util.List;

public class ServerConnectionViewModel extends ViewModel {
    private MutableLiveData<String> address;
    private MutableLiveData<Boolean> connected;
    private MutableLiveData<List<MovieApiModel>> movies;

    public ServerConnectionViewModel() {
    }

    public MutableLiveData<String> getAddress() {
        if (address == null) {
            address = new MutableLiveData<>();
            address.setValue("host:9999");
        }

        return address;
    }

    public MutableLiveData<Boolean> isConnected() {
        if (connected == null) {
            connected = new MutableLiveData<>();
            connected.setValue(false);
        }

        return connected;
    }

    public MutableLiveData<List<MovieApiModel>> getMovies() {
        if (movies == null) {
            movies = new MutableLiveData<>();
            movies.setValue(new ArrayList<>());
        }

        return movies;
    }
}
