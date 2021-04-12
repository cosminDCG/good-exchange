package com.platform.exchange.service.impl;

import com.google.maps.errors.ApiException;
import com.platform.exchange.model.product.Product;
import com.platform.exchange.repository.ProductRepository;
import com.platform.exchange.service.ProductLocatorService;
import com.platform.exchange.utils.Coordinates;
import com.platform.exchange.utils.PlacesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductLocatorServiceImpl implements ProductLocatorService {

    @Autowired
    private ProductRepository productRepository;

    private final PlacesUtils placesUtils = PlacesUtils.getInstance();
    
    @Override
    public List<Product> getProductsByRange(String userAddress, int range) throws InterruptedException, ApiException, IOException {
        List<Product> products = productRepository.findAll();
        Coordinates userCoordinates = placesUtils.getAddressCoordinate(userAddress);
        if (products.size() == 0) {
            return Collections.emptyList();
        }
        return products.stream().filter(product -> {
            try {
                return productIsInRange(userCoordinates, placesUtils.getAddressCoordinate(product.getAddress()), range);
            } catch (InterruptedException | ApiException | IOException e) {
                e.printStackTrace();
            }
            return false;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Product> getSpecificProductsByRange(String detail, int range) {
        return null;
    }

    private boolean productIsInRange(Coordinates userCoordinates, Coordinates productCoordinates, int range) {
        final int R = 6371;

        double latDistance = Math.toRadians(productCoordinates.getLatitude() - userCoordinates.getLatitude());
        double lonDistance = Math.toRadians(productCoordinates.getLongitude() - userCoordinates.getLongitude());
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(userCoordinates.getLatitude())) * Math.cos(Math.toRadians(productCoordinates.getLatitude()))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;
        return distance < range;
    }
}
