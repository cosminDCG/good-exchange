package com.platform.exchange.model;

import com.platform.exchange.model.product.Product;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "meeting")
public class Meeting implements Serializable {

    @Id
    private UUID id;

    @ManyToOne
    private User seller;

    @ManyToOne
    private User buyer;

    @ManyToOne
    private Product product;

    private Date date;

    private String location;

    private boolean approved;

    public Meeting() {
    }

    public Meeting(UUID id, User seller, User buyer, Date date, String location, boolean approved) {
        this.id = id;
        this.seller = seller;
        this.buyer = buyer;
        this.date = date;
        this.location = location;
        this.approved = approved;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean isApproved() {
        return approved;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        return approved == meeting.approved &&
                Objects.equals(id, meeting.id) &&
                Objects.equals(seller, meeting.seller) &&
                Objects.equals(buyer, meeting.buyer) &&
                Objects.equals(date, meeting.date) &&
                Objects.equals(location, meeting.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, seller, buyer, date, location, approved);
    }
}
