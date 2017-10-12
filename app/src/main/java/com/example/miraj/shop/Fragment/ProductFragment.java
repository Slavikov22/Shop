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
import com.example.miraj.shop.Provider.DBProvider;
import com.example.miraj.shop.R;

public class ProductFragment extends Fragment {
    public static final String ARG_PRODUCT = "Product";

    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int TOP = 2;

    private OnFragmentInteractionListener mListener;
    private Product product;

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
        mListener = (OnFragmentInteractionListener) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        product = (Product) getArguments().getSerializable(ARG_PRODUCT);

        DBProvider dbProvider = new DBProvider(getContext());
        dbProvider.addRecentViewedProduct(product);
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
                mListener.prevProduct(product);
            }

            @Override
            public void onSwipeLeft() {
                mListener.nextProduct(product);
            }

            @Override
            public void onSwipeTop() {
                mListener.closeProduct();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        startAnimation();
    }

    public void startAnimation() {
        if (getView() != null) {
            View view = getView();

            AnimationSet set = new AnimationSet(true);
            set.addAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fragment_product_scale));
            set.addAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fragment_product_alpha_up));
            set.setDuration(300);
            view.startAnimation(set);
        }
    }

    public void closeAnimation(int direction) {
        if (getView() != null) {
            View view = getView();

            AnimationSet set = new AnimationSet(true);
            set.addAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fragment_product_alpha_down));
            switch (direction) {
                case LEFT:
                    set.addAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fragment_product_swipe_left));
                    break;
                case RIGHT:
                    set.addAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fragment_product_swipe_right));
                    break;
                case TOP:
                    set.addAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.fragment_product_swipe_top));
                    break;
            }
            set.setDuration(200);
            view.setAnimation(set);
        }
    }

    public interface OnFragmentInteractionListener {
        void nextProduct(Product currentProduct);
        void prevProduct(Product currentProduct);
        void closeProduct();
    }
}
