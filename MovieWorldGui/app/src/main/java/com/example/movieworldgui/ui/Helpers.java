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

/***
 * Author - Shelby Mun (19049176) & Angelo Ryndon (18028033)
 */
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

    public static void getTicketsAsync(Call<List<TicketApiModel>> request, OwnedTicketViewModel viewModel) {
        List<TicketApiModel> items = new ArrayList<>();

        request.enqueue(new Callback<List<TicketApiModel>>() {

            @Override
            public void onResponse(Call<List<TicketApiModel>> call,
                                   Response<List<TicketApiModel>> response) {

                if (response.isSuccessful()) {

                    items.addAll(response.body());
                } else {
                    items.add(new TicketApiModel("Sorry we can't get your tickets right now :("));
                }
                viewModel.getItems().setValue(items);
            }

            @Override
            public void onFailure(Call<List<TicketApiModel>> call, Throwable t) {
                items.add(new TicketApiModel("Sorry we can't get tickets right now :("));
                viewModel.getItems().setValue(items);
            }
        });
    }

    public static void postTicketAsync(Call<TicketApiModel> request, MainActivity host, OwnedTicketViewModel viewModel, TicketApiModel ticket) {

        request.enqueue(new Callback<TicketApiModel>() {

            @Override
            public void onResponse(Call<TicketApiModel> call, Response<TicketApiModel> response) {

                if (response.isSuccessful()) {

                    viewModel.getItems().getValue().add(ticket);
                    host.sendToastMessage("Ticket successfully saved on the server");
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

    public static void deleteTicketAsync(Call<String> request,MainActivity host, OwnedTicketViewModel viewModel, TicketApiModel ticket) {
        request.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful()) {

                    List<TicketApiModel> ownedTickets = viewModel.getItems().getValue();
                    ownedTickets.remove(ticket);
                    viewModel.getItems().setValue(ownedTickets);

                    host.sendToastMessage("Ticket successfully used.");
                } else {
                    host.sendToastMessage("Sorry we can't use the ticket on the server");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                host.sendToastMessage("Sorry we can't use the ticket on the server");
            }
        });
    }

    //get a constructed api object to send requests
    public static ApiMethods api(String hostAddress) {
        return new Retrofit.Builder().baseUrl("http://" + hostAddress)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiMethods.class);
    }
}
