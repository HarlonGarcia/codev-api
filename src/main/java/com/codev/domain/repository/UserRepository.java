package com.codev.domain.repository;

import com.codev.domain.dto.form.UserFiltersDTOForm;
import com.codev.domain.model.User;

import java.util.List;

public interface UserRepository {

    List<User> findAllUsers(UserFiltersDTOForm filters);

    User findByUsername(String email);

}
