package com.example.movieworldgui.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieworldgui.ui.movies.placeholder.PlaceholderMovies;

public class SelectedMovieViewModel extends ViewModel {

    private MutableLiveData<PlaceholderMovies.MovieItem> movie;

    public SelectedMovieViewModel() {
    }

    public MutableLiveData<PlaceholderMovies.MovieItem> getItem() {

        if (movie == null){
            movie = new MutableLiveData<>();
            movie.setValue(new PlaceholderMovies.MovieItem("","",""));
        }

        return movie;
    }
}