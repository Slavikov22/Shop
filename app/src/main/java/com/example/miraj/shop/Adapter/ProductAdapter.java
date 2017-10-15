package com.example.miraj.shop.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.miraj.shop.Helper.BitmapHelper;
import com.example.miraj.shop.Model.Product;
import com.example.miraj.shop.R;

import java.util.List;

public class ProductAdapter extends ArrayAdapter {
    private LayoutInflater inflater;
    private int layout;
    private List<Product> products;

    public ProductAdapter(Context context, int resource, List<Product> products) {
        super(context, resource, products);

        this.products = products;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(this.layout, parent, false);

        Product product = products.get(position);

        ((TextView) view.findViewById(R.id.name)).setText(product.getName());
        ((ImageView) view.findViewById(R.id.image)).setImageBitmap(BitmapHelper.getProductImage(product));

        return view;
    }
}
