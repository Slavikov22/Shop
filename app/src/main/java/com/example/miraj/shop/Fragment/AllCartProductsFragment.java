package com.example.miraj.shop.Fragment;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.miraj.shop.Helper.DBHelper;
import com.example.miraj.shop.Model.Product;
import com.example.miraj.shop.Provider.DBProvider;
import com.example.miraj.shop.R;

import org.w3c.dom.Text;

public class AllCartProductsFragment extends Fragment {
    public static final String ARG_COUNTS = "counts";

    private OnFragmentInteractionListener mListener;

    public static AllCartProductsFragment newInstance() {
        return new AllCartProductsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_cart_products, container, false);

        final SQLiteDatabase db = new DBHelper(getContext()).getWritableDatabase();
        Cursor query = db.rawQuery(
                " SELECT category.name, p_name, price, count FROM " +
                        "category INNER JOIN ( " +
                            "SELECT product.name AS p_name, product.price AS price, product.category AS p_category, cart_product.count AS count FROM " +
                            "product INNER JOIN cart_product ON product.id = cart_product.product_id " +
                        ") ON category.name = p_category;",
                null
        );

        LinearLayout productList = view.findViewById(R.id.products);

        if (query.moveToFirst()) {
            do {
                String categoryName = query.getString(0);
                String productName = query.getString(1);
                int productPrice = query.getInt(2);
                int productCount = query.getInt(3);

                View productView = View.inflate(getContext(), R.layout.table_product, null);
                ((TextView) productView.findViewById(R.id.category)).setText(categoryName);
                ((TextView) productView.findViewById(R.id.productName)).setText(productName);
                ((TextView) productView.findViewById(R.id.productCount)).setText(String.valueOf(productCount));
                ((TextView) productView.findViewById(R.id.productPrice)).setText("$" + String.valueOf(productPrice*productCount));

                productList.addView(productView);
            } while (query.moveToNext());
        }

        query.close();

        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.closeAllCartProductFragment();
            }
        });

        view.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBProvider dbProvider = new DBProvider(getContext());
                for (Product product : dbProvider.getCartProducts())
                    dbProvider.removeCartProduct(product);

                mListener.closeAllCartProductFragment();
            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (OnFragmentInteractionListener) context;
    }

    public interface OnFragmentInteractionListener {
        void closeAllCartProductFragment();
    }
}
