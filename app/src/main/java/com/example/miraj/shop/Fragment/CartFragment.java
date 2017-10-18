package com.example.miraj.shop.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.miraj.shop.Helper.BitmapHelper;
import com.example.miraj.shop.Model.Product;
import com.example.miraj.shop.Provider.DBProvider;
import com.example.miraj.shop.R;
import com.example.miraj.shop.Widget.ClickButton;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private List<Product> products;
    private Map<Product, View> views;

    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        ((ClickButton) view.findViewById(R.id.buy)).setOnMyClickListener(new ClickButton.OnMyClickListener() {
            @Override
            public void OnMyClick(View view) {
                for (Product product : products) {
                    View v = views.get(product);
                    if (((CheckBox) v.findViewById(R.id.selected)).isChecked()) {
                        new DBProvider(getContext()).removeCartProduct(product);

                        if (getView() != null) {
                            ((LinearLayout) getView().findViewById(R.id.productList)).removeView(v);
                        }
                    }
                }

                updateProducts();
                updateViews();
                invalidatePrices();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateProducts();
        updateViews();
        invalidatePrices();
    }

    public void updateProducts() {
        products = new DBProvider(getContext()).getCartProducts();
    }

    public void updateViews() {
        views = new HashMap<>();

        if (getView() != null) {
            View view = getView();

            final DBProvider dbProvider = new DBProvider(getContext());

            final LinearLayout productList = view.findViewById(R.id.productList);
            productList.removeAllViews();

            for (final Product product : products) {
                final View cartProductView = View.inflate(getContext(), R.layout.cart_product, null);
                views.put(product, cartProductView);

                ((TextView) cartProductView.findViewById(R.id.name)).setText(product.getName());
                ((TextView) cartProductView.findViewById(R.id.price)).setText("$" + String.valueOf(product.getPrice()));
                ((ImageView) cartProductView.findViewById(R.id.image)).setImageBitmap(BitmapHelper.getProductImage(product));
                ((TextView) cartProductView.findViewById(R.id.count)).setText(String.valueOf(dbProvider.getCartProductCount(product)));

                cartProductView.findViewById(R.id.dec).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int count = dbProvider.getCartProductCount(product);
                        if (count == 1) return;
                        dbProvider.updateCartProductCount(product, count - 1);
                        ((TextView) cartProductView.findViewById(R.id.count))
                                .setText(String.valueOf(dbProvider.getCartProductCount(product)));
                        invalidatePrices();
                    }
                });

                cartProductView.findViewById(R.id.inc).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int count = dbProvider.getCartProductCount(product);
                        if (count == 99) return;
                        dbProvider.updateCartProductCount(product, count + 1);
                        ((TextView) cartProductView.findViewById(R.id.count))
                                .setText(String.valueOf(dbProvider.getCartProductCount(product)));
                        invalidatePrices();
                    }
                });

                cartProductView.findViewById(R.id.selected).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        invalidatePrices();
                    }
                });

                productList.addView(cartProductView);
            }
        }
    }

    public void invalidatePrices() {
        DBProvider dbProvider = new DBProvider(getContext());

        int totalCartPrice = 0;
        for (Product product: products) {
            View view = views.get(product);

            int totalPrice = product.getPrice() * dbProvider.getCartProductCount(product);

            ((TextView) view.findViewById(R.id.totalPrice))
                    .setText("$" + String.valueOf(totalPrice));

            if (((CheckBox)(view.findViewById(R.id.selected))).isChecked())
                totalCartPrice += totalPrice;
        }

        if (getView() != null)
            ((TextView) getView().findViewById(R.id.totalCartPrice))
                    .setText("$" + String.valueOf(totalCartPrice));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public interface OnFragmentInteractionListener {
    }
}
