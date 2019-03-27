package edu.iastate.graysonc.fastfood.fragments;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import edu.iastate.graysonc.fastfood.App;
import edu.iastate.graysonc.fastfood.R;
import edu.iastate.graysonc.fastfood.database.entities.Food;
import edu.iastate.graysonc.fastfood.recyclerClasses.FavoritesListAdapter;
import edu.iastate.graysonc.fastfood.view_models.FavoritesViewModel;

public class FavoritesFragment extends Fragment {
    private static final String TAG = "FavoritesFragment";

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private FavoritesViewModel mViewModel;

    private RecyclerView mRecyclerView;
    private FavoritesListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public FavoritesFragment() {}

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Configure Dagger 2
        AndroidSupportInjection.inject(this);

        // Configure ViewModel
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(FavoritesViewModel.class);
        mViewModel.init(((GoogleSignInAccount)getArguments().getParcelable("ACCOUNT")).getEmail());
        mViewModel.getFavorites().observe(this, f -> {
            if (f != null) {
                //updateUI(f);
            }
        });

        // Set up list
        buildRecyclerView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorites, container, false);
    }

    public void removeItem(int position) {
        Food selectedItem = mViewModel.getFavorites().getValue().get(position);
        Log.d(TAG, "removeItem: " + selectedItem.getName());
        //mAdapter.notifyItemRemoved(position);
    }

    public void openFoodPage(int position) {
        Food selectedItem = mViewModel.getFavorites().getValue().get(position);
        Log.d(TAG, "openFoodPage: " + selectedItem.getName());
        //mAdapter.notifyItemChanged(position);
    }

    public void buildRecyclerView() {
        mRecyclerView = getView().findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(App.context);
        mAdapter = new FavoritesListAdapter(mViewModel.getFavorites().getValue());

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new FavoritesListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                openFoodPage(position);
            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });
    }
}