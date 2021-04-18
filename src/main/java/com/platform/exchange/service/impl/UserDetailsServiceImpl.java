package com.platform.exchange.service.impl;

import com.platform.exchange.exception.ErrorMessage;
import com.platform.exchange.exception.user.UserNotFoundException;
import com.platform.exchange.model.User;
import com.platform.exchange.repository.UserRepository;
import com.platform.exchange.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(ErrorMessage.USER_NOT_FOUND));
        CustomUserDetails customUserDetails = new CustomUserDetails(user.getEmail(), user.getPassword(), emptyList());
        customUserDetails.setId(user.getId().toString());
        customUserDetails.setFirstName(user.getFirstName());
        customUserDetails.setLastName(user.getLastName());
        customUserDetails.setAddress(user.getAddress());
        customUserDetails.setPhone(user.getPhone());
        return customUserDetails;
    }
}
