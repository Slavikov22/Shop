package com.example.miraj.shop.Activity;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.miraj.shop.Fragment.AllCartProductsFragment;
import com.example.miraj.shop.Fragment.CartFragment;
import com.example.miraj.shop.R;
import com.example.miraj.shop.Service.StateService;

public class CartActivity extends AppCompatActivity
        implements
        CartFragment.OnFragmentInteractionListener,
        AllCartProductsFragment.OnFragmentInteractionListener {
    private static final String TAG = "CART_ACTIVITY";

    private AllCartProductsFragment allCartProductFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
    }

    @Override
    protected void onResume() {
        super.onResume();

        StateService.startAction(this, TAG + " displayed");
    }

    @Override
    protected void onPause() {
        super.onPause();
        StateService.stopAction(this);
    }

    @Override
    public void showAllOnTable() {
        allCartProductFragment = AllCartProductsFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("MMM")
                .add(R.id.container, allCartProductFragment)
                .commit();
    }

    @Override
    public void closeAllCartProductFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .remove(allCartProductFragment)
                .commit();

        ((CartFragment) getSupportFragmentManager().findFragmentById(R.id.cartFragment)).updateProducts();
        ((CartFragment) getSupportFragmentManager().findFragmentById(R.id.cartFragment)).updateViews();
    }
}
