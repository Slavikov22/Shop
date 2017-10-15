package com.example.miraj.shop.Helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.example.miraj.shop.Model.Category;
import com.example.miraj.shop.Model.Product;
import com.example.miraj.shop.Provider.ProductProvider;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

public class BitmapHelper {
    private static final String IMAGES_SUFFIX = "images/";
    private static final String IMG_FORMAT = ".jpg";
    private static final String METHOD = "GET";

    public static Bitmap getProductImage(Product product) {
        GetProductImageTask task = new GetProductImageTask();
        task.execute(product);
        try {
            return task.get();
        }
        catch (ExecutionException e) {return null;}
        catch (InterruptedException e) {return null;}
    }

    public static Bitmap getCategoryImage(Context context, Category category) {
        InputStream inputStream = null;
        Bitmap bitmap = null;

        try{
            inputStream = context.getAssets().open("categories/" + category.getName() + ".png");
            bitmap = BitmapFactory.decodeStream(inputStream);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                if(inputStream!=null)
                    inputStream.close();
            }
            catch (IOException ex){
                ex.printStackTrace();
            }
        }

        return bitmap;
    }

    private static class GetProductImageTask extends AsyncTask<Product, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(Product... products) {
            Product product = products[0];
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 1;

            try {
                URL url = new URL(
                        ProductProvider.SHOP_URL + IMAGES_SUFFIX + product.getName() + IMG_FORMAT);
                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod(METHOD);
                c.connect();

                int a = c.getResponseCode();

                InputStream is = c.getInputStream();
                return BitmapFactory.decodeStream(is, null, options);
            }
            catch (IOException e) {return null;}
        }
    }
}
