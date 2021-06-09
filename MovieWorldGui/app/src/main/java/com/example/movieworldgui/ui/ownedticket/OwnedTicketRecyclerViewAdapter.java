package com.example.movieworldgui.ui.ownedticket;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.movieworldgui.SelectedTicketViewModel;
import com.example.movieworldgui.databinding.FragmentOwnedTicketBinding;
import com.example.movieworldgui.ui.ownedticket.placeholder.PlaceholderContent;
import com.example.movieworldgui.ui.ownedticket.placeholder.PlaceholderContent.PlaceholderItem;

import java.util.List;

import static com.example.movieworldgui.ui.ownedticket.placeholder.PlaceholderContent.ITEM_MAP;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class OwnedTicketRecyclerViewAdapter extends RecyclerView.Adapter<OwnedTicketRecyclerViewAdapter.ViewHolder> {

    private final List<PlaceholderItem> mValues;
    Fragment parent;

    public OwnedTicketRecyclerViewAdapter(List<PlaceholderItem> items, Fragment parent) {
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
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);

        holder.mContentView.setOnClickListener(l -> {
            SelectedTicketViewModel viewModel = new ViewModelProvider(parent).get(SelectedTicketViewModel.class);
            viewModel.getItem().setValue(holder.mItem);
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public PlaceholderItem mItem;

        public ViewHolder(FragmentOwnedTicketBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemNumber;
            mContentView = binding.content;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}