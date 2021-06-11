package com.example.movieworldgui.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.movieworldgui.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private SelectedMovieViewModel selectedMovieViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        selectedMovieViewModel = new ViewModelProvider(getActivity()).get(SelectedMovieViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.selectedMovie;
        selectedMovieViewModel.getText().observe(getViewLifecycleOwner(), s -> textView.setText(s.content));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}