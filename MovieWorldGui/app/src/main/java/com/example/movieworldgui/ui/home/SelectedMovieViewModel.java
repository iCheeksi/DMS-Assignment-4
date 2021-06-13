package com.example.movieworldgui.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieworldgui.api.MovieApiModel;

/***
 * Author - Shelby Mun (19049176) & Angelo Ryndon (18028033)
 */
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