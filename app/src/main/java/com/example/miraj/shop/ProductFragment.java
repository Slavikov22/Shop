package com.example.miraj.shop;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.miraj.shop.Model.Product;

public class ProductFragment extends Fragment {
    Product product;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        Bundle bundle = getArguments();
        product = (Product) bundle.getSerializable("Product");

        TextView nameView = view.findViewById(R.id.name);
        nameView.setText(product.getName());

        TextView priceView = view.findViewById(R.id.price);
        priceView.setText(String.valueOf(product.getPrice()));

        this.view = view;

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Animation animation = AnimationUtils.loadAnimation(this.getContext(), R.anim.fragment_product_scale);
        animation.setDuration(100);
        view.startAnimation(animation);
    }
}
