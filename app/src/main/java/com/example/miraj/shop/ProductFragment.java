package com.example.miraj.shop;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.miraj.shop.Model.Product;

public class ProductFragment extends Fragment {
    Product product;

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

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }
}
