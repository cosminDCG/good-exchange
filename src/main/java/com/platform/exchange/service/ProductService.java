package com.platform.exchange.service;

import com.platform.exchange.model.product.Product;

import java.util.List;

public interface ProductService {

    Product saveProduct(Product product);

    void deleteProduct(String uuid);

    Product updateProduct(Product product);

    Product getProduct(String uuid);

    List<Product> getAllProducts();

    List<Product> getProductsByUserUUID(String uuid);

    List<Product> getAvailableProducts(String uuid);

    List<Product> getAvailableProductsByUserUUID(String uuid);
}
