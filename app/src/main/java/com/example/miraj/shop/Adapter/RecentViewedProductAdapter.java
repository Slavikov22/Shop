package com.example.miraj.shop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.miraj.shop.Model.Product;
import com.example.miraj.shop.R;

import java.util.List;

public class RecentViewedProductAdapter extends ArrayAdapter {
    private Context context;
    private LayoutInflater inflater;
    private int layout;
    private List<Product> products;

    public RecentViewedProductAdapter(Context context, int resource, List<Product> products) {
        super(context, resource, products);

        this.context = context;
        this.products = products;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(this.layout, parent, false);

        TextView nameView = view.findViewById(R.id.name);

        Product product = products.get(position);

        nameView.setText(product.getName());

        return view;
    }
}
