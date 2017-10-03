package com.example.miraj.shop.Provider;

import com.example.miraj.shop.Model.Category;
import com.example.miraj.shop.Model.Product;

import java.util.List;

public interface IProductProvider {
    public List<Product> getProducts(Category category);
}
