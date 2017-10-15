package com.example.miraj.shop.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.miraj.shop.Helper.BitmapHelper;
import com.example.miraj.shop.Model.Product;
import com.example.miraj.shop.Provider.DBProvider;
import com.example.miraj.shop.R;

import java.util.Collections;
import java.util.List;

public class RecentViewedProductsFragment extends Fragment {
    private static final int MAX_RECENT_VIEWED_PRODUCTS = 10;

    private OnFragmentInteractionListener mListener;
    private List<Product> products;

    public static RecentViewedProductsFragment newInstance() {
        return new RecentViewedProductsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DBProvider dbProvider = new DBProvider(getContext());
        products = dbProvider.getRecentViewedProducts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recent_viewed_products, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (OnFragmentInteractionListener) context;
    }

    @Override
    public void onResume() {
        updateProducts();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        DBProvider dbProvider = new DBProvider(getContext());
        for (Product product : products)
            dbProvider.removeRecentViewedProduct(product);
        super.onDestroy();
    }

    private void updateProducts() {
        DBProvider dbProvider = new DBProvider(getContext());
        products = dbProvider.getRecentViewedProducts();
        for (int i = 0; i < products.size() - MAX_RECENT_VIEWED_PRODUCTS; i++)
            dbProvider.removeRecentViewedProduct(products.get(0));

        products = dbProvider.getRecentViewedProducts();
        Collections.reverse(products);

        if (getView() != null) {
            LinearLayout container = getView().findViewById(R.id.products);
            container.removeAllViews();

            for (final Product product : products) {
                View view = View.inflate(getContext(), R.layout.recent_viewed_product, null);
                ((TextView) view.findViewById(R.id.name)).setText(product.getName());
                ((ImageView) view.findViewById(R.id.image)).setImageBitmap(BitmapHelper.getProductImage(product));

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.showProduct(product);
                    }
                });

                container.addView(view);
            }
        }
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
