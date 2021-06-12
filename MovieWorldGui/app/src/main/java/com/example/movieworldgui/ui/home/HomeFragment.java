package com.example.movieworldgui.ui.home;

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
import com.example.movieworldgui.databinding.FragmentHomeBinding;
import com.example.movieworldgui.ui.ownedticket.OwnedTicketViewModel;
import com.example.movieworldgui.ui.ownedticket.placeholder.PlaceholderTickets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

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


        if (serverConnectionViewModel.isConnected().getValue()){
            disableServerConnectionPrompt();
        }

        binding.serverNameText.setText(serverConnectionViewModel.getAddress().getValue());

        final TextView textView = binding.selectedMovie;
        selectedMovieViewModel.getItem().observe(getViewLifecycleOwner(), s -> textView.setText(s.getName()));

        binding.buyTicket.setOnClickListener(l -> {

            if (textView.getText().length() <= 0) return;

            //TODO - send it off to the api. create a toast depending on the result of api request
            MovieApiModel item = selectedMovieViewModel.getItem().getValue();
            PlaceholderTickets.TicketItem newTicket = new PlaceholderTickets.TicketItem(UUID.randomUUID().toString(), item.getName() + " ticket", item.getGenre());
            PlaceholderTickets.addItem(newTicket);
            ownedTickets.getItems().setValue(PlaceholderTickets.ITEMS);

            MainActivity host = (MainActivity) getActivity();
            host.sendToastMessage("Ticket bought successfully");
        });

        binding.viewDetails.setOnClickListener(l -> {

            if (textView.getText().length() <= 0) return;

            Navigation.findNavController(l).navigate(R.id.action_navigation_home_to_movieDetailFragment);
        });

        binding.serverConnect.setOnClickListener(l -> {
            if (serverConnectionViewModel.getAddress().getValue().isEmpty()) return;

            ApiMethods api = new Retrofit.Builder().baseUrl("http://" + serverConnectionViewModel.getAddress().getValue())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build()
                    .create(ApiMethods.class);

            api.testConnection().enqueue(new Callback<String>() {

                MainActivity host = (MainActivity) getActivity();

                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    if (response.isSuccessful()) {

                        serverConnectionViewModel.isConnected().setValue(true);

                        disableServerConnectionPrompt();
                        host.sendToastMessage("Server connection successful.");

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