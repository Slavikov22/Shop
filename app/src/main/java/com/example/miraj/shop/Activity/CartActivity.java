package com.example.miraj.shop.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.miraj.shop.R;
import com.example.miraj.shop.Service.StateService;

public class CartActivity extends AppCompatActivity {
    private static final String TAG = "CART_ACTIVITY";

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
}
