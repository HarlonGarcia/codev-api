package com.codev.domain.repository;

import com.codev.domain.dto.form.UserFiltersDTOForm;
import com.codev.domain.model.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository {

    List<User> findAllUsers(UserFiltersDTOForm filters);

    User findByEmail(String email);

    User findById(UUID userId);

    void createUser(User user);

}
