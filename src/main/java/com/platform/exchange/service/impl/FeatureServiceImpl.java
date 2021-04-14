package com.platform.exchange.service.impl;

import com.platform.exchange.exception.ErrorMessage;
import com.platform.exchange.exception.feature.FeatureNotFoundException;
import com.platform.exchange.model.Feature;
import com.platform.exchange.model.product.ProductType;
import com.platform.exchange.repository.FeatureRepository;
import com.platform.exchange.service.FeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class FeatureServiceImpl implements FeatureService {

    private static final String BRAND = "brand";
    private static final String YEAR = "year";
    private static final String MODEL = "model";
    private static final String KILOMETERS = "kilometers";
    private static final String FUEL = "fuel";
    private static final String CUBIC_CAPACITY = "cubic capacity";
    private static final String ROOMS = "rooms";
    private static final String PARK_SLOT = "park slot";
    private static final String DIMENSION = "dimension";

    @Autowired
    private FeatureRepository featureRepository;

    @Override
    @Transactional
    public Feature saveFeature(Feature feature) {
        feature.setId(UUID.randomUUID());
        return featureRepository.save(feature);
    }

    @Override
    @Transactional
    public void deleteFeature(String uuid) {
        Feature feature = featureRepository.findById(UUID.fromString(uuid))
                                           .orElseThrow(() -> new FeatureNotFoundException(ErrorMessage.FEATURE_NOT_FOUND));
        featureRepository.delete(feature);
    }

    @Override
    @Transactional
    public Feature updateFeature(Feature feature) {
        featureRepository.findById(feature.getId())
                         .orElseThrow(() -> new FeatureNotFoundException(ErrorMessage.FEATURE_NOT_FOUND));
        return featureRepository.save(feature);
    }

    @Override
    public List<String> getMandatoryFieldsForProduct(ProductType type) {
        switch (type) {
            case OTHER:
                return Collections.emptyList();
            case AUTO:
                return Arrays.asList(YEAR, BRAND, MODEL, KILOMETERS, FUEL, CUBIC_CAPACITY);
            case REAL_ESTATE:
                return Arrays.asList(YEAR, ROOMS, PARK_SLOT, DIMENSION);
            default:
                return null;
        }
    }
}
