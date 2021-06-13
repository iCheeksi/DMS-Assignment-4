package com.example.movieworldgui.ui.ownedticket;

import android.bluetooth.BluetoothAdapter;
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

import com.example.movieworldgui.api.ApiMethods;
import com.example.movieworldgui.databinding.FragmentOwnedTicketListBinding;
import com.example.movieworldgui.ui.Helpers;
import com.example.movieworldgui.ui.home.ServerConnectionViewModel;

/***
 * Author - Shelby Mun (19049176) & Angelo Ryndon (18028033)
 */
public class OwnedTicketFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OwnedTicketViewModel ownedTicketViewModel;
    ServerConnectionViewModel serverConnectionViewModel;

    public OwnedTicketFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static OwnedTicketFragment newInstance(int columnCount) {
        OwnedTicketFragment fragment = new OwnedTicketFragment();
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

        ownedTicketViewModel = new ViewModelProvider(requireActivity()).get(OwnedTicketViewModel.class);
        serverConnectionViewModel = new ViewModelProvider(requireActivity()).get(ServerConnectionViewModel.class);
        FragmentOwnedTicketListBinding binding = FragmentOwnedTicketListBinding.inflate(inflater,container,false);
        View root = binding.getRoot();

        //get a user's tickets on the server
        ApiMethods api = Helpers.api(serverConnectionViewModel.getAddress().getValue());
        Helpers.getTicketsAsync(api.requestTickets(BluetoothAdapter.getDefaultAdapter().getName()), ownedTicketViewModel);

        if (root instanceof RecyclerView) {
            Context context = root.getContext();
            RecyclerView recyclerView = (RecyclerView) root;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            ownedTicketViewModel.getItems().observe(getViewLifecycleOwner(), items -> {
                recyclerView.setAdapter(new OwnedTicketRecyclerViewAdapter(items, requireParentFragment()));
            });
        }
        return root;
    }
}