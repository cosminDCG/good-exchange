package com.platform.exchange.validator.product;

import com.platform.exchange.exception.ErrorMessage;
import com.platform.exchange.exception.feature.MandatoryFeatureException;
import com.platform.exchange.model.Feature;
import com.platform.exchange.model.product.Product;

import java.util.List;
import java.util.stream.Collectors;

public class RealEstateValidator extends ProductValidator {

    private static final String YEAR = "year";
    private static final String ROOMS = "rooms";
    private static final String PARK_SLOT = "park slot";
    private static final String DIMENSION = "dimension";

    private static final String[] MANDATORY_FEATURES = {YEAR, ROOMS, PARK_SLOT, DIMENSION};

    @Override
    public void validate(Product product) {
        validatePrice(product);
        validateDescription(product);
        validateMandatoryFeatures(product);
    }

    private void validateMandatoryFeatures(Product product) {
        List<String> keys = product.getFeatures().stream().map(Feature::getKey).collect(Collectors.toList());
        for (String feature: MANDATORY_FEATURES) {
            if (!keys.contains(feature)) {
                throw new MandatoryFeatureException(ErrorMessage.MANDATORY_FEATURE_NOT_FOUND, feature);
            }
        }
    }
}
