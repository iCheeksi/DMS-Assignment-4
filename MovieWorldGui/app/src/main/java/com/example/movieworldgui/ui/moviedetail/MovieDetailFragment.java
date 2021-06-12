package com.example.movieworldgui.ui.moviedetail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.movieworldgui.MainActivity;
import com.example.movieworldgui.R;
import com.example.movieworldgui.api.MovieApiModel;
import com.example.movieworldgui.databinding.FragmentMovieDetailBinding;
import com.example.movieworldgui.ui.home.SelectedMovieViewModel;
import com.example.movieworldgui.ui.movies.placeholder.PlaceholderMovies;
import com.example.movieworldgui.ui.ownedticket.OwnedTicketViewModel;
import com.example.movieworldgui.ui.ownedticket.placeholder.PlaceholderTickets;

import java.util.UUID;

public class MovieDetailFragment extends Fragment {

    private SelectedMovieViewModel selectedMovieViewModel;
    private FragmentMovieDetailBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        OwnedTicketViewModel ownedTickets = new ViewModelProvider(requireActivity()).get(OwnedTicketViewModel.class);
        selectedMovieViewModel = new ViewModelProvider(requireActivity()).get(SelectedMovieViewModel.class);
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView header = binding.movieDetailHeader;
        final TextView details = binding.movieDetail;

        selectedMovieViewModel.getItem().observe(getViewLifecycleOwner(), s ->
        {
            header.setText(s.getName());
            details.setText(s.getGenre());
        });

        MovieApiModel item = selectedMovieViewModel.getItem().getValue();
        binding.buyFromDetail.setOnClickListener(l -> {
            //TODO - send it off to the api. create a toast depending on the result of api request
            PlaceholderTickets.TicketItem newTicket = new PlaceholderTickets.TicketItem(UUID.randomUUID().toString(), item.getName() + " ticket", item.getGenre());
            PlaceholderTickets.addItem(newTicket);
            ownedTickets.getItems().setValue(PlaceholderTickets.ITEMS);

            MainActivity host = (MainActivity) getActivity();
            host.sendToastMessage("Ticket bought successfully");
        });

        binding.backHome.setOnClickListener(l -> {
            Navigation.findNavController(l).navigate(R.id.action_movieDetailFragment_to_navigation_home);
        });

        return root;
    }
}