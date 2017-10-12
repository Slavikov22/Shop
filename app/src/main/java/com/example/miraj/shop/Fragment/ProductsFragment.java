package com.example.miraj.shop.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.miraj.shop.Adapter.ProductAdapter;
import com.example.miraj.shop.Model.Product;
import com.example.miraj.shop.R;

import java.util.ArrayList;
import java.util.List;

public class ProductsFragment
        extends Fragment
{
    public static final String ARG_PRODUCTS = "products";

    private OnFragmentInteractionListener mListener;
    private List<Product> products;

    public static ProductsFragment newInstance(List<Product> products) {
        ProductsFragment fragment = new ProductsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PRODUCTS,(ArrayList<Product>) products);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        products = (ArrayList<Product>) getArguments().getSerializable(ARG_PRODUCTS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup c,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products, c, false);

        ListView container = (ListView) view;
        container.setAdapter(new ProductAdapter(getContext(), R.layout.list_product, products));
        container.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mListener.showProduct(products.get(i));
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (OnFragmentInteractionListener) context;
    }

    public Product getNextProduct(Product currentProduct) {
        return products.get(products.indexOf(currentProduct) + 1);
    }

    public Product getPrevProduct(Product currentProduct) {
        return products.get(products.indexOf(currentProduct) - 1);
    }

    public interface OnFragmentInteractionListener {
        void showProduct(Product product);
    }
}
