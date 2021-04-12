package com.platform.exchange.controller;

import com.platform.exchange.model.Feature;
import com.platform.exchange.model.product.ProductType;
import com.platform.exchange.service.FeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/features")
public class FeatureController {

    @Autowired
    private FeatureService featureService;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Feature>> createFeature(@RequestBody Feature feature) {
        return Mono.fromCallable(() -> featureService.saveFeature(feature))
                .subscribeOn(Schedulers.boundedElastic())
                .map(ResponseEntity::ok);
    }

    @PutMapping(value = "/{featureId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Feature>> updateFeature(@PathVariable("featureId") String featureId, @RequestBody Feature feature) {
        feature.setId(UUID.fromString(featureId));
        return Mono.fromCallable(() -> featureService.updateFeature(feature))
                .subscribeOn(Schedulers.boundedElastic())
                .map(ResponseEntity::ok);
    }

    @DeleteMapping(value = "/{featureId}")
    public Mono<ResponseEntity<Void>> deleteFeature(@PathVariable("featureId") String featureId) {
        return Mono.fromRunnable(() -> featureService.deleteFeature(featureId))
                .subscribeOn(Schedulers.boundedElastic())
                .thenReturn(ResponseEntity.accepted().build());
    }

    @GetMapping(path = "/mandatory")
    public Mono<ResponseEntity<List<String>>> getMandatoryFields(@RequestParam String type) {
        return Mono.fromCallable(() -> featureService.getMandatoryFieldsForProduct(ProductType.fromText(type)))
                .subscribeOn(Schedulers.boundedElastic())
                .map(ResponseEntity::ok);
    }
}
