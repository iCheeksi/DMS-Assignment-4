package com.example.movieworldgui.ui;

import com.example.movieworldgui.api.ApiMethods;
import com.example.movieworldgui.api.MovieApiModel;
import com.example.movieworldgui.ui.home.ServerConnectionViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Helpers {

    public static void sendRequestAsync(Call<List<MovieApiModel>> request, ServerConnectionViewModel viewModel) {

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

    public static ApiMethods api(String hostAddress){
        return new Retrofit.Builder().baseUrl("http://" + hostAddress)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiMethods.class);
    }
}
