package com.example.miraj.shop.Fragment;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.miraj.shop.Helper.BitmapHelper;
import com.example.miraj.shop.Listener.OnSwipeTouchListener;
import com.example.miraj.shop.Model.Product;
import com.example.miraj.shop.Provider.DBProvider;
import com.example.miraj.shop.R;
import com.example.miraj.shop.Widget.ZoomImageView;

public class ProductFragment extends Fragment {
    public static final String ARG_PRODUCT = "Product";
    private static final String DOLLAR = "$";

    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int TOP = 2;

    private OnFragmentInteractionListener mListener;
    private Product product;

    MediaPlayer mediaPlayer;

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

        mediaPlayer = MediaPlayer.create(context, R.raw.click);
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.stop();

                if (getView() != null) {
                    Button button = (Button) getView().findViewById(R.id.add_to_cart);
                    button.setEnabled(false);
                    try {
                        mediaPlayer.prepare();
                        mediaPlayer.seekTo(0);
                        button.setEnabled(true);
                    } catch (Throwable t) {}
                }
            }
        });
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
        ((TextView) view.findViewById(R.id.price)).setText(DOLLAR + String.valueOf(product.getPrice()));

        ((ImageView) view.findViewById(R.id.image)).setImageBitmap(BitmapHelper.getProductImage(product));
        view.findViewById(R.id.image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = ZoomImageFragment.newInstance(product);
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack("")
                        .add(R.id.container, fragment)
                        .commit();
            }
        });

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

        view.findViewById(R.id.add_to_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBProvider dbProvider = new DBProvider(getContext());
                dbProvider.addProductToCart(product);
                mediaPlayer.start();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    public interface OnFragmentInteractionListener {
        void nextProduct(Product currentProduct);
        void prevProduct(Product currentProduct);
        void closeProduct();
    }
}
