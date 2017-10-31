package com.example.miraj.shop.Provider;

import android.os.AsyncTask;

import com.example.miraj.shop.Helper.StreamHelper;
import com.example.miraj.shop.Model.Category;
import com.example.miraj.shop.Model.Product;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class ProductProvider {
    public static final String SHOP_URL = "http://192.168.43.34/shop/";
    private static final String METHOD = "GET";

    public static Product getProduct(int id) {
        return null;
    }

    public static ArrayList<Product> getProducts() {
        GetProductsTask task = new GetProductsTask();
        task.execute(SHOP_URL);
        try {
            return task.get();
        }
        catch (ExecutionException e) {return null;}
        catch (InterruptedException e) {return null;}
    }

    public static ArrayList<Product> getProducts(Category category) {
        ArrayList<Product> products = new ArrayList<>();
        ArrayList<Product> allProducts = getProducts();
        if (allProducts != null) {
            for (Product product : getProducts())
                if (product.getCategory().equals(category.getName()))
                    products.add(product);
            return products;
        }
        else return null;
    }

    private static class GetProductsTask extends AsyncTask<String, Void, ArrayList<Product>> {
        @Override
        protected ArrayList<Product> doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod(METHOD);
                c.setConnectTimeout(500);
                c.connect();

                String json = StreamHelper.convertInputStreamToString(c.getInputStream());
                return new ArrayList<>(Arrays.asList(new Gson().fromJson(json, Product[].class)));
            }
            catch (IOException e) {return null;}
        }
    }
}
