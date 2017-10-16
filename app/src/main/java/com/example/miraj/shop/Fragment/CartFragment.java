package com.example.miraj.shop.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        products = dbProvider.getCartProducts();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        final DBProvider dbProvider = new DBProvider(getContext());

        LinearLayout productList = view.findViewById(R.id.productList);
        for (final Product product : products) {
            final View cartProductView = View.inflate(getContext(), R.layout.cart_product, null);
            ((TextView) cartProductView.findViewById(R.id.name)).setText(product.getName());
            ((TextView) cartProductView.findViewById(R.id.price)).setText("$" + String.valueOf(product.getPrice()));
            ((ImageView) cartProductView.findViewById(R.id.image)).setImageBitmap(BitmapHelper.getProductImage(product));

            invalidateCount(cartProductView, product);

            cartProductView.findViewById(R.id.dec).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int count = dbProvider.getCartProductCount(product);
                    if (count == 1) return;
                    dbProvider.updateCartProductCount(product, count - 1);
                    invalidateCount(cartProductView, product);
                }
            });

            cartProductView.findViewById(R.id.inc).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int count = dbProvider.getCartProductCount(product);
                    if (count == 99) return;
                    dbProvider.updateCartProductCount(product, count + 1);
                    invalidateCount(cartProductView, product);
                }
            });

            productList.addView(cartProductView);
        }
        return view;
    }

    public void invalidateCount(View cartProductView, Product product) {
        DBProvider dbProvider = new DBProvider(getContext());

        int count = dbProvider.getCartProductCount(product);
        ((TextView) cartProductView.findViewById(R.id.count)).setText(String.valueOf(count));
        ((TextView) cartProductView.findViewById(R.id.totalPrice))
                .setText("$" + String.valueOf(product.getPrice() * count));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public interface OnFragmentInteractionListener {
    }
}
