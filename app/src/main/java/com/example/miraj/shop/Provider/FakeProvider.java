package com.example.miraj.shop.Provider;

import com.example.miraj.shop.Model.Category;
import com.example.miraj.shop.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class FakeProvider implements IProductProvider {

    @Override
    public List<Product> getProducts(Category category) {
        if (category.getName().equals("Laptops")) {
            List<Product> products = new ArrayList<>();
            products.add(new Product("Lenovo", 2000));
            products.add(new Product("Asus", 1800));
            products.add(new Product("HP", 1000));
            products.add(new Product("Asfgdf", 3000));
            products.add(new Product("DFgdfg", 4000));
            products.add(new Product("CVbcv", 5000));
            products.add(new Product("AAAA", 6000));
            products.add(new Product("QQQ", 800));
            products.add(new Product("Bvbn", 900));

            return products;
        }
        else return null;
    }
}
