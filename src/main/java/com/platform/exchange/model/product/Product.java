package com.platform.exchange.model.product;

import com.platform.exchange.model.Feature;
import com.platform.exchange.model.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "product")
public class Product implements Serializable {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private ProductType type;

    private String name;

    private String description;

    @ManyToOne
    private User seller;

    private String address;

    private Double price;

    private Boolean available;

    private String image;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Feature> features;

    public Product() {
    }

    private Product(ProductBuilder builder) {
        this.id = builder.id;
        this.type = builder.type;
        this.name = builder.name;
        this.description = builder.description;
        this.seller = builder.seller;
        this.address = builder.address;
        this.price = builder.price;
        this.available = builder.available;
        this.features = builder.features;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return available == product.available &&
                Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(description, product.description) &&
                Objects.equals(seller, product.seller) &&
                Objects.equals(address, product.address) &&
                Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, seller, address, price, available);
    }

    public static class ProductBuilder {

        private UUID id;
        private ProductType type;
        private String name;
        private String description;
        private String address;
        private User seller;
        private Double price;
        private Boolean available;
        private List<Feature> features;

        public ProductBuilder(ProductType type, String name, Double price) {
            this.type = type;
            this.name = name;
            this.price = price;
        }

        public ProductBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public ProductBuilder withAddress(String address) {
            this.address = address;
            return this;
        }

        public ProductBuilder withSeller(User seller) {
            this.seller = seller;
            return this;
        }

        public ProductBuilder setAvailability(Boolean available) {
            this.available = available;
            return this;
        }

        public ProductBuilder setFeatures(List<Feature> features) {
            this.features = features;
            return this;
        }

        public ProductBuilder addFeature(Feature feature) {
            if (this.features == null) {
                this.features = new ArrayList<>();
            }
            this.features.add(feature);
            return this;
        }

        public ProductBuilder removeFeature(String key) {
            this.features.removeIf(f -> f.getKey().equals(key));
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
