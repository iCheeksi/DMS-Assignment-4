package com.example.movieworldgui.ui.movies;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieworldgui.R;
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

/**
 * A fragment representing a list of Items.
 */
public class MovieFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    ServerConnectionViewModel viewModel;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovieFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MovieFragment newInstance(int columnCount) {
        MovieFragment fragment = new MovieFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ServerConnectionViewModel.class);

        ApiMethods api = new Retrofit.Builder().baseUrl("http://" + viewModel.getAddress().getValue())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiMethods.class);

        return sendRequestAsync(api.requestMovies(), view);
    }

    private View sendRequestAsync(Call<List<MovieApiModel>> request, View view) {

        List<MovieApiModel> items = new ArrayList<>();

        request.enqueue(new Callback<List<MovieApiModel>>() {

            @Override
            public void onResponse(Call<List<MovieApiModel>> call,
                                   Response<List<MovieApiModel>> response) {

                if (response.isSuccessful()) {

                    items.addAll(response.body());
                } else {
                    System.out.println("Response unsuccessful: " + response.message());
                    items.add(new MovieApiModel("Sorry we can't get movies right now :("));
                }
                viewModel.getMovies().setValue(items);
                SetupRecyclerViewContent(view);
            }

            @Override
            public void onFailure(Call<List<MovieApiModel>> call, Throwable t) {
                System.out.println("Failed request: " + t.getMessage());
                items.add(new MovieApiModel("Sorry we can't get movies right now :("));
                viewModel.getMovies().setValue(items);
                SetupRecyclerViewContent(view);
            }
        });

        return view;
    }

    private void SetupRecyclerViewContent(View view) {
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            viewModel.getMovies().observe(getViewLifecycleOwner(), values -> {
                recyclerView.setAdapter(new MovieRecyclerViewAdapter(values, requireParentFragment()));
            });

        }
    }
}