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
    Call<MovieDetailApiModel> requestMovieDetail(@Path("movie") String movieName);

    @GET("MovieWorld/movieworldservice/movies/ticket/{deviceId}")
    Call<List<TicketApiModel>> requestTickets(@Path("deviceId") String deviceId);

    @POST("MovieWorld/movieworldservice/movies/ticket")
    Call<TicketApiModel> requestPostTicket(@Body TicketApiModel ticket);
}
