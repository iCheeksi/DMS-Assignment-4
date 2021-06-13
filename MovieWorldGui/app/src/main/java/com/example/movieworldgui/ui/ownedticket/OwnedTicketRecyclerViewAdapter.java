package com.example.movieworldgui.ui.ownedticket;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieworldgui.api.TicketApiModel;
import com.example.movieworldgui.databinding.FragmentOwnedTicketBinding;
import com.example.movieworldgui.ui.dashboard.SelectedTicketViewModel;

import java.util.List;

/***
 * Author - Shelby Mun (19049176) & Angelo Ryndon (18028033)
 */
public class OwnedTicketRecyclerViewAdapter extends RecyclerView.Adapter<OwnedTicketRecyclerViewAdapter.ViewHolder> {

    private final List<TicketApiModel> mValues;
    Fragment parent;

    public OwnedTicketRecyclerViewAdapter(List<TicketApiModel> items, Fragment parent) {
        mValues = items;
        this.parent = parent;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentOwnedTicketBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mContentView.setText(mValues.get(position).getMovieName());

        holder.mContentView.setOnClickListener(l -> {
            SelectedTicketViewModel viewModel = new ViewModelProvider(parent.getActivity()).get(SelectedTicketViewModel.class);
            viewModel.getItem().setValue(holder.mItem);
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mContentView;
        public TicketApiModel mItem;

        public ViewHolder(FragmentOwnedTicketBinding binding) {
            super(binding.getRoot());
            mContentView = binding.content;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}