package com.example.movieworldgui.ui;

import com.example.movieworldgui.MainActivity;
import com.example.movieworldgui.api.ApiMethods;
import com.example.movieworldgui.api.MovieApiModel;
import com.example.movieworldgui.api.TicketApiModel;
import com.example.movieworldgui.ui.home.ServerConnectionViewModel;
import com.example.movieworldgui.ui.ownedticket.OwnedTicketViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Helpers {

    public static void getMoviesAsync(Call<List<MovieApiModel>> request, ServerConnectionViewModel viewModel) {

        List<MovieApiModel> items = new ArrayList<>();

        request.enqueue(new Callback<List<MovieApiModel>>() {

            @Override
            public void onResponse(Call<List<MovieApiModel>> call,
                                   Response<List<MovieApiModel>> response) {

                if (response.isSuccessful()) {

                    items.addAll(response.body());
                } else {
                    items.add(new MovieApiModel("Sorry we can't get movies right now :("));
                }
                viewModel.getMovies().setValue(items);
            }

            @Override
            public void onFailure(Call<List<MovieApiModel>> call, Throwable t) {
                items.add(new MovieApiModel("Sorry we can't get movies right now :("));
                viewModel.getMovies().setValue(items);
            }
        });
    }

    public static void postTicketAsync(Call<TicketApiModel> request, MainActivity host) {

        request.enqueue(new Callback<TicketApiModel>() {

            @Override
            public void onResponse(Call<TicketApiModel> call, Response<TicketApiModel> response) {

                if (response.isSuccessful()) {

                    host.sendToastMessage("Ticket saved on the server");
                } else {
                    host.sendToastMessage("Sorry we can't save the ticket on the server");
                }
            }

            @Override
            public void onFailure(Call<TicketApiModel> call, Throwable t) {
                host.sendToastMessage("Sorry we can't save the ticket on the server");
            }
        });
    }

    public static ApiMethods api(String hostAddress){
        return new Retrofit.Builder().baseUrl("http://" + hostAddress)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiMethods.class);
    }
}
