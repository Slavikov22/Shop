package com.example.miraj.shop.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.miraj.shop.Listener.OnSwipeTouchListener;
import com.example.miraj.shop.Model.Product;
import com.example.miraj.shop.R;

public class ProductFragment extends Fragment{
    private static final String ARG_PRODUCT = "Product";

    OnProductEventListener productEventListener;
    Product product;

    public static ProductFragment newInstance(Product product) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PRODUCT, product);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        productEventListener = (OnProductEventListener) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        product = (Product) getArguments().getSerializable(ARG_PRODUCT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        ((TextView) view.findViewById(R.id.name)).setText(product.getName());
        ((TextView) view.findViewById(R.id.description)).setText(product.getDescription());
        ((TextView) view.findViewById(R.id.price)).setText(String.valueOf(product.getPrice()));
        ((ImageView) view.findViewById(R.id.image)).setImageBitmap(product.getImage());

        view.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            @Override
            public void onSwipeRight() {
                productEventListener.prevProduct(product);
            }

            @Override
            public void onSwipeLeft() {
                productEventListener.nextProduct(product);
            }

            @Override
            public void onSwipeTop() {
                productEventListener.closeProduct();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (getView() != null) {
            View view = getView();

            AnimationSet set = new AnimationSet(true);
            set.addAnimation(AnimationUtils.loadAnimation(this.getContext(), R.anim.fragment_product_scale));
            set.addAnimation(AnimationUtils.loadAnimation(this.getContext(), R.anim.fragment_product_alpha_up));
            set.setDuration(300);
            view.startAnimation(set);
        }
    }

    public interface OnProductEventListener {
        void nextProduct(Product currentProduct);
        void prevProduct(Product currentProduct);
        void closeProduct();
    }
}
