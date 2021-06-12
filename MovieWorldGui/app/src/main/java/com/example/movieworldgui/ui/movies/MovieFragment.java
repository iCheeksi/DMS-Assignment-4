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
import com.example.movieworldgui.databinding.FragmentMovieListBinding;
import com.example.movieworldgui.ui.Helpers;
import com.example.movieworldgui.ui.home.ServerConnectionViewModel;

import java.io.Serializable;
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
        FragmentMovieListBinding binding = FragmentMovieListBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ServerConnectionViewModel.class);

        View root = binding.getRoot();

        ApiMethods api = Helpers.api(viewModel.getAddress().getValue());
        Helpers.sendRequestAsync(api.requestMovies(),viewModel);

        if (root instanceof RecyclerView) {
            Context context = root.getContext();
            RecyclerView recyclerView = (RecyclerView) root;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            viewModel.getMovies().observe(getViewLifecycleOwner(), values -> {
                recyclerView.setAdapter(new MovieRecyclerViewAdapter(values, requireParentFragment()));
            });
        }

        return root;
    }


}