package com.example.movieworldgui.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.movieworldgui.MainActivity;
import com.example.movieworldgui.R;
import com.example.movieworldgui.databinding.FragmentHomeBinding;
import com.example.movieworldgui.ui.movies.placeholder.PlaceholderMovies;
import com.example.movieworldgui.ui.ownedticket.OwnedTicketViewModel;
import com.example.movieworldgui.ui.ownedticket.placeholder.PlaceholderTickets;

import java.util.UUID;

public class HomeFragment extends Fragment {

    private SelectedMovieViewModel selectedMovieViewModel;
    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        OwnedTicketViewModel ownedTickets = new ViewModelProvider(requireActivity()).get(OwnedTicketViewModel.class);
        selectedMovieViewModel = new ViewModelProvider(requireActivity()).get(SelectedMovieViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.selectedMovie;
        selectedMovieViewModel.getItem().observe(getViewLifecycleOwner(), s -> textView.setText(s.content));

        binding.buyTicket.setOnClickListener(l -> {

            if (textView.getText().length() <= 0) return;

            //TODO - send it off to the api. create a toast depending on the result of api request
            PlaceholderMovies.MovieItem item = selectedMovieViewModel.getItem().getValue();
            PlaceholderTickets.TicketItem newTicket = new PlaceholderTickets.TicketItem(UUID.randomUUID().toString(),item.content + " ticket",item.details);
            PlaceholderTickets.addItem(newTicket);
            ownedTickets.getItems().setValue(PlaceholderTickets.ITEMS);

            MainActivity host = (MainActivity) getActivity();
            host.sendToastMessage("Ticket bought successfully");
        });

        binding.viewDetails.setOnClickListener(l -> {

            if (textView.getText().length() <= 0) return;

            Navigation.findNavController(l).navigate(R.id.action_navigation_home_to_movieDetailFragment);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}