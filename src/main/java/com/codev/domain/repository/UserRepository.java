package com.codev.domain.repository;

import com.codev.domain.model.User;

import java.util.List;

public interface UserRepository {

    List<User> findAllUsers();
}
