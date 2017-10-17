package com.example.miraj.shop.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.miraj.shop.Helper.BitmapHelper;
import com.example.miraj.shop.Listener.OnSwipeTouchListener;
import com.example.miraj.shop.Model.Product;
import com.example.miraj.shop.R;
import com.example.miraj.shop.Widget.ZoomImageView;

public class ZoomImageFragment extends Fragment {
    private static final String ARG_PRODUCT = "product";

    private Product product;

    private OnFragmentInteractionListener mListener;

    public static ZoomImageFragment newInstance(Product product) {
        ZoomImageFragment fragment = new ZoomImageFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PRODUCT, product);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        product = (Product) getArguments().getSerializable(ARG_PRODUCT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zoom_image, container, false);

        ((ZoomImageView) view.findViewById(R.id.image)).setImageBitmap(BitmapHelper.getProductImage(product));

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //mListener = (OnFragmentInteractionListener) context;
    }

    public interface OnFragmentInteractionListener {
    }
}
