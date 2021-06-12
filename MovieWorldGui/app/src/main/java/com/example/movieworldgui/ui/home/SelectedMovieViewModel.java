package com.example.movieworldgui.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieworldgui.api.MovieApiModel;
import com.example.movieworldgui.ui.movies.placeholder.PlaceholderMovies;

public class SelectedMovieViewModel extends ViewModel {

    private MutableLiveData<MovieApiModel> movie;

    public SelectedMovieViewModel() {
    }

    public MutableLiveData<MovieApiModel> getItem() {

        if (movie == null){
            movie = new MutableLiveData<>();
            movie.setValue(new MovieApiModel(""));
        }

        return movie;
    }
}