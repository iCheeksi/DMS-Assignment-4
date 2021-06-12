package com.example.movieworldgui.ui.moviedetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.movieworldgui.MainActivity;
import com.example.movieworldgui.R;
import com.example.movieworldgui.api.ApiMethods;
import com.example.movieworldgui.api.MovieApiModel;
import com.example.movieworldgui.api.MovieDetailApiModel;
import com.example.movieworldgui.databinding.FragmentMovieDetailBinding;
import com.example.movieworldgui.ui.Helpers;
import com.example.movieworldgui.ui.home.SelectedMovieViewModel;
import com.example.movieworldgui.ui.home.ServerConnectionViewModel;
import com.example.movieworldgui.ui.ownedticket.OwnedTicketViewModel;
import com.example.movieworldgui.ui.ownedticket.placeholder.PlaceholderTickets;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
//options menu needed to be enabled on the fragment for the top arrow to work
        setHasOptionsMenu(true);
        OwnedTicketViewModel ownedTickets = new ViewModelProvider(requireActivity()).get(OwnedTicketViewModel.class);
        SelectedMovieViewModel selectedMovieViewModel = new ViewModelProvider(requireActivity()).get(SelectedMovieViewModel.class);
        ServerConnectionViewModel serverConnectionViewModel = new ViewModelProvider((requireActivity())).get(ServerConnectionViewModel.class);
        FragmentMovieDetailBinding binding = FragmentMovieDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView header = binding.movieDetailHeader;
        final TextView details = binding.movieDetail;

        MainActivity host = (MainActivity) getActivity();
        selectedMovieViewModel.getItem().observe(getViewLifecycleOwner(), s ->
        {
            ApiMethods api = Helpers.api(serverConnectionViewModel.getAddress().getValue());
            Call<MovieDetailApiModel> request =  api.requestMovieDetail(s.getName());

            request.enqueue(new Callback<MovieDetailApiModel>() {
                @Override
                public void onResponse(Call<MovieDetailApiModel> call, Response<MovieDetailApiModel> response) {
                    if (response.isSuccessful()){
                        MovieDetailApiModel item = response.body();
                        header.setText(item.getMovieName());
                        details.setText(item.getMovieSummary());

                    }else{
                        header.setText("No Details");
                        details.setText("No movie details :(");
                        host.sendToastMessage("Sorry we weren't able to get movie details from the server.");
                    }
                }

                @Override
                public void onFailure(Call<MovieDetailApiModel> call, Throwable t) {
                    header.setText("No Details");
                    details.setText("No movie details :(");
                    host.sendToastMessage("Sorry we weren't able to get movie details from the server.");
                }
            });

            header.setText(s.getName());
        });

        MovieApiModel item = selectedMovieViewModel.getItem().getValue();
        binding.buyFromDetail.setOnClickListener(l -> {
            //TODO - send it off to the api. create a toast depending on the result of api request
            PlaceholderTickets.TicketItem newTicket = new PlaceholderTickets.TicketItem(UUID.randomUUID().toString(), item.getName() + " ticket", item.getGenre());
            PlaceholderTickets.addItem(newTicket);
            ownedTickets.getItems().setValue(PlaceholderTickets.ITEMS);

            host.sendToastMessage("Ticket bought successfully");
        });

        binding.backHome.setOnClickListener(l -> {
            Navigation.findNavController(l).navigate(R.id.action_movieDetailFragment_to_navigation_home);
        });

        OnBackPressedCallback backPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                System.out.println("BACK BUTTON PRESSED");
                Navigation.findNavController(requireView()).navigate(R.id.action_movieDetailFragment_to_navigation_home);
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), backPressedCallback);

        return root;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Navigation.findNavController(requireView()).navigate(R.id.action_movieDetailFragment_to_navigation_home);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}