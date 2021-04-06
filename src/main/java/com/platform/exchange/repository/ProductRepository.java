package com.platform.exchange.repository;

import com.platform.exchange.model.User;
import com.platform.exchange.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    List<Product> findAllBySeller(User user);

    List<Product> findAllByAvailableIsTrueAndSellerIsNot(User user);

    List<Product> findAllByAvailableIsTrueAndSeller(User user);
}
