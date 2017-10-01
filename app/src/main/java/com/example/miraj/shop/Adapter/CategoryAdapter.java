package com.example.miraj.shop.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.miraj.shop.Model.Category;
import com.example.miraj.shop.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class CategoryAdapter extends ArrayAdapter {
    private Context context;
    private LayoutInflater inflater;
    private int layout;
    private List<Category> categories;

    public CategoryAdapter(Context context, int resource, List<Category> categories) {
        super(context, resource, categories);

        this.context = context;
        this.categories = categories;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(this.layout, parent, false);

        ImageView imageView = view.findViewById(R.id.image);
        TextView nameView = view.findViewById(R.id.name);

        Category category = categories.get(position);

        nameView.setText(category.getName());

        InputStream inputStream = null;
        try{
            inputStream = this.context.getAssets().open("categories/" + category.getName() + ".png");
            Drawable d = Drawable.createFromStream(inputStream, null);
            imageView.setImageDrawable(d);
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

        return view;
    }
}
