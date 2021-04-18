package com.platform.exchange.service;

import com.platform.exchange.model.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    void deleteUser(String uuid);

    User updateUser(User user);

    User getUser(String uuid);

    List<User> getAllUsers();

    List<User> getAllUsersButMe(String uuid);
}
