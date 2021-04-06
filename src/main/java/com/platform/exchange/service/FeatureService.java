package com.platform.exchange.service;

import com.platform.exchange.model.Feature;

import java.util.List;

public interface FeatureService {

    Feature saveFeature(Feature feature);

    void deleteFeature(String uuid);

    Feature updateFeature(Feature feature);

    List<String> getMandatoryFieldsForProduct(ProductType type);
}
