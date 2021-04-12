package com.platform.exchange.service;

import com.google.maps.errors.ApiException;
import com.platform.exchange.model.product.Product;

import java.io.IOException;
import java.util.List;

public interface ProductLocatorService {

    List<Product> getProductsByRange(String userAddress, int range) throws InterruptedException, ApiException, IOException;

    List<Product> getSpecificProductsByRange(String detail, int range);
}
