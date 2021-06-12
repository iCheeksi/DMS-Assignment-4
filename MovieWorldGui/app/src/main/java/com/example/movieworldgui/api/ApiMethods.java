package com.example.movieworldgui.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;


public interface ApiMethods {

    @GET("MovieWorld/")
    Call<String> testConnection();

    @GET("MovieWorld/movieworldservice/movies")
    Call<List<MovieApiModel>> requestMovies();

    @GET("MovieWorld/movieworldservice/details/{movie}")
    Call<MovieApiModel> requestMovieDetail(@Path("movie") String movieName);
}
