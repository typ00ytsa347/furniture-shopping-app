package com.example.projectone306;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projectone306.activity.AccountActivity;
import com.example.projectone306.activity.CartActivity;
import com.example.projectone306.activity.MainActivity;
import com.example.projectone306.activity.SearchActivity;
import com.example.projectone306.activity.WishListActivity;
import com.example.projectone306.wishlistmethod.GetListsOfNames;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BottomPanelFragment extends Fragment {

    Activity current;


    public BottomPanelFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        //get the wishlist data
        new GetListsOfNames("wishlist");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        current = getActivity();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom_panel, container, false);
    }

    @SuppressLint("NonConstantResourceId")
    public void onStart() {
        super.onStart();

        BottomNavigationView bottomNavigationView = current.findViewById(R.id.bottomNavigationView);
        FloatingActionButton searchBtn = current.findViewById(R.id.search_button);

        // initialise the navigation view
        bottomNavigationView.getMenu().setGroupCheckable(0, true, true);

        // set highlighted icon to current activity
        searchBtn.setBackgroundTintList(AppCompatResources.getColorStateList(getContext(), R.color.orange));
        switch (current.toString()) {
            case "MainActivity":
                bottomNavigationView.getMenu().findItem(R.id.home).setChecked(true);
                break;
            case "WishListActivity":
                bottomNavigationView.getMenu().findItem(R.id.wishlist).setChecked(true);
                break;
            case "SearchActivity":
                bottomNavigationView.getMenu().setGroupCheckable(0, false, true);
                searchBtn.setBackgroundTintList(AppCompatResources.getColorStateList(getContext(), R.color.orange_dark));
            case "CartActivity":
                bottomNavigationView.getMenu().findItem(R.id.cart).setChecked(true);
                break;
            case "AccountActivity":
                bottomNavigationView.getMenu().findItem(R.id.account).setChecked(true);
                break;
        }

        // listener for advanced search
        searchBtn.setOnClickListener(v -> goToActivity(SearchActivity.class));

        // navigate to the activity selected
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    goToActivity(MainActivity.class);
                    break;
                case R.id.wishlist:
                    goToWishListActivity();
                    break;
                case R.id.cart:
                    goToCartActivity();
                    break;
                case R.id.account:
                    goToActivity(AccountActivity.class);
                    break;
            }
                return true;
        });
    }

    private void goToActivity(Class activity) {
        Intent intent = new Intent(current, activity);
        startActivity(intent);
    }

    private void goToWishListActivity() {
        //refresh the wishlist
        new GetListsOfNames("wishlist");
        Intent intent = new Intent(current, WishListActivity.class);
        intent.putStringArrayListExtra("nameArray", GetListsOfNames.getWishListItemNames());
        this.startActivity(intent);
    }

    private void goToCartActivity() {
        //refresh the wishlist
        new GetListsOfNames("cart");
        Intent intent = new Intent(current, CartActivity.class);
        intent.putStringArrayListExtra("nameArray", GetListsOfNames.getCartItemNames());
        this.startActivity(intent);
    }



}