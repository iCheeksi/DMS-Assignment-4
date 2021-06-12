package com.example.movieworldgui.ui.home;

import android.bluetooth.BluetoothAdapter;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.movieworldgui.api.ApiMethods;
import com.example.movieworldgui.api.MovieApiModel;
import com.example.movieworldgui.api.TicketApiModel;
import com.example.movieworldgui.databinding.FragmentHomeBinding;
import com.example.movieworldgui.ui.Helpers;
import com.example.movieworldgui.ui.ownedticket.OwnedTicketViewModel;
import com.example.movieworldgui.ui.ownedticket.placeholder.PlaceholderTickets;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private SelectedMovieViewModel selectedMovieViewModel;
    private ServerConnectionViewModel serverConnectionViewModel;
    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        OwnedTicketViewModel ownedTickets = new ViewModelProvider(requireActivity()).get(OwnedTicketViewModel.class);
        selectedMovieViewModel = new ViewModelProvider(requireActivity()).get(SelectedMovieViewModel.class);
        serverConnectionViewModel = new ViewModelProvider(requireActivity()).get(ServerConnectionViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if (serverConnectionViewModel.isConnected().getValue()) {
            disableServerConnectionPrompt();
        }

        binding.serverNameText.setText(serverConnectionViewModel.getAddress().getValue());

        final TextView selectedMovie = binding.selectedMovie;
        selectedMovieViewModel.getItem().observe(getViewLifecycleOwner(), s -> selectedMovie.setText(s.getName()));

        binding.buyTicket.setOnClickListener(l -> {

            if (selectedMovie.getText().length() <= 0) return;

            MovieApiModel item = selectedMovieViewModel.getItem().getValue();
            ApiMethods api = Helpers.api(serverConnectionViewModel.getAddress().getValue());
            TicketApiModel ticket = new TicketApiModel(UUID.randomUUID().toString(),BluetoothAdapter.getDefaultAdapter().getName(),item.getName());

            Call<TicketApiModel> request = api.requestPostTicket(ticket);
            Helpers.postTicketAsync(request,(MainActivity)getActivity(),ownedTickets,ticket);
        });

        binding.viewDetails.setOnClickListener(l -> {

            if (selectedMovie.getText().length() <= 0) return;

            Navigation.findNavController(l).navigate(R.id.action_navigation_home_to_movieDetailFragment);
        });

        binding.serverConnect.setOnClickListener(l -> {
            if (serverConnectionViewModel.getAddress().getValue().isEmpty()) return;

            ApiMethods api = Helpers.api(serverConnectionViewModel.getAddress().getValue());
            api.testConnection().enqueue(new Callback<String>() {

                MainActivity host = (MainActivity) getActivity();

                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    if (response.isSuccessful()) {

                        serverConnectionViewModel.isConnected().setValue(true);
                        disableServerConnectionPrompt();
                        Helpers.getMoviesAsync(api.requestMovies(), serverConnectionViewModel);
                        selectedMovie.setText("");

                    } else {
                        serverConnectionViewModel.isConnected().setValue(false);
                        host.sendToastMessage("Unable to connect to that IP address.");
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    serverConnectionViewModel.isConnected().setValue(false);
                    host.sendToastMessage("Unable to connect to that IP address.");
                }
            });
        });

        binding.serverNameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String value = s.toString();
                if (!value.isEmpty()) {
                    serverConnectionViewModel.getAddress().setValue(value);
                } else {
                    serverConnectionViewModel.getAddress().setValue("");
                }
            }
        });

        return root;
    }

    private void disableServerConnectionPrompt() {
        binding.serverConnect.setEnabled(false);
        binding.serverConnect.setClickable(false);
        binding.serverNameText.setEnabled(false);
        binding.serverNameText.setFocusable(false);
        binding.serverNameText.setCursorVisible(false);
        binding.serverNameText.setKeyListener(null);
        binding.serverNameText.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}