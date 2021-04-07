package com.platform.exchange.controller;

import com.platform.exchange.model.product.Product;
import com.platform.exchange.service.ProductLocatorService;
import com.platform.exchange.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductLocatorService productLocatorService;

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Product>> createProduct(@RequestBody Product product) {
        return Mono.fromCallable(() -> productService.saveProduct(product))
                .subscribeOn(Schedulers.boundedElastic())
                .map(ResponseEntity::ok);
    }

    @GetMapping(path = "/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Product>> getProductById(@PathVariable("productId") String productId) {
        return Mono.fromCallable(() -> productService.getProduct(productId))
                .subscribeOn(Schedulers.boundedElastic())
                .map(ResponseEntity::ok);
    }

    @PutMapping(value = "/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Product>> updateProduct(@PathVariable("productId") String productId,@RequestBody Product product) {
        product.setId(UUID.fromString(productId));
        return Mono.fromCallable(() -> productService.updateProduct(product))
                .subscribeOn(Schedulers.boundedElastic())
                .map(ResponseEntity::ok);
    }

    @DeleteMapping(value = "/{productId}")
    public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable("productId") String productId) {
        return Mono.fromRunnable(() -> productService.deleteProduct(productId))
                .subscribeOn(Schedulers.boundedElastic())
                .thenReturn(ResponseEntity.accepted().build());
    }

    @GetMapping(path = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<Product>>> getAllProducts() {
        return Mono.fromCallable(() -> productService.getAllProducts())
                .subscribeOn(Schedulers.boundedElastic())
                .map(ResponseEntity::ok);
    }

    @GetMapping(path = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<Product>>> getProductsByUserUUID(@PathVariable("userId") String userId) {
        return Mono.fromCallable(() -> productService.getProductsByUserUUID(userId))
                .subscribeOn(Schedulers.boundedElastic())
                .map(ResponseEntity::ok);
    }

    @GetMapping(path = "/available", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<Product>>> getAvailableProducts(@RequestParam String userId) {
        return Mono.fromCallable(() -> productService.getAvailableProducts(userId))
                .subscribeOn(Schedulers.boundedElastic())
                .map(ResponseEntity::ok);
    }

    @GetMapping(path = "/range", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<Product>>> getProductsInRange(@RequestParam String userAddress, @RequestParam int range) {
        return Mono.fromCallable(() -> productLocatorService.getProductsByRange(userAddress, range))
                .subscribeOn(Schedulers.boundedElastic())
                .map(ResponseEntity::ok);
    }

    @GetMapping(path = "/user/{userId}/available", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<List<Product>>> getAvailableProductsByUserUUID(@PathVariable("userId") String userId) {
        return Mono.fromCallable(() -> productService.getAvailableProductsByUserUUID(userId))
                .subscribeOn(Schedulers.boundedElastic())
                .map(ResponseEntity::ok);
    }
}
