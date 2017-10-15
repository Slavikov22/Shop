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

import java.util.List;

public class CartFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private List<Product> products;

    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DBProvider dbProvider = new DBProvider(getContext());
        // TODO: change recentViewedProducts to cartProducts
        products = dbProvider.getRecentViewedProducts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        LinearLayout productList = view.findViewById(R.id.productList);
        for (Product product : products) {
            View cartProductView = View.inflate(getContext(), R.layout.cart_product, null);
            ((TextView) cartProductView.findViewById(R.id.name)).setText(product.getName());
            //((TextView) cartProductView.findViewById(R.id.price)).setText(product.getPrice());
            ((ImageView) cartProductView.findViewById(R.id.image)).setImageBitmap(BitmapHelper.getProductImage(product));

            productList.addView(cartProductView);
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public interface OnFragmentInteractionListener {
    }
}
