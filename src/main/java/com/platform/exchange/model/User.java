package com.platform.exchange.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.platform.exchange.security.CustomUserDetails;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    private UUID id;

    private String firstName;

    private String lastName;

    private String address;

    private String phone;

    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    public User() {
    }

    public User(UUID id) {
        this.id = id;
    }

    public User(UUID id, String firstName, String lastName, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public User(CustomUserDetails customUserDetails) {
        this.id = UUID.fromString(customUserDetails.getId());
        this.firstName = customUserDetails.getFirstName();
        this.lastName = customUserDetails.getLastName();
        this.email = customUserDetails.getAddress();
        this.address = customUserDetails.getAddress();
        this.phone = customUserDetails.getPhone();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o ) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
               Objects.equals(firstName, user.firstName) &&
               Objects.equals(lastName, user.lastName) &&
               Objects.equals(address, user.getAddress()) &&
               Objects.equals(phone, user.getPhone()) &&
               Objects.equals(email, user.email) &&
               Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }
}
