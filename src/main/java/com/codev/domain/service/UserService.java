package com.codev.domain.service;

import com.codev.domain.model.User;
import com.codev.domain.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    public List<User> findAllUsers() {
        return userRepository.findAllUsers();
    }
}
