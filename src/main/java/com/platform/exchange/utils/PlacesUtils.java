package com.platform.exchange.utils;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

import java.io.IOException;

public final class PlacesUtils {

    private static PlacesUtils INSTANCE;

    public static final String PLACES_API_KEY = "PLACES_API_KEY";

    private PlacesUtils() {}

    public static PlacesUtils getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PlacesUtils();
        }
        return INSTANCE;
    }

    public Coordinates getAddressCoordinate(String address) throws InterruptedException, ApiException, IOException {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(getPlacesApiKey())
                .build();
        GeocodingResult[] results =  GeocodingApi.geocode(context,
                address).await();

        return new Coordinates(results[0].geometry.location.lat, results[0].geometry.location.lat);
    }

    private String getPlacesApiKey() {
        return System.getenv(PLACES_API_KEY);
    }
}
