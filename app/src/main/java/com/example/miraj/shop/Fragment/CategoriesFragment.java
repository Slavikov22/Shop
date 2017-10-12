package com.example.miraj.shop.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.miraj.shop.Adapter.CategoryAdapter;
import com.example.miraj.shop.Model.Category;
import com.example.miraj.shop.Provider.DBProvider;
import com.example.miraj.shop.Provider.FakeProvider;
import com.example.miraj.shop.Provider.IProductProvider;
import com.example.miraj.shop.R;

import java.util.List;

public class CategoriesFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    private List<Category> categories;

    public static CategoriesFragment newInstance() {
        return new CategoriesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DBProvider dbProvider = new DBProvider(getContext());
        IProductProvider provider = new FakeProvider(getContext());
        categories = provider.getCategories();
        for (Category category : categories)
            dbProvider.addCategory(category);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup c,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, c, false);

        ListView container = (ListView) view;
        container.setAdapter(new CategoryAdapter(getContext(), R.layout.list_category, categories));
        container.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mListener.showCategory(categories.get(i));
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
        void showCategory(Category category);
    }
}
