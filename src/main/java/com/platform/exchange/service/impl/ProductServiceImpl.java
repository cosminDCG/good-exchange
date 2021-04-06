package com.platform.exchange.service.impl;

import com.platform.exchange.exception.ErrorMessage;
import com.platform.exchange.exception.product.OutOfProductsException;
import com.platform.exchange.exception.product.ProductNotFoundException;
import com.platform.exchange.model.User;
import com.platform.exchange.model.product.Product;
import com.platform.exchange.repository.ProductRepository;
import com.platform.exchange.service.ProductService;
import com.platform.exchange.validator.product.ProductValidator;
import com.platform.exchange.validator.product.ProductValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional
    public Product saveProduct(Product product) {
        ProductValidator validator = ProductValidatorFactory.getValidator(product.getType());
        validator.validate(product);

        product.setId(UUID.randomUUID());
        product.getFeatures().forEach(feature -> {
            feature.setId(UUID.randomUUID());
            feature.setProduct(product);
        });
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public void deleteProduct(String uuid) {
        Product product = productRepository.findById(UUID.fromString(uuid))
                                           .orElseThrow(() -> new ProductNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND));
        productRepository.delete(product);
    }

    @Override
    @Transactional
    public Product updateProduct(Product product) {
        ProductValidator validator = ProductValidatorFactory.getValidator(product.getType());
        validator.validate(product);

        productRepository.findById(product.getId())
                         .orElseThrow(() -> new ProductNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND));
        return productRepository.save(product);
    }

    @Override
    public Product getProduct(String uuid) {
        return productRepository.findById(UUID.fromString(uuid))
                                .orElseThrow(() -> new ProductNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND));
    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        if (products.size() == 0) {
            throw new OutOfProductsException(ErrorMessage.OUT_OF_PRODUCTS);
        }
        return products;
    }

    @Override
    public List<Product> getProductsByUserUUID(String uuid) {
        User user = new User(UUID.fromString(uuid));
        List<Product> products = productRepository.findAllBySeller(user);
        if (products.size() == 0) {
            throw new OutOfProductsException(ErrorMessage.OUT_OF_PRODUCTS);
        }
        return products;
    }

    @Override
    public List<Product> getAvailableProducts(String uuid) {
        User user = new User(UUID.fromString(uuid));
        List<Product> products = productRepository.findAllByAvailableIsTrueAndSellerIsNot(user);
        if (products.size() == 0) {
            throw new OutOfProductsException(ErrorMessage.OUT_OF_PRODUCTS);
        }
        return products;
    }

    @Override
    public List<Product> getAvailableProductsByUserUUID(String uuid) {
        User user = new User(UUID.fromString(uuid));
        List<Product> products = productRepository.findAllByAvailableIsTrueAndSeller(user);
        if (products.size() == 0) {
            throw new OutOfProductsException(ErrorMessage.OUT_OF_PRODUCTS);
        }
        return products;
    }
}
