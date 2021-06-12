package com.example.movieworldgui.ui.movies;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.movieworldgui.api.MovieApiModel;
import com.example.movieworldgui.databinding.FragmentMovieBinding;
import com.example.movieworldgui.ui.home.SelectedMovieViewModel;
import com.example.movieworldgui.ui.movies.placeholder.PlaceholderMovies.MovieItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link MovieItem}.
 */
public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder> {

    private final List<MovieApiModel> mValues;
    Fragment parent;

    public MovieRecyclerViewAdapter(List<MovieApiModel> items, Fragment parent) {

        mValues = items;
        this.parent = parent;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentMovieBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mContentView.setText(mValues.get(position).getName());

        holder.mContentView.setOnClickListener(l -> {
            SelectedMovieViewModel viewModel = new ViewModelProvider(parent.getActivity()).get(SelectedMovieViewModel.class);
            viewModel.getItem().setValue(holder.mItem);
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mContentView;
        public MovieApiModel mItem;

        public ViewHolder(FragmentMovieBinding binding) {
            super(binding.getRoot());
            mContentView = binding.content;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}