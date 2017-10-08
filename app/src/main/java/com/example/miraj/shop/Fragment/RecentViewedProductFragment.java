package com.example.miraj.shop.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.miraj.shop.Adapter.RecentViewedProductAdapter;
import com.example.miraj.shop.Model.Product;
import com.example.miraj.shop.R;

import java.util.ArrayList;
import java.util.List;

public class RecentViewedProductFragment extends Fragment {
    OnRecentViewedProductsEventListener recentViewedProductsEventListener;
    List<Product> products;

    public static RecentViewedProductFragment newInstance() {
        return new RecentViewedProductFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        recentViewedProductsEventListener = (OnRecentViewedProductsEventListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recent_viewed_product, container, false);
//
//        ListView productList = view.findViewById(R.id.productList);
//        RecentViewedProductAdapter adapter = new RecentViewedProductAdapter(
//                getContext(), R.layout.list_recent_viewed_product, products);
//        productList.setAdapter(adapter);
//
//        productList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                recentViewedProductsEventListener.openRecentViewedProduct(products.get(i));
//            }
//        });

        return view;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public interface OnRecentViewedProductsEventListener {
        void openRecentViewedProduct(Product product);
    }
}
