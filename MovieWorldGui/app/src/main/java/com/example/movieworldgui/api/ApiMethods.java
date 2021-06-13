package com.example.movieworldgui.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

/***
 *  Author - Shelby Mun (19049176) & Angelo Ryndon (18028033)
 */
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

    @POST("MovieWorld/movieworldservice/movies/ticket/delete/{id}")
    Call<String> requestDeleteTicket(@Path("id") String id);
}
